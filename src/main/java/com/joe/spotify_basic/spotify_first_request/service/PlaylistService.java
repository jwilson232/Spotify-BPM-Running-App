package com.joe.spotify_basic.spotify_first_request.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joe.spotify_basic.spotify_first_request.authentication.ClientCredentials;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.SnapshotResult;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.playlists.AddTracksToPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistService extends ClientCredentials {

    public static String createSeedBpmPlaylist(String accessToken, Integer noOfTracks, String artist, Float bpm) {
        try {
            User user = UserService.getCurrentUser(accessToken);
            spotifyApi.setAccessToken(accessToken);

            ObjectMapper mapper = new ObjectMapper();

            Recommendations recommendations = TrackService.bpmByString(artist, bpm, noOfTracks);
            Paging<Artist> fullArtist = ArtistService.searchArtist(artist, 1);
            String name = fullArtist.getItems()[0].getName() + " - " + bpm + " bpm";



            CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(user.getId(), name)
                    .build();

            final Playlist playlist = createPlaylistRequest.execute();

            List<String> urisArrayList = new ArrayList<>();

            for (int i = 0; i < recommendations.getTracks().length; i++) {
                urisArrayList.add(recommendations.getTracks()[i].getUri());
            }

            String[] uris = new String[urisArrayList.size()];
            uris = urisArrayList.toArray(uris);
            String[] urisHardCore = new String[]{"spotify:track:01iyCAUm8EvOFqVWYJ3dVX", "spotify:track:01iyCAUm8EvOFqVWYJ3dVX"};
            AddTracksToPlaylistRequest addTracksToPlaylistRequest = spotifyApi
                    .addTracksToPlaylist(playlist.getId(), uris)
                    .build();

            try {
                final SnapshotResult snapshotResult = addTracksToPlaylistRequest.execute();
                System.out.println(mapper.writeValueAsString(snapshotResult));
                //System.out.println("Snapshot ID: " + snapshotResult.getSnapshotId());
            } catch (IOException | SpotifyWebApiException e) {
                System.out.println("Add track to playlist Error: " + e.getMessage());
            }

            String newPlaylistJson = mapper.writeValueAsString(playlist);
            return mapper.writeValueAsString(newPlaylistJson);

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("This Error: " + e.getMessage());
        }
        return null;
    }
}
