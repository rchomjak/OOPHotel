package com.company;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Hotel implements HotelInterface {

    private List<RoomInterface> rooms = new ArrayList<>();
    private int lastRoomId = 0;

    static final HotelInterface hotel = new Hotel();
    private int totalBedsCapacity = 0;

    //private List<Reservation> reservations = new ArrayList<>();
    private int lastReservationId = 0;

    private Hotel() {

        return;
    }

    public static HotelInterface getInstance() {
        return hotel;
    }

    @Override
    public void addRoom(int nOfBeds, LuxuryCategory luxuryCategory) {

        RoomInterface isExistRoom = findRoom(lastRoomId);

        if (isExistRoom != null) {
            throw new IllegalArgumentException("Something is really fucked up, you are creating two rooms with same id: " + lastRoomId);

        }

        RoomInterface newRoom = new Room(lastRoomId, nOfBeds, luxuryCategory);

        rooms.add(newRoom);
        totalBedsCapacity += nOfBeds;

        lastRoomId++;

        return;
    }

    @Override
    public RoomInterface findRoom(int id) {

        return rooms.stream().filter(x->x.getId() == id).findAny().orElse(null);
    }


    @Override
    public void deleteRoom(int id) {

        RoomInterface delRoom = findRoom(id);

        if (delRoom != null && !delRoom.getIsDeleted()) {

            delRoom.setIsDeleted();
            totalBedsCapacity -= delRoom.getNumberOfBeds();

        } else {

            throw new IllegalArgumentException("There is no room with id: " + id);
        }

        return;
    }

    public void setAvailabilityRoom(int id, Boolean availability) {

        RoomInterface room =  findRoom(id);

        if (room != null) {
            room.setIsOpen(availability);

        } else {
            throw new IllegalArgumentException("There is no room with id: " + id);
        }
    }

    private int calculatesBedsCapacity() {
        //If we will use Availability parameters, we must rewrite this, otherwise is ok.

        int bedsCapacity = 0;

        for(RoomInterface room: rooms) {
            if (!room.getIsDeleted()) {
                bedsCapacity += room.getNumberOfBeds();
            }
        }

        this.totalBedsCapacity = bedsCapacity;
        return totalBedsCapacity;
    }
/*
    @Override
    public List<ReservationInfo> findFreeRooms(Period period, List<Integer> rooms) {


    }

*/

    @Override
    public Boolean makeReservation(ClientInterface client, ReservationInfo request) {

        /*
        Reservation newReservation =  new Reservation(lastReservationId, client, request);
        Reservations.add(newReservation);

       // System.out.println(client.getId() + request.getId());
        */

        return true;

    }


}
