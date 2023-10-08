package com.metro.announcer;

import com.metro.objectconfigurator.InjectProperty;

public class BeverageRecommender implements Recommender {
    @InjectProperty
    private String sponsor;

    @Override
    public String getSponsor() {
        return sponsor;
    }
}
