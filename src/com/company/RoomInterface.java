package com.company;

public interface RoomInterface {

    public int getNumberOfBeds();
    public void setNumberOfBeds(int numberOfBeds);

    public float getBasePrice();
    public void setBasePrice(float price);

    public LuxuryCategory getLuxuryCategory();
    public void setLuxuryCategory(LuxuryCategory category);

    public Boolean getIsOpen();
    public void setIsOpen(Boolean isOpen);

    public int getId();

    public void setIsDeleted();
    public Boolean getIsDeleted();

    public boolean equals(Object a);

}
