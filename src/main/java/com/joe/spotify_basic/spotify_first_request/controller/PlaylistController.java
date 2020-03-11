package com.joe.spotify_basic.spotify_first_request.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joe.spotify_basic.spotify_first_request.authentication.ClientCredentials;
import com.joe.spotify_basic.spotify_first_request.service.ArtistService;
import com.joe.spotify_basic.spotify_first_request.service.PlaylistService;
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
public class PlaylistController {

    @GetMapping("/bpm")
    public static String createPlaylist(@RequestParam(value = "accessToken") String accessToken,
                                        @RequestParam(value = "noOfTracks") Integer noOfTracks,
                                        @RequestParam(value = "artist") String artist,
                                        @RequestParam(value = "bpm") Float bpm) {

        if (noOfTracks > 80) noOfTracks = 80;
        return PlaylistService.createSeedBpmPlaylist(accessToken, noOfTracks, artist, bpm);
    }
}
