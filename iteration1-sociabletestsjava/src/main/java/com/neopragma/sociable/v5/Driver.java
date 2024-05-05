package com.neopragma.sociable.v5;

public class Driver {

    public static void main(String[] args) {
        System.out.println("The day with the smallest temperature spread is day number " +
                WeatherFile.create().getDayWithMinimumTemperatureSpread());
        System.out.println("The team with the smallest spread between goals scored and goals suffered " +
                FootballFile.create().getTeamWithMinimumScoringSpread());
        System.exit(0);
    }
}
