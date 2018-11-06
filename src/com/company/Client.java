package com.company;



public class Client implements ClientInterface {

    //Id is an email
    private String id;
    //For discount purpose
    private int noFinishedReservations = 0;

    Client(String id) {
        this.id = id;
    }

    public int getNoFinishedReservations() {
        return noFinishedReservations;
    }

    public void incrNoFinishedReservation() {
        this.noFinishedReservations += 1;
    }

    public String getId() {
        return id;
    }


}
