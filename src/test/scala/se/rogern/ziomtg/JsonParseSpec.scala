package se.rogern.ziomtg

import zio.test.Assertion._
import zio.test.{DefaultRunnableSpec, _}

object JsonParseSpec extends DefaultRunnableSpec {

  import ManagedSource._
  import TestData._

  override def spec = suite("Parse card")(
    testM("should create card") {
      for {
        card <- ManagedSource("card.json").toJson[Card]
      } yield {
        assert(card.id)(equalTo(testCard.id)) &&
        assert(card.imageUris)(equalTo(testCard.imageUris)) &&
        assert(card.manaCost)(equalTo(testCard.manaCost))
      }
    },
    testM("should handle empty mana cost") {
      for {
        card <- ManagedSource("empty_mana_cost.json").toJson[Card]
      } yield {
        assert(card.manaCost)(isNone)
      }
    },
    testM("should handle missing mana cost") {
      for {
        card <- ManagedSource("missing_mana_cost.json").toJson[Card]
      } yield {
        assert(card.manaCost)(isNone)
      }
    }
  )
}
