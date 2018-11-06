package com.company;

import java.util.ArrayList;
import java.util.List;

public class Hotel implements HotelInterface {

    List<RoomInterface> Rooms = new ArrayList<>();
    int lastRoomId = 0;

    static final HotelInterface hotel = new Hotel();


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
        lastRoomId++;

        return;
    }

    @Override
    public RoomInterface findRoom(int id) {

        return Rooms.stream().filter(x->x.getId() == id).findAny().orElse(null);
    }


    @Override
    public void deleteRoom(int id) {

        RoomInterface delRoom =  findRoom(id);

        if (delRoom != null) {

            Rooms.remove(delRoom);

        } else {

            throw new IllegalArgumentException("There is no room with id: " + String.valueOf(id));
        }

        return;
    }

    public void setAvailibityRoom(int id, Boolean availability) {

        RoomInterface room =  findRoom(id);

        if (room != null) {

            room.setIsOpen(availability);

        } else {

            throw new IllegalArgumentException("There is no room with id: " + String.valueOf(id));
        }
    }

}
