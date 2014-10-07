package de.mxro.maven.tools;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.mxro.maven.tools.deploy.DeploymentParameters;
import de.mxro.maven.tools.deploy.DeploymentParametersBuilder;
import de.mxro.process.Spawn;

/**
 * Utility methods for working with Maven remote repositories.
 * 
 * @author <a href="http://www.mxro.de">Max Rohde</a>
 *
 */
public class MavenRemoteRepository {

    public static void downloadRepositoryXml(final String repositoryUrl, final Path destFolder, final String groupId,
            final String artifactId) {

        if (Files.exists(destFolder.resolve("maven-metadata.xml"))) {
            throw new RuntimeException("maven-metadata.xml file already existed in folder: " + destFolder);
        }

        final String path = repositoryUrl + groupId.replaceAll("\\.", "/") + "/" + artifactId + "/maven-metadata.xml";
        final String output = Spawn.runBashCommand("wget " + path, destFolder.toFile());
        System.out.println(output);

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

    public static DeploymentParametersBuilder deploymentParameters() {
        return new DeploymentParametersBuilder();
    }

    /**
     * If there is a -distribution JAR in the source project, this is deployed
     * as the main JAR, otherwise the normal JAR is deployed.
     * 
     * @param sourceDir
     * @param sourceJar
     *            may be null
     * @param groupId
     * @param artifactId
     * @param newVersion
     * @throws Exception
     */
    public static void performDeployment(final DeploymentParameters params) throws Exception {

        final Path deploymentDir = Files.createTempDirectory("maven-deployment"); // FileSystems.getDefault().getPath("/data/tmp");

        downloadRepositoryXml("http://maven.appjangle.com/appjangle/releases/", deploymentDir, params.artifact()
                .groupId(), params.artifact().artifactId());

        assertVersionInRepositoryXml(deploymentDir, params.artifact().version());

        final Path metadataFile = deploymentDir.resolve("maven-metadata.xml");

        assert Files.exists(metadataFile);

        WriteHashes.forFile(metadataFile);

        final Path versionDir = deploymentDir.resolve(params.artifact().version());
        Files.createDirectory(versionDir);

        Path sourceJar = params.forcedSourceJar();
        if (sourceJar == null) {
            final Path distributionJar = params.projectDir().resolve(
                    "target/" + params.artifact().artifactId() + "-" + params.artifact().version()
                    + "-distribution.jar");

            if (Files.exists(distributionJar)) {
                sourceJar = distributionJar;
            } else {
                sourceJar = params.projectDir().resolve(
                        "target/" + params.artifact().artifactId() + "-" + params.artifact().version() + ".jar");
            }
        }

        final Path newJar = versionDir.resolve(params.artifact().artifactId() + "-" + params.artifact().version()
                + ".jar");

        Files.copy(sourceJar, newJar);

        WriteHashes.forFile(newJar);

        final Path sourcePom = params.projectDir().resolve("pom.xml");

        final byte[] sourcePomBytes = Files.readAllBytes(sourcePom);

        final String sourcePomText = new String(sourcePomBytes, "UTF-8");

        final Path destPom = versionDir.resolve(params.artifact().artifactId() + "-" + params.artifact().version()
                + ".pom");

        Files.write(destPom, sourcePomText.replaceAll("<!-- inject-provided-scope -->", "<scope>provided</scope>")
                .getBytes("UTF-8"));

        WriteHashes.forFile(destPom);

        final String deploymentPath = params.artifact().groupId().replaceAll("\\.", "/") + "/"
                + params.artifact().artifactId();

        final String command = "rsync -avz -e \"ssh -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null\" --progress "
                + deploymentDir.toFile().getAbsolutePath()
                + "/*"
                + " maven@maven.appjangle.com:/mxdata/mvn/appjangle/releases/" + deploymentPath;
        System.out.println(command);

        System.out.println(Spawn.runBashCommand(command, deploymentDir.toFile()));

    }
}
