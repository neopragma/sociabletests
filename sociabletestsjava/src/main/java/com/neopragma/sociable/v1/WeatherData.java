package com.neopragma.sociable.v1;

import java.util.List;

public interface WeatherData {
    public List<MinMaxTemps> getMinMaxTempsForMonth(Integer monthNumber);
}