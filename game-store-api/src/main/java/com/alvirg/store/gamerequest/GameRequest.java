package com.alvirg.store.gamerequest;

import com.alvirg.store.common.BaseEntity;
import com.alvirg.store.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class GameRequest extends BaseEntity {
    private String title;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
