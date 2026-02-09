package com.alvirg.store.game;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {
    private String title;
    private String supportedPlatforms;
    private String coverPicture;
}
