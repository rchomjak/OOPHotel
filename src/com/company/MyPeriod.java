package com.company;

import java.time.LocalDate;

public class MyPeriod implements MyPeriodInterface {

    private LocalDate startDate;
    private LocalDate stopDate;

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
        return this.stopDate;
    }
}
