package com.alvirg.store.wishlist;

import com.alvirg.store.common.BaseEntity;
import com.alvirg.store.game.Game;
import com.alvirg.store.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class WishList extends BaseEntity {
    private String name;

    @OneToOne
    private User user;

    @ManyToMany(mappedBy = "wishLists")
    private List<Game> games;

}
