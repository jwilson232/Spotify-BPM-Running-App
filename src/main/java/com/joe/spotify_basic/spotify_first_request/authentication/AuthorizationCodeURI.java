package com.joe.spotify_basic.spotify_first_request.authentication;

import com.joe.spotify_basic.spotify_first_request.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("/uri")
public class AuthorizationCodeURI extends ClientCredentials{

    @Autowired
    AuthorizationService authorizationService;

    @GetMapping("/code")
    public static ModelAndView generateAuthorizationUri() throws IOException {
        return AuthorizationService.generateAuthorizationURL();
    }
}
