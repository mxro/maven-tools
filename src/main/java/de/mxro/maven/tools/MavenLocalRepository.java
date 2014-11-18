package de.mxro.maven.tools;

import java.io.File;

public class MavenLocalRepository {

    /**
     * Deletes the specified artifact from the local repository.
     * 
     * @param localRepositoryRoot
     * @param artifact
     */
    public static void wipeArtifact(final File localRepositoryRoot, final Dependency artifact) {

    }

    public static File getFolderInLocalRepository(final Dependency artifact, final File localRepositoryRoot) {
        final String artifactRootPath = localRepositoryRoot.getAbsolutePath() + "/"
                + artifact.groupId().replace(".", "/") + "/" + artifact.artifactId() + "/" + artifact.version();

        return new File(artifactRootPath);
    }

}
