require './spec/spec_helper'
require_relative '../lib/file_wrapper.rb'

describe FileWrapper do
  it "reads a file" do  
    sut = FileWrapper.new "testfile.txt"
    expect(sut.readline).to eq("Record One\n")
    expect(sut.readline).to eq("Record Two\n")
    expect { sut.readline }.to raise_error EOFError
  end
end