package com.geirsson.coursiersmall

final class ResolutionException(
    val settings: Settings,
    val errors: List[ResolutionError]
) extends Exception {
  override def getMessage: String = {
    val sb = new StringBuilder
    sb.append("Failed to resolve ")
      .append(settings.toString)
      .append("\n")
    errors.foreach { error =>
      val dep = error.dependency.asCoursierString
      sb.append("Dependency ")
        .append("'")
        .append(dep)
        .append("'")
        .append(error.errors.mkString("\n  ", "\n  ", ""))
    }
    sb.toString()
  }
}

final class ResolutionError(
    val dependency: Dependency,
    val errors: List[String]
) {
  override def toString: String = {
    s"""ResolutionError($dependency, $errors)"""
  }
}
