package com.alvirg.store.platform;

import com.alvirg.store.common.BaseEntity;
import com.alvirg.store.game.Game;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Platform extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Console console;

    @ManyToMany(mappedBy = "platforms")
    private List<Game> games;
}
