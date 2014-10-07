package de.mxro.maven.tools.deploy;

import java.nio.file.Path;

import de.mxro.maven.tools.Dependency;

public interface DeploymentParameters {
    public Dependency artifact();

    public Path projectDir();

    public Path forcedSourceJar();

    public String repositoryRemoteUri();

    public String rsyncConnectionPath();
}
