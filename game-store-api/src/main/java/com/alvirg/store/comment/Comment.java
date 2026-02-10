package com.alvirg.store.comment;

import com.alvirg.store.common.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comment extends BaseEntity {
    private String comment;
}
