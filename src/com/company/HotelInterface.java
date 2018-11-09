package com.company;

import java.io.Reader;
import java.io.Writer;
import java.time.Period;
import java.util.List;

public interface HotelInterface {


    /* Needs to be load from DB or CSV file */
    //void loadRooms(Reader reader);
    //void saveRooms(Writer writer);


    void addRoom(int nOfBeds, LuxuryCategory luxuryCategory);
    void deleteRoom(int id);
    RoomInterface findRoom(int id);

    //rooms jest listą liczb określających ile osób chcemy zakwaterować w pokoju
    //np.: { 1, 2} oznacza, że potrzebujemy pokoju dla jednej osoby i drugiego pokoju dla dwu osób.
  //  List<ReservationInfo> findFreeRooms(Period period, List<Integer> rooms);
    Boolean makeReservation(ClientInterface client,  ReservationInfo request);



}
