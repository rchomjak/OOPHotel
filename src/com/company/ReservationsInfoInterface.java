package com.company;

import java.util.List;

public interface ReservationsInfoInterface {

    public void addReservationsInfo(List<ReservationInfo> reservInfo);
    public void delReservationInfoById(int id);
    public void delReservationInfoByGid(int gid);
    public List<ReservationInfo> findReservationInfoByGid(int gid);

}
