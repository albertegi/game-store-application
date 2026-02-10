package com.alvirg.store.notification;

import com.alvirg.store.common.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Notification extends BaseEntity {
    private String message;
    private String receiver;
    private NotificationLevel level;
    private NotificationStatus status;
}
