package com.joe.spotify_basic.spotify_first_request.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joe.spotify_basic.spotify_first_request.authentication.ClientCredentials;
import com.joe.spotify_basic.spotify_first_request.service.ArtistService;
import com.joe.spotify_basic.spotify_first_request.service.TrackService;
import com.joe.spotify_basic.spotify_first_request.service.UserService;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.SnapshotResult;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.playlists.AddTracksToPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController extends ClientCredentials {

    @GetMapping("/bpm")
    public static String createPlaylist(@RequestParam(value = "accessToken") String accessToken,
                                        @RequestParam(value = "noOfTracks") Integer noOfTracks,
                                        @RequestParam(value = "artist") String artist,
                                        @RequestParam(value = "bpm") Float bpm) {
        try {
            User user = UserService.getCurrentUser(accessToken);

            ObjectMapper mapper = new ObjectMapper();

            if (noOfTracks > 100) noOfTracks = 100;
            Recommendations recommendations = TrackService.bpmByString(artist, bpm, noOfTracks);
            Paging<Artist> fullArtist = ArtistService.searchArtist(artist, 1);
            String name = fullArtist.getItems()[0].getName() + " - " + bpm + " bpm";

            SpotifyApi spotifyApiAuthenticate = new SpotifyApi.Builder()
                    .setAccessToken(accessToken)
                    .build();

            spotifyApiAuthenticate.setAccessToken(accessToken);

            CreatePlaylistRequest createPlaylistRequest = spotifyApiAuthenticate.createPlaylist(user.getId(), name)
                    .build();
            final Playlist playlist = createPlaylistRequest.execute();

            List<String> urisArrayList = new ArrayList<>();

            for (int i = 0; i < recommendations.getTracks().length; i++) {
                urisArrayList.add(recommendations.getTracks()[i].getUri());
            }

            //final String[] uris = new String[]{"spotify:track:01iyCAUm8EvOFqVWYJ3dVX", "spotify:track:6Ui2yvA0D5bXxZiPFWd47y"};
            String[] uris = new String[urisArrayList.size()];
            uris = urisArrayList.toArray(uris);

            System.out.println(uris);

            AddTracksToPlaylistRequest addTracksToPlaylistRequest = spotifyApiAuthenticate
                    .addTracksToPlaylist(playlist.getId(), uris)
                    .build();

            try {
                final SnapshotResult snapshotResult = addTracksToPlaylistRequest.execute();
                // System.out.println("Snapshot ID: " + snapshotResult.getSnapshotId());
            } catch (IOException | SpotifyWebApiException e) {
                System.out.println("Add track to playlist Error: " + e.getMessage());
            }


            String newPlaylistJson = mapper.writeValueAsString(playlist);
            System.out.println(newPlaylistJson);
            return mapper.writeValueAsString(recommendations);

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("This Error: " + e.getMessage());
        }

        return null;
    }
}
