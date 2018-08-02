package com.geirsson.coursiersmall

import java.nio.file.Path

sealed abstract class FileException(val `type`: String, val message: String)
    extends Exception {
  override def getMessage: String = s"${`type`}: $message"

  final def notFound: Boolean = this match {
    case _: FileException.NotFound => true
    case _ => false
  }
}

object FileException {

  final class DownloadError(val reason: String)
      extends FileException("download error", reason)

  final class NotFound(val file: String, val permanent: Option[Boolean])
      extends FileException("not found", file)

  final class Unauthorized(val file: String, val realm: Option[String])
      extends FileException(
        "unauthorized",
        file + realm.fold("")(" (" + _ + ")"))

  final class ChecksumNotFound(val sumType: String, val file: String)
      extends FileException("checksum not found", file)

  final class ChecksumFormatError(val sumType: String, val file: String)
      extends FileException("checksum format error", file)

  final class WrongChecksum(
      val sumType: String,
      val got: String,
      val expected: String,
      val file: String,
      val sumFile: String
  ) extends FileException("wrong checksum", file)

  sealed abstract class Recoverable(`type`: String, message: String)
      extends FileException(`type`, message)

  final class Locked(val path: Path)
      extends Recoverable("locked", path.toString)
  final class ConcurrentDownload(val url: String)
      extends Recoverable("concurrent download", url)

}
