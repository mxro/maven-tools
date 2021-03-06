package de.mxro.maven.tools.tests;

import de.mxro.file.FileItem;
import de.mxro.file.Jre.FilesJre;
import de.mxro.maven.tools.Dependency;
import de.mxro.maven.tools.MavenProject;
import de.oehme.xtend.junit.JUnit;
import java.io.File;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.ArrayComparisonFailure;
import org.junit.rules.TemporaryFolder;

@JUnit
@SuppressWarnings("all")
public class TestThatDependenciesCanBeReplaced {
  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();
  
  @Test
  public void test() {
    final FileItem root = FilesJre.wrap(this.tempFolder.newFolder("test"));
    final FileItem pom = root.createFile("pom.xml");
    pom.setText(this.examplePom);
    String _path = root.getPath();
    File _file = new File(_path);
    MavenProject.replaceDependency(_file, Dependency.define("junit", "junit", null), 
      Dependency.define("junit", "junit", "4.11"));
    boolean _contains = pom.getText().contains("<version>4.7</version>");
    TestThatDependenciesCanBeReplaced.<Boolean, Boolean>operator_doubleArrow(Boolean.valueOf(_contains), Boolean.valueOf(false));
    boolean _contains_1 = pom.getText().contains("<version>4.11</version>");
    TestThatDependenciesCanBeReplaced.<Boolean, Boolean>operator_doubleArrow(Boolean.valueOf(_contains_1), Boolean.valueOf(true));
  }
  
  private final String examplePom = new Function0<String>() {
    public String apply() {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      _builder.newLine();
      _builder.append("<!-- Template: Source + GWT + in workspace dependencies 27.11.2010 -->");
      _builder.newLine();
      _builder.append("<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<groupId>de.mxro.factories</groupId>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<artifactId>factories</artifactId>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<version>0.0.3</version>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<description>Allows definition and management of lightweight factories.</description>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<url>https://github.com/appjangle/factories</url>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<properties>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<module.gwtVersion>2.2.0</module.gwtVersion>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<module.draftCompile>true</module.draftCompile>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<module.importedPackages>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("org.junit,");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("*");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("</module.importedPackages>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<module.exportedPackages>!de.mxro.factories.internal*,de.mxro.factories*");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("</module.exportedPackages>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<module.serviceDefinitions>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("</module.serviceDefinitions>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("</properties>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<dependencies>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<dependency>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<groupId>junit</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<artifactId>junit</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<version>4.7</version>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<scope>test</scope>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("</dependency>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<!-- only for gwt maven plugin!!! -->");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<dependency>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<groupId>com.google.gwt</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<artifactId>gwt-user</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<version>${module.gwtVersion}</version>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<scope>provided</scope>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("</dependency>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("</dependencies>");
      _builder.newLine();
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<!-- XXXXXXXXXXXXXX Maven declarations XXXXXXXXXXXXXXXXXX -->");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<modelVersion>4.0.0</modelVersion>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<name>${project.artifactId}</name>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<packaging>bundle</packaging>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<build>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<plugins>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<artifactId>maven-compiler-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<configuration>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<source>1.6</source>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<target>1.6</target>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("</configuration>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<!-- FOR BUNDLE MANAGEMENT -->");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<!-- The Maven bundle plugin generates Meta-data required for OSGi -->");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<groupId>org.apache.felix</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<artifactId>maven-bundle-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<extensions>true</extensions>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<version>2.3.6</version>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<configuration>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<instructions>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<Bundle-SymbolicName>${project.artifactId}</Bundle-SymbolicName>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<Bundle-Version>${project.version}</Bundle-Version>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<Import-Package>${module.importedPackages}</Import-Package>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<Export-Package>${module.exportedPackages}</Export-Package>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<Service-Component>${module.serviceDefinitions}");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("</Service-Component>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<Bundle-RequiredExecutionEnvironment>JavaSE-1.6");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("</Bundle-RequiredExecutionEnvironment>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("</instructions>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("</configuration>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<groupId>org.apache.maven.plugins</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<artifactId>maven-eclipse-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<version>2.8</version>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<configuration>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<pde>true</pde>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("</configuration>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<!-- <sourceExcludes> <sourceExclude>**/.svn/**</sourceExclude> </sourceExcludes> ");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<sourceIncludes> <sourceInclude>**/*.myExtension</sourceInclude> </sourceIncludes> ");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("</configuration> -->");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<!-- XXXXXXXXXXXXXXXXXXXXXXXXXXXXX -->");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<!-- For GWT -->");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<!-- XXXXXXXXXXXXXXXXXXXXXXXXXXXXX -->");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<groupId>org.codehaus.mojo</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<artifactId>gwt-maven-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<version>2.2.0</version>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<configuration>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<draftCompile>${module.draftCompile}</draftCompile>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<logLevel>INFO</logLevel>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<!-- -->");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<validateOnly>true</validateOnly>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<gwtVersion>${module.gwtVersion}</gwtVersion>");
      _builder.newLine();
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<webappDirectory>${basedir}/src/main/webapp</webappDirectory>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("</configuration>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<executions>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<execution>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<id>gwtcompile</id>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<phase>prepare-package</phase>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<goals>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<goal>compile</goal>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("</goals>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("</execution>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("</executions>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<!-- FOR MAVEN ECLIPSE PLUGIN -->");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<!-- Dependency Plugin used to copy the dependency JARs into the root ");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("project folder. There the Maven eclipse plugin will add them to the classpath ");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("of PDE projects. -->");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<artifactId>maven-dependency-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<executions>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("</executions>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<!-- Cleanup necessary because of PDE tweaks, clear the project directory -->");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<artifactId>maven-clean-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<version>2.3</version>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<configuration>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<filesets>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<fileset>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<directory>${basedir}</directory>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<includes>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<include>*.jar</include>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("</includes>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<followSymlinks>false</followSymlinks>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("</fileset>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("</filesets>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("</configuration>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<!-- Keep the MANIFEST.MF used by eclipse in sync with the MANIFEST.MF ");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("created by the maven bundle plugin -->");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<artifactId>maven-antrun-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<executions>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<execution>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<phase>package</phase>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<goals>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<goal>run</goal>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("</goals>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<configuration>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<tasks>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<delete file=\"${basedir}/META-INF/MANIFEST.MF\" />");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<copy file=\"target/classes/META-INF/MANIFEST.MF\" tofile=\"${basedir}/META-INF/MANIFEST.MF\" />");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("</tasks>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("</configuration>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("</execution>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("</executions>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<groupId>org.codehaus.mojo</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<artifactId>findbugs-maven-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<version>3.0.0</version>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<groupId>org.apache.maven.plugins</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<artifactId>maven-site-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<version>3.4</version>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<configuration>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<skipDeploy>true</skipDeploy>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<reportPlugins>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<groupId>org.apache.maven.plugins</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<artifactId>maven-project-info-reports-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<version>2.7</version>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<reports>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<report>index</report>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<report>project-team</report>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<report>license</report>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<report>mailing-list</report>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<report>dependencies</report>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<report>dependency-convergence</report>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<report>plugin-management</report>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<report>cim</report>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<report>issue-tracking</report>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<report>scm</report>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<report>summary</report>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("</reports>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<groupId>org.apache.maven.plugins</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<artifactId>maven-javadoc-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<version>2.9.1</version>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<configuration>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<links>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t\t");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("</links>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("</configuration>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<groupId>org.apache.maven.plugins</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<artifactId>maven-jxr-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<version>2.1</version>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<groupId>org.apache.maven.plugins</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<artifactId>maven-surefire-report-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<version>2.6</version>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<groupId>org.codehaus.mojo</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<artifactId>findbugs-maven-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<version>3.0.0</version>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<configuration>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<xmlOutput>true</xmlOutput>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<!-- Optional directory to put findbugs xdoc xml report -->");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<xmlOutputDirectory>target/site</xmlOutputDirectory>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("</configuration>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<groupId>org.apache.maven.plugins</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<artifactId>maven-pmd-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<version>2.5</version>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<configuration>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<linkXref>true</linkXref>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<minimumTokens>100</minimumTokens>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<minimumPriority>3</minimumPriority>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<!-- Change minimum priority to see more or less messages -->");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t\t");
      _builder.append("<targetJdk>1.6</targetJdk>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("</configuration>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<groupId>org.apache.maven.plugins</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<artifactId>maven-checkstyle-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<version>2.6</version>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<groupId>org.codehaus.mojo</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<artifactId>jdepend-maven-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<version>2.0-beta-2</version>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<groupId>org.codehaus.mojo</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<artifactId>cobertura-maven-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<version>2.4</version>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("<plugin>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<groupId>org.codehaus.mojo</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<artifactId>taglist-maven-plugin</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t\t");
      _builder.append("<version>2.4</version>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("</reportPlugins>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("</configuration>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("</plugin>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("</plugins>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<!-- RESOURCES -->");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<resources>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<!-- Required to be valid GWT Library (requires *.java files in jar) -->");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<resource>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<directory>src/main/java</directory>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<includes>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<include>**/*.java</include>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<include>**/*.gwt.xml</include>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("</includes>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("</resource>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<!-- This entry makes sure that resources, which lie in the same package ");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("as Java classes, are copied into the target. Often external libraries require ");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("resources, which are loaded using Class.getResource or Class.getResourceAsStream ");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("and which are in a subpackage of the class. For instance, the NetBeans template ");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("for the Swing Application Framework does so. -->");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<resource>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<filtering>false</filtering>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<directory>src/main/java</directory>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<includes>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<include>**</include>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("</includes>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<excludes>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<exclude>**/*.java</exclude>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("</excludes>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("</resource>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<!-- This entry makes sure component definitions for OSGi declarative ");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("services are copied into the destination -->");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<resource>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<targetPath>OSGI-INF</targetPath>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<filtering>false</filtering>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<directory>OSGI-INF</directory>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<includes>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<include>**</include>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("</includes>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("</resource>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<!-- I really do not know why know a manual entry for src/main/resources ");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("is necessary? It should be included following the Maven convention. -->");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<resource>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<filtering>false</filtering>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<directory>src/main/resources</directory>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<includes>");
      _builder.newLine();
      _builder.append("\t\t\t\t\t");
      _builder.append("<include>**</include>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("</includes>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("</resource>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("</resources>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<extensions>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<extension>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<groupId>org.apache.maven.wagon</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<artifactId>wagon-ssh-external</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<version>2.5</version>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("</extension>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<extension>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<groupId>org.apache.maven.wagon</groupId>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<artifactId>wagon-ssh</artifactId>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<version>2.5</version>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("</extension>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("</extensions>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("</build>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<distributionManagement>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<repository>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<id>appjangle-releases</id>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<url>${repos.appjangle-releases}</url>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("</repository>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<snapshotRepository>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<id>appjangle-snapshots</id>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<url>${repos.appjangle-snapshots}</url>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("</snapshotRepository>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("</distributionManagement>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<developers>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<developer>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<id>mxro</id>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<name>Max Rohde</name>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<email>noemail@mxro.de</email>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<url>http://www.mxro.de/</url>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<roles>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<role>architect</role>");
      _builder.newLine();
      _builder.append("\t\t\t\t");
      _builder.append("<role>developer</role>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("</roles>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<timezone>+10</timezone>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<organization>pureleap</organization>");
      _builder.newLine();
      _builder.append("\t\t\t");
      _builder.append("<organizationUrl>http://pureleap.com</organizationUrl>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("</developer>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("</developers>");
      _builder.newLine();
      _builder.newLine();
      _builder.append("\t");
      _builder.append("<scm>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<connection>scm:git:git@github.com:appjangle/factories.git</connection>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<url>https://github.com/appjangle/factories</url>");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("<developerConnection>scm:git:git@github.com:appjangle/factories.git");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("</developerConnection>");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("</scm>");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("</project>");
      _builder.newLine();
      _builder.append("\t");
      _builder.newLine();
      return _builder.toString();
    }
  }.apply();
  
  private static void assertArrayEquals(final Object[] expecteds, final Object[] actuals) {
    Assert.assertArrayEquals(expecteds, actuals);
  }
  
  private static void assertArrayEquals(final byte[] expecteds, final byte[] actuals) {
    Assert.assertArrayEquals(expecteds, actuals);
  }
  
  private static void assertArrayEquals(final char[] expecteds, final char[] actuals) {
    Assert.assertArrayEquals(expecteds, actuals);
  }
  
  private static void assertArrayEquals(final short[] expecteds, final short[] actuals) {
    Assert.assertArrayEquals(expecteds, actuals);
  }
  
  private static void assertArrayEquals(final int[] expecteds, final int[] actuals) {
    Assert.assertArrayEquals(expecteds, actuals);
  }
  
  private static void assertArrayEquals(final long[] expecteds, final long[] actuals) {
    Assert.assertArrayEquals(expecteds, actuals);
  }
  
  private static void assertArrayEquals(final String message, final Object[] expecteds, final Object[] actuals) throws ArrayComparisonFailure {
    Assert.assertArrayEquals(message, expecteds, actuals);
  }
  
  private static void assertArrayEquals(final String message, final byte[] expecteds, final byte[] actuals) throws ArrayComparisonFailure {
    Assert.assertArrayEquals(message, expecteds, actuals);
  }
  
  private static void assertArrayEquals(final String message, final char[] expecteds, final char[] actuals) throws ArrayComparisonFailure {
    Assert.assertArrayEquals(message, expecteds, actuals);
  }
  
  private static void assertArrayEquals(final String message, final short[] expecteds, final short[] actuals) throws ArrayComparisonFailure {
    Assert.assertArrayEquals(message, expecteds, actuals);
  }
  
  private static void assertArrayEquals(final String message, final int[] expecteds, final int[] actuals) throws ArrayComparisonFailure {
    Assert.assertArrayEquals(message, expecteds, actuals);
  }
  
  private static void assertArrayEquals(final String message, final long[] expecteds, final long[] actuals) throws ArrayComparisonFailure {
    Assert.assertArrayEquals(message, expecteds, actuals);
  }
  
  private static void assertArrayEquals(final double[] expecteds, final double[] actuals, final double delta) {
    Assert.assertArrayEquals(expecteds, actuals, delta);
  }
  
  private static void assertArrayEquals(final float[] expecteds, final float[] actuals, final float delta) {
    Assert.assertArrayEquals(expecteds, actuals, delta);
  }
  
  private static void assertArrayEquals(final String message, final double[] expecteds, final double[] actuals, final double delta) throws ArrayComparisonFailure {
    Assert.assertArrayEquals(message, expecteds, actuals, delta);
  }
  
  private static void assertArrayEquals(final String message, final float[] expecteds, final float[] actuals, final float delta) throws ArrayComparisonFailure {
    Assert.assertArrayEquals(message, expecteds, actuals, delta);
  }
  
  private static void assertEquals(final Object expected, final Object actual) {
    Assert.assertEquals(expected, actual);
  }
  
  private static void assertEquals(final long expected, final long actual) {
    Assert.assertEquals(expected, actual);
  }
  
  private static void assertEquals(final String message, final Object expected, final Object actual) {
    Assert.assertEquals(message, expected, actual);
  }
  
  private static void assertEquals(final String message, final long expected, final long actual) {
    Assert.assertEquals(message, expected, actual);
  }
  
  private static void assertEquals(final double expected, final double actual, final double delta) {
    Assert.assertEquals(expected, actual, delta);
  }
  
  private static void assertEquals(final String message, final double expected, final double actual, final double delta) {
    Assert.assertEquals(message, expected, actual, delta);
  }
  
  private static void assertFalse(final boolean condition) {
    Assert.assertFalse(condition);
  }
  
  private static void assertFalse(final String message, final boolean condition) {
    Assert.assertFalse(message, condition);
  }
  
  private static void assertNotNull(final Object object) {
    Assert.assertNotNull(object);
  }
  
  private static void assertNotNull(final String message, final Object object) {
    Assert.assertNotNull(message, object);
  }
  
  private static void assertNotSame(final Object unexpected, final Object actual) {
    Assert.assertNotSame(unexpected, actual);
  }
  
  private static void assertNotSame(final String message, final Object unexpected, final Object actual) {
    Assert.assertNotSame(message, unexpected, actual);
  }
  
  private static void assertNull(final Object object) {
    Assert.assertNull(object);
  }
  
  private static void assertNull(final String message, final Object object) {
    Assert.assertNull(message, object);
  }
  
  private static void assertSame(final Object expected, final Object actual) {
    Assert.assertSame(expected, actual);
  }
  
  private static void assertSame(final String message, final Object expected, final Object actual) {
    Assert.assertSame(message, expected, actual);
  }
  
  private static <T extends Object> void assertThat(final T actual, final Matcher<T> matcher) {
    Assert.<T>assertThat(actual, matcher);
  }
  
  private static <T extends Object> void assertThat(final String reason, final T actual, final Matcher<T> matcher) {
    Assert.<T>assertThat(reason, actual, matcher);
  }
  
  private static void assertTrue(final boolean condition) {
    Assert.assertTrue(condition);
  }
  
  private static void assertTrue(final String message, final boolean condition) {
    Assert.assertTrue(message, condition);
  }
  
  private static void fail() {
    Assert.fail();
  }
  
  private static void fail(final String message) {
    Assert.fail(message);
  }
  
  private static <T extends Object, U extends T> void operator_doubleArrow(final T actual, final U expected) {
    Assert.assertEquals(expected, actual);
  }
  
  private static <T extends Exception> void isThrownBy(final Class<T> expected, final Procedure0 block) {
    try {
    	block.apply();
    	Assert.fail("Expected a " + expected.getName());
    } catch (Exception e) {
    	Class<?> actual = e.getClass();
    	Assert.assertTrue(
    		"Expected a " + expected.getName() + " but got " + actual.getName(), 
    		expected.isAssignableFrom(actual)
    	);
    }
  }
}
