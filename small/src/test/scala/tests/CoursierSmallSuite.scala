package tests

import com.geirsson.coursiersmall.Repository.Ivy
import com.geirsson.coursiersmall._
import utest._

object CoursierSmallSuite extends TestSuite {
  val scalafmt = new Dependency("com.geirsson", "scalafmt-cli_2.12", "1.5.1")

  def settings(dep: Dependency): Settings = {
    new Settings()
      .withDependencies(List(dep))
      .withTtl(None)
      .withForceVersions(Nil)
  }
  val tests = Tests {
    "success" - {
      val jars = CoursierSmall.fetch(settings(scalafmt))
      assert(
        jars.exists(
          jar => jar.getFileName.toString == "scalafmt-cli_2.12-1.5.1.jar"
        )
      )
    }
    "resolution-fail" - {
      intercept[ResolutionException] {
        CoursierSmall.fetch(
          settings(new Dependency("doesnotexist", "foo", "1.0"))
        )
      }
    }
    "forceVersion" - {
      val scalafmt16 =
        new Dependency("com.geirsson", "scalafmt-cli_2.12", "1.6.0-RC4")
      val jars = CoursierSmall.fetch(
        settings(scalafmt).withForceVersions(List(scalafmt16))
      )
      assert(
        jars.exists(
          jar => jar.getFileName.toString == "scalafmt-cli_2.12-1.6.0-RC4.jar"
        )
      )
    }
    "ivy" - {
      val assembly =
        new Dependency(
          "com.eed3si9n",
          "sbt-assembly;sbtVersion=1.0;scalaVersion=2.12",
          "0.14.9"
        )
      val jars = CoursierSmall.fetch(
        settings(assembly)
          .withRepositories(
            new Settings().repositories ++
              List(Repository.bintrayIvyRepo("sbt", "sbt-plugin-releases"))
          )
      )
      assert(
        jars.exists(jar => jar.getFileName.toString.contains("sbt-assembly"))
      )
    }
    "cache-policy" - {
      val assembly =
        new Dependency(
          "ch.epfl.scala",
          "bloop-frontend_2.12",
          "1.0.0+369-a2222610"
        )
      val jars = CoursierSmall.fetch(
        settings(assembly).addRepositories(
          List(Repository.bintrayRepo("scalacenter", "releases"))
        )
      )
      assert(
        jars.exists(jar => jar.getFileName.toString.contains("frontend"))
      )
    }
    "sources" - {
      val ujson = new Dependency(
        "com.lihaoyi",
        "ujson_2.12",
        "0.7.1"
      )
      val jars =
        CoursierSmall.fetch(
          settings(ujson).withClassifiers(List("sources", "_"))
        )
      assert(
        jars.exists(jar => jar.getFileName.toString.endsWith("-sources.jar"))
      )
      assert(
        jars.exists(jar => !jar.getFileName.toString.endsWith("-sources.jar"))
      )
    }
  }
}
