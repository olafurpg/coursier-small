package tests

import com.geirsson.coursiersmall._
import java.nio.file.Path
import utest._

object CoursierSmallSuite extends TestSuite {
  val scalafmt = new Dependency("com.geirsson", "scalafmt-cli_2.12", "1.5.1")

  def fetch(dep: Dependency): List[Path] = {
    val settings = new Settings().withDependencies(List(dep))
    CoursierSmall.fetch(settings)
  }
  val tests = Tests {
    "success" - {
      val jars = fetch(scalafmt)
      assert(
        jars.exists(jar =>
          jar.getFileName.toString == "scalafmt-cli_2.12-1.5.1.jar")
      )
    }
    "resolution-fail" - {
      intercept[ResolutionException] {
        fetch(new Dependency("doesnotexist", "foo", "1.0"))
      }
    }
  }
}
