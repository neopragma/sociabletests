# Learning About Nullables and Sociable Tests

Sociable Tests are a kind of executable test used in software development and testing. They were devised by [James Shore](https://www.jamesshore.com), author of [_The Art of Agile Development_](https://jamesshore.com/v2/books/aoad2) (highly recommended, by the way) and a leading proponent of robust software engineering practices. 

Through his long experience applying test-driven development (TDD) and related practices, and teaching/coaching such practices in a wide range of companies over the years, James noticed certain recurring problems with executable test suites and application design that he felt may arise from conventional approaches to TDD. Even if the problems don't arise from those practices _per se_, they are at least commonly seen in codebases that were built using TDD.  

He wanted to find a way to enjoy the benefits of TDD that didn't tend to create or exacerbate those problems. Sociable Tests are part of his solution, which he calls a "pattern language" for testing. His approach is documented in the article, ["Testing Without Mocks: A Pattern Language"](https://www.jamesshore.com/v2/projects/nullables/testing-without-mocks). There is more to the approach than just Nullables and Sociable Tests, but that is the focus of this exploration. 

## Schools of TDD

TDD has a long history in the software industry, and you're probably familiar with it. I'd like to offer a little context just to set the stage for the exploration. 

There are several ways to approach TDD that have yielded good results, and many more ways in which TDD has been misunderstood and misapplied, with...well, _unsurprising_ results. It's easy to see how TDD may be misunderstood because some of the key terms used to describe it are not really precise - notably the words "test," "driven," "development," and "unit."

In a nutshell, there are a couple of popular ways to approach TDD. They are sometimes called the Detroit School (or Chicago School) and the London School of TDD. For reasons that remain unclear to me, many people seem to think the two are very different, and we must choose either one or the other when we use TDD. 

Practitioners actually mix and match the two freely. The choice depends on what we're doing at the moment. We don't even pause and think, "Gee, I guess I should switch to the other school now." We just do what makes sense in context. So, it's a little puzzling that people make such a fuss over it.

Dave Farley, the person behind the [Continuous Delivery channel](https://www.youtube.com/@ContinuousDelivery) on YouTube (highly recommended, by the way), was an early adopter of both "schools" of TDD and knows most of the people who were involved in their creation. At time offset 13:36 in his video, [Are You Chicago or London When It Comes to TDD?](https://www.youtube.com/watch?v=_S5iUf0ANyQ), he explains, "The London School is really an extra layer of ideas on top of the Chicago School that allows us to proceed when we have fewer answers."

The point of mentioning this here is to clarify that there's no need to plow through all the exercises using Detroit and London TDD separately. That would not tell us anything useful. The key difference to explore is the use of mocks vs. the use of Nullables with Socialized Tests, which don't use mocks. 

Most material you see on the subject will talk about _classes_, _objects_, and _methods_. That's because TDD and its friends were invented in a time when Object-Oriented languages were very popular and commonly used for application development. Yet, TDD is not limited just to Object-Oriented languages. 

## What are mocks?

One issue James noticed is the difficulty in working with test doubles, such as mocks and stubs. Complicated mock setups and fragile test cases are common problems. But what are mocks and why do people use them?

In order to isolate the code under test from external dependencies for the duration of specific test cases, we use a construct known as a Test Double. A Test Double stands in for a real dependency of the code under test in much the same way as a [stunt double](https://stuntteam.org/what-is-a-stunt-double-everything-you-need-to-know/) stands in for an actor in a movie. 

Like a stunt double, a test double is not as good-looking as the original, but as long as it's [dressed up to look like the real thing](https://metro.co.uk/2020/08/28/johnny-depps-pirates-caribbean-stunt-double-career-ending-injury-epic-fight-scenes-gift-actor-13119975/) it's all good.

I don't know who coined the term or when, but it became popularized after the publication of Gerard Meszaros' 2007 book, _xUnit Test Patterns: Refactoring Test Code_. 

Meszaros is also credited with creating a taxomony of Test Doubles. As far as I know he didn't make them up, but he sought to regularize the terminology around them. People were using various terms that seemed intuitive to them, but the hodge-podge of inconsistent terms were confusing for people interested in getting started with TDD and refactoring. Meszaros settled on _stub_, _mock_, _spy_, _fake_, and _dummy_. 

J.B. Rainsberger's 2001 book, _JUnit Recipes: Practical Methods for Programmer Testing_, contains these plus quite a few more, but most of the terms have fallen out of use. I think some or most of the terms were already in use before then.

Regardless of who should get credit for what, the basic idea of a _mock_ is a software construct that looks and acts like a real external dependency, but isn't real. It can return a predefined response to a call to any method/function of the real dependency, and it can keep track of the interactions between the client code (usually a test case) and itself. It reduces the cost of test setup and execution and ensures the behavior of the dependency will be consistent across executions of the test case, so the test case provides consistent and reliable results. 

Most people these days say "mock" when they refer to any type of test double. Some people are quick to remind us that there are specific names for specific types, but here I'm just going to say "mock" and leave it at that.

## Do mocks lead to bad design?

It's common to hear/read that using mocks with TDD leads to bad software design. Rather, it seems to be the case that bad design leads to ever-more-complicated mock setups in executable test cases. At some point, things become so cumbersome that the ship falls over and sinks, like the [Vasa](https://www.vasamuseet.se/en).

Now, no one likes to think they had anything to do with sinking the ship. So, people often blame their tools - in this case, the use of mocks or the use of TDD itself. But I'm pretty sure if you don't apply fundamental software design principles to your work, you can cause the same problems using Nullables and Sociable Tests as you can using mocks. 

![Guilty dog](images/guilty-dog.png)

If the bad design is causing problems with mocks rather than the other way around, then substituting Nullables and Sociable Tests for mocks won't solve it. Let's find out.

## About this exploration 

To get a sense of Nullables, I wanted to test-drive a small application using conventional TDD on the one hand, and again using Nullables and Sociable Tests on the other. 

James' pattern language is explicitly focused on Object-Oriented languages, so let's start with those. I propose we try languages with static typing and dynamic typing, and languages that separate test code from production code in different ways. That way, if any of those characteristics has an effect on James' approach, we'll see it. 

With that in mind, I suggest we try Java, C#, and Ruby. Java and C# use static typing and developers conventionally use different techniques to separate test and production code. Ruby uses dynamic typing. 

## What toys shall we play with?

I'm thinking of using Pragmatic Dave Thomas' Kata #4, _Data Munging_, from the [Code Kata](http://codekata.com/kata/kata04-data-munging/) site. It's simple, yet involves more than one component and includes refactoring, as well. Those characteristics should help us quickly get a sense of the Nullables with Sociable Tests approach.

The kata comprises three parts. We are asked not to read ahead, but due to the nature of this exercise it's hard to avoid doing so. We'll pretend we don't know what's coming, as best we can. 

Part 1 involves reading some data about weather in Morristown, New Jersey, for the month of June, 2002. We want to find the day in which the difference between the minimum and maximum temperatures was the smallest during the month. 

Here's a screenshot of the top few records in the input file. 

![Weather data](images/weather.png)

This looks like fixed-format data in which each field starts at a particular offset from the beginning of each record and is a specific number of bytes in length. Numerical values have leading zeroes removed. 

This format would be hard to ingest in some languages and easy in others. However, I/O handling is not the point of the exercise. Besides that, I/O is a separate concern from the "business logic" of the application. 

Looking at this in terms of London School TDD, the interface between the application and the I/O functionality is at the "edge" of our code. Therefore, we want to write an adapter layer for it. 

So, we already have a candidate for a mock and, conversely, for a Nullable. Good! We're using an OO language, so we'll mock the adapter interface or make the adapter class Nullable. 

## Cheating 

Approaching this with Detroit School TDD, I would not make assumptions about what classes to create. Instead, I would start writing microtests to tease out information about what the design "wants" to be, and when the test suite answers that question I would extract the production code into the appropriate classes and clean up the remaining test code.

But that's not the point of the exercise. We want to get down to the mocks vs. Nullables exploration right away. So, I'll assume we'll want a Weather class that responds to client requests for the day that had the smallest temperature spread, and an adapter class to read the data from an external source. Those will be our two components, with one depending on the other. 

## Exploration 1: Java

The first language to explore is Java, an OO language with static typing. There's a rambling sort of walk-through of what I did, in the nature of a travel diary, in [a separate document](java-exploration.md). 

Here's the tl;dr.

#### Developer experience

Working with Java, Maven, Mockito, and IntelliJ together isn't exactly a dream weekend at [Barbie's beach house](https://www.vogue.com/article/barbie-dreamhouse-airbnb-malibu). A development approach that eliminates one type of dependency - in this case, the mocking library - sounds like an instant improvement.

Yet I found working with the Nullables version was more tedious and time-consuming than working with the mock version. To my surprise, I soon felt eager to go back to struggling with Mockito/IntelliJ integration. On balance, it was less troublesome than using Nullables.

I also didn't like the frequent context-switching between hand-rolling the Embedded Stub and working on the actual problem at hand. There was no way to write test cases without the stub. I wanted to focus on the application logic at first, and mocks enable that.

#### Recruiting and staff retention

The vast majority of Java developers available for hire are already familiar with mock libraries. Every organization that uses Nullables will have their own "flavor" of it, as it's all hand-written. That means surprises, and lots of them, in the sense of the Principle of Least Surprise. It's not out of the question to expect an organization that required this approach to face higher turnover of technical staff than they would otherwise. 

#### Codebase size

The Nullables solution for Kata 4 ended up with thirteen (13) production classes as compared with six (6) for the version using mocks. 

Granted, I didn't finish the whole thing using mocks, and there would be eight (8) classes in a version equivalent to the Nullables version. Even so, the exercise suggests there's more code to manage with the Nullables approach.

#### Separation of concerns 

Blending production code with stub code in the same class or source file felt like a violation of Separation of Concerns. Depending on the main responsibility of the class, it may be valid for it to support a Nullable instance. If the only purpose of the Nullable instance is to pretend not to be a test double, then I think that's a separate concern. 

#### Impact on throughput

The most complicated logic is in the ```StubbedReader``` class, which is to all intents and purposes just a hand-rolled mock or stub. Most of the development time went into that class, as well. It's still a hack, and would require modification to correspond with every future change to the product. I suspect this is characteristic of the approach. A codebase containing hundreds or even thousands of these things would cause teams to burn a lot of time working on code that isn't really part of the product.

The exploration suggests a "real" team that worked in this way would spend proportionally more time on test setup code than they normally would do. That means less time spent on value-add activities, lower process cycle efficiency, and lower throughput.

#### Unused code in production

I'm not comfortable having excess code present in the deployed production application as it affords hackers opportunities to stick malware there, using the unused object code as a patch area. I think people call this the "threat surface" of the system. The hand-rolled mocks are not used in production  They provide an area in memory where hackers can inject malware without being noticed.

James suggests some possible use cases for Nullables in production code. They are highly dependent on what the application does, not relevant in most cases, and can always be implemented in some other way.

Not all applications are cloud applications, but cloud applications have some additional considerations that may be relevant here. They're sensitive to "gray failures" and to unusual sequences of events that aren't easy (if even possible) to set up in a controlled test environment prior to deployment. The more "extra" code there is in the environment, the more chances there are for unexpected things to happen. It's generally a good idea to minimize production code and keep things pretty tight.

#### Blurred distinction between isolated low-level tests and tests of larger scope

While the Kata solution is not as complicated as a "real" codebase, I worry that the Socialized Tests would weaken test isolation, inviting test failures that do not point directly to the immediate cause of a problem, but instead break multiple tests cases at once. This would require 20th-century-style problem analysis in situations when a well-isolated unit test could point directly to the problem. If true, this would also impact throughput negatively.

It reminds me of relying on integration tests or other types of tests of large scope for all levels of testing. In my experience, we're better served by building test suites at multiple levels of abstraction, with more small ones than large ones, each calling out a very specific system behavior. 

## C#

Next, I tried another statically-typed OO language that's widely used for business applications, C#. The "travel diary" is in [a separate document](csharp-exploration.md).

Here's the tl;dr.




## Ruby 

As it seems the Nullables approach was developed in a shop that mainly uses JavaScript, it seemed reasonable to try it using a nother dynamically-types language. 


