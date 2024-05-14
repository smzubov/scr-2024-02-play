package controllers

import com.google.inject.Inject
import models.dao.entities.{Author, PlayList, Song, SongsToPlayList}
import models.dao.repositories.SongRepository
import play.api.mvc.{Action, Controller}

class SongController @Inject()(val songRepository: SongRepository) extends Controller{
  def test() = Action{
    val s = Song("1", "hello", "1")
    val a = Author("1", "Sting")
    val pl1 = PlayList("1", "play list 1")
    val songToPl1 = SongsToPlayList("1", s.id, pl1.id)
//    songRepository.insert(s, a)
//    songRepository.insert(pl1)
//    songRepository.insert(songToPl1)
    val r = songRepository.getPlayList(pl1.id)
    Ok(r.toString())
  }
}
