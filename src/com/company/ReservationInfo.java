package com.company;

import java.util.Date;
import java.util.List;



public class ReservationInfo implements  ReservationInfoInterface {

    private MyPeriodInterface period;

    private Client client;
    private List<RoomInterface> orderedRooms;

    //TODO: Here we have should change List<RoomInterface> to List<roomID> or something like that
    ReservationInfo(Date startDate, Date endDate, List<RoomInterface> orderedRooms) {

        this.period = new Period(startDate, endDate);
        this.orderedRooms = orderedRooms;

    }


    public MyPeriodInterface getPeriod() {
        return this.period;
    }

    @Override
    public List<RoomInterface> getRoomsInfo() {
        return orderedRooms;
    }





}
