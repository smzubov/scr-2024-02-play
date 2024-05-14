package models.dao.repositories

import models.dao.entities.{Author, PlayList, Song, SongDB, SongsToPlayList}

trait SongRepository {
   def insert(a: Author): Author
   def insert(s: Song, a: Author): Song
   def getAuthor(id: String):  (Author, List[Song])
   def getAuthor(s: Song): Author
   def delete(author: Author): Unit
   def insert(playList: PlayList): PlayList
   def insert(songsToPlayList: SongsToPlayList): SongsToPlayList
   def getPlayList(id: String): (PlayList, List[Song])
}

class SongRepositoryImpl extends SongRepository{
  import org.squeryl.PrimitiveTypeMode._

  val authorSchema = SongDB.author
  val songSchema = SongDB.songs
  val playListSchema = SongDB.playList

  override def insert(a: Author): Author = transaction(authorSchema.insert(a))

  override def insert(s: Song, a: Author): Song = transaction {
    a.songs.associate(s)
  }

  def insert(playList: PlayList): PlayList =
    transaction(playListSchema.insert(playList))

  def insert(songsToPlayList: SongsToPlayList): SongsToPlayList =
    transaction(SongDB.songToPlayList.insert(songsToPlayList))

  override def getAuthor(id: String): (Author, List[Song]) = transaction{
    val a = from(authorSchema)(a => where(a.id === id) select(a)).head
    (a, a.songs.toList)
  }

  def getPlayList(id: String): (PlayList, List[Song]) = transaction{
    val pl = from(playListSchema)(pl => where(pl.id === id) select(pl)).head
    (pl, pl.songs.toList)
  }

  override def getAuthor(s: Song): Author = ???

  override def delete(author: Author): Unit = transaction{
    author.songs.deleteAll
    authorSchema.deleteWhere(_.id === author.id)
  }
}
