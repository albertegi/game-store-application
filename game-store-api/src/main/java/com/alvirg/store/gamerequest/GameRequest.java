package com.alvirg.store.gamerequest;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameRequest {
    private String title;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;
}
