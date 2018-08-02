package com.geirsson.coursiersmall

final class Dependency(
    val organization: String,
    val name: String,
    val version: String
) {

  def asGradleString: String = s"$organization:$name:$version"

  override def toString: String = {
    s"""Dependency("$organization", "$name", "$version")"""
  }
}
