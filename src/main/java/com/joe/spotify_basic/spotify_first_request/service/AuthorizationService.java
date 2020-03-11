package com.joe.spotify_basic.spotify_first_request.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joe.spotify_basic.spotify_first_request.authentication.ClientCredentials;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URI;

@Service
public class AuthorizationService extends ClientCredentials {



    // Generates a URI that will redirect the user to log in with Spotify, returns a code in the URI once logged in
    public static ModelAndView generateAuthorizationURL() {
        AuthorizationCodeUriRequest authorizationCodeUriRequest =
                spotifyApi.authorizationCodeUri()
                        .scope("playlist-modify, user-read-private")
                        .build();

        final URI uri = authorizationCodeUriRequest.execute();
        return new ModelAndView("redirect:" + uri);
    }

    // Takes the code generated from generateAuthorizationURL() and returns final access token that can be used to
    // make requests to the API
    public static String authenticateWithCode(String code) {
        try {
            AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
                    .build();
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());

            ObjectMapper mapper = new ObjectMapper();
            String apiJson = mapper.writeValueAsString(spotifyApi);
            System.out.println(apiJson);
            return apiJson;

        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public static AuthorizationCodeCredentials refreshAccessToken(String refreshToken) {
        try {
            AuthorizationCodeRefreshRequest authorizationCodeRequest =
                    spotifyApi.authorizationCodeRefresh(spotifyApi.getClientId(), spotifyApi.getClientSecret(), refreshToken)
                            .build();

            final AuthorizationCodeCredentials refreshedAuthorizationCodeCredentials = authorizationCodeRequest.execute();
//            ObjectMapper mapper = new ObjectMapper();
//            String refreshedToken = mapper.writeValueAsString(refreshedAuthorizationCodeCredentials);
            return refreshedAuthorizationCodeCredentials;
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error in Authorization.java: " + e.getMessage());
        }
        return null;
    }

}
