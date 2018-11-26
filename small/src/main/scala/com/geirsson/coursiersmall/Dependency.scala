package com.geirsson.coursiersmall
import scala.runtime.Statics

final class Dependency private (
    val organization: String,
    val name: String,
    val version: String,
    val transitive: Boolean
) {
  def this(
      organization: String,
      name: String,
      version: String
  ) = this(
    organization,
    name,
    version,
    true
  )

  def withTransitive(isTransitive: Boolean): Dependency =
    copy(transitive = isTransitive)

  override def hashCode(): Int = {
    var acc: Int = -889275714
    acc = Statics.mix(acc, Statics.anyHash(organization))
    acc = Statics.mix(acc, Statics.anyHash(name))
    acc = Statics.mix(acc, Statics.anyHash(version))
    acc = Statics.mix(acc, Statics.anyHash(transitive))
    Statics.finalizeHash(acc, 4)
  }

  override def equals(obj: scala.Any): Boolean =
    this.eq(obj.asInstanceOf[Object]) || (obj match {
      case d: Dependency =>
        organization == d.organization &&
          name == d.name &&
          version == d.version &&
          transitive == d.transitive
      case _ => false
    })

  def asCoursierString: String = s"$organization:$name:$version"

  override def toString: String = {
    val intransitive =
      if (transitive) ""
      else ".intransitive"
    s"""Dependency("$organization", "$name", "$version")$intransitive"""
  }

  private[this] def copy(
      organization: String = this.organization,
      name: String = this.name,
      version: String = this.version,
      transitive: Boolean = this.transitive
  ): Dependency = {
    new Dependency(
      organization = organization,
      name = name,
      version = version,
      transitive = transitive
    )
  }
}
