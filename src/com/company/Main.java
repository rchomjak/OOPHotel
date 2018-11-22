package com.company;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String args[]){

        HotelInterface h1 =  Hotel.getInstance();

        h1.addRoom(4, LuxuryCategory.Cheap);
        h1.addRoom(4, LuxuryCategory.Cheap);
        h1.addRoom(4, LuxuryCategory.Cheap);
        h1.addRoom(3, LuxuryCategory.Cheap);
        h1.addRoom(4, LuxuryCategory.Cheap);
        h1.addRoom(4, LuxuryCategory.Cheap);
        h1.addRoom(4, LuxuryCategory.Cheap);
        h1.addRoom(4, LuxuryCategory.Cheap);
        h1.addRoom(4, LuxuryCategory.Cheap);
        h1.addRoom(4, LuxuryCategory.Cheap);

        h1.addRoom(2, LuxuryCategory.Cheap);
        h1.addRoom(4, LuxuryCategory.Cheap);
        h1.addRoom(4, LuxuryCategory.Cheap);


        h1.addRoom(4, LuxuryCategory.Cheap);

        h1.addRoom(1, LuxuryCategory.Cheap);

        System.out.println(h1.findRoom(0).getBasePrice());
        System.out.println(h1);

        HotelInterface h2 = Hotel.getInstance();
        System.out.println(h2.findRoom(0).getBasePrice());

        //h2.deleteRoom(0);
        // Should throw exception
        //h2.deleteRoom(0);

        LocalDate dateStart = LocalDate.parse("2018-01-01");
        LocalDate dateStop = LocalDate.parse("2018-02-02");

        MyPeriodInterface timeRange = new MyPeriod(dateStart, dateStop);
        //ClientInterface client = new Client("Jozko@jozko", false);

        List<Integer> request = new ArrayList<>();

        request.add(1);
        request.add(1);
        request.add(1);

        request.add(1);
        request.add(1);
        request.add(1);

        request.add(2);

        request.add(1);

        request.add(2);

        request.add(4);

        h1.findFreeRooms(timeRange, request);


        //ReservationInfoInterface reservation = new ReservationInfo()

    }

}
