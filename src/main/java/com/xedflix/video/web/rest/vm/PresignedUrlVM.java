package com.xedflix.video.web.rest.vm;

import com.xedflix.video.service.dto.PresignedUrlDTO;

public class PresignedUrlVM extends PresignedUrlDTO {
    public PresignedUrlVM(PresignedUrlDTO presignedUrlDTO) {
        this.setNewName(presignedUrlDTO.getNewName());
        this.setExpiresIn(presignedUrlDTO.getExpiresIn());
        this.setUrl(presignedUrlDTO.getUrl());
    }
}
