package com.company;



enum OrderState {
    New,
    Accepted,
    Canceled,
    //Deleted simulates operation delete
    Deleted,
}

public class Reservation implements ReservationInterface {

    private Client client;
    private ReservationInfoInterface reservationInfo;
    private int id;

    private OrderState orderStatus = OrderState.New;
    private float price;
    private float discount;

    Reservation(int id, ClientInterface client, ReservationInfo reservationInfo, float price, float discount) {

        this.id = id;
        this.client = (Client)client;
        this.reservationInfo = reservationInfo;
        this.price = price;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setOrderState(OrderState orderState) {
        this.orderStatus = orderState;
    }

    @Override
    public OrderState getOrderState() {
        return orderStatus;
    }

    @Override
    public ReservationInfoInterface getReservationInfo() {
        return reservationInfo;
    }

    @Override
    public Boolean isReservedForMyPeriod(MyPeriodInterface inMyPeriod) {

        MyPeriodInterface reserveDateRange = this.getReservationInfo().getPeriod();

        //Start date should be before stop date
        assert inMyPeriod.getStartDate().isBefore(inMyPeriod.getStopDate());


        //I know, that could be simplified, but I have choose this form.
        // (START_Planned) (STOP_Planned) [START_RESERVED] [STOP_RESERVED], inMyPeriod start and stop date should be before Reserved Start date
        if (inMyPeriod.getStartDate().isBefore(reserveDateRange.getStartDate()) && inMyPeriod.getStopDate().isBefore(reserveDateRange.getStartDate())) {
            return false;
        }

        // [START_RESERVED] [STOP_RESERVED] (START_Planned) (STOP_Planned), inMyPeriod start and stop date should be after Reserved stop date
        if (inMyPeriod.getStartDate().isAfter(reserveDateRange.getStopDate()) && inMyPeriod.getStopDate().isAfter(reserveDateRange.getStopDate())) {
            return false;
        }

        return true;
    }
    @Override
    public Client getClient() {
        return this.client;
    }

    @Override
    public String toString() {
        return "{ \"id\":" + this.id +  ", \"Client\":" + this.client + ", Price:" + this.price + ", Discount:" + this.discount + ", \"AdditionalRoomInformation\": " + this.getReservationInfo() + "}";
    }
}
