class WrongTypeError < StandardError
    def initialize(message = "FileWrapper constructor argument must be a String (pathname) or an Array of Strings (fake records)")
      super
    end
  end