package de.mxro.maven.tools.deploy;

import java.nio.file.Path;

import de.mxro.maven.tools.Dependency;

public class DeploymentParametersBuilder {

    private Dependency artifact;
    private Path projectDir;
    private Path forcedSourceJar;
    private String repositoryRemoteUri;
    protected String serverDir;
    protected String server;
    protected String user;

    public DeploymentParametersBuilder setArtifact(final Dependency artifact) {
        this.artifact = artifact;
        return this;
    }

    public DeploymentParametersBuilder setProjectDir(final Path projectDir) {
        this.projectDir = projectDir;
        return this;
    }

    public DeploymentParametersBuilder setForcedSourceJar(final Path forcedSourceJar) {
        this.forcedSourceJar = forcedSourceJar;
        return this;
    }

    public DeploymentParametersBuilder setRepositoryRemoteUri(final String repositoryRemoteUri) {
        this.repositoryRemoteUri = repositoryRemoteUri;
        return this;
    }

    public DeploymentParametersBuilder setServerDir(final String serverDir) {
        this.serverDir = serverDir;
        return this;
    }

    public DeploymentParametersBuilder setServer(final String server) {
        this.server = server;
        return this;
    }

    public DeploymentParametersBuilder setUser(final String user) {
        this.user = user;
        return this;
    }

    public DeploymentParameters build() {
        return new DeploymentParameters() {

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

            @Override
            public String serverDir() {
                return serverDir;
            }

            @Override
            public String server() {
                return server;
            }

            @Override
            public String user() {
                return user;
            }
        };
    }

}
