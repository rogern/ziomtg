package se.rogern

import scala.util.Try

package object ziomtg {
  def by[A, B](fn: A => B)(from: A): Either[String, B] = {
    Try(fn(from)).toEither.left.map(_.getMessage)
  }
}
