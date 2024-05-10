require './lib/value_range.rb'
require './lib/range_finder.rb'

# Ruby solution for Dave Thomas' Kata #4, Data Munging
class WeatherFile
include RangeFinder  

  def self.create data_source 
    self.new(FileWrapper.create data_source)
  end   
  def self.createNull data_source
    self.new(FileWrapper.createNull data_source) 
  end   

  def initialize file_wrapper 
    @file = file_wrapper 
    @value_ranges = Array.new
  end 

  # Get the day number, minimum temperature, and maximum temparature
  # for each record in the input file.
  # 
  # File contains fixed-format records in which each field starts at
  # a specific offset from the beginning of the record and has a
  # fixed length. Numeric fields may have leading zeroes stripped and
  # replaced by spaces.
  #
  # We identify data records by the presence of a numeric digit in
  # position 3 of the record. We skip other records (header records
  # and blank lines.  
  #
  # @return an array of ValueRange objects.
  #
  def extract_temp_data_per_day 
    begin
      rec = @file.readline  
      while rec != nil do   
        @value_ranges << ValueRange.new(
          rec[2..3], 
          rec[11..13].tr('^0-9','').to_i, 
          rec[5..7].tr('^0-9','').to_i) if rec.length > 1 && rec[3].match?(/[[:digit:]]/)
        rec = @file.readline
        end
    rescue EOFError 
    end  
    @value_ranges          
  end 
end     