package com.company;

public class Main {

    public static void main(String args[]){

        HotelInterface h1 =  Hotel.getInstance();

        h1.addRoom(4, LuxuryCategory.Cheap);

        System.out.println(h1.findRoom(0).getBasePrice());
        System.out.println(h1);

        HotelInterface h2 = Hotel.getInstance();
        System.out.println(h2.findRoom(0).getBasePrice());

        h2.deleteRoom(0);
        // Should throw exception
        h2.deleteRoom(0);

        // ReservationInfoInterface reservation = new ReservationInfo()

    }
    
}
