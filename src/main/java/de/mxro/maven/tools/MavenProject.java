package de.mxro.maven.tools;

import static org.joox.JOOX.$;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.joox.JOOX;
import org.joox.Match;
import org.w3c.dom.Document;

import de.mxro.file.FileItem;
import de.mxro.file.Jre.FilesJre;
import de.mxro.javafileutils.Collect;
import de.mxro.javafileutils.Collect.LeafCheck;
import de.mxro.process.Spawn;

/**
 * Utility methods for working with Maven projects.
 * 
 * @author <a href="http://www.mxro.de">Max Rohde</a>
 *
 */
public class MavenProject {
    /**
     * Checks if a directory contains a pom.xml file.
     * 
     * @param directory
     * @return
     */
    public static boolean isMavenProject(final File directory) {
        for (final File child : directory.listFiles()) {
            if (child.getName().equals("pom.xml")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Takes a list of Maven project directories (with a pom.xml file) and
     * orders it in a way that dependent projects come before projects dependent
     * on them.
     * 
     * @param directories
     * @param buildOrder
     * @return
     */
    public static List<File> orderDirectoriesByBuildOrder(final List<File> directories,
            final List<Dependency> buildOrder) {
        return orderDirectoriesByBuildOrder(directories, buildOrder, true);
    }

    public static List<File> getDependendProjectsInCorrectOrder(final List<File> directories,
            final List<Dependency> buildOrder) {
        return orderDirectoriesByBuildOrder(directories, buildOrder, false);
    }

    public static boolean buildSuccessful(final String mavenOutput) {
        if (mavenOutput.contains("BUILD FAILURE") || !mavenOutput.contains("BUILD SUCCESS")) {
            return false;
        } else {
            return true;
        }
    }

    public static List<Dependency> dependencyBuildOrder(final File project) {
        final List<Dependency> res = new ArrayList<Dependency>(100);

        final String dependencyOutput = Spawn.runBashCommand("mvn dependency:tree -o | tail -n 10000", project);

        final Pattern pattern = Pattern.compile("- ([^:]*):([^:]*):([^:]*):([^:]*):");

        final Matcher matcher = pattern.matcher(dependencyOutput);

        while (matcher.find()) {

            if (matcher.groupCount() == 4) {

                final String groupId = matcher.group(1);
                final String artifactId = matcher.group(2);
                final String version = matcher.group(4);

                final Dependency d = new Dependency() {

                    @Override
                    public String groupId() {
                        return groupId;
                    }

                    @Override
                    public String artifactId() {
                        return artifactId;
                    }

                    @Override
                    public String version() {
                        return version;
                    }

                };
                res.add(d);
            }

        }

        Collections.reverse(res);

        return res;

    }

    private static void replaceVersion(final File pomFile, final String newVersion) {
        try {
            final List<String> lines = new ArrayList<String>();

            final BufferedReader in = new BufferedReader(new FileReader(pomFile));
            String line = in.readLine();
            boolean found = false;
            while (line != null) {

                if (!found && line.contains("<version>")) {
                    lines.add("    <version>" + newVersion + "</version>");
                    found = true;
                } else {
                    lines.add(line);
                }
                line = in.readLine();
            }
            in.close();

            final PrintWriter out = new PrintWriter(pomFile);
            for (final String l : lines) {
                out.println(l);
            }
            out.close();
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    private static Dependency retrieveDependency(final File pomFile) {
        try {
            final byte[] pomBytes = Files.readAllBytes(FileSystems.getDefault().getPath(pomFile.getAbsolutePath()));

            final String pom = new String(pomBytes, "UTF-8");

            final String groupId;
            final String artifactId;
            final String version;
            {
                final Pattern pattern = Pattern.compile("<groupId>([^<]*)</groupId>");
                final Matcher matcher = pattern.matcher(pom);
                matcher.find();
                groupId = matcher.group(1);
            }

            {
                final Pattern pattern = Pattern.compile("<artifactId>([^<]*)</artifactId>");
                final Matcher matcher = pattern.matcher(pom);
                matcher.find();
                artifactId = matcher.group(1);
            }

            {
                final Pattern pattern = Pattern.compile("<version>([^<]*)</version>");
                final Matcher matcher = pattern.matcher(pom);
                matcher.find();
                version = matcher.group(1);
            }

            return new Dependency() {

                @Override
                public String version() {
                    return version;
                }

                @Override
                public String groupId() {
                    return groupId;
                }

                @Override
                public String artifactId() {
                    return artifactId;
                }
            };
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public static String getVersion(final File projectDir) {
        for (final File file : projectDir.listFiles()) {
            if (file.getName().equals("pom.xml")) {
                return retrieveDependency(file).version();
            }
        }
        throw new RuntimeException("No pom.xml found in project dir " + projectDir);

    }

    public static Dependency getMavenDependency(final File projectDir) {
        for (final File file : projectDir.listFiles()) {
            if (file.getName().equals("pom.xml")) {
                return retrieveDependency(file);
            }
        }
        throw new RuntimeException("No pom.xml found in project dir " + projectDir);
    }

    public static void setVersion(final File projectDir, final String version) {

        for (final File file : projectDir.listFiles()) {
            if (file.getName().equals("pom.xml")) {
                replaceVersion(file, version);
                return;
            }
        }
        throw new RuntimeException("No pom.xml found in project dir " + projectDir);
    }

    public static List<File> getProjects(final File rootDir) {
        return Collect.getLeafDirectoriesRecursively(rootDir, new LeafCheck() {

            @Override
            public boolean isLeaf(final File f) {
                if (!f.isDirectory()) {
                    return false;
                }

                return f.listFiles(new FileFilter() {

                    @Override
                    public boolean accept(final File pathname) {
                        return pathname.getName().equals("pom.xml");
                    }
                }).length > 0;
            }

        });
    }

    /**
     * 
     * <p>
     * Replace a dependency for a maven project.
     * <p>
     * If properties of the provided oldDependency are <code>null</code>, any
     * dependency of the project will be matched.
     * <p>
     * If properties of newDependency are <code>null</code>, the specific
     * property will not be replaced.
     * 
     * @param projectDir
     * @param oldDependency
     * @param newDependency
     * @return
     */
    public static boolean replaceDependency(final File projectDir, final Dependency oldDependency,
            final Dependency newDependency) {

        final FileItem pom = FilesJre.wrap(projectDir).get("pom.xml");

        if (!pom.exists()) {
            throw new IllegalArgumentException("Specified directory does not contain a pom.xml file: " + projectDir);
        }

        Document document;
        try {
            document = $(JOOX.builder().parse(new File(pom.getPath()))).document();
        } catch (final Exception e2) {
            throw new RuntimeException(e2);
        }

        final Match root = $(document);
        final Match project = root.first();

        if (project.size() != 1) {
            throw new RuntimeException("Illegal pom [" + pom + "]. Element project cannot be found.");
        }

        final Match dependencies = project.child("dependencies");

        if (dependencies.size() == 0) {
            return false;
        }

        // if (dependencies.size() != 1) {
        // throw new RuntimeException("Illegal pom [" + pom + "]");
        // }

        // System.out.println(dependencies);

        final Match children = dependencies.children("dependency");

        // System.out.println(children);

        boolean changed = false;
        for (final org.w3c.dom.Element e : children) {

            final String groupId = $(e).child("groupId").content();
            final String artifactId = $(e).child("artifactId").content();
            final String version = $(e).child("version").content();

            if (oldDependency.groupId() != null && !oldDependency.groupId().equals(groupId)) {
                continue;
            }

            if (oldDependency.artifactId() != null && !oldDependency.artifactId().equals(artifactId)) {
                continue;
            }

            if (oldDependency.version() != null && !oldDependency.version().equals(version)) {
                continue;
            }

            if (newDependency.version() != null) {
                $(e).child("version").content(newDependency.version());
                changed = true;
            }

            if (newDependency.groupId() != null) {
                $(e).child("groupId").content(newDependency.groupId());
                changed = true;
            }

            if (newDependency.artifactId() != null) {
                $(e).child("artifactId").content(newDependency.artifactId());
                changed = true;
            }

        }

        if (changed) {
            try {
                final TransformerFactory tf = TransformerFactory.newInstance();
                final Transformer transformer = tf.newTransformer();
                // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
                // "yes");
                final StringWriter writer = new StringWriter();

                transformer.transform(new DOMSource(document), new StreamResult(writer));

                final String output = writer.getBuffer().toString();

                // System.out.println("Defining new pom\n");
                // System.out.println(output);
                pom.setText(output);
            } catch (final TransformerException e1) {
                throw new RuntimeException(e1);
            }
        }

        return changed;

    }

    private static List<File> orderDirectoriesByBuildOrder(final List<File> directories,
            final List<Dependency> buildOrder, final boolean addOthers) {

        final List<File> res = new ArrayList<File>(directories.size());
        final List<File> unprocessed = new LinkedList<File>(directories);

        final Map<String, Dependency> map = new HashMap<String, Dependency>();

        for (final Dependency d : buildOrder) {
            map.put(d.artifactId(), d);

        }

        for (final File f : directories) {

            final Dependency match = map.get(f.getName());

            if (match == null) {

                continue;
            }

            unprocessed.remove(f);
            res.add(f);

        }

        // System.out.println(unprocessed.size());
        if (addOthers) {
            res.addAll(unprocessed);
        }
        return res;

    }
}
