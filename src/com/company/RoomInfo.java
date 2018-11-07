package com.company;

public class RoomInfo implements  RoomInfoInterface {

    private int id;
    private int numberOfBedsInUse;

    RoomInfo(int id, int numberOfBedsInUse) {

        this.id = id;
        this.numberOfBedsInUse = numberOfBedsInUse;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getNumberOfBedsInUse() {
        return numberOfBedsInUse;
    }


}
