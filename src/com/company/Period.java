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
    public Date getStartDate() {
        return this.startDate;

    }

    @Override
    public Date getStopDate() {
        return this.startDate;
    }
}
