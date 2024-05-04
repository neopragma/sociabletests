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

The point of mentioning this here is to clarify that there's no need to plow through all the exercises using Detroit and London TDD separately. That would not tell us anything useful. The key difference to explore is the use of mocks vs. the use of Nullables with Sociable Tests, which don't use mocks. 

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

!["The cat did it," said the dog](images/guilty-dog.png)

If bad design is causing problems with mocks rather than the other way around, then substituting Nullables and Sociable Tests for mocks won't solve it. Let's find out.

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

## General observations (language-independent)

James Shore took a look at the solution in package ```com.neopragma.sociable.v4``` and kindly offered detailed feedback on it. I'll refer to his comments as "JS May 1" because I saw them on May 1, 2024. I'll document how his feedback changed my understanding of the technique. I'm keeping the original observations in place because others who have a background using mocks may experience the same issues. 

#### Separation of concerns 

Blending production code with stub code in the same class or source file felt like a violation of Separation of Concerns. Depending on the main responsibility of the class, it may be valid for it to support a Nullable instance. If the only purpose of the Nullable instance is to pretend not to be a test double, then I think that's a separate concern. 

![I'm not a double, I'm an Embedded Stub!](images/not-a-double.png)

**After May 1** 

It still seems to me the Embedded Stubs are effectively the same thing as Test Doubles, except in cases when they have some legitimate function in the production code. The Kata I'm using in the exploration doesn't offer any use cases for this other than to support tests. In effect, we're using mocks without using a mock library. That's not not using mocks. 

#### Unused code in production

I'm not comfortable having excess code present in the deployed production application as it affords hackers opportunities to stick malware there, using the unused object code as a patch area. I think people call this the "threat surface" of the system. The hand-rolled Embedded Stubs are not used in production. They provide an area in memory where hackers can inject malware without being noticed.

James suggests some possible use cases for Nullables in production code. They are highly dependent on what the application does, not relevant in most cases, and can always be implemented in some other way.

Not all applications are cloud applications, but cloud applications have some additional considerations that may be relevant here. They're susceptible to "gray failures" and to unusual sequences of events that aren't easy (if even possible) to set up in a controlled test environment prior to deployment. The more "extra" code there is in the environment, the more chances there are for unexpected things to happen. It's generally a good idea to minimize production code and keep things pretty tight.

**After May 1** 

Applying James' suggestions, I learned that creating infrastructure wrappers with Embedded Stubs involves far more code in Java than it does in JavaScript. I'm moving this concern from the General Observations section to the Java-specific section. It seems like a language-specific issue.

#### Blurred distinction between isolated low-level tests and tests of larger scope

While the Kata solution is not as complicated as a "real" codebase, I worry that the Sociable Tests would weaken test isolation, inviting test failures that do not point directly to the immediate cause of a problem, but instead break multiple tests cases at once. This would require 20th-century-style problem analysis in situations when a well-isolated unit test could point directly to the problem. If true, this would also impact throughput negatively.

It reminds me of relying on integration tests or other types of tests of large scope for all levels of testing. In my experience, we're better served by building test suites at multiple levels of abstraction, with more small ones than large ones, each calling out a very specific system behavior. 

**After May 1** 

Until now, I haven't learned anything about the Nullables approach that alleviates this concern. Still open to learning.

## Exploration 1: Java

The first language to explore is Java, an OO language with static typing. 
 
- [Steps I took](java-dev-steps.md). 
- [Java-specific observations](java-observations.md).

### tl;dr 

Initially, I had thought this approach was a no-go for Java. On reflection, I want to give it a qualified "yes." 

There are three general concerns to be addressed.

- test code deployed to production 
- increased threat surface due to unused code in production - Embedded Stubs require more code in Java than in some other languages
- potential recruitment and retention challenges due to non-standard approach - developers want to stay marketable

Famously, Java runs on many different platforms. A couple of production environments may provide enough security and bandwidth to cancel out the first two concerns. Those environments also alleviate the concern about recruitment and retention, for reasons unrelated to Java community conventions. 

#### Cloud environment

If the application is deployed into a cloud environment and is executed in a virtual machine or container, the environment provides some isolation for the code, and if the size of the code is larger than necessary it will not interfere with any other applications. If you're deploying discrete services that are "right-sized" rather than a large, monolithic application, so much the better.

In addition, two operational standards can help with security concerns. 

The first is the phoenix server strategy, in which server instances are routinely destroyed and re-created from base instances that are "clean." This reduces the time available for hackers to introduce malware, and when they succeed, it limits the time when the malware can be active. For instance, if servers are cycled every hour, then malware will never live more than an hour. Its average lifespan will be on the order of 30 minutes.

The second is active, real-time monitoring of the production environment. This is usually based on the concept of _observability_, and is handled with special tooling and software design details. Some people conflate aggregated log analysis with observability-based monitoring, but log analysis occurs after the fact and provides bad actors with too much time to cause trouble before intrusions are detected.

In a cloud environment managed in that way, I can see using Embedded Stubs as substitutes for Test Doubles. 

#### Mainframe environment 

The other environment that can alleviate these concerns is the IBM System Z, popularly called "mainframe." This is relevant to Java in particular. Java is a first-class citizen of the System Z. Other popular languages for cloud solutions don't enjoy the same degree of custom support for performance and security.

One relevant characteristic of the Z platform is the huge amount of memory available. Memory is called _storage_ in that environment. Z systems can be configured with over 40 terabytes of real storage and 6 terabytes of flash memory. The 64-bit addressing mode allows for a theoretical maximum of 16 exabytes of virtual storage per address space. A little excess code here and there has no measurable effect on throughput. 

Another relevant characteristic is the extensive security measures built into the system. Regardless of which operating system hosts the application, it benefits from the on-chip CPACF crypto accelerator in each processor core as well as the Crypto Express device, which applies security algorithms to every instruction execution (plus more that is too lengthy to describe here). 

For the traditional operating systems, z/OS, z/VSE, z/TPF, and the type 1 hypervisor z/VM, there are also four different software-based storage protection schemes checking every store and fetch operation. For KVM and Linux LPARS, the Crypto Express can be fitted with a Kubernetes adapter, extending further hardware-level security features to cloud workloads that run in those environments on the System Z. 

The measures IBM has taken to maximize performance for Java also help with security, to an extent. Java bytecodes are taken apart and put back together under the covers. Bytecodes run under highly customized JVMs. Five levels of software-based performance enhancements are dynamically applied at runtime based on frequency of method calls. The unused code deployed into production (that is, the Embedded Stubs) may not exist for long in virtual storage. They are never called, so the system may remove them to free up storage. An infrequently-called method will be loaded on demand, but there's no need to keep it resident all the time. 

In that environment, I can see using Embedded Stubs as substitutes for Test Doubles for Java. 

#### Recruitment and retention concerns 

Both cloud environments and the System Z are enjoying growth in job opportunities (and the System Z is used as a cloud infrastructure, too). Many software professionals will be glad to have an opportunity to get started in one or both these environments. 

Another consideration is that most jobs in these areas are filled by H1B visa holders (in the US, anyway). They are constrained by the fact their visas are "owned" by their employers. Even if they worry that their mainstream Java skills may be eroded by focusing on a non-standard way of structuring code, the stability of their employment is likely to be a more important consideration. 

## Exploration 2: Ruby 

As it seems the Nullables approach was developed in a shop that mainly uses JavaScript, it seemed reasonable to try it using another dynamically-typed language. Will the approach be as easy to use with Ruby as with JavaScript? Let's find out.

- tl;dr - TBD 
- Steps I took - TBD
- Ruby-specific observations - TBD

## Exploration 3: C#

C# is a statically-typed OO language, widely used for business applications. In may respects, it's very similar to Java. There are some differences that make it interesting for this exploration, in my opinion. Let's find out.


- tl;dr - TBD 
- Steps I took - TBD
- C#-specific observations - TBD

## Exploration 4: F# 

The pattern language was developed with OO languages in mind. Since TDD applies to all kinds of languges, it seemed sensible to try the Nullables and Sociable Tests approach using non-OO languages, as well. F# is a Functional language for the .NET environment.

- tl;dr - TBD 
- Steps I took - TBD
- F#-specific observations - TBD

## Exploration 5: COBOL 

Procedural languages represent another category of imperative languages, intrinsically neither Object-Oriented nor Functional, although they may have some OO and/or Functional support. COBOL is a well-known procedural language designed for business applications. It's still used in legacy systems in the financial, energy, and public services sectors. 

- tl;dr - TBD 
- Steps I took - TBD
- COBOL-specific observations - TBD

