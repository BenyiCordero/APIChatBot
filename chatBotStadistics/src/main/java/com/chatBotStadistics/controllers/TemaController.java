package com.chatBotStadistics.controllers;

import com.chatBotStadistics.domain.Tema;
import com.chatBotStadistics.service.TemaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tema")
public class TemaController {

    private final TemaService temaService;

    public TemaController(TemaService temaService) {
        this.temaService = temaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTema(@PathVariable Integer id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(temaService.getTema(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<?> getTemas(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(temaService.getTemas());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> postTema(@RequestBody Tema tema){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(temaService.postTema(tema));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTema(@PathVariable Integer id){
        try{
            temaService.deleteTema(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
