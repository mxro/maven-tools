package de.mxro.maven.tools.tests

import de.mxro.maven.tools.Dependency
import de.mxro.maven.tools.MavenLocalRepository
import de.oehme.xtend.junit.JUnit
import java.io.File
import org.junit.Test

@JUnit
class TestThatPathsDeterminedCorrectlyInLocalRepository {
	
	 @Test
    def test() {
        val File file = MavenLocalRepository.getFolderInLocalRepository(Dependency.define("com.appjangle.java",
                "appjangle-jre", "0.3.6"), new File("/data/databases/localMavenRepository/"));

		file.getAbsolutePath() => "/data/databases/localMavenRepository/com/appjangle/java/appjangle-jre/0.3.6"

    }
	
}