package de.mxro.maven.tools;

public abstract class Dependency {
    public abstract String groupId();

    public abstract String artifactId();

    public abstract String version();

    @Override
    public String toString() {
        return "(" + groupId() + ":" + artifactId() + ":" + version() + ")";
    }

}