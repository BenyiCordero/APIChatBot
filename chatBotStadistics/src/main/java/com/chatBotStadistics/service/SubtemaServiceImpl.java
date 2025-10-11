package com.chatBotStadistics.service;

import com.chatBotStadistics.domain.Subtema;
import com.chatBotStadistics.domain.Tema;
import com.chatBotStadistics.repository.SubtemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtemaServiceImpl implements SubtemaService {

    private final SubtemaRepository subtemaRepository;

    public SubtemaServiceImpl(SubtemaRepository subtemaRepository) {
        this.subtemaRepository = subtemaRepository;
    }

    @Override
    public Subtema getSubtema(Integer id) throws Exception {
        Subtema subtema = subtemaRepository.findById(id).get();
        if(subtema == null){
            throw new Exception("Subtema no encontrado");
        }
        return subtema;
    }

    @Override
    public Subtema postSubtema(Subtema subtema) {
        try{
            return subtemaRepository.save(subtema);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Subtema> getSubtemas() throws Exception {
        List<Subtema> subtemas = subtemaRepository.findAll();
        if(subtemas == null){
            throw new Exception("Subtemas no encontrados");
        }
        return subtemas;
    }

    @Override
    public void deleteSubtema(Integer id) throws Exception {
        try{
            subtemaRepository.deleteById(id);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
