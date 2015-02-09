[![Build Status](https://travis-ci.org/mxro/maven-tools.svg?branch=master)](https://travis-ci.org/mxro/maven-tools)

# Maven Tools

[Maven Tools](https://github.com/mxro/maven-tools) is a collection of vanilla Java tools to work with Maven Projects and Repositories.

**Why**: To provide a lightweight way to work with Maven projects and repositories.  

## Usage

### Maven Projects

To perform various operations on Maven Projects (folders which contain a pom.xml file), explore the MavenProject class:

[MavenProject JavaDoc](http://modules.appjangle.com/maven-tools/latest/apidocs/de/mxro/maven/tools/MavenProject.html)

[MavenProject.java Source](http://modules.appjangle.com/maven-tools/latest/xref/de/mxro/maven/tools/MavenProject.html)

### Maven Local Repository

To perform operations on the local Maven repository, use the methods provided in the MavenLocalRepository class:

[MavenLocalRepository JavaDoc](http://modules.appjangle.com/maven-tools/latest/apidocs/de/mxro/maven/tools/MavenLocalRepository.html)

[MavenLocalRepository.java Source](http://modules.appjangle.com/maven-tools/latest/xref/de/mxro/maven/tools/MavenLocalRepository.html)

### Maven Remote Repositories

To work with remote Maven repositories, use methods provided in the MavenRemoteRepository class:

[MavenRemoteRepository JavaDoc](http://modules.appjangle.com/maven-tools/latest/apidocs/de/mxro/maven/tools/MavenRemoteRepository.html)

[MavenRemoteRepository.java Source](http://modules.appjangle.com/maven-tools/latest/xref/de/mxro/maven/tools/MavenRemoteRepository.html)

### Upload Artifacts to Repository using RSync

For uploading artifacts to a remote repository using RSync, use the class MavenRsync.class:

[MavenRsync JavaDoc](http://modules.appjangle.com/maven-tools/latest/apidocs/de/mxro/maven/tools/deploy/MavenRsync.html)

[MavenRsync.java Source](http://modules.appjangle.com/maven-tools/latest/xref/de/mxro/maven/tools/deploy/MavenRsync.html)

You can define the required parameters for a deployment using the DeploymentParamtersBuilder 
([JavaDoc](http://modules.appjangle.com/maven-tools/latest/apidocs/de/mxro/maven/tools/deploy/DeploymentParametersBuilder.html), 
[Source](http://modules.appjangle.com/maven-tools/latest/xref/de/mxro/maven/tools/deploy/DeploymentParametersBuilder.html)).

## Maven Dependency

```xml
<dependency>
    <groupId>de.mxro.maven.tools</groupId>
	<artifactId>maven-tools</artifactId>
	<version>[latest version]</version>
</dependency>
```

Find latest version [here](http://modules.appjangle.com/lightweight-java-metrics/latest/project-summary.html).

Add repository if required:

```xml
<repositories>
	<repository>
		<id>Appjangle Releases</id>
		<url>http://maven.appjangle.com/appjangle/releases</url>
	</repository>
</repositories>
```

## Compatibility

This project is compatible with the following environments:

- Java 1.6+
- OSGi (any)

## License

Apache 2.0

## Further Resources

- [JavaDocs](http://modules.appjangle.com/maven-tools/latest/apidocs/)
- [Project Reports](http://modules.appjangle.com/maven-tools/latest/project-reports.html)
- [Rendered README](http://documentup.com/mxro/maven-tools)
- [ReadTheDocs](http://maven-tools.rtfd.org/)

[![Documentation Status](https://readthedocs.org/projects/async-log/badge/?version=latest)](https://readthedocs.org/projects/maven-tools/?badge=latest)
