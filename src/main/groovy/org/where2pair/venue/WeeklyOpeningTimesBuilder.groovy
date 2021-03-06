package org.where2pair.venue

import org.where2pair.venue.DailyOpeningTimes.OpenPeriod
import org.where2pair.venue.DailyOpeningTimes.SimpleTime

import static org.where2pair.venue.DayOfWeek.MONDAY
import static org.where2pair.venue.DayOfWeek.SUNDAY

class WeeklyOpeningTimesBuilder {

    Map weeklyOpeningTimes = (MONDAY..SUNDAY).collectEntries { [it, new DailyOpeningTimes()] }

    WeeklyOpeningTimesBuilder addOpenPeriod(DayOfWeek day, SimpleTime openTime, SimpleTime closeTime) {
        DailyOpeningTimes dailyOpeningTimes = weeklyOpeningTimes[day]
        dailyOpeningTimes.openPeriods << new OpenPeriod(openTime, closeTime)
        this
    }

    WeeklyOpeningTimes build() {
        new WeeklyOpeningTimes(weeklyOpeningTimes: weeklyOpeningTimes)
    }
}
