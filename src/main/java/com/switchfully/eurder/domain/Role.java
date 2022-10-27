package com.switchfully.eurder.domain;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.switchfully.eurder.domain.Feature.*;
public enum Role {
    ADMIN(newArrayList(ADD_NEW_ITEM)),
    CUSTOMER(newArrayList());
    private final List<Feature> featureList;

    Role(List<Feature> featureList) {
        this.featureList = featureList;
    }

    public boolean containsFeature(Feature feature) {
        return featureList.contains(feature);
    }
}
