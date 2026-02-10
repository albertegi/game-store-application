package com.alvirg.store.user;

import com.alvirg.store.common.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String profilePictureUrl;
}
