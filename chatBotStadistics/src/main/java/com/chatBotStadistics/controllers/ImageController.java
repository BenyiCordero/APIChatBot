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

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<SubtemaImagenes> getURLImage(@PathVariable Integer id_subtema) {
        return ResponseEntity.ok(imageService.getURLSubtema(id_subtema));
    }

    @PostMapping
    public ResponseEntity<?> postURLImage(@RequestBody SubtemaImagenesRequest request) {
        imageService.postURLSubtema(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
