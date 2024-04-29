package com.neopragma.withmocks.v1;

import java.util.List;

public class Weather {
    private WeatherData weatherData;

    public Weather(WeatherData weatherData) {
        this.weatherData = weatherData;
    }

    public Integer getDayWithMinimumTemperatureSpreadForMonth(Integer monthNumber) {
        List<MinMaxTemps> temps = weatherData.getMinMaxTempsForMonth(monthNumber);
        return temps.getFirst().dayNumber();
    }

}
