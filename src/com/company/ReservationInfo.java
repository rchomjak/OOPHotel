package com.company;

import java.util.List;

public class ReservationInfo implements  ReservationInfoInterface {

    private MyPeriodInterface period;
    private List<RoomInterface> orderedRooms;

    private static int gid = 0;
    private static int id = 0;

    private int lGid;
    private int lId;

    private float capacityRatio;
    private ReservationState reservationState = ReservationState.New;


    enum ReservationState {

        New,
        Canceled,
        //Deleted simulates operation delete
        Deleted,
    }


    ReservationInfo(MyPeriod inMyPeriod, List<RoomInterface> orderedRooms, float capacityRatio) {
        this.period = inMyPeriod;
        this.orderedRooms = orderedRooms;
        this.capacityRatio = capacityRatio;
        this.lId = id++;
        this.lGid = gid;

    }

    public MyPeriodInterface getPeriod() {
        return this.period;
    }

    @Override
    public List<RoomInterface> getRoomsInfo() {
        return orderedRooms;
    }

    public static void incGid() {
        gid++;
    }

    @Override
    public String toString() {

        return " { \"Reservation_ID\": "  + this.lId + ", \"Date_start\": " +  "\"" + this.getPeriod().getStartDate().toString() + "\"" +  ", \"Date_end\": " + "\"" + this.getPeriod().getStopDate().toString() + "\"" + ", \"Rooms\": " + this.getRoomsInfo().toString() + "} " ;
    }

    public float getId() {
        return this.id;
    }

    public ReservationState getReservationState() {
        return this.reservationState;
    }

    public void setReservationState(ReservationState a) {
        this.reservationState = a;
    }







}
