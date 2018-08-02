package com.geirsson.small

import java.io.PrintStream

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
      dependencies = Nil,
      repositories = Nil,
      out = System.out
    )
  }

  def withModules(dependencies: List[Dependency]): Settings = {
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
