package de.mxro.maven.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.mxro.process.Spawn;

/**
 * Utility methods for working with Maven remote repositories.
 * 
 * @author <a href="http://www.mxro.de">Max Rohde</a>
 *
 */
public class MavenRemoteRepository {

    /**
     * <p>
     * Downloads the <code>maven-metadata.xml</code> from a remote repository.
     * 
     * @param repositoryUrl
     * @param destFolder
     * @param groupId
     * @param artifactId
     * @return true if xml file could be downloaded, false otherwise.
     * @throws IOException
     */
    public static boolean downloadRepositoryXml(final String repositoryUrl, final Path destFolder,
            final String groupId, final String artifactId) throws IOException {

        if (Files.exists(destFolder.resolve("maven-metadata.xml"))) {
            throw new RuntimeException("maven-metadata.xml file already existed in folder: " + destFolder);
        }

        final String path = repositoryUrl + groupId.replaceAll("\\.", "/") + "/" + artifactId + "/maven-metadata.xml";
        final String output = Spawn.runBashCommand("wget " + path, destFolder.toFile());

        if (output.contains("ERROR 404") || output.contains("Not Found")) {

            return;
        }

        if (output.contains("ERROR")) {
            throw new RuntimeException("Error while downloading repository index file from: " + path);
        }

    }

    public static void createRepository(final String repositoryUrl, final Path destFolder, final String groupId,
            final String artifactId) throws IOException {
        final Path file = Files.createFile(destFolder.resolve("maven-metadata.xml"));

        String xml = "";
        xml += "<metadata>\n";
        xml += "  <groupId>" + groupId + "</groupId>\n";
        xml += "  <artifactId>" + artifactId + "</artifactId>\n";
        xml += "  <versioning>\n";
        xml += "    <release>0.0.0</release>\n";
        xml += "    <versions>\n";
        xml += "    </versions>\n";
        xml += "    <lastUpdated>00000</lastUpdated>\n";
        xml += "  </versioning>\n";
        xml += "</metadata>";

        Files.write(file, xml.getBytes("UTF-8"));
    }

    public static void assertVersionInRepositoryXml(final Path destFolder, final String newVersion) throws Exception {

        final Path mavenMetadata = destFolder.resolve("maven-metadata.xml");

        final byte[] mavenMetadataBytes = Files.readAllBytes(mavenMetadata);

        String mavenMetadataString = new String(mavenMetadataBytes, "UTF-8");

        if (!mavenMetadataString.contains(newVersion)) {
            final int versionsEndTagIndex = mavenMetadataString.indexOf("</versions>");
            mavenMetadataString = mavenMetadataString.substring(0, versionsEndTagIndex) + "\n         <version>"
                    + newVersion + "</version>\n" + mavenMetadataString.substring(versionsEndTagIndex);
        }

        final String lastChanged = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        mavenMetadataString = mavenMetadataString.replaceAll("<lastUpdated>[^<]*</lastUpdated>", "<lastUpdated>"
                + lastChanged + "</lastUpdated>");

        mavenMetadataString = mavenMetadataString.replaceAll("<release>[^<]*</release>", "<release>" + newVersion
                + "</release>");

        Files.write(mavenMetadata, mavenMetadataString.getBytes("UTF-8"));

    }
}
