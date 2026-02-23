package com.alvirg.store.comment;

import com.alvirg.store.common.BaseEntity;
import com.alvirg.store.game.Game;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comment extends BaseEntity {
    private String content;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
}
