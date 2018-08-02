package com.geirsson.small

final class ResolutionError(
    val dependency: Dependency,
    val errors: List[String]
) {
  override def toString: String = {
    s"""ResolutionError($dependency, $errors)"""
  }
}
