class FileWrapper
    def initialize(*args)
        @file = File.open(args[0], "r") unless args.empty?
    end 
    def readline 
        @file.readline 
    end        
end 