package org.statistic.eggs;

import org.statistic.eggs.core.entity.Counter;

import java.util.Map;

public class DayStInfo {
    private Map<Integer, Counter> weeksStatistic;

    public Map<Integer, Counter> getWeeksStatistic() {
        return weeksStatistic;
    }

    public void setWeeksStatistic(Map<Integer, Counter> weeksStatistic) {
        this.weeksStatistic = weeksStatistic;
    }
}
