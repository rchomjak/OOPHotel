package com.company;

public class Main {

    public static void main(String args[]){

        Hotel h1 = new Hotel();

        h1.addRoom(4, LuxuryCategory.Cheap);
        h1.findRoom(0);

        System.out.println(h1);

        Hotel h2 = new Hotel();

        System.out.println(h2);

    }
    
}
