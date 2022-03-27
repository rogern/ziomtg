package se.rogern.ziomtg

import zio.json.{DeriveJsonDecoder, JsonDecoder, jsonField}

import java.net.URI
import java.util.UUID
import scala.util.matching.Regex

case class ImageUris(
    small: URI,
    normal: URI,
    large: URI,
    png: URI,
    @jsonField("art_crop") artCrop: URI,
    @jsonField("border_crop") borderCrop: URI,
)

object ImageUris {
  implicit val u: JsonDecoder[URI]       = JsonDecoder[String].mapOrFail(by(URI.create))
  implicit val d: JsonDecoder[ImageUris] = DeriveJsonDecoder.gen[ImageUris]
}

trait ManaColor

object ManaColor {
  case object Red                   extends ManaColor
  case object White                 extends ManaColor
  case object Black                 extends ManaColor
  case object Blue                  extends ManaColor
  case object Green                 extends ManaColor
  case class Colorless(amount: Int) extends ManaColor
  case class Special(value: String) extends ManaColor

  def fromList(colors: List[String]): List[ManaColor] = colors.map {
    case "R"         => Red
    case "W"         => White
    case "B"         => Black
    case "U"         => Blue
    case "G"         => Green
    case Int(amount) => Colorless(amount)
    case c           => Special(c)
  }

  object Int {
    def unapply(s: String): Option[Int] = util.Try(s.toInt).toOption
  }
}

case class ManaCost(costs: List[ManaColor])

object ManaCost {

  def apply(mc: ManaColor*): ManaCost = ManaCost(mc.toList)

  implicit val d: JsonDecoder[Option[ManaCost]] = JsonDecoder.option[String].map {
    case None | Some("")         => None
    case Some(ValidManaCost(mc)) => Some(mc)
  }

  object ValidManaCost {
    val manaCost: Regex = """(\d|\w)""".r

    def unapply(y: String): Option[ManaCost] = {
      manaCost.findAllMatchIn(y).toList match {
        case Nil  => None
        case list => Some(ManaCost(ManaColor.fromList(list.map(_.matched))))
      }
    }
  }
}

case class Card(
    id: UUID,
    @jsonField("image_uris") imageUris: Option[ImageUris],
    @jsonField("mana_cost") manaCost: Option[ManaCost]
)

object Card {
  implicit val u: JsonDecoder[UUID] = JsonDecoder[String].mapOrFail(by(UUID.fromString))
  implicit val d: JsonDecoder[Card] = DeriveJsonDecoder.gen
}
