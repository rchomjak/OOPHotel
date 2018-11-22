package com.company;

import java.util.List;

public class ReservationInfo implements  ReservationInfoInterface {

    private MyPeriodInterface period;
    private List<RoomInterface> orderedRooms;

    private static int gid = 0;
    private int id;

    private float capacityRatio;

    //TODO: Here we have should change List<RoomInterface> to List<roomID> or something like that
    ReservationInfo(MyPeriod inMyPeriod, List<RoomInterface> orderedRooms, float capacityRatio) {
        this.period = inMyPeriod;
        this.orderedRooms = orderedRooms;
        this.capacityRatio = capacityRatio;
        this.id = this.gid++;
        System.out.println(orderedRooms);
    }

    public MyPeriodInterface getPeriod() {
        return this.period;
    }

    @Override
    public List<RoomInterface> getRoomsInfo() {
        return orderedRooms;
    }

    /*
    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return this.price;
    }
    */


}
