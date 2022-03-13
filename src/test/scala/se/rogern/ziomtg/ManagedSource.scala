package se.rogern.ziomtg

import zio.json._
import zio.{Managed, Task, ZIO, ZManaged}

import scala.io.{BufferedSource, Source}

object ManagedSource {
  def apply(fileName: String): ZManaged[Any, Throwable, BufferedSource] =
    Managed.makeEffect(Source.fromResource(fileName))(_.close())

  implicit class Enricher(m: ZManaged[Any, Throwable, BufferedSource]) {
    def toJson[T: JsonDecoder]: Task[T] =
      m.use(src => ZIO.fromEither(src.getLines().mkString.fromJson[T]).mapError(new Exception(_)))
  }
}
