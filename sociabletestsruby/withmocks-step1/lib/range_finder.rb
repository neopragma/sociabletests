module RangeFinder  
  def minimum_range value_ranges
    value_ranges.reduce(ValueRange.new('initial',0,1000)) { |result, current|
      result = current.difference < result.difference ? current : result 
    }
  end     
end