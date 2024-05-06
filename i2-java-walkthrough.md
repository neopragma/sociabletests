[To README.md](README.md)

# Iteration 2 Java Walkthrough 

In iteration 1 we "cheated" - skipped ahead in the TDD process to get to a point where the difference between mocks and Embedded Stubs would come up. The result was a bit of a mess that didn't really apply the test pattern language in a way that gave us useful information about how it compares with using Test Doubles. This time, we'll painstakingly test-drive the solution. 

We know the solution to the Weather portion of the Kata has to read text records that contain the day number and minimum and maximum temperatures in string form. Then it has to extract the day number and compute the temperature difference per day. Finally, it must return the day number of the day that had the smallest difference between the minimum and maximum temperatures. 

First test: Calculate the difference between two temperatures. The code under test (CUT) is inside the test class because we don't yet have enough examples in place to suggest how we might structure the solution. We expect the wrong answer so we can see that it is possible for the example to fail for the right reason.

![Calculate the temperature difference](images/i2/i2-java-test-1-1.png)

Running the tests we get failure for the right reason. 

![First failing example](images/i2/i2-java-test-1-failure.png)

We correct the implementation:

![This implementation expected to pass](images/i2/i2-java-test-1-2.png)

Now the test passes. 

![First passing example](images/i2/i2-java-test-1-pass.png)

We know we'll have to pluck the day number and temperatures out of the input record. Let's drive out that functionality next. We set up an example to check that the code can extract the day number from a record. We write the implementation wrong in a deliberate way to ensure the example can fail for the right reason. In this case, we extract bytes 2 through 5 instead of bytes 2 through 4. We should end up with the day number plus an extra blank space.

![Extract the day number](images/i2/i2-java-test-2-1.png)

![Failing example](images/i2/i2-java-test-2-failure.png)

And there it is - failure for the right reason. 

Let's fix the implementation and get to green.

![Corrected implementation](images/i2/i2-java-test-2-2.png)

![Passing example](images/i2/i2-java-test-2-pass.png)

We also need to extract the minimum and maximum temperature values from the input record. There's a twist - the input records look like lines from a report that has been formatted for human consumption. The values are spaced out along the line apparently for readability. Numerical values have leading zeroes replaced with spaces. That's fine for day number; it's for display, so showing it as a string with leading zeroes supporessed works well. But we have to convert the temperatures to a numerical format to perform arithmetic on them. As the temperatures are in Fahrenheit, they may be up to three digits long. So, the maximum temperature field in the input record starts in position 11 and is three bytes long. We'll have to handle the leading space somehow. First, let's drive out the logic to extract the field as-is. We'll write the implementation so that it will return the value with a trailing space, to make sure the example can fail for the right reason.

![Extract the maximum temperature as a string](images/i2/i2-java-test-3-1.png)

![Failing example](images/i2/i2-java-test-3-failure.png)

Okay, the example can fail for the right reason. Let's fix the implementation and get to green.

![Corrected implementation](images/i2/i2-java-test-3-2.png)

![Passing example](images/i2/i2-java-test-3-pass.png)

So far, so good. We can pluck out the correct characters from the input record. From examining the input data, we know the values are always integers. Therefore, the simplest way to convert the string representation of a number tha may be from 1 to 3 digits long is to use a Regular Expression to eliminate all characters that are not decimal digits. We changed the last example to expect an integer 84, and added the Regular Expression to the method implementation. Skipped taking a screen capture of the failed example this time. The final result is:

![Now returning Integer and expecting Integer](images/i2/i2-java-test-3-3.png)

![Passing example](images/i2/i2-java-test-3-3-pass.png)

What if we have a negative temperature value? Our naive Regular Expression may discard the minus sign. Let's try it and see.

![Extract negative temperature value](images/i2/i2-java-test-4-1.png)

![Failing example](images/i2/i2-java-test-4-1-failure.png)

As expected, our implementation doesn't work for negative numbers. Let's adjust the Regular Expression. The change is pretty small. In case you didn't notice it, we added a dash inside the square brackets where we have the negated character class for digits.

![Extract negative temperature value](images/i2/i2-java-test-4-2.png)

![Passing example](images/i2/i2-java-test-4-2-pass.png)

The logic to pick up the minimum temperature for the day is the same, except we'll look at different positions in the input record.

At this point, we have snippets of code in a test class that verifiably extract the day number, minimum temperature, and maximum temperature from a single input record, and code to compute the difference between the maximum and minimum temperatures. We still haven't decided what Java classes to create. First, let's consider the next interesting bit of functionality we have to implement. 

The solution has to process a list of input records and find the day that had the smallest difference between minimum and maximum temperatures. That suggests we'll need a list of objects that contain the day number and the temperature difference for that day. Java doesn't natively support tuples. There's a library we could use for that, but we'd rather not add a dependency to the project just for that. We can use a Java Record instead.

Before getting into list processing, let's make sure the code we have will produce a Java Record with the contents we want. We cobble together the pieces based on some of the small examples we've coded. It doesn't have to be pretty at this point.

![Testing code to create a Java Record](images/i2/i2-java-test-5-1.png)

![Passing example](images/i2/i2-java-test-5-1-pass.png)

This is getting to be a bit much to jam into a test class. Let's think about domain entities and other such matters. 

This part of the Kata concerns weather, so let's encapsulate our logic in a class named ```Weather```. We are asked to find the day with the smallest temperature range in the input data, and return the day number. But users/clients are not going to create the input records themselves and pass them directly to the Weather application. They'll specify a file the Weather application must read. File I/O is a separate concern from the application logic. So we have two entities - the ```Weather``` class for the "business logic" and some other class to manage access to the file. 

We don't want to have real files as dependencies in our unit test suite, so we need a way to supply the ```Weather``` class with a real or fake file. 

This is where we start to do things differently when we're using mocks and when we're using James' pattern language for testing. We'll supply our fake file to the ```Weather``` class in different ways.

Using a mocking library, the typical way to do this is _via_ dependency injection. We'll define a mock (actually, a stub) and configure it to return one fake input record at a time. Test cases will inject the mock into the ```Weather``` object. That way, the production code can read the file as usual, while test code will receive the fake records from the mock. It might look something like this (crudely):

<img alt="Mocked BufferedReader" src="images/i2/weather-mock.png" width="400"/>

Using the pattern language, the way to do this is to define an adapter for the external dependency. File accesses will go through the adapter. The adapter will contain an [Embedded Stub](https://www.jamesshore.com/v2/projects/nullables/testing-without-mocks#embedded-stub) that acts like the real object that reads the file. We're going to use a Java ```BufferedReader``` instance for that, so the Embedded Stub will have the method, ```readLine()```, which will behave just like the ```readLine()``` method of ```BufferedReader```. Then we'll use dependency injection to supply the adapter to the ```Weather``` class, configured to perform real file I/O or to return fake records from the ```readLine()``` method. It might look something like this (crudely):

<img alt="Embedded Stub" src="images/i2/weather-stub.png" width="400"/>

The source files for this exploration are in subdirectory ```iteration2-sociabletestsjava```. The version using mocks lives in package ```com.neopragma.withmocks.v1``` and the version using Sociable Tests lives in package ```com.neopragma.sociable.v1```. 


