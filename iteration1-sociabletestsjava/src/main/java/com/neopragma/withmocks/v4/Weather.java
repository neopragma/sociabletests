package com.neopragma.withmocks.v4;

import java.util.List;

public class Weather {
    private WeatherData weatherData;

    public Weather(WeatherData weatherData) {
        this.weatherData = weatherData;
    }

    public String getDayWithMinimumTemperatureSpread() {
        return Helpers.findSmallestRangeIn(weatherData.getMinMaxTemps());
    }

}
