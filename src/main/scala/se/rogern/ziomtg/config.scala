package se.rogern.ziomtg

import zio.IO
import zio.config.magnolia.{DeriveConfigDescriptor, Descriptor}
import zio.config.typesafe.TypesafeConfigSource
import zio.config.{ReadError, _}

import java.net.URI

case class AppConfig(cardSource: URI)

object AppConfig {

  private val uriMatcher: String => Either[String, URI] = {
    case s"classpath:$path" => Right(getClass.getResource(path).toURI)
    case unhandled          => Left(s"Unhandled source in config: $unhandled")
  }

  private val customDerivation = new DeriveConfigDescriptor {
    override def mapClassName(name: String): String = toSnakeCase(name)
    override def mapFieldName(name: String): String = toKebabCase(name)
    override val implicitUriDesc: Descriptor[URI] =
      Descriptor[String].transformOrFail(uriMatcher, r => Right(r.toString))
  }

  import customDerivation._

  def apply(): IO[ReadError[String], AppConfig] =
    read(descriptor[AppConfig] from TypesafeConfigSource.fromResourcePath)
}
