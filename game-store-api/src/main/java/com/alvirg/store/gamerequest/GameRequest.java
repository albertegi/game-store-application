package com.alvirg.store.gamerequest;

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
public class GameRequest extends BaseEntity {
    private String title;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;
}
