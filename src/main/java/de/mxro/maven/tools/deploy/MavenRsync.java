package de.mxro.maven.tools.deploy;

import java.nio.file.Files;
import java.nio.file.Path;

import de.mxro.maven.tools.MavenRemoteRepository;
import de.mxro.maven.tools.WriteHashes;
import de.mxro.process.Spawn;

public class MavenRsync {

    public static DeploymentParametersBuilder parameters() {
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
    public static void deploy(final DeploymentParameters params) {
        try {
            final Path deploymentDir = Files.createTempDirectory("maven-deployment"); // FileSystems.getDefault().getPath("/data/tmp");

            MavenRemoteRepository.downloadRepositoryXml(params.repositoryRemoteUri(), deploymentDir, params.artifact()
                    .groupId(), params.artifact().artifactId());

            MavenRemoteRepository.assertVersionInRepositoryXml(deploymentDir, params.artifact().version());

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
                    + " "
                    + params.rsyncConnectionPath()
                    + deploymentPath;
            System.out.println(command);

            System.out.println(Spawn.runBashCommand(command, deploymentDir.toFile()));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }

    }

}
