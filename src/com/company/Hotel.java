package com.company;

import java.util.ArrayList;
import java.util.List;

public class Hotel implements HotelInterface {

    private List<RoomInterface> Rooms = new ArrayList<>();
    private int lastRoomId = 0;

    static final HotelInterface hotel = new Hotel();
    private int totalBedsCapacity = 0;

    private List<ReservationInfoInterface> Reservations = new ArrayList<>();
    private int lastReservationId = 0;


    Hotel() {
        //
        return;
    }

    public static HotelInterface getInstance() {
        return hotel;
    }

    @Override
    public void addRoom(int nOfBeds, LuxuryCategory luxuryCategory) {

        RoomInterface isExistRoom = findRoom(lastRoomId);

        if (isExistRoom != null) {
            throw new IllegalArgumentException("Something is really fucked up, you are creating two rooms with same id: " + String.valueOf(lastRoomId));

        }

        RoomInterface newRoom = new Room(lastRoomId, nOfBeds, luxuryCategory);

        Rooms.add(newRoom);
        totalBedsCapacity += nOfBeds;

        lastRoomId++;

        return;
    }

    @Override
    public RoomInterface findRoom(int id) {

        return Rooms.stream().filter(x->x.getId() == id).findAny().orElse(null);
    }


    @Override
    public void deleteRoom(int id) {

        RoomInterface delRoom = findRoom(id);

        if (delRoom != null && !delRoom.getIsDeleted()) {

            delRoom.setIsDeleted();
            totalBedsCapacity -= delRoom.getNumberOfBeds();

        } else {

            throw new IllegalArgumentException("There is no room with id: " + String.valueOf(id));
        }

        return;
    }

    public void setAvailabilityRoom(int id, Boolean availability) {

        RoomInterface room =  findRoom(id);

        if (room != null) {

            room.setIsOpen(availability);

        } else {

            throw new IllegalArgumentException("There is no room with id: " + String.valueOf(id));
        }
    }

    private int calculatesBedsCapacity() {
        //If we will use "open/closed" parameters, we must rewrite this, otherwise is ok.

        int bedsCapacity = 0;

        for(RoomInterface room: Rooms) {
            if (!room.getIsDeleted()) {
                bedsCapacity += room.getNumberOfBeds();
            }
        }

        this.totalBedsCapacity = bedsCapacity;
        return totalBedsCapacity;
    }

}
