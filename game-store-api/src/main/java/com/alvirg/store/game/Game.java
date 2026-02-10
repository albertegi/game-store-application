package com.alvirg.store.game;

import com.alvirg.store.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Game extends BaseEntity {
    private String title;

    @Enumerated(value = EnumType.STRING)
    private SupportedPlatforms supportedPlatforms;

    private String coverPicture;
}
