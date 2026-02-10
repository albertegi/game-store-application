package com.alvirg.store.user;

import com.alvirg.store.common.BaseEntity;
import com.alvirg.store.gamerequest.GameRequest;
import com.alvirg.store.notification.Notification;
import com.alvirg.store.wishlist.WishList;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "_user")
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String profilePictureUrl;

    @OneToOne(mappedBy = "user")
    private WishList wishList;

    @OneToMany(mappedBy = "user")
    private List<Notification> notification;

    @OneToMany(mappedBy = "user")
    private List<GameRequest> gameRequests;
}
