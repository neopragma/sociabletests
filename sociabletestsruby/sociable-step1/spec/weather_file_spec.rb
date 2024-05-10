require './spec/spec_helper'
require_relative '../lib/weather_file.rb'
require_relative '../lib/file_wrapper.rb'

describe 'WeatherFile' do
  let(:sut) { 
    WeatherFile.createNull [ 
      "header record",
      "",
      "  15 072   032        ", 
      "   6  91    59        " 
    ]
  }
  it "extracts values from records" do  
    value_ranges = sut.extract_temp_data_per_day 
    expect(value_ranges.length).to eq(2)
    expect(value_ranges[0].key).to eq("15")
    expect(value_ranges[0].difference).to eq(40)
    expect(value_ranges[1].key).to eq(" 6")
    expect(value_ranges[1].difference).to eq(32)
  end
  it "finds the range with the smallest difference" do 
    value_ranges = sut.extract_temp_data_per_day 
    expect(sut.minimum_range(value_ranges).key).to eq(' 6')
  end  
end    