package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Reservations implements ReservationsInterface {

    List<ReservationInterface> reservations = new ArrayList<>();
    int lastReservationId = 0;

    @Override
    public void deleteReservation(int id) {

        ReservationInterface reserv = findReservation(id);
        if (reserv != null && !reserv.getOrderState().equals(OrderState.Deleted)) {

            reserv.setOrderState(OrderState.Deleted);
        } else {

            throw new IllegalArgumentException("There is no reservation with id: " + id);
        }
    }

    @Override
    public void addReservation(Reservation in_reservation) {

        for (ReservationInterface reservation: reservations) {
            if (reservation.getId() == in_reservation.getId()) {

                throw new IllegalArgumentException("There is already exists client with same id: " + reservation.getId());
            }
        }

        reservations.add(in_reservation);
    }

    @Override
    public ReservationInterface findReservation(int id) {

        return reservations.stream().filter(x -> x.getId() == id).findAny().orElse(null);
    }

    public List<ReservationInterface> reservationsInMyPeriod(MyPeriodInterface inMyPeriod) {

        return reservations.stream().filter(x->x.isReservedForMyPeriod(inMyPeriod)).collect(Collectors.toList());
    }

    public List<ReservationInterface> getAllReservations() {
        return reservations;
    }

}
