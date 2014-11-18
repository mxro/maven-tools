package de.mxro.maven.tools;

public abstract class Dependency {

    public abstract String groupId();

    public abstract String artifactId();

    public abstract String version();

    @Override
    public String toString() {
        return "(" + groupId() + ":" + artifactId() + ":" + version() + ")";
    }

    public static Dependency define(final String groupId, final String artifactId, final String version) {
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
    }

}