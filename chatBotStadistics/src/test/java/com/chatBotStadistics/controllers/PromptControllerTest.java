package com.chatBotStadistics.controllers;

import com.chatBotStadistics.domain.Prompt;
import com.chatBotStadistics.dto.PromptRequestDTO;
import com.chatBotStadistics.service.PromptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) //Permite inyeccion de dependencias en testeo.
public class PromptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PromptService promptService;

    @InjectMocks //Inyectamos el mock de la clase que vamos a testear.
    private PromptController controller;

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

}
