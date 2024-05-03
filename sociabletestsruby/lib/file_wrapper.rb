class FileWrapper 
  def initialize pathname 
    @file = File.new(pathname) unless pathname == nil
  end
  def readline
    @file.readline 
  end  
end 