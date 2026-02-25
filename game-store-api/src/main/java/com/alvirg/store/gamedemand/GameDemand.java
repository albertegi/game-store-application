package com.alvirg.store.gamedemand;

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
public class GameDemand extends BaseEntity {
    private String title;

    @Enumerated(value = EnumType.STRING)
    private DemandStatus status = DemandStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
