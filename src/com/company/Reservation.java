package com.company;



enum OrderState {
    New,
    Accepted,
    Canceled,
}

public class Reservation {

    ClientInterface client;
    ReservationInfoInterface reservationInfo;
    int id;

    private OrderState orderStatus = OrderState.New;
    private float price;


    Reservation(int id, ClientInterface client, ReservationInfo reservationInfo, float price) {

        this.id = id;
        this.client = client;
        this.reservationInfo = reservationInfo;
        this.price = price;

    }

    public int getId() {
        return this.id;
    }

    public void setOrderState(OrderState orderState) {
        this.orderStatus = orderState;
    }

}
