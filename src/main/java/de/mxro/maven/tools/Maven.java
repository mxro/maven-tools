package de.mxro.maven.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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

import de.mxro.process.Spawn;

public class Maven {

    public static void main(final String[] args) {

    }

    public static abstract class Dependency {
        public abstract String groupId();

        public abstract String artifactId();

        public abstract String version();

        @Override
        public String toString() {
            return "(" + groupId() + ":" + artifactId() + ":" + version() + ")";
        }

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

    public static boolean isMavenProject(final File directory) {
        for (final File child : directory.listFiles()) {
            if (child.getName().equals("pom.xml")) {
                return true;
            }
        }
        return false;
    }

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
}
