package models.dao.entities

import org.squeryl.dsl.ManyToMany
import org.squeryl.{KeyedEntity, Query, Schema}

case class Author(id: String, name: String) extends KeyedEntity[String]{
  lazy val songs = SongDB.authorSongs.left(this)
}

case class Song(id: String, title: String, author: String) extends KeyedEntity[String]{
  lazy val playLists: Query[PlayList] with ManyToMany[PlayList, SongsToPlayList] = SongDB.songsToPlayList.left(this)
}

case class PlayList(id: String, title: String) extends KeyedEntity[String] {
  lazy val songs: Query[Song] with ManyToMany[Song, SongsToPlayList] = SongDB.songsToPlayList.right(this)
}

case class SongsToPlayList(id: String, songId: String, playListId: String) extends KeyedEntity[String]


object SongDB extends Schema{
  import org.squeryl.PrimitiveTypeMode._
  val author = table[Author]
  val songs = table[Song]
  val playList = table[PlayList]
  val songToPlayList = table[SongsToPlayList]

  val songsToPlayList = manyToManyRelation(songs, playList)
    .via[SongsToPlayList]((s, p, stp) =>
      (stp.songId === s.id, stp.playListId === p.id))

  val authorSongs = oneToManyRelation(author, songs).via(_.id === _.author)
}