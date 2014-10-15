package de.mxro.maven.tools.deploy;

import java.nio.file.Path;

import de.mxro.maven.tools.Dependency;

public interface DeploymentParameters {
    public Dependency artifact();

    public Path projectDir();

    public Path forcedSourceJar();

    public String repositoryRemoteUri();

    /**
     * @return Path to server directory.
     */
    public String serverDir();

    /**
     * @return Domain of the server
     */
    public String server();

    /**
     * 
     * @return User to be used to log in at server.
     */
    public String user();
}
