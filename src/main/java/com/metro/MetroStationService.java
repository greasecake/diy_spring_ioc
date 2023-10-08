package com.metro;

import com.metro.announcer.Announcer;
import com.metro.client.MachinistClient;
import com.metro.client.StandardMachinistClient;

public class MetroStationService {
    private Announcer announcer = ObjectFactory.getInstance().createObject(Announcer.class);
    private MachinistClient machinistClient = ObjectFactory.getInstance().createObject(StandardMachinistClient.class);

    public void finishBoarding() {
        announcer.announce("Announcing: \"Dear passengers, the doors are closing!\"");
        closeDoors();
        machinistClient.notifyReady();
        announcer.announce("Announcing: \"Next station is... City Center\"");
    }

    public void closeDoors() {
        System.out.println("Closing doors...");
    }
}
