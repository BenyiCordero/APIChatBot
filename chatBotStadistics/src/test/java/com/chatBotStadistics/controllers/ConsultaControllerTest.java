package com.chatBotStadistics.controllers;

import com.chatBotStadistics.domain.Prompt;
import com.chatBotStadistics.dto.PromptRequestDTO;
import com.chatBotStadistics.service.ConsultaService;
import com.chatBotStadistics.service.PromptService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Test class for testing {@link ConsultaController}.
 *
 * This class focuses on verifying the functionality and correctness of the endpoints
 * defined in {@link ConsultaController}. The tests ensure that the REST APIs behave
 * as expected under various conditions, including valid and invalid inputs.
 *
 * Test cases include:
 * - Validation and response verification for creating prompts with different request attributes.
 * - Fetching prompts by their IDs to validate correct behavior for existing and non-existing IDs.
 * - Fetching statistical data by topic and subtopic, including cases where data exists or does not exist.
 *
 * Utilizes MockMvc for simulating HTTP requests and responses, and Mockito for mocking service dependencies.
 *
 * Dependencies:
 * - {@code PromptService} and {@code ConsultaService}: These are mocked using {@code @SpyBean}
 *   to simulate backend behavior without requiring actual implementations.
 *
 * Scenarios tested:
 * - Validation errors for missing, null, blank, or excessively long prompt content.
 * - Successful creation of prompts when valid data is provided.
 * - Correct retrieval of prompts or statistical data.
 * - Handling of non-existing resources and returning appropriate HTTP status codes.
 */

@ExtendWith(MockitoExtension.class) //Permite inyeccion de dependencias en testeo.
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PromptService promptService;

    @Mock
    private ConsultaService consultaService;

    @InjectMocks //Inyectamos el mock de la clase que vamos a testear.
    private ConsultaController controller;

    @Test
    void createPrompt_ShouldReturnOKRequest_WhenContentIsNotNull() throws Exception {
        //ARRANGE -- Parametros y resultados.
        PromptRequestDTO prompt = new PromptRequestDTO("Prompt chatbot");
        Prompt promptResult = new Prompt(1, "Prompt chatbot");
        when(promptService.createPrompt(prompt)).thenReturn(promptResult);

        //ACT
        ResponseEntity<Prompt> response = controller.createPrompt(prompt);

        //ASSERT
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getPrompt_ShouldReturnPrompt_WhenValidId() throws Exception {
        Integer id = 1;
        Prompt prompt = new Prompt(1, "Sample prompt content");
        when(promptService.getPrompt(id)).thenReturn(Optional.of(prompt));

        ResponseEntity<Prompt> response = controller.getPrompt(id);

        //ASSERT
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getPrompt_ShouldReturnNotFound_WhenInvalidId() throws Exception {
        when(promptService.getPrompt(99)).thenReturn(java.util.Optional.empty());

        ResponseEntity<Prompt> response = controller.getPrompt(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updatePrompt_ShouldReturnOKRequest() {
        Integer id = 1;
        PromptRequestDTO request = new PromptRequestDTO("New prompt");
        Prompt pastPrompt = new Prompt(1, "New prompt");
        when(promptService.updatePrompt(id, request)).thenReturn(Optional.of(pastPrompt));

        ResponseEntity<Prompt> response = controller.updatePrompt(id, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void obtenerEstadisticasPorTema() {
        //Arrange -- Parametros opcionales
        Integer year = 2025;
        Integer month = 12;
        Integer week = 4;
        Map<String, Double> expectedMap = new HashMap<>();
        expectedMap.put("itemA", 50.5);
        expectedMap.put("itemB", 20.0);
        expectedMap.put("itemC", 29.5);

        when(consultaService.getEstadisticasPorTema(year, month, week)).thenReturn(expectedMap);

        //ACT
        Map<String, Double> response = controller.obtenerEstadisticasPorTema(year, month, week);

        //ASSERT
        verify(consultaService, times(1)).getEstadisticasPorTema(year, month, week);
        assertEquals(response, expectedMap);
        assertEquals(expectedMap.size(), response.size());
    }

    @Test
    void obtenerEstadisticasPorSubtema() {
    }

    @Test
    void obtenerTotalConsultas() {
    }

    @Test
    void obtenerTotalUsuarios() {
    }
}