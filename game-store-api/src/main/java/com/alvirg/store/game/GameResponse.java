package com.alvirg.store.game;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameResponse {
    private String id;
    private String name;
    private Set<String> platforms;
    private String imageUrl; // the CDN url
}
