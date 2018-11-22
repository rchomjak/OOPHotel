package com.company;

import java.time.LocalDate;

public class MyPeriod implements MyPeriodInterface {

    LocalDate startDate;
    LocalDate stopDate;

    MyPeriod(LocalDate startDate, LocalDate stopDate) {
        this.startDate = startDate;
        this.stopDate = stopDate;
    }

    @Override
    public LocalDate getStartDate() {
        return this.startDate;

    }

    @Override
    public LocalDate getStopDate() {
        return this.startDate;
    }
}
