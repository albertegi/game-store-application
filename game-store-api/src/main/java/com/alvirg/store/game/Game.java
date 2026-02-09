package com.alvirg.store.game;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {
    private String title;

    @Enumerated(value = EnumType.STRING)
    private SupportedPlatforms supportedPlatforms;

    private String coverPicture;
}
