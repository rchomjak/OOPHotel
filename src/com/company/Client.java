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

    public void setNoFinishedReservations(int noFinishedRes) {
        this.noFinishedReservations = noFinishedRes;
    }

    public void incrNoFinishedReservation() {
        this.noFinishedReservations += 1;
    }


    public String getId() {
        return id;
    }

    public void setDeleted(Boolean state){
        this.isDeleted = state;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public Boolean isSuperUser() {
        return this.isSuperUser;
    }

    @Override
    public String toString() {
        return "{" + "\"id\":" + "\"" + getId() + "\"" + ", \"Admin_rights\": " + this.isSuperUser + ", \"Finished_reservations\":" + this.getNoFinishedReservations() + "}";
    }

    @Override
    public boolean equals(Object a) {

        if (a == null) {
            return false;
        }

        if (a == this) {
            return true;
        }

        if (!(a instanceof ClientInterface)) {
            return false;
        }

        Client b = (Client)a;

        if ((this.getId() == b.getId())) {
            return true;
        }

        return false;

    }


}

