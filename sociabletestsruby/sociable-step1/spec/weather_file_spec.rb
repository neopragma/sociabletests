require './spec/spec_helper'
require_relative '../lib/weather_file.rb'

describe 'stubbed WeatherFile' do
  let(:sut) { 
    WeatherFile.new FileWrapper.new([ 
      "header record",
      "",
      "  15 072   032        ", 
      "   6  91    59        " 
    ])
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
describe 'real WeatherFile' do
  let(:sut) { 
    WeatherFile.new FileWrapper.new "../weather.dat"
  }
  it "extracts values from records" do  
    value_ranges = sut.extract_temp_data_per_day 
    expect(value_ranges.length).to eq(30)
    expect(value_ranges[0].key).to eq(" 1")
    expect(value_ranges[0].difference).to eq(29)
    expect(value_ranges[1].key).to eq(" 2")
    expect(value_ranges[1].difference).to eq(16)
  end
  it "finds the range with the smallest difference" do 
    value_ranges = sut.extract_temp_data_per_day 
    expect(sut.minimum_range(value_ranges).key).to eq('14')
  end  
end    
