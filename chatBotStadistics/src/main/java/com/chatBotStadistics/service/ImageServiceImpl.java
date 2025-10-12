package com.chatBotStadistics.service;

import com.chatBotStadistics.domain.SubtemaImagenes;
import com.chatBotStadistics.dto.SubtemaImagenesRequest;
import com.chatBotStadistics.repository.ImageRepository;
import com.chatBotStadistics.repository.SubtemaRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository repository;
    private final SubtemaService subtemaService;

    public ImageServiceImpl(ImageRepository repository, SubtemaRepository subtemaRepository, SubtemaService subtemaService) {
        this.repository = repository;
        this.subtemaService = subtemaService;
    }

    @Override
    public SubtemaImagenes getURLSubtema(Integer id_subtema) {
        return repository.getImageBySubtema(id_subtema);
    }

    @Override
    public void postURLSubtema(SubtemaImagenesRequest request) {
        try {
            repository.save(SubtemaImagenes.
                    builder().
                    urlImage(request.url()).
                    subtema(subtemaService.getSubtema(request.idSubtema())).
                    build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
