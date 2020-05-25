package net.azarquiel.example.model

import java.io.Serializable

//RV TopArtist LoggedInActivity

data class Artist (
    var followers: Followers,
    var genres: List<String>,
    var id: String,
    var images: List<Images>,
    var name: String,
    var popularity: Int
): Serializable

data class Followers (
    var total: Int
): Serializable

data class Images(
    var height: Int,
    var width: Int,
    var url: String
): Serializable

data class ResponseTopArtistsLoggedIn (
    val items: List<Artist>
)

//RV TopSongs LoggedInActivity

data class Tracks (
    var album: Album,
    var artists: List<Artistas>,
    var duration_ms: Int,
    var id: String,
    var name: String,
    var preview_url: String
)

data class Album (
    var album_type: String,
    var artists: List<Artistas>,
    var external_urls: AlbumUrl,
    var id: String,
    var images: List<Images>,
    var name: String,
    var release_date : String,
    var total_tracks: Int
)

data class Artistas (
    var id: String,
    var name: String
)

data class AlbumUrl(
    var spotify: String
)

data class ResponseTopTracksLoggedIn(
    val items: List<Tracks>
)

//RV RecentlyPlayed RecentlyPlayed

data class RecentlyPlayedTrack(
    var track: Tracks,
    var played_at: String
)

data class ResponseRecentlyPlayed(
    val items: List<RecentlyPlayedTrack>
)

//search album - Search Activity
data class SearchAlbumClass(
    var items: List<Album>
)

data class ResponseSearchAlbum(
    val albums: SearchAlbumClass
)

//search artist - Search Activity
data class SearchArtistClass(
    var items: List<Artist>
)

data class ResponseSearchArtist(
    val artists: SearchArtistClass
)

//search track - Search Activity
data class SeachTrackClass(
    var items: List<Tracks>
)

data class ResponseSearchTrack(
    val tracks: SeachTrackClass
)

//comentarios en perfil de artist - Artist Comments Activity
data class ArtistComment(
    var artist_id: String = "",
    var user_id: String = "",
    var comment: String = "",
    var timestamp: String = "",
    var user_img: String = ""
)

//datos de usuario para publicar comment - Artist Comments Activity
data class MyImages(
    val url: String
)

data class MyUser(
    var id: String,
    var images: List<MyImages>
)


//albums de aritsta - Artist Activity
data class ResponseArtistAlbums(
    val items: List<Album>
)

//singles de artista - Artist Activity
data class ResponseArtistSingles(
    val items: List<Album>
)

//artistas relacionados de un artista - Artist Activity
data class ResponseArtistRelatedArtists(
    val artists: List<Artist>
)

//top tracks de un artista - Artist Activity
data class ResponseArtistTopTracks(
    val tracks: List<Tracks>
)


