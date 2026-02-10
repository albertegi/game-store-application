package com.alvirg.store.notification;

import com.alvirg.store.common.BaseEntity;
import com.alvirg.store.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
