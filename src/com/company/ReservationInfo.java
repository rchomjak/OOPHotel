package com.company;

import java.util.Date;
import java.util.List;

enum OrderState {
    New,
    Accepted,
    Canceled,
}

public class ReservationInfo implements  ReservationInfoInterface {

    private int id;
    private Period period;
    private OrderState orderStatus = OrderState.New;

    private List<RoomInterface> orderedRooms;

    ReservationInfo(int id, Date startDate, Date endDate, List<RoomInterface> orderedRooms) {
        this.id = id;
        this.period = new Period(startDate, endDate);
        this.orderedRooms = orderedRooms;
    }

    public int getId() {
        return this.id;
    }

    public Period getPeriod() {
        return this.period;
    }

    @Override
    public List<RoomInterface> getRoomsInfo() {
        return orderedRooms;
    }

    public void setOrderState(OrderState orderState) {
        this.orderStatus = orderState;

    }

}
