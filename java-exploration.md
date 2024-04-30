# Java Exploration 

[Back to README.md](README.md)

## Kata 4, Part 1, Java version using mocks, step 1

By convention, nearly all Java developers separate production and test code by creating separate directory trees for the two, and defining the same package in both trees. Tools like Maven, Gradle, IntelliJ IDEA, and Eclipse IDE assume this is the default directory structure for all Java projects. 

So, making the same assumptions and skipping ahead a bit in the TDD process, the code in subdirectory ```sociabletestsjava``` in package ```com.neopragma.withmocks.v1``` is what I came up with as a starting point for developing Part 1 of the Kata.

Where we are: 

- We have a single failing unit test case that fails for "the right reason". 

- We have a mock of interface ```WeatherData```, which ostensibly will become the basis for a class to read the input file and get the data into a Java-esque form. At the moment, we don't need a concrete implementation class for ```WeatherData```. 

- The Java-esque form of the weather data is represented by a value object, implemented as a Java Record named ```MinMaxTemp```. 


## Kata 4, Part 1, Java version using Nullables, step 1

Now let's bring the Nullables with Sociable Tests version up to an equivalent point. 

Per the pattern language, a Nullable is a class that can be instantiated with a minimal configuration, just suffient to provide a "valid" instance that supports all necessary "business" logic but excludes real functionality to read/write external data stores and so forth. James suggests defining a factory method named createNull() to handle the instantiation. 

His article also provides examples of how to include an Embedded Stub to mimic selected functionality of an external dependency, and how to define parameters for the Nullable's createNull method to pass in values we want to be returned in our test cases. 

Bear in mind I'm not an expert at this; I'm trying it for the first time. Hence, the code in subdirectory ```sociabletestsjava```, package ```com.neopragma.sociabletests``` may be more complicated than necessary. 

Where we are:

- We have a single failing unit test case that fails for "the right reason", not using mocks.  

- We have a Nullable concrete implementation of interface ```WeatherData```, named ```WeatherDataImpl```. It has factory methods ```create()``` and ```createNull()``` based on the explanation and examples from James' article.  

- The Java-esque form of the weather data is represented by a value object, implemented as a Java Record named ```MinMaxTemp```. This is the same as for the version using mocks. 

## Impressions so far 

At this point, I had not built enough functionality to experience the potential value of Nullables and Sociable Tests over mocks and isolated tests, but I already had an impression of the two approaches from the perspective of how hard or easy they are to use.

Just getting to this bare-bones starting point was a bit of a hassle using mocks. In my view, it's always a hassle to use Java with Maven or Gradle plus IntelliJ IDEA or Eclipse with Mockito or any other mocking library added on. All those tools work fine individually; the hassle comes up when you try to use them together. 

Maybe I should say, when _I_ try to use them together. No matter how many times I've used these tools together, it never gets any easier. You're probably better at this than I, so that might sound funny to you. But I may be a pretty good example of an "average" programmer, and that's what you'll have on your team, so don't laugh too hard. 

I was looking forward to writing the same code without using mocks. I understand that the first time any of us tries something new, we encounter some speed bumps. That's only to be expected. There's a learning curve, after all. But even considering that, I found the process of getting the first failing test case to the right state to be far more tedious using Nullables than it was using mocks. 

The main reason was that I had to invest mental energy into the details of handling file I/O. I wanted to focus on the challenge of the Kata, and leave the I/O handling for later. But I had no choice if I wanted to write a Nullable that included an Embedded Stub that could behave exactly like a Java BufferedReader to ingest the input file provided for the Kata. 

Even then, the result is an awful hack that will require ongoing improvement throughout the development process. All that effort amounts to hand-rolling a mock. We discard the mocking _library_, but we end up rolling our own in the end. The Nullable ```WeatherDataImpl``` class is a kludge of production code mixed with pseudo-mock code. This violates Separation of Concerns. When combined with Sociable Tests, it also weakens test isolation. 

None of the pseudo-mock code belongs with the production code. Its presence increases the threat surface of the deployed application, without adding any value beyond what we get by using mocks and keeping all test-related code separate from the production code. At least, that's my impression so far...but I've only taken the first baby steps. Let's see how it goes from here. 

## Kata 4, Part 1, Java version using mocks, step 2 

This isn't Part 2 of the Kata (it has three parts). It's just "step 2" in the pseudo-TDD development process I'm following. 

What I'd like to do in this step is implement enough functionality to identify the day that has the smallest temperature spread, given more than one day's weather data. 

First, I set up a test case with a mock that returns three values in succession, like this. 

![Failing test for 3 days' data using mocks](images/withmocks-v2-test-3-days-fail.png)

As expected, it failed because the production code always returned the first entry in the list. So far, so good. 

![Logic always returns 1st day in the list](images/withmocks-v2-weather-takes-1st-entry.png)

A naive implementation makes the test case pass. 

![Find smallest temperature spread](images/withmocks-v2-naive-implementation.png)

## Kata 4, Part 1, Java version using Nullables, step 2

Now let's do the same with our Nullables version. 

![Failing Sociable test for 3 days' data](images/sociable-v2-test-3-days-fail.png)

Here's the output from the test run:

![Test output - expected 15, got 4](images/sociable-v2-expected-15-got-4.png)

The new test case fails as expected, but not for a "good" reason. Notice the actual result was Day 4, which isn't correct. The production code is plucking out the second digit of the day number from the input record, and the first day in this set of input data is 14. This is because of the hacky implementation of the Embedded Stub. 

This is in the ```loadMinMaxTemps()``` method of class ```WeatherDataImpl```. 

![Big Hammer code to extract fields from input records](images/hacky-way-to-pick-out-input-fields.png)

If we change this hack to a slightly different hack, we can extract the strings corresponding to field values in each input record into integers, removing all characters that are not numerical digits. We can also handle day numbers of 1 or 2 digits and temperatures of up to 3 digits (but not negative numbers or decimal places).

![Slightly less-hacky code to extract fields from input records](images/less-hacky-way-to-pick-out-fields-1.png)

![Slightly less-hacky code to extract fields from input records](images/less-hacky-way-to-pick-out-fields-2.png)

Now the new test case fails for the "right reason" - the production code always returns the first day number in the list. 

If we put the same naive solution into the ```Weather``` class as we did in the mock version, the test cases all pass. Now both versions are at the same point.

## Impressions so far

I spent considerably more time taking this small step with the Nullables version than with the version using mocks. I had to context-switch between thinking about the business logic of the application and thinking about how to make the Embedded Stub behave like a mocked-out BufferedReader. This involved some fiddling because the input file format is not typical for Java applications. 

Since I had to make the Embedded Stub work the same as a BufferedReader, I wondered if the program would "just work" with the entire input file. I wrote a driver class, cleverly named ```Driver```, to run the application from a command line. Lo and behold, after a little tweaking of the ```substring()``` values for the fields, the "full application" ran and yielded the correct answer. 

This result would have been deferred using the version with mocks, as I wouldn't have bothered to get the I/O functionality working this early in the development process.

But I still don't like having hand-rolled mock code included in the production code, even if it is carefully not named "hand-rolled mock code." 

## Kata 4, Part 2, Java version using mocks 

The second part of the Kata asks us to write code roughly similar to the Weather program to analyze statistics from the English Premier Football League. A file is provided containing data about teams that played in the 2001-2002 season. 

Our program is to determine the team with the smallest difference between the number of goals they scored against opponents and the number of goals opponents scored against them.

The input file contains fixed-format records, like the Weather data file, but with different fields.  

The challenge is intentionally similar to the Weather problem in Part 1 of the Kata, because we'll try extracting common code in Part 3 (yes, I read ahead.) It took virtually no time to set up the same kind of unit tests and starter code for this application, which is in subdirectory ```sociabletestsjava``` in package ```com.neopragma.withmocks.v3```. 

Having lived through the initial horror of setting up Mockito (but still not fully recognized by IntelliJ), adding more to the project proved to be dirt simple. 

## Kata 4, Part 2, Java version using Nullables 

It took much longer to get the Sociable Test version to the same point. The time was spent in fiddling with the details of field positions and lengths in the ```StubbedReader``` class for ```FootballDataImpl```. 

## Kata 4, Part 3, Java version using mocks, refactoring

Part 4 of the Kata asks us to factor out common code from the Weather and Football solutions. Let's see how easy or hard it is to test-drive these changes, starting with the version using mocks. 

These two methods are almost identical. This is ```getDayWithMinimumTemperatureSpread()``` in class ```Weather```. 

![Method getDayWithMinimumTemperatureSpread() before refactoring](images/getdaywithminimumtemperaturespread.png)

This is method ```getTeamWithMinimumScoringSpread()``` in class ```Football```.

![Method getTeamWithMinimumScoringSpread() before refactoring](images/getteamwithminimumscoringspread.png)

Both these methods look for an entry in a list of value objects that meet certain criteria. In both cases, the entry of interest has the minimum difference between two integers. 

![MinMaxTemps Record before refactoring](images/minmaxtemps.png)

![GoalsForAndAgainst Record before refactoring](images/goalsforandagainst.png)

The value objects could be of a generalized common class. Each contains a sort of "key" that identifies either a day or a football team and two integers that represent a range of values. 

The methods to find the value object in a list that has the smallest range of values could be generalized and moved to a helper class. 

The "key" for the Weather solution is the day number when the temperature range was smallest. It's currently an Integer, but there's no reason it couldn't be a String. That would make the types of the values in the data object the same. 

Let's see what changes we must make to the test cases to express the intent of the refactoring. 

In class ```FootballTest```, our test case refers to the value object class ```GoalsForAndAgainst```. We'll want to change those references to the name of our common value object class. Let's call it ```ValueRange```, for lack of a better name. 

That will change this...

![Football test case before changes](images/football-test-case-before-changes.png) 

...to this...

![Football test case after changes](images/football-test-case-after-changes.png)

At this point the test class doesn't compile. We need to create the ```ValueRange``` class. 

Why not change the other football-specific references? We want the code to be self-describing. If everything were generalized, people would not be able to see at a glance which code pertains to football and which code pertains to weather. We'll leave the class names and method names for football-related code the same. 

![ValueRange Record definition](images/valuerange-record-definition.png)

Now in class ```Football```, method ```getTeamWithMinimumScoringSpread()```, let's generalize the names of things so we can extract a common method to find the smallest range for "any" data. 

![Method getTeamWithMinimumScoringSpread() after refactoring](images/getteamwithminimumscoringspread-after.png)

We need to modify the ```FootballData``` interface so it returns a list of ```ValueRange``` objects instead of the old ```GoalsForAndAgainst``` objects. 

![Interface FootBallData after refactoring](images/footballdata-interface-after.png)

With these changes done, running the tests for package ```com.neopragma.withmocks.v4``` got one test failure - the one we changed. It failed for the expected reason. The code under test returned "Team1" instead of "Team2". We set the wrong expectation so we would see that the test case could fail for the right reason. It can. Fixing that, we're all green. 

Now to tackle the Weather side. We start by making similar changes to class ```WeatherTest``` so we're using the common ```ValueRange``` class instead of the Weather-specific ```MinMaxTemps``` class. This time, we have to change the "key" values to Strings instead of Integers, and all references to those values. 

In class ```Weather``` we change method ```getDayWithMinimumTemperatureSpread()``` to create a list of ```ValueRange``` objects instead of ```MinMaxTemps``` objects, and we change the return type to String.

In class ```WeatherData```, method ```getMinMaxTemps()``` needs to return a list of ```ValueRange``` objects. 

Several changes were required, but they were straightforward. 

We can delete classes ```MinMaxTemps``` and ```GoalsForAndAgainst```. One more test run to make sure we didn't inadvertently break something when we deleted them. 

Next, we need to extract the common code for finding the list entry with the smallest spread of values into a separate helper class. 

![Extracted helper methods](images/helper-findsmallestrangein.png)

That reduces ```getTeamWithMinimumScoringSpread()``` to just this...

![Simplified method](images/getteamwithminimumscoringspread-simplified.png)

...and ```getDayWithMinimumTemperatureSpread()``` to this:

![Simplified method](images/getdaywithminimumtemperaturespread-simplified.png)

## Kata 4, Part 3, Java version using Nullables, refactoring #1 

This is the same refactoring I did for the mock version, to generalize the ```GoalsForAndAgainst``` and ```MinMaxTemps``` value object classes and extract the code to find the list entry with the smallest range of values to a Helper class. 

Here I experienced an advantage of the Nullables approach. There was no need to modify the test cases to make them refer to ```ValueRange``` objects, because the test code for the Nullables solution passes fake input records as Strings rather than Lists of ```ValueRange``` objects. 


## Kata 4, Part 3, Java version using Nullables, refactoring #2

When copying source from the \*.v2 and \*.v3 packages, the project wouldn't build due to duplicate class names - the Embedded Stub classes in ```WeatherDataImpl``` and ```FootballDataImpl```. This is part of the hand-rolled mock code I wrote to try and follow the guidelines for Nullables. 

I was planning to extract that into a common class anyway, but I wanted to try and get all tests passing with the code as it currently stood in package \*.v2 for Weather and package \*.v3 for Football. Had the code been in the same package from the beginning, this name collision wouldn't have occurred (or would have occurred much sooner). My fault for keeping the code separate for parts 1 and 2 of the Kata.  

The twist is the ```withInputRecords()``` method in both ```StubbedReader``` classes. To mimic the behavior of the "real" BufferedReader, that method adds header lines to the input record array that look like the header lines from the ```weather.dat``` and ```football.dat``` files. 

That part of the code must differ between the Weather and Football solutions; but I'm loathe to start changing that code until I have a clean test run for the existing code. Alternatively, we could shift the burden of supplying the fake header records to test methods, and omit that logic from ```StubbedReader```. That would simplify the extraction, but complicate the test suite and invite errors in test case setup.

In my opinion, this problem is a direct result of using the Nullables approach to avoid mocks. In the version that uses mocks, none of this code exists. I can only imagine how this kind of problem would mushroom in a "real" code base consisting of more than two components and a couple of helpers. The practical effect on most "average" programmers is likely to be that they just don't refactor. 

As a quick workaround, I changed the name of the ```StubbedReader``` class in ```WeatherDataImpl``` to ```StubbedReader2```. That isn't a very good name, but it will soon go away. 

Extracting a single ```StubbedReader``` class is straightforward and doesn't require any changes to test code. But this will be more than "just" a refactoring, because I need to support the fake header records for the two input file formats. 

The code in ```WeatherDataImpl``` and ```FootballDataImpl``` that uses the ```StubbedReader``` instances will have to pass the appropriate fake header records to those instances. Then, the logic in the ```StubbedReader``` will have to change slightly to handle the fake header records. 

With ```StubbedReader``` now separate from ```WeatherDataImpl``` and ```FootballDataImpl```, I moved the responsibility for inserting the header records to the callers of method ```withInputRecords()```. 

Here's the original ```createNull()``` method in class ```WeatherDataImpl```. 

![Original WeatherDataImpl.createNull()](images/orig-weatherdataimpl-createnull.png)

Here's the original ```withInputRecords()``` method in the Embedded Stub class ```StubbedReader``` from class ```WeatherDataImpl```. 

![Original WeatherDataImpl StubbedReader.withInputRecords()](images/orig-weatherdataimpl-withinputrecords.png)

I took the code from ```withInputRecord``` that adds fake header records to the array of fake input records and moved it to the caller, ```WeatherDataImpl.createNull()```. 

The methods then looked like this:

![New WeatherDataImpl.createNull()](images/new-weatherdataimpl-createnull.png)

![New WeatherDataImpl StubbedReader.withInputRecords()](images/new-weatherdataimpl-withinputrecords.png)

Now let's make similar changes to ```FootballDataImpl```. The original version:

![Original FootballDataImpl.createNull()](images/orig-footballdataimpl-createnull.png)

The modified version:

![New FootballDataImpl.createNull()](images/new-footballdataimpl-createnull.png)

Both ```WeatherDataImpl``` and ```FootballDataImpl``` can use the same ```StubbedReader```.

And that concludes the first refactoring. 


## Kata 4, Part 3, Java version using Nullables, refactoring #3 

Both ```WeatherDataImpl``` and ```FootballDataImpl``` contain the same one-line helper method, ```stringToInteger()```. The method takes a string that may contain a numerical value along with non-numeric characters, removes all the non-numeric characters and returns an Integer. We could move this to the ```Helpers``` class we created earlier.

It's only a one-liner, but it is used in more than one place and it contains a Regular Expression. If multiple copies of it exist in the codebase, the Regular Expression may be modified unintentionally and the results could be confusing. So, let's move it. 

No changes to test code are necessary.

This refactoring was quite simple to test-drive. I'm still not convinced this code should exist at all, however.







