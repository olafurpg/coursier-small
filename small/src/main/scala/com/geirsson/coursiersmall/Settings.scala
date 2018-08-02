package com.geirsson.coursiersmall

import java.io.PrintStream

/**
  * Configuration for a resolution.
  *
  * @param dependencies the root module dependencies to resolve.
  * @param repositories the external repositories to use for resolution,
  *                     defaults to Maven Central and ivy2local.
  * @param out Where to print out progress and diagnostics during resolution and
  *            downloading of artifacts.
  */
final class Settings private (
    val dependencies: List[Dependency],
    val repositories: List[Repository],
    val out: PrintStream
) {

  override def toString: String = {
    s"""|Settings(
        |  dependencies = $dependencies,
        |  repositories = $repositories
        |)""".stripMargin
  }

  def this() = {
    this(
      dependencies = List(),
      repositories = List(
        Repository.MavenCentral,
        Repository.Ivy2Local
      ),
      out = System.out
    )
  }

  def withDependencies(dependencies: List[Dependency]): Settings = {
    copy(dependencies = dependencies)
  }

  def withRepositories(repositories: List[Repository]): Settings = {
    copy(repositories = repositories)
  }

  def withOut(out: PrintStream): Settings = {
    copy(out = out)
  }

  private[this] def copy(
      dependencies: List[Dependency] = this.dependencies,
      repositories: List[Repository] = this.repositories,
      out: PrintStream = this.out
  ): Settings = {
    new Settings(
      dependencies = dependencies,
      repositories = repositories,
      out = out
    )
  }
}
