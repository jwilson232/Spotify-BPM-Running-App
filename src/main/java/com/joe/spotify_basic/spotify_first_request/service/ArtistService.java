package com.joe.spotify_basic.spotify_first_request.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joe.spotify_basic.spotify_first_request.authentication.ClientCredentials;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.requests.data.search.simplified.SearchArtistsRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ArtistService extends ClientCredentials {
    public static Paging<Artist> searchArtist(String artistName, int limit) {
        try {
            clientCredentials();
            SearchArtistsRequest searchArtistsRequest = spotifyApi.searchArtists(artistName)
                    .limit(limit)
                    .build();

            final Paging<com.wrapper.spotify.model_objects.specification.Artist> artistPaging = searchArtistsRequest.execute();

            return artistPaging;

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public static Paging<Artist> searchArtistObject(String artistName, int limit) {
        try {
            clientCredentials();
            SearchArtistsRequest searchArtistsRequest = spotifyApi.searchArtists(artistName)
                    .limit(limit)
                    .build();

            final Paging<com.wrapper.spotify.model_objects.specification.Artist> artistPaging = searchArtistsRequest.execute();
            return artistPaging;

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

}

