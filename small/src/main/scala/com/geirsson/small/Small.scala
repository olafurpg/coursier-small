package com.geirsson.small

import java.nio.file.Path
import coursier._
import coursier.util.Gather
import coursier.util.Task
import java.io.OutputStreamWriter
import scala.concurrent.ExecutionContext.Implicits.global

object Small {
  def fetch(settings: Settings): List[Path] = {
    val dependencies = settings.dependencies.map { dep =>
      coursier.Dependency(Module(dep.organization, dep.name), dep.version)
    }
    val res = Resolution(dependencies.toSet)
    val repositories = settings.repositories.map {
      case Repository.Ivy2Local => Cache.ivy2Local
      case maven: Repository.Maven => MavenRepository(maven.root)
    }
    val term =
      new TermDisplay(new OutputStreamWriter(settings.out), fallbackMode = true)
    term.init()
    val fetch = Fetch.from(repositories, Cache.fetch[Task](logger = Some(term)))
    val resolution = res.process.run(fetch).unsafeRun()
    val errors = resolution.errors
    if (errors.nonEmpty) {
      val resolutionErrors = errors.map {
        case ((module, version), messages) =>
          new ResolutionError(
            new Dependency(module.organization, module.name, version),
            messages.toList
          )
      }
      throw new ResolutionException(settings, resolutionErrors.toList)
    }
    val artifacts = resolution.artifacts
    val localArtifacts = Gather[Task]
      .gather(artifacts.map(artifact => Cache.file[Task](artifact).run))
      .unsafeRun()
    val jars = localArtifacts.flatMap {
      case Left(e) => throw new IllegalArgumentException(e.describe)
      case Right(jar) if jar.getName.endsWith(".jar") => jar.toPath :: Nil
      case _ => Nil
    }
    term.stop()
    jars.toList
  }
}
