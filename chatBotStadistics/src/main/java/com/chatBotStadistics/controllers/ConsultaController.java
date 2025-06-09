package com.chatBotStadistics.controllers;

import com.chatBotStadistics.domain.Prompt;
import com.chatBotStadistics.dto.PromptRequestDTO;
import com.chatBotStadistics.service.ConsultaService;
import com.chatBotStadistics.service.PromptService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller for managing consultas and prompts.
 *
 * This class handles various endpoints related to statistical data of "consultas"
 * (queries) and CRUD operations for "prompts." It uses the {@code ConsultaService}
 * and {@code PromptService} for interacting with the backend data and performing required operations.
 *
 * The controller is annotated with {@code @RestController} to define it as a RESTful web service
 * and maps requests to the base URI "/consultas" using {@code @RequestMapping}.
 *
 * Dependencies are injected into this controller using the {@code @Autowired} annotation.
 */
@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    ConsultaService consultaService;
    @Autowired
    PromptService promptService;

    @PostMapping ("/prompt/create")
    public ResponseEntity<Prompt> createPrompt(@Valid @RequestBody PromptRequestDTO promptRequestDTO) {
        Prompt createdPrompt = promptService.createPrompt(promptRequestDTO);
        return new ResponseEntity<>(createdPrompt, HttpStatus.CREATED);
    }

    @GetMapping ("/prompt/{id}")
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

    //New endpoints
    @GetMapping("/por-temav2")
    public Map<String, Double> obtenerEstadisticasPorTema(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer week) {
        Map<String, Double> result = consultaService.getEstadisticasPorTema(year, month, week);
        System.out.println("Controller Result: " + result); // Debugging
        return result;
    }

    @GetMapping("/por-subtemav2")
    public Map<String, Double> obtenerEstadisticasPorSubtema(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer week) {
        Map<String, Double> result = consultaService.getEstadisticasPorSubtema(year, month, week);
        System.out.println("Controller Result: " + result); // Debugging
        return result;
    }

    @GetMapping
    public Long obtenerTotalConsultas(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer week
    ) {
        return consultaService.getConsultas(year, month, week);
    }

    @GetMapping("/cantidad-usuarios")
    public Long obtenerTotalUsuarios(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer week
    ) {
        return consultaService.getUsuarios(year, month, week);
    }
}
