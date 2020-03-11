package com.joe.spotify_basic.spotify_first_request.authentication;

import com.joe.spotify_basic.spotify_first_request.service.AuthorizationService;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class Authorization extends ClientCredentials{

    @GetMapping("/sync")
    public static String authorizationCode(@RequestParam(value = "code", required = false) String code) {
        return AuthorizationService.authenticateWithCode(code);
    }

    @GetMapping("/refresh")
    public static String refreshAcessToken(@RequestParam(value = "token") String refreshToken) throws IOException, SpotifyWebApiException {
        return AuthorizationService.refreshAccessToken(refreshToken);
    }

//    @GetMapping("/timeout")
//    public static String timeout() {
//
//    }
}
