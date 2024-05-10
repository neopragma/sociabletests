class FileWrapper 
  def self.create data_source 
    raise ArgumentError "Pathname to file is required" if data_source == nil
    self.new(RealReader.new data_source)
  end
  def self.createNull data_source 
    raise ArgumentError "Array of fake records is required" if data_source == nil
    self.new(StubbedReader.new data_source) 
  end    
  def initialize reader
    @reader = reader
  end
  def readline 
    @reader.readline
  end      
  class StubbedReader
    def initialize data_source
      @record_list = data_source
      @next_record = 0
    end  
    def readline
      rec = @record_list[@next_record]
      raise EOFError if rec == nil
      @next_record += 1
      rec 
    end  
  end   
  class RealReader
    def initialize data_source 
      @file = File.open(data_source, "r")
    end  
    def readline
      @file.readline 
    end  
  end  
end 