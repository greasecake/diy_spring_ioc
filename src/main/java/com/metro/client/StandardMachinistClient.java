package com.metro.client;

public class StandardMachinistClient implements MachinistClient {
    public void notifyReady() {
        System.out.println("Notifying machinist: \"Clear to go!\"");
    }
}
