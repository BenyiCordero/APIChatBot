package com.chatBotStadistics.service;

import com.chatBotStadistics.domain.Subtema;
import com.chatBotStadistics.domain.Tema;

import java.util.List;

public interface SubtemaService {

    Subtema getSubtema(Integer id) throws Exception;
    Subtema postSubtema(Subtema subtema);
    List<Subtema> getSubtemas() throws Exception;
    void deleteSubtema(Integer id) throws Exception;

}
