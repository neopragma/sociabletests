package com.neopragma.sociable.v4;

public class Driver {

    private static Weather app;
    public static void main(String[] args) {
        app = new Weather(WeatherDataImpl.create());
        System.out.println("The day with the smallest temperature spread is day number " +
                app.getDayWithMinimumTemperatureSpread());
        System.exit(0);
    }
}
