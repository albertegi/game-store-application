package com.alvirg.store.game;

import com.alvirg.store.platform.Platform;

import java.util.List;
import java.util.Set;

public record GameRequest(
        String title, // perform check not to allow duplicates
        String categoryId, // check that the category exists because it has a relationship with this entity/table
        List<String> platforms // check that the platforms exists because it has a relationship with this entity/table
) {
}
