package com.geirsson.coursiersmall

sealed abstract class Repository
object Repository {
  final class Maven(val root: String) extends Repository {
    override def toString: String = {
      s"""Maven("$root")"""
    }
  }
  case object Ivy2Local extends Repository

  def MavenCentral: Repository =
    new Maven("https://repo1.maven.org/maven2")
  def SonatypeReleases: Repository =
    new Maven("https://oss.sonatype.org/content/repositories/releases")
  def SonatypeSnapshots: Repository =
    new Maven("https://oss.sonatype.org/content/repositories/snapshots")
}
