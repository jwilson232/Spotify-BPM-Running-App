package com.joe.spotify_basic.spotify_first_request.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joe.spotify_basic.spotify_first_request.authentication.ClientCredentials;
import com.joe.spotify_basic.spotify_first_request.service.ArtistService;
import com.joe.spotify_basic.spotify_first_request.service.TrackService;
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Recommendations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tracks")
public class TrackController extends ClientCredentials {

    @GetMapping("/seed/{numberOfTracks}/{seedArtist}/{bpm}")
    public static String bpmBySeed(@PathVariable(required = true) String seedArtist,
                                                 @PathVariable(required = true) Float bpm,
                                                 @PathVariable(required = true) Integer numberOfTracks) throws JsonProcessingException {

        if (numberOfTracks > 100) numberOfTracks = 100;
        ObjectMapper mapper = new ObjectMapper();
        Recommendations recommendations = TrackService.tracksByArtistBpm(numberOfTracks, seedArtist, bpm);
        System.out.println(mapper.writeValueAsString(recommendations));
        return mapper.writeValueAsString(recommendations);
    }

    @GetMapping("/string/{numberOfTracks}/{artistName}/{bpm}")
    public static Recommendations bpmByString(@PathVariable(required = true) String artistName,
                                   @PathVariable(required = true) Float bpm,
                                   @PathVariable(required = true) Integer numberOfTracks) {

        return TrackService.bpmByString(artistName, bpm, numberOfTracks);


    }
}
