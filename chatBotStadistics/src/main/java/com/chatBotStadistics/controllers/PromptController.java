package com.chatBotStadistics.controllers;

import com.chatBotStadistics.domain.Prompt;
import com.chatBotStadistics.dto.PromptRequestDTO;
import com.chatBotStadistics.service.PromptService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class PromptController {

    @Autowired
    PromptService promptService;

    @PostMapping("/prompt/create")
    public ResponseEntity<Prompt> createPrompt(@Valid @RequestBody PromptRequestDTO promptRequestDTO) {
        Prompt createdPrompt = promptService.createPrompt(promptRequestDTO);
        return new ResponseEntity<>(createdPrompt, HttpStatus.CREATED);
    }

    @GetMapping("/prompt/{id}")
    public ResponseEntity<Prompt> getPrompt(@PathVariable Integer id) {
        return promptService.getPrompt(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/prompt/actualizar/{id}")
    public ResponseEntity<Prompt> updatePrompt(@PathVariable Integer id,
                                               @Valid @RequestBody PromptRequestDTO promptRequestDTO) {
        return promptService.updatePrompt(id, promptRequestDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
