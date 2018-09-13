package com.xedflix.video.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PresignedUrlDTO {
    private String url;
    private String newName;
    private Long expiresIn;
}
