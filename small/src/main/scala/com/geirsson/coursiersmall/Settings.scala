package com.geirsson.coursiersmall

import coursier.Cache
import java.io.OutputStreamWriter
import java.io.Writer
import scala.concurrent.duration.Duration

/**
  * Configuration for a resolution.
  *
  * @param dependencies the root module dependencies to resolve.
  * @param repositories the external repositories to use for resolution,
  *                     defaults to Maven Central and ivy2local.
  * @param writer Where to print out progress and diagnostics during resolution and
  *               downloading of artifacts.
  */
final class Settings private (
    val dependencies: List[Dependency],
    val repositories: List[Repository],
    val writer: Writer,
    val ttl: Option[Duration],
    val forceVersions: List[Dependency]
) {

  override def toString: String = {
    s"""|Settings(
        |  dependencies  = $dependencies
        |  repositories  = $repositories
        |  ttl           = $ttl
        |  forceVersions = $forceVersions
        |)""".stripMargin
  }

  def this() = {
    this(
      dependencies = List(),
      repositories = List(
        Repository.MavenCentral,
        Repository.Ivy2Local
      ),
      writer = new OutputStreamWriter(System.out),
      ttl = Cache.defaultTtl,
      forceVersions = Nil
    )
  }

  def withDependencies(dependencies: List[Dependency]): Settings = {
    copy(dependencies = dependencies)
  }

  def addRepositories(newRepositories: List[Repository]): Settings = {
    withRepositories(repositories ++ newRepositories)
  }

  def withRepositories(repositories: List[Repository]): Settings = {
    copy(repositories = repositories)
  }

  def withWriter(writer: Writer): Settings = {
    copy(writer = writer)
  }

  def withTtl(ttl: Option[Duration]): Settings = {
    copy(ttl = ttl)
  }

  def withForceVersions(forceVersions: List[Dependency]): Settings = {
    copy(forceVersions = forceVersions)
  }

  private[this] def copy(
      dependencies: List[Dependency] = this.dependencies,
      repositories: List[Repository] = this.repositories,
      writer: Writer = this.writer,
      ttl: Option[Duration] = this.ttl,
      forceVersions: List[Dependency] = this.forceVersions
  ): Settings = {
    new Settings(
      dependencies = dependencies,
      repositories = repositories,
      writer = writer,
      ttl = ttl,
      forceVersions = forceVersions
    )
  }
}
