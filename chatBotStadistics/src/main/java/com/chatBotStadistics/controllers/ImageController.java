package com.chatBotStadistics.controllers;

import com.chatBotStadistics.domain.SubtemaImagenes;
import com.chatBotStadistics.dto.SubtemaImagenesRequest;
import com.chatBotStadistics.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<SubtemaImagenes> getURLImage(@PathVariable Integer id_subtema) {
        return ResponseEntity.ok(service.getURLSubtema(id_subtema));
    }

    @PostMapping
    public ResponseEntity<?> postURLImage(@RequestBody SubtemaImagenesRequest request) {
        service.postURLSubtema(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}