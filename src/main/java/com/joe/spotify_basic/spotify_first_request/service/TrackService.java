package com.joe.spotify_basic.spotify_first_request.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joe.spotify_basic.spotify_first_request.authentication.ClientCredentials;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Recommendations;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import com.wrapper.spotify.requests.data.browse.GetRecommendationsRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.LinkedHashMap;

public class TrackService extends ClientCredentials {
    public static Recommendations tracksByArtistBpm(int numberOfTracks, String seedArtist, Float bpm) {
        try {
            clientCredentials();
            GetRecommendationsRequest getRecommendationsRequest = spotifyApi.getRecommendations()
                    .limit(numberOfTracks)
                    .seed_artists(seedArtist)
                    .target_tempo(bpm)
                    .build();

            final Recommendations recommendations = getRecommendationsRequest.execute();

//            LinkedHashMap<String, String> artistSong = new LinkedHashMap<>();
//
//            for (int i = 0; i < numberOfTracks; i ++) {
//                TrackSimplified track = recommendations.getTracks()[i];
//                artistSong.put(track.getArtists()[0].getName(), track.getName());
//            }

            ObjectMapper mapper = new ObjectMapper();
            System.out.println(mapper.writeValueAsString(recommendations));
            return recommendations;

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    public static Recommendations bpmByString(String artistName, Float bpm, Integer numberOfTracks) {

        try {
            Paging<Artist> artist = ArtistService.searchArtist(artistName, 1);
            String seedArtist = artist.getItems()[0].getId();
            if (numberOfTracks > 100) numberOfTracks = 100;
            ObjectMapper mapper = new ObjectMapper();
            Recommendations recommendations = TrackService.tracksByArtistBpm(numberOfTracks, seedArtist, bpm);
            return recommendations;

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println(artistName + " could not be found");
        }

        return null;
    }

}
