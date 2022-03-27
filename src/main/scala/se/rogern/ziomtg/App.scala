package se.rogern.ziomtg

import se.rogern.ziomtg.module.JsonArrayStreamer
import zio.blocking.Blocking
import zio.console._
import zio.stream._
import zio.{ExitCode, Task, URIO}

object App extends zio.App {

  val jsonArrayStreamer = new JsonArrayStreamer(Blocking.live)

  def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    mainProgram.exitCode

  val sink: Sink[Throwable, Card, Any, Unit] =
    ZSink.foreach(c => putStrLn(c.toString).provideLayer(Console.live))

  val mainProgram: Task[Unit] = {
    for {
      conf <- AppConfig()
      _ <- jsonArrayStreamer.readJsonItem[Card](conf.cardSource).run(sink)
    } yield ()
  }
}