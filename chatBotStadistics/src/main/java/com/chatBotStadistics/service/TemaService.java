package com.chatBotStadistics.service;

import com.chatBotStadistics.domain.Tema;

import java.util.List;

public interface TemaService {

    Tema getTema(Integer id) throws Exception;
    Tema postTema(Tema tema);
    List<Tema> getTemas() throws Exception;
    void deleteTema(Integer id) throws Exception;

}
