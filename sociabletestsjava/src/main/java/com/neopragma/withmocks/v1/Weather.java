package com.neopragma.withmocks.v1;

import java.util.List;

public class Weather {
    private WeatherData weatherData;

    public Weather(WeatherData weatherData) {
        this.weatherData = weatherData;
    }

    public Integer getDayWithMinimumTemperatureSpread() {
        List<MinMaxTemps> temps = weatherData.getMinMaxTemps();
        return temps.getFirst().dayNumber();
    }

}
