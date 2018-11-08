package com.company;



public class Client implements ClientInterface {

    //Id is an email
    private String id;
    //For discount purpose
    private int noFinishedReservations = 0;

    private Boolean isDeleted = false;

    private Boolean isSuperUser;

    Client(String id, Boolean superUser) {
        this.id = id;
        this.isSuperUser = superUser;
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

    public void setDeleted(){
        this.isDeleted = true;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public Boolean isSuperUser() {
        return this.isSuperUser;
    }



}
