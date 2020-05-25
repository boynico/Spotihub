package net.azarquiel.example.api

import kotlinx.coroutines.Deferred
import net.azarquiel.example.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SpotifyService {

    // get Top Artists list - LoggedInActivity
    @GET("getTopArtists")
    fun getTopArtists(): Deferred<Response<ResponseTopArtistsLoggedIn>>

    //get Top Tracks list - LoggedInActivity
    @GET("getTopTracks")
    fun getTopTracks(): Deferred<Response<ResponseTopTracksLoggedIn>>

    //get Recently Played Tracks list - RecentlyPlayedActivity
    @GET("getRecentlyPlayed")
    fun getRecentlyPlayed(): Deferred<Response<ResponseRecentlyPlayed>>

    //get search Album - SearchActivity
    @GET("searchAlbum")
    fun searchAlbum(
        @Query("stringAlbum") stringAlbum: String
    ): Deferred<Response<ResponseSearchAlbum>>

    //get search Album - SearchActivity
    @GET("searchArtist")
    fun searchArtist(
        @Query("stringArtist") stringArtist: String
    ): Deferred<Response<ResponseSearchArtist>>

    //get search Album - SearchActivity
    @GET("searchTrack")
    fun searchTrack(
        @Query("stringTrack") stringTrack: String
    ): Deferred<Response<ResponseSearchTrack>>

    //get user data - Artist Comment activity
    @GET("getUserData")
    fun getUserData(): Deferred<Response<MyUser>>

    //get artist albums - Artist Activity
    @GET("getArtistAlbums")
    fun getArtistAlbums(
        @Query("id") id: String
    ): Deferred<Response<ResponseArtistAlbums>>

    //get artist singles - Artist Activity
    @GET("getArtistSingles")
    fun getArtistSingles(
        @Query("id") id: String
    ): Deferred<Response<ResponseArtistSingles>>

    //get artistas relacionados de un artista - Artist Activity
    @GET("getArtistRelatedArtists")
    fun getArtistRelatedArtists(
        @Query("id") id: String
    ): Deferred<Response<ResponseArtistRelatedArtists>>

    //get top tracks de un artist - Artist Activity
    @GET("getArtistTopTracks")
    fun getArtistTopTracks(
        @Query("id") id: String
    ): Deferred<Response<ResponseArtistTopTracks>>
}