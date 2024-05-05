package com.neopragma.sociable.v4;

public class Weather {
    private WeatherData weatherData;

    public Weather(WeatherData weatherData) {
        this.weatherData = weatherData;
    }

    public String getDayWithMinimumTemperatureSpread() {
        return Helpers.findSmallestRangeIn(weatherData.getMinMaxTemps());
    }
}
