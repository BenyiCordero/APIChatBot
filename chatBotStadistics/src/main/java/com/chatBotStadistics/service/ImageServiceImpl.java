package com.chatBotStadistics.service;

import com.chatBotStadistics.domain.SubtemaImagenes;
import com.chatBotStadistics.dto.SubtemaImagenesRequest;
import com.chatBotStadistics.repository.ImageRepository;
import com.chatBotStadistics.repository.SubtemaRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository repository;
    private final SubtemaRepository subtemaRepository;

    public ImageServiceImpl(ImageRepository repository, SubtemaRepository subtemaRepository) {
        this.repository = repository;
        this.subtemaRepository = subtemaRepository;
    }

    @Override
    public SubtemaImagenes getURLSubtema(Integer id_subtema) {
        return repository.getImageBySubtema(id_subtema);
    }

    @Override
    public void postURLSubtema(SubtemaImagenesRequest request) {
        repository.save(SubtemaImagenes.
                builder().
                urlImage(request.url()).
                subtema(subtemaRepository.getSubtemaById(request.idSubtema())).
                build());
    }
}
