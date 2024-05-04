require_relative './wrong_type_error.rb'

class FileWrapper 
  def initialize data_source 
    if data_source.is_a? String
      @file = File.new(data_source)
    elsif data_source.is_a? Array    
      @record_list = data_source 
      @next_record = 0
    else 
      raise WrongTypeError
    end      
  end   
  def readline
    if @file 
      @file.readline 
    elsif @record_list         
      rec = @record_list[@next_record]
      raise EOFError if rec == nil
      @next_record += 1
      rec 
    else 
      raise WrongTypeError   
    end     
  end  
end 