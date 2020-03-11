package com.joe.spotify_basic.spotify_first_request.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joe.spotify_basic.spotify_first_request.service.ArtistService;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    private static final String type = ModelObjectType.ARTIST.getType();

    @GetMapping("/{artistName}/{limit}")
    public static String searchArtists(@PathVariable(required = true) String artistName,
                                       @PathVariable(required = true) Integer limit) throws JsonProcessingException {

        if (limit > 10) limit = 10;
        Paging<Artist> artistPaging = ArtistService.searchArtist(artistName, limit);
        ObjectMapper mapper = new ObjectMapper();
        String jsonArtist = mapper.writeValueAsString(artistPaging);
        return jsonArtist;
    }

}
