package de.mxro.maven.tools.tests

import de.mxro.file.Jre.FilesJre
import de.mxro.maven.tools.Dependency
import de.mxro.maven.tools.MavenProject
import de.oehme.xtend.junit.JUnit
import java.io.File
import org.junit.Rule
import org.junit.rules.TemporaryFolder

@JUnit
class TestThatDependenciesCanBeReplaced {

	@Rule
	TemporaryFolder tempFolder = new TemporaryFolder();

	def test() {

		val root = FilesJre.wrap(tempFolder.newFolder("test"))

		val pom = root.createFile("pom.xml")

		pom.text = examplePom

		MavenProject.replaceDependency(new File(root.path), Dependency.define("junit", "junit", null),
			Dependency.define("junit", "junit", "4.11"))

		pom.text.contains("<version>4.7</version>") => false
		pom.text.contains("<version>4.11</version>") => true

	}

	val examplePom = '''<?xml version="1.0" encoding="UTF-8"?>
<!-- Template: Source + GWT + in workspace dependencies 27.11.2010 -->
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<groupId>de.mxro.factories</groupId>
	<artifactId>factories</artifactId>
	<version>0.0.3</version>
	<description>Allows definition and management of lightweight factories.</description>
	<url>https://github.com/appjangle/factories</url>
		
	<properties>

		<module.gwtVersion>2.2.0</module.gwtVersion>
		<module.draftCompile>true</module.draftCompile>
		<module.importedPackages>
			org.junit,
			*
		</module.importedPackages>
		<module.exportedPackages>!de.mxro.factories.internal*,de.mxro.factories*
		</module.exportedPackages>
		<module.serviceDefinitions>
		</module.serviceDefinitions>
	</properties>

	<dependencies>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.7</version>
			<scope>test</scope>
		</dependency>

		<!-- only for gwt maven plugin!!! -->
		<dependency>
			<groupId>com.google.gwt</groupId>
			<artifactId>gwt-user</artifactId>
			<version>${module.gwtVersion}</version>
			<scope>provided</scope>
		</dependency>

	</dependencies>


	<!-- XXXXXXXXXXXXXX Maven declarations XXXXXXXXXXXXXXXXXX -->

	<modelVersion>4.0.0</modelVersion>
	<name>${project.artifactId}</name>
	<packaging>bundle</packaging>
	<build>

		<plugins>
			<plugin>
				<artifactId>maven-compiler-plugin</artifactId>
				<configuration>
					<source>1.6</source>
					<target>1.6</target>
				</configuration>
			</plugin>


			<!-- FOR BUNDLE MANAGEMENT -->
			<!-- The Maven bundle plugin generates Meta-data required for OSGi -->

			<plugin>
				<groupId>org.apache.felix</groupId>
				<artifactId>maven-bundle-plugin</artifactId>
				<extensions>true</extensions>
				<version>2.3.6</version>
				<configuration>
					<instructions>
						<Bundle-SymbolicName>${project.artifactId}</Bundle-SymbolicName>
						<Bundle-Version>${project.version}</Bundle-Version>
						<Import-Package>${module.importedPackages}</Import-Package>
						<Export-Package>${module.exportedPackages}</Export-Package>
						<Service-Component>${module.serviceDefinitions}
						</Service-Component>
						<Bundle-RequiredExecutionEnvironment>JavaSE-1.6
						</Bundle-RequiredExecutionEnvironment>
					</instructions>
				</configuration>
			</plugin>

			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-eclipse-plugin</artifactId>
				<version>2.8</version>
				<configuration>
					<pde>true</pde>
				</configuration>
				<!-- <sourceExcludes> <sourceExclude>**/.svn/**</sourceExclude> </sourceExcludes> 
					<sourceIncludes> <sourceInclude>**/*.myExtension</sourceInclude> </sourceIncludes> 
					</configuration> -->
			</plugin>


			<!-- XXXXXXXXXXXXXXXXXXXXXXXXXXXXX -->
			<!-- For GWT -->
			<!-- XXXXXXXXXXXXXXXXXXXXXXXXXXXXX -->
			<plugin>
				<groupId>org.codehaus.mojo</groupId>
				<artifactId>gwt-maven-plugin</artifactId>
				<version>2.2.0</version>
				<configuration>

					<draftCompile>${module.draftCompile}</draftCompile>
					<logLevel>INFO</logLevel>
					<!-- -->
					<validateOnly>true</validateOnly>
					<gwtVersion>${module.gwtVersion}</gwtVersion>


					<webappDirectory>${basedir}/src/main/webapp</webappDirectory>

				</configuration>
				<executions>
					<execution>
						<id>gwtcompile</id>
						<phase>prepare-package</phase>
						<goals>
							<goal>compile</goal>
						</goals>
					</execution>
				</executions>
			</plugin>

			<!-- FOR MAVEN ECLIPSE PLUGIN -->
			<!-- Dependency Plugin used to copy the dependency JARs into the root 
				project folder. There the Maven eclipse plugin will add them to the classpath 
				of PDE projects. -->
			<plugin>
				<artifactId>maven-dependency-plugin</artifactId>
				<executions>

				</executions>
			</plugin>

			<!-- Cleanup necessary because of PDE tweaks, clear the project directory -->
			<plugin>
				<artifactId>maven-clean-plugin</artifactId>
				<version>2.3</version>
				<configuration>
					<filesets>
						<fileset>
							<directory>${basedir}</directory>
							<includes>
								<include>*.jar</include>
							</includes>
							<followSymlinks>false</followSymlinks>
						</fileset>
					</filesets>
				</configuration>
			</plugin>

			<!-- Keep the MANIFEST.MF used by eclipse in sync with the MANIFEST.MF 
				created by the maven bundle plugin -->
			<plugin>
				<artifactId>maven-antrun-plugin</artifactId>
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>run</goal>
						</goals>
						<configuration>
							<tasks>
								<delete file="${basedir}/META-INF/MANIFEST.MF" />
								<copy file="target/classes/META-INF/MANIFEST.MF" tofile="${basedir}/META-INF/MANIFEST.MF" />
							</tasks>
						</configuration>
					</execution>
				</executions>
			</plugin>
			
			
			<plugin>
				<groupId>org.codehaus.mojo</groupId>
				<artifactId>findbugs-maven-plugin</artifactId>
				<version>3.0.0</version>
			</plugin>

			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-site-plugin</artifactId>
				<version>3.4</version>
				<configuration>
					<skipDeploy>true</skipDeploy>

					<reportPlugins>
						<plugin>
							<groupId>org.apache.maven.plugins</groupId>
							<artifactId>maven-project-info-reports-plugin</artifactId>
							<version>2.7</version>
							<reports>
								<report>index</report>
								<report>project-team</report>
								<report>license</report>
								<report>mailing-list</report>
								<report>dependencies</report>
								<report>dependency-convergence</report>
								<report>plugin-management</report>
								<report>cim</report>
								<report>issue-tracking</report>
								<report>scm</report>
								<report>summary</report>
							</reports>
						</plugin>


						<plugin>
							<groupId>org.apache.maven.plugins</groupId>
							<artifactId>maven-javadoc-plugin</artifactId>
							<version>2.9.1</version>
							<configuration>
								<links>
									
								</links>
							</configuration>
						</plugin>

						<plugin>
							<groupId>org.apache.maven.plugins</groupId>
							<artifactId>maven-jxr-plugin</artifactId>
							<version>2.1</version>
						</plugin>

						<plugin>
							<groupId>org.apache.maven.plugins</groupId>
							<artifactId>maven-surefire-report-plugin</artifactId>
							<version>2.6</version>
						</plugin>

						<plugin>
							<groupId>org.codehaus.mojo</groupId>
							<artifactId>findbugs-maven-plugin</artifactId>
							<version>3.0.0</version>
							<configuration>
								<xmlOutput>true</xmlOutput>
								<!-- Optional directory to put findbugs xdoc xml report -->
								<xmlOutputDirectory>target/site</xmlOutputDirectory>
							</configuration>
						</plugin>

						<plugin>
							<groupId>org.apache.maven.plugins</groupId>
							<artifactId>maven-pmd-plugin</artifactId>
							<version>2.5</version>
							<configuration>
								<linkXref>true</linkXref>
								<minimumTokens>100</minimumTokens>
								<minimumPriority>3</minimumPriority>
								<!-- Change minimum priority to see more or less messages -->
								<targetJdk>1.6</targetJdk>
							</configuration>
						</plugin>

						<plugin>
							<groupId>org.apache.maven.plugins</groupId>
							<artifactId>maven-checkstyle-plugin</artifactId>
							<version>2.6</version>

						</plugin>
						<plugin>
							<groupId>org.codehaus.mojo</groupId>
							<artifactId>jdepend-maven-plugin</artifactId>
							<version>2.0-beta-2</version>
						</plugin>
						<plugin>
							<groupId>org.codehaus.mojo</groupId>
							<artifactId>cobertura-maven-plugin</artifactId>
							<version>2.4</version>
						</plugin>
						<plugin>
							<groupId>org.codehaus.mojo</groupId>
							<artifactId>taglist-maven-plugin</artifactId>
							<version>2.4</version>
						</plugin>


					</reportPlugins>
				</configuration>
			</plugin>
			
		</plugins>

		<!-- RESOURCES -->
		<resources>
			<!-- Required to be valid GWT Library (requires *.java files in jar) -->

			<resource>
				<directory>src/main/java</directory>
				<includes>
					<include>**/*.java</include>
					<include>**/*.gwt.xml</include>
				</includes>
			</resource>

			<!-- This entry makes sure that resources, which lie in the same package 
				as Java classes, are copied into the target. Often external libraries require 
				resources, which are loaded using Class.getResource or Class.getResourceAsStream 
				and which are in a subpackage of the class. For instance, the NetBeans template 
				for the Swing Application Framework does so. -->
			<resource>
				<filtering>false</filtering>
				<directory>src/main/java</directory>
				<includes>
					<include>**</include>
				</includes>
				<excludes>
					<exclude>**/*.java</exclude>
				</excludes>
			</resource>
			<!-- This entry makes sure component definitions for OSGi declarative 
				services are copied into the destination -->
			<resource>
				<targetPath>OSGI-INF</targetPath>
				<filtering>false</filtering>
				<directory>OSGI-INF</directory>
				<includes>
					<include>**</include>
				</includes>
			</resource>
			<!-- I really do not know why know a manual entry for src/main/resources 
				is necessary? It should be included following the Maven convention. -->
			<resource>
				<filtering>false</filtering>
				<directory>src/main/resources</directory>
				<includes>
					<include>**</include>
				</includes>
			</resource>

		</resources>

		<extensions>
			<extension>
				<groupId>org.apache.maven.wagon</groupId>
				<artifactId>wagon-ssh-external</artifactId>
				<version>2.5</version>
			</extension>

			<extension>
				<groupId>org.apache.maven.wagon</groupId>
				<artifactId>wagon-ssh</artifactId>
				<version>2.5</version>
			</extension>
		</extensions>
	</build>

	<distributionManagement>
		<repository>
			<id>appjangle-releases</id>
			<url>${repos.appjangle-releases}</url>
		</repository>
		<snapshotRepository>
			<id>appjangle-snapshots</id>
			<url>${repos.appjangle-snapshots}</url>
		</snapshotRepository>
	</distributionManagement>

	
	
	
	<developers>
		<developer>
			<id>mxro</id>
			<name>Max Rohde</name>
			<email>noemail@mxro.de</email>
			<url>http://www.mxro.de/</url>
			<roles>
				<role>architect</role>
				<role>developer</role>
			</roles>
			<timezone>+10</timezone>
			<organization>pureleap</organization>
			<organizationUrl>http://pureleap.com</organizationUrl>
		</developer>
	</developers>

	<scm>
		<connection>scm:git:git@github.com:appjangle/factories.git</connection>
		<url>https://github.com/appjangle/factories</url>
		<developerConnection>scm:git:git@github.com:appjangle/factories.git
		</developerConnection>
	</scm>
	
</project>
	
	'''

}
