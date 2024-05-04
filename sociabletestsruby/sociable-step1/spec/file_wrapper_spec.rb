require './spec/spec_helper'
require_relative '../lib/file_wrapper.rb'

describe FileWrapper do
  it "reads a real file" do  
    sut = FileWrapper.new "file_wrapper_test_data.txt"
    expect(sut.readline).to eq("Record One\n")
    expect(sut.readline).to eq("Record Two\n")
    expect { sut.readline }.to raise_error EOFError
  end
  it "reads a fake file" do
    sut = FileWrapper.new [ "Fake Record One", "Fake Record Two" ]
    expect(sut.readline).to eq("Fake Record One")
    expect(sut.readline).to eq("Fake Record Two")
    expect { sut.readline }.to raise_error EOFError
  end 
  it "complains if no argument is passed to the constructor" do 
    expect { FileWrapper.new }.to raise_error ArgumentError
  end   
  it "complains if the constructor argument is of the wrong type" do 
    expect { FileWrapper.new 123 }.to raise_error WrongTypeError
  end   
end