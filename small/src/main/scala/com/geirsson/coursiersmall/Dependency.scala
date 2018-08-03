package com.geirsson.coursiersmall
import scala.runtime.Statics

final class Dependency(
    val organization: String,
    val name: String,
    val version: String
) {

  override def hashCode(): Int = {
    var acc: Int = -889275714
    acc = Statics.mix(acc, Statics.anyHash(organization))
    acc = Statics.mix(acc, Statics.anyHash(name))
    acc = Statics.mix(acc, Statics.anyHash(version))
    Statics.finalizeHash(acc, 4)
  }

  override def equals(obj: scala.Any): Boolean =
    this.eq(obj.asInstanceOf[Object]) || (obj match {
      case d: Dependency =>
        organization == d.organization &&
          name == d.name &&
          version == d.version
      case _ => false
    })

  def asCoursierString: String = s"$organization:$name:$version"

  override def toString: String = {
    s"""Dependency("$organization", "$name", "$version")"""
  }
}
