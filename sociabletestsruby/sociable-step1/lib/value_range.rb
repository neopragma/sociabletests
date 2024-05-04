class ValueRange
  def initialize key, min, max 
    @key = key
    @key = key
    @min = min 
    @max = max
  end 
  def key 
    @key
  end
  def difference 
    (@max - @min).abs() 
  end  
end    