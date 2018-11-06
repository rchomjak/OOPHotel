package com.company;

//Hey if you wanna change this, do not FORGET change other parts in the code!
enum LuxuryCategory {

    Cheap,
    Medium,
    Luxury,
    SuperLuxury
}


public class Room implements RoomInterface {

    int id;
    float price;
    LuxuryCategory luxuryCategory;
    int numberOfBeds;
    Boolean isOpen;


    Room(int id, int numberOfBeds,  LuxuryCategory luxuryCategory, float price) {

        this.id = id;
        this.numberOfBeds = numberOfBeds;
        this.luxuryCategory = luxuryCategory;
        this.price = price;
    }

    Room(int id, int numberOfBeds, LuxuryCategory luxuryCategory) {

        this.id = id;
        this.numberOfBeds = numberOfBeds;
        this.luxuryCategory = luxuryCategory;

        //It can be rewritten into for loop and match with some linear equation based on ordinal number -> you have to
        //keep it sorted in this case. So in EVERY case your change in enum change this code!

        switch (luxuryCategory) {
            case Cheap:
                this.price = 100;
                break;
            case Medium:
                this.price = 150;
                break;
            case  Luxury:
                this.price = 300;
                break;
            case SuperLuxury:
                this.price = 500;
                break;

            default:
                System.err.println("Oh noes, The prices of room is incorrect, probably you had changed the enum LuxuryCategory");
                break;
        }

    }


    @Override
    public float getBasePrice() {
        return price;
    }

    //Do not change this value during price calculation, the sense of the method is "just in the case"
    @Override
    public void setBasePrice(float price) {
        this.price = price;
    }

    @Override
    public Boolean getIsOpen() {
        return isOpen;
    }

    @Override
    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    @Override
    public LuxuryCategory getLuxuryCategory() {
        return luxuryCategory;
    }

    @Override
    public void setLuxuryCategory(LuxuryCategory luxuryCategory) {
        this.luxuryCategory = luxuryCategory;
    }

    @Override
    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    @Override
    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    @Override
    public int getId() {
        return id;
    }

}
