package de.mxro.maven.tools.tests;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

import de.mxro.maven.tools.Dependency;
import de.mxro.maven.tools.MavenLocalRepository;

public class TestLocalRepositoryOperations {

    @Test
    public void test_that_path_determined_correctly() {
        final File file = MavenLocalRepository.getFolderInLocalRepository(Dependency.define("com.appjangle.java",
                "appjangle-jre", "0.3.6"), new File("/data/databases/localMavenRepository/"));

        System.out.println(file);

        Assert.assertEquals("/data/databases/localMavenRepository/com/appjangle/java/appjangle-jre/0.3.6",
                file.getAbsolutePath());

    }
}
