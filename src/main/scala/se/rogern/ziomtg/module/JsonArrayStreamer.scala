package se.rogern.ziomtg.module

import zio.{Chunk, Layer, TaskLayer, ZIO, ZManaged}
import zio.blocking.Blocking
import zio.json.JsonDecoder
import zio.stream.{Stream, Transducer, ZStream, ZTransducer}

import java.io.IOException
import java.net.URL

/**
 * Parts of code taken from:
 * ´https://github.com/zio/zio-json/blob/develop/zio-json/jvm/src/main/scala/zio/JsonPackagePlatformSpecific.scala´
 * This to be able to stream from json array.
 * License provided in ´zio-json-license/LICENCE´
 */
class JsonArrayStreamer(zioLayer: TaskLayer[Blocking]) {
  def readJsonItem[A: JsonDecoder](url: URL): Stream[Throwable, A] = {
    val managed = ZManaged
      .fromAutoCloseable(ZIO.effect(url.openStream()))
      .refineToOrDie[IOException]

    Stream
      .fromInputStreamManaged(managed)
      .transduce(
        ZTransducer.utf8Decode >>>
          stringToChars >>>
          JsonDecoder[A].decodeJsonTransducer()
      ).provideLayer(zioLayer)
  }

  def stringToChars: Transducer[Nothing, String, Char] =
    ZTransducer
      .fromFunction[String, Chunk[Char]](s => Chunk.fromArray(s.toCharArray))
      .mapChunks(_.flatten)
}
