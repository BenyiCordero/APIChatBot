package com.chatBotStadistics.service;

import com.chatBotStadistics.domain.Tema;
import com.chatBotStadistics.repository.TemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemaServiceImpl implements TemaService {

    private final TemaRepository temaRepository;

    public TemaServiceImpl(TemaRepository temaRepository) {
        this.temaRepository = temaRepository;
    }

    @Override
    public Tema getTema(Integer id) throws Exception {
        Tema tema = temaRepository.findById(id).get();
        if(tema == null){
            throw new Exception("Tema no encontrado");
        }
        return tema;
    }

    @Override
    public Tema postTema(Tema tema) {
        try{
            return  temaRepository.save(tema);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Tema> getTemas() throws Exception {
        List<Tema> temas = temaRepository.findAll();
        if(temas == null){
            throw new Exception("No existen temas");
        }
        return temas;
    }

    @Override
    public void deleteTema(Integer id) throws Exception {
        try {
            temaRepository.deleteById(id);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
