package com.joe.spotify_basic.spotify_first_request.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joe.spotify_basic.spotify_first_request.service.UserService;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.User;
import com.wrapper.spotify.requests.data.playlists.GetListOfUsersPlaylistsRequest;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/profile/{accessToken}")
    public static User getCurrentUsersProfile_Sync(@PathVariable(required = true) String accessToken) {
        return UserService.getCurrentUser(accessToken);
    }

    @GetMapping("/playlist/{accessToken}")
    public static String usersPlaylists(@PathVariable(required = true) String accessToken) {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();

        User user = getCurrentUsersProfile_Sync(accessToken);
        user.getId();

        GetListOfUsersPlaylistsRequest getListOfUsersPlaylistsRequest = spotifyApi
                .getListOfUsersPlaylists(user.getId())
                .build();

        try {
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfUsersPlaylistsRequest.execute();

            ObjectMapper mapper = new ObjectMapper();
            String playlistSimplifiedPagingJson = mapper.writeValueAsString(playlistSimplifiedPaging);
            return playlistSimplifiedPagingJson;
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }
}
