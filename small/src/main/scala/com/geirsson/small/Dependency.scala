package com.geirsson.small

final class Dependency(
    val organization: String,
    val name: String,
    val version: String
) {
  override def toString: String = {
    s"""Dependency("$organization", "$name", "$version")"""
  }
}
