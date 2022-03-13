package se.rogern.ziomtg

import java.net.URI
import java.util.UUID

object TestData {

  val testCard: Card = Card(
    id = UUID.fromString("0000579f-7b35-4ed3-b44c-db2a538066fe"),
    imageUris = Some(ImageUris(
      small = URI.create(
        "https://c1.scryfall.com/file/scryfall-cards/small/front/0/0/0000579f-7b35-4ed3-b44c-db2a538066fe.jpg?1562894979"),
      normal = URI.create(
        "https://c1.scryfall.com/file/scryfall-cards/normal/front/0/0/0000579f-7b35-4ed3-b44c-db2a538066fe.jpg?1562894979"),
      large = URI.create(
        "https://c1.scryfall.com/file/scryfall-cards/large/front/0/0/0000579f-7b35-4ed3-b44c-db2a538066fe.jpg?1562894979"),
      png = URI.create(
        "https://c1.scryfall.com/file/scryfall-cards/png/front/0/0/0000579f-7b35-4ed3-b44c-db2a538066fe.png?1562894979"),
      artCrop = URI.create(
        "https://c1.scryfall.com/file/scryfall-cards/art_crop/front/0/0/0000579f-7b35-4ed3-b44c-db2a538066fe.jpg?1562894979"),
      borderCrop = URI.create(
        "https://c1.scryfall.com/file/scryfall-cards/border_crop/front/0/0/0000579f-7b35-4ed3-b44c-db2a538066fe.jpg?1562894979")
    )),
    manaCost = Some(ManaCost(ManaColor.Colorless(5), ManaColor.Red))
  )

}
