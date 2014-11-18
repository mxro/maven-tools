package de.mxro.maven.tools;

import java.io.File;

public class MavenLocalRepository {

    /**
     * <p>
     * Deletes the specified artifact from the local repository.
     * <p>
     * WARNING: Assumes there is only Maven stuff in the directory. The used
     * delete method is not save when there are symlinks etc in the directory.
     * 
     * @param localRepositoryRoot
     * @param artifact
     */
    public static void wipeArtifact(final File localRepositoryRoot, final Dependency artifact) {
        delete(getFolderInLocalRepository(artifact, localRepositoryRoot));
    }

    private static void delete(final File f) {
        if (f.isDirectory()) {
            for (final File c : f.listFiles()) {
                delete(c);
            }
        }
        if (!f.delete()) {
            throw new RuntimeException("Could not delete file" + f);
        }
    }

    /**
     * Determines the path within a local repository for a Maven artifact.
     * 
     * @param artifact
     * @param localRepositoryRoot
     * @return
     */
    public static File getFolderInLocalRepository(final Dependency artifact, final File localRepositoryRoot) {
        final String artifactRootPath = localRepositoryRoot.getAbsolutePath() + "/"
                + artifact.groupId().replace(".", "/") + "/" + artifact.artifactId() + "/" + artifact.version();

        return new File(artifactRootPath);
    }

}
