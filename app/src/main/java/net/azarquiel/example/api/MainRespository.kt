package net.azarquiel.example.api

import net.azarquiel.example.model.*

class MainRespository {

    val service = WebAccess.spotifyService

    //Top Artists - LoggedIn Activity
    suspend fun getTopArtists(): List<Artist> {
        val webResponse = service.getTopArtists().await()
        if(webResponse.isSuccessful){
            return webResponse.body()!!.items
        }
        return emptyList()
    }

    //Top Tracks - LoggedIn Activity
    suspend fun getTopTracks(): List<Tracks> {
        val webResponse = service.getTopTracks().await()
        if(webResponse.isSuccessful){
            return webResponse.body()!!.items
        }
        return emptyList()
    }

    //Recently Played - RecentlyPlayed Activity
    suspend fun getRecentlyPlayed(): List<RecentlyPlayedTrack> {
        val webResponse = service.getRecentlyPlayed().await()
        if (webResponse.isSuccessful){
            return webResponse.body()!!.items
        }
        return emptyList()
    }

    //Search Album - Search Activity
    suspend fun searchAlbum(albumString: String): SearchAlbumClass? {
        val webResponse = service.searchAlbum(albumString).await()
        if (webResponse.isSuccessful){
            return webResponse.body()!!.albums
        }
        return null
    }

    //Search Artist - Search Activity
    suspend fun searchArtist(stringArtist: String): SearchArtistClass? {
        val webResponse = service.searchArtist(stringArtist).await()
        if (webResponse.isSuccessful){
            return webResponse.body()!!.artists
        }
        return null
    }

    //Search Track - Search Activity
    suspend fun searchTrack(stringTrack: String): SeachTrackClass? {
        val webResponse = service.searchTrack(stringTrack).await()
        if (webResponse.isSuccessful){
            return webResponse.body()!!.tracks
        }
        return null
    }

    //get user data - Artist Comment Activity
    suspend fun getUserData(): MyUser? {
        val webResponse = service.getUserData().await()
        if (webResponse.isSuccessful){
            return webResponse.body()!!.copy()
        }
        return null
    }

    //get artistas relacionados de artist - Artist Activity
    suspend fun getArtistRelatedArtists(id: String): List<Artist> {
        val webResponse = service.getArtistRelatedArtists(id).await()
        if(webResponse.isSuccessful){
            return webResponse.body()!!.artists
        }
        return emptyList()
    }

    //get top tracks de artist  - Artist Activity
    suspend fun getArtistTopTracks(id: String): List<Tracks> {
        val webResponse = service.getArtistTopTracks(id).await()
        if(webResponse.isSuccessful){
            return webResponse.body()!!.tracks
        }
        return emptyList()
    }

    //get albums de artist -  - Artist Activity
    suspend fun getArtistAlbums(id: String): List<Album> {
        val webResponse = service.getArtistAlbums(id).await()
        if(webResponse.isSuccessful){
            return webResponse.body()!!.items
        }
        return emptyList()
    }

    //get singles de artist  - Artist Activity
    suspend fun getArtistSingles(id: String): List<Album> {
        val webResponse = service.getArtistSingles(id).await()
        if(webResponse.isSuccessful){
            return webResponse.body()!!.items
        }
        return emptyList()
    }
}