package de.mxro.maven.tools.deploy;

import java.nio.file.Path;

import de.mxro.maven.tools.Dependency;

public class DeploymentParametersBuilder {

    private Dependency artifact;
    private Path projectDir;
    private Path forcedSourceJar;
    private String repositoryRemoteUri;
    private String rsyncConnectionPath;

    public void setArtifact(final Dependency artifact) {
        this.artifact = artifact;
    }

    public void setProjectDir(final Path projectDir) {
        this.projectDir = projectDir;
    }

    public void setForcedSourceJar(final Path forcedSourceJar) {
        this.forcedSourceJar = forcedSourceJar;
    }

    public void setRepositoryRemoteUri(final String repositoryRemoteUri) {
        this.repositoryRemoteUri = repositoryRemoteUri;
    }

    public void setRsyncConnectionPath(final String rsyncConnectionPath) {
        this.rsyncConnectionPath = rsyncConnectionPath;
    }

    public DeploymentParameters build() {
        return new DeploymentParameters() {

            @Override
            public String rsyncConnectionPath() {
                return rsyncConnectionPath;
            }

            @Override
            public String repositoryRemoteUri() {
                return repositoryRemoteUri;
            }

            @Override
            public Path projectDir() {
                return projectDir;
            }

            @Override
            public Path forcedSourceJar() {
                return forcedSourceJar;
            }

            @Override
            public Dependency artifact() {
                return artifact;
            }
        };
    }

}
