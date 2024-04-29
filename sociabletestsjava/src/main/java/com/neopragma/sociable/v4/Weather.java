package com.neopragma.sociable.v4;

import java.util.List;

public class Weather {
    private WeatherData weatherData;

    public Weather(WeatherData weatherData) {
        this.weatherData = weatherData;
    }

    public Integer getDayWithMinimumTemperatureSpread() {
        List<MinMaxTemps> temps = weatherData.getMinMaxTemps();
        int dayNumber = 0;
        int minimumTempSpread = Integer.MAX_VALUE;
        for (MinMaxTemps dataForADay : temps) {
            int todaysTempSpread = dataForADay.maximumTemp() - dataForADay.minimumTemp();
            if (todaysTempSpread < minimumTempSpread) {
                dayNumber = dataForADay.dayNumber();
                minimumTempSpread = todaysTempSpread;
            }
        }
        return dayNumber;
    }

}
