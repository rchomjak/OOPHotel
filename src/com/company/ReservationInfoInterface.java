package com.company;

import java.time.Period;
import java.util.List;

public interface ReservationInfoInterface {

    Period getPeriod();
    List<RoomInfoInterface> getRoomsInfo();


}
