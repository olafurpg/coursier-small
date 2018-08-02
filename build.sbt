import scala.xml.{Node => XmlNode, NodeSeq => XmlNodeSeq, _}
import scala.xml.transform.{RewriteRule, RuleTransformer}

inThisBuild(
  List(
    organization := "com.geirsson",
    homepage := Some(url("https://github.com/olafurpg/coursier-small")),
    licenses := List(
      "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        "olafurpg",
        "Ólafur Páll Geirsson",
        "olafurpg@gmail.com",
        url("https://geirsson.com")
      )
    ),
    scalaVersion := "2.12.6",
    crossScalaVersions := List(
      "2.12.6",
      "2.11.12",
      "2.10.6"
    ),
    testFrameworks += new TestFramework("utest.runner.Framework"),
    libraryDependencies += "com.lihaoyi" %% "utest" % "0.6.3" % Test
  )
)

skip in publish := true

lazy val small = project
  .settings(
    moduleName := "coursier-small",
    assemblyShadeRules.in(assembly) := Seq(
      ShadeRule.rename("coursier.**" -> "com.geirsson.coursier.shaded.@1").inAll
    ),
    artifact.in(Compile, packageBin) := artifact.in(Compile, assembly).value,
    assemblyOption.in(assembly) ~= { _.copy(includeScala = false) },
    addArtifact(artifact.in(Compile, packageBin), assembly),
    pomPostProcess := { node =>
      new RuleTransformer(new RewriteRule {
        override def transform(node: XmlNode): XmlNodeSeq = node match {
          case e: Elem if node.label == "dependency" =>
            Comment(
              "the dependency that was here has been absorbed via sbt-assembly"
            )
          case _ => node
        }
      }).transform(node).head
    },
    libraryDependencies ++= List(
      "io.get-coursier" %% "coursier-cache" % "1.1.0-M6"
    )
  )
