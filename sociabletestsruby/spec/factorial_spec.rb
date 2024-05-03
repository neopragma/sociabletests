require './spec/spec_helper'
require_relative '../lib/factorial.rb'

describe Factorial do
    let(:calculator) { Factorial.new }
  
    it "finds the factorial of 5" do  
      expect(calculator.factorial_of(5)).to eq(120)
    end
  end