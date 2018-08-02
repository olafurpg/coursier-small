package com.geirsson.small

final class ResolutionException(
    settings: Settings,
    errors: List[ResolutionError]
) extends Exception {
  override def getMessage: String = {
    val sb = new StringBuilder
    sb.append("Failed to resolve ")
      .append(settings.toString)
      .append("\n")
    errors.foreach { error =>
      val dep = error.dependency.toString
      sb.append(dep)
        .append(error.errors.mkString("\n" + (" " * dep.length)))
        .append("\n")
    }
    sb.toString()
  }
}
