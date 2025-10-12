package com.chatBotStadistics.service;

import com.chatBotStadistics.domain.SubtemaImagenes;
import com.chatBotStadistics.dto.SubtemaImagenesRequest;
import org.springframework.stereotype.Service;

@Service
public interface ImageService {
    SubtemaImagenes getURLSubtema(Integer id_subtema);
    void postURLSubtema(SubtemaImagenesRequest request);
}
