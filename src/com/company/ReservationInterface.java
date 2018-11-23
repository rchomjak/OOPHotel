package com.company;

public interface ReservationInterface {

    int getId();
    void setOrderState(OrderState orderState);
    OrderState getOrderState();
    Boolean isReservedForMyPeriod(MyPeriodInterface inMyPeriod);
    ReservationInfoInterface getReservationInfo();
    Client getClient();

}
