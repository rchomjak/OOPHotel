package com.company;

import java.util.ArrayList;
import java.util.List;

public class ReservationsInfo implements ReservationsInfoInterface {


    private List<List<ReservationInfo>> reservationsInfo = new ArrayList<>();

    ReservationsInfo() {
        return;
    }


    @Override
    public void delReservationInfoByGid(int gid) {
        return;
    }

    @Override
    public void delReservationInfoById(int id) {
        return;
    }


    @Override
    public void addReservationInfo(List<ReservationInfo> reservInfo){

        return;
    }

    @Override
    public List<ReservationInfo> findReservationInfoByGid(int gid) {
        return null;
    }
}

