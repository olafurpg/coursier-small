# coursier-small

[![Build Status](https://travis-ci.org/olafurpg/coursier-small.svg?branch=master)](https://travis-ci.org/olafurpg/coursier-small)


This project is a small wrapper around the library APIs for
[Coursier](https://github.com/coursier/coursier/) with the objective to provide
a stable binary compatible interface.

Goals:

- never break binary compatibility
- no transitive dependencies besides scala-library, coursier is shaded into an internal namespace

Non-goals:

- support the full coursier library API, if you need advanced configuration such as authentication
  or custom cache locations use Coursier directly.

## Usage


[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.geirsson/coursier-small_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.geirsson/coursier-small_2.12)


```scala
// Supports 2.10, 2.11 and 2.12
libraryDependencies += "com.geirsson" %% "coursier-small" % "VERSION"
```

Then use it like this
```scala
import com.geirsson.coursiersmall._
val dependency = new Dependency("com.geirsson", "scalafmt-cli_2.12", "1.5.1")
val settings = new Settings() .withDependencies(List(dependency))
val jars = CoursierSmall.fetch(settings)
// List(~/.coursier/cache/.../scalafmt-cli_2.12-1.5.1.jar, ...)
```

In case of resolution or file errors, the exceptions `ResolutionException` or `FileException` are thrown.
