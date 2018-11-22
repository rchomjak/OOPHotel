package com.company;

import java.util.List;

public interface ReservationsInterface {

    ReservationInterface findReservation(int id);
    void addReservation(Reservation reservation);
    void deleteReservation(int id);

    List<ReservationInterface> reservationsInMyPeriod(MyPeriodInterface inMyPeriod);

    //TODO:
    //loadReservations(Reader reader);
    //saveReservations(Writer writer);





}
