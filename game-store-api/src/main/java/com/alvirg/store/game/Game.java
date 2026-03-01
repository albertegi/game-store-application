package com.alvirg.store.game;

import com.alvirg.store.category.Category;
import com.alvirg.store.comment.Comment;
import com.alvirg.store.common.BaseEntity;
import com.alvirg.store.platform.Console;
import com.alvirg.store.platform.Platform;
import com.alvirg.store.wishlist.WishList;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Game extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String title;

    private String coverPicture;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "game")
    @OrderBy(value = "content") // ordering all your contents in game when getting the comments may be for a particular game
    private List<Comment> comments;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "game_platform",
            joinColumns = {
                    @JoinColumn(name = "game_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "platform_id"
                    )
            }
    )
    private List<Platform> platforms;

    @ManyToMany( cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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

    /** select g.* from
    Game g
     Inner join game_wishlist gw on gw.game_id = g.id
     Inner join WishLight w on w.id = gw.wishlist_id
     **/

    private List<WishList> wishLists;

    public void addWishList(WishList wishList){
        this.wishLists.add(wishList);
        wishList.getGames().add(this);
    }

    public void removeWishList(WishList wishList){
        this.wishLists.remove(wishList);
        wishList.getGames().remove(this);
    }

    // paging
// Why paging: improve performance
// fetching and sending the whole data from db can be very slow
// we need to get the page number and the size

public void addPlatform(Platform platform){
        this.platforms.add(platform);
        platform.getGames().add(this);
}

public void removePlatform(Platform platform){
        this.platforms.remove(platform);
        platform.getGames().remove(this);
}



}
