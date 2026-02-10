package com.alvirg.store.category;

import com.alvirg.store.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Category extends BaseEntity {

    private String name;
    private String description;
}
