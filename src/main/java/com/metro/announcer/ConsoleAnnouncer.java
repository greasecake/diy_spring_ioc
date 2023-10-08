package com.metro.announcer;

import com.metro.ObjectFactory;

public class ConsoleAnnouncer implements Announcer {
    private Recommender recommender = ObjectFactory.getInstance().createObject(Recommender.class);

    public void announce(String message) {
        System.out.println(message);
        runAd();
    }

    private void runAd() {
        System.out.printf("-- This message brought to you by %s --%n", recommender.getSponsor());
    }
}
