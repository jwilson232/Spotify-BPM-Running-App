package com.joe.spotify_basic.spotify_first_request.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joe.spotify_basic.spotify_first_request.authentication.ClientCredentials;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.User;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;

public class UserService extends ClientCredentials {

    public static User getCurrentUser(String accessToken) {
        try {
            GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile()
                    .build();

            final User user = getCurrentUsersProfileRequest.execute();

            ObjectMapper mapper = new ObjectMapper();
            String jsonUser = mapper.writeValueAsString(user);
            System.out.println(jsonUser);
            return user;
        }
        catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
