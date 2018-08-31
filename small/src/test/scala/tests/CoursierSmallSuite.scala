package tests

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
        jars.exists(jar =>
          jar.getFileName.toString == "scalafmt-cli_2.12-1.5.1.jar")
      )
    }
    "resolution-fail" - {
      intercept[ResolutionException] {
        CoursierSmall.fetch(
          settings(new Dependency("doesnotexist", "foo", "1.0")))
      }
    }
    "forceVersion" - {
      val scalafmt16 =
        new Dependency("com.geirsson", "scalafmt-cli_2.12", "1.6.0-RC4")
      val jars = CoursierSmall.fetch(
        settings(scalafmt).withForceVersions(List(scalafmt16)))
      assert(
        jars.exists(jar =>
          jar.getFileName.toString == "scalafmt-cli_2.12-1.6.0-RC4.jar")
      )
    }
  }
}
