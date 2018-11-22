package com.geirsson.coursiersmall

sealed abstract class Repository
object Repository {
  final class Maven(val root: String) extends Repository {
    override def toString: String = {
      s"""Maven("$root")"""
    }
  }

  final class Ivy(val root: String) extends Repository {
    override def toString: String = {
      s"""Ivy("$root")"""
    }
  }

  case object Ivy2Local extends Repository

  def MavenCentral: Repository =
    new Maven("https://repo1.maven.org/maven2")
  def SonatypeReleases: Repository =
    new Maven("https://oss.sonatype.org/content/repositories/releases")
  def SonatypeSnapshots: Repository =
    new Maven("https://oss.sonatype.org/content/repositories/snapshots")
  def bintrayRepo(owner: String, repo: String): Repository =
    new Maven(s"https://dl.bintray.com/$owner/$repo/")
  def bintrayIvyRepo(owner: String, repo: String): Repository =
    new Ivy(s"https://dl.bintray.com/$owner/$repo/")
}
