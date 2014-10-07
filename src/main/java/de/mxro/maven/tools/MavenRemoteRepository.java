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

    public static void downloadOrCreateRepositoryXml(final String repositoryUrl, final Path destFolder,
            final String groupId, final String artifactId) throws IOException {

        if (Files.exists(destFolder.resolve("maven-metadata.xml"))) {
            throw new RuntimeException("maven-metadata.xml file already existed in folder: " + destFolder);
        }

        final String path = repositoryUrl + groupId.replaceAll("\\.", "/") + "/" + artifactId + "/maven-metadata.xml";
        final String output = Spawn.runBashCommand("wget " + path, destFolder.toFile());
        System.out.println(output);

        if (output.contains("ERROR 404") || output.contains("Not Found")) {
            final Path file = Files.createFile(destFolder.resolve("maven-metadata.xml"));
            return;
        }

        if (output.contains("ERROR")) {
            throw new RuntimeException("Error while downloading repository index file from: " + path);
        }

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
