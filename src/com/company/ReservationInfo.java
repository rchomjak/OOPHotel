package com.company;

import java.util.List;

public class ReservationInfo implements  ReservationInfoInterface {

    private MyPeriodInterface period;
    private List<RoomInterface> orderedRooms;

    private static int id = 0;
    private static int gid = 0;

    private int lId;
    private int lgId;

    private float capacityRatio;
   // private ReservationState reservationState = ReservationState.New;



    ReservationInfo(MyPeriod inMyPeriod, List<RoomInterface> orderedRooms, float capacityRatio) {
        this.period = inMyPeriod;
        this.orderedRooms = orderedRooms;
        this.capacityRatio = capacityRatio;
        this.lId = id++;
        this.lgId = gid;

    }

    public MyPeriodInterface getPeriod() {
        return this.period;
    }

    @Override
    public List<RoomInterface> getRoomsInfo() {
        return orderedRooms;
    }


    @Override
    public String toString() {

        return " { \"Reservation_ID\": "  + this.lId + ", \"Date_start\": " +  "\"" + this.getPeriod().getStartDate().toString() + "\"" +  ", \"Date_end\": " + "\"" + this.getPeriod().getStopDate().toString() + "\"" + ", \"Rooms\": " + this.getRoomsInfo().toString() + "} " ;
    }

    public int getId() {
        return this.lId;
    }

    public void setId (int id) {
        this.lId = id;
    }

    public void setGid(int gid) { this.lgId = gid; }

    public int getGid() {return this.lgId;}


    public static void incGid() { gid++; }

    public static void setgGid(int in_gid) { gid=in_gid; }

/*
    public ReservationState getReservationState() { return this.reservationState;
    }

    public void setReservationState(ReservationState a) {
        this.reservationState = a;
    }


*/


}
