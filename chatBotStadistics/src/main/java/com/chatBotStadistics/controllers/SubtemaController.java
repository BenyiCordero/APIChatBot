package com.chatBotStadistics.controllers;

import com.chatBotStadistics.domain.Subtema;
import com.chatBotStadistics.domain.Tema;
import com.chatBotStadistics.service.SubtemaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subtema")
public class SubtemaController {

    private final SubtemaService subtemaService;

    public SubtemaController(SubtemaService subtemaService) {
        this.subtemaService = subtemaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubtema(@PathVariable Integer id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(subtemaService.getSubtema(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getSubtemas(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(subtemaService.getSubtemas());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> postSubtema(@RequestBody Subtema subtema){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(subtemaService.postSubtema(subtema));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubtema(@PathVariable Integer id){
        try{
            subtemaService.deleteSubtema(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
