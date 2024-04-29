package com.neopragma.sociable.v2;

import java.util.List;

public interface WeatherData {
    public List<MinMaxTemps> getMinMaxTempsForMonth(Integer monthNumber);
}