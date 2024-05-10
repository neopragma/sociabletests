require './spec/spec_helper'
require_relative '../lib/value_range.rb'

describe ValueRange do
  it "calculates the distance from minimum to maximum" do  
      expect(ValueRange.new("alpha", 14, 86).difference).to eq(72)
      expect(ValueRange.new("beta", -827, 208).difference).to eq(1035)
      expect(ValueRange.new("delta", -7, -9).difference).to eq(2)
    end
    it "returns the correct key value" do  
      expect(ValueRange.new("alpha", 14, 86).key).to eq("alpha")
    end
  end    
