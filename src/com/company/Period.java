package com.company;

import java.util.Date;

public class Period implements PeriodInterface {

    Date startDate;
    Date stopDate;

    Period(Date startDate, Date stopDate) {
        this.startDate = startDate;
        this.stopDate = stopDate;
    }

    @Override
    public void getStartDate() {

    }

    @Override
    public void getStopDate() {

    }
}
