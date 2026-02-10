package com.alvirg.store.game;

import com.alvirg.store.category.Category;
import com.alvirg.store.comment.Comment;
import com.alvirg.store.common.BaseEntity;
import com.alvirg.store.wishlist.WishList;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "game")
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "game_wishlist",
            joinColumns = {
                    @JoinColumn(
                            name = "game_id"
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "wishlist_id"
                    )
            }
    )
    private List<WishList> wishLists;


}
