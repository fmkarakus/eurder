package com.switchfully.eurder.domain.users;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.switchfully.eurder.domain.users.Feature.*;
public enum Role {
    ADMIN(newArrayList(ADD_NEW_ITEM,ORDER,GET_ALL_ITEMS,VIEW_USERS)),
    CUSTOMER(newArrayList(ORDER,GET_ALL_ITEMS));
    private final List<Feature> featureList;

    Role(List<Feature> featureList) {
        this.featureList = featureList;
    }

    public boolean containsFeature(Feature feature) {
        return featureList.contains(feature);
    }
}
