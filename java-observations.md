[README.md](README.md)

# Nullables and Sociable Tests - Java-specific observations

## Developer experience

Working with Java, Maven, Mockito, and IntelliJ together isn't exactly a dream weekend at [Barbie's beach house](https://www.vogue.com/article/barbie-dreamhouse-airbnb-malibu). A development approach that eliminates one type of dependency - in this case, the mocking library - sounds like an instant improvement.

Yet I found working with the Nullables version was more tedious and time-consuming than working with the mock version. To my surprise, I soon felt eager to go back to struggling with Mockito/IntelliJ integration. On balance, it was less troublesome than using Nullables.

I also didn't like the frequent context-switching between hand-rolling the Embedded Stub and working on the actual problem at hand. There was no way to write test cases without the stub. I wanted to focus on the application logic at first, and mocks enable that.

**After May 1** 

With the benefit of James' feedback and continued practice, I started to get a better sense of the Nullables pattern. Originally, I had brought a mock-oriented mindset to the work, and that caused some friction. As I started to adjust my thinking, I found the developer experience easier than I did at first. 

## Recruiting and staff retention

The vast majority of Java developers available for hire are already familiar with mock libraries. Every organization that uses Nullables will have their own "flavor" of it, as it's all hand-written. That means surprises, and lots of them, in the sense of the [Principle of Least Surprise](http://principles-wiki.net/principles:principle_of_least_surprise). It's not out of the question to expect an organization that required this approach to face higher turnover of technical staff than they would otherwise. 

**After May 1** 

I still think this is a factor to consider. The pattern language is not a standard "thing" that is supported by a well-known library "everyone" uses. Maybe someday it will be. 

## Codebase size

The Nullables solution for Kata 4 ended up with thirteen (13) production classes as compared with six (6) for the version using mocks. 

Granted, I didn't finish the whole thing using mocks, and there would be eight (8) classes in a version equivalent to the Nullables version. Even so, the exercise suggests there's more code to manage with the Nullables approach.

**After May 1** 

There were two separate issues initially. First, my own understanding of the Nullables pattern was incorrect. I wrote more code than necessary to implement most of the ideas in the model. The second issue looks like a language-specific problem. It takes more code to implement an infrastructure wrapper with an Embedded Stub in Java than it does in JavaScript. 

## Unused code in production 

**After May 1**

I initially noted this concern under General Observations, but after applying James' suggestions to bring the Java solution into better alignment with the Nullable pattern, I learned that Java requires a lot more code than JavaScript to implement an infrastructure wrapper with an Embedded Stub. So I'm moving this concern here, to the Java-specific section of the notes. 

## Impact on throughput

The most complicated logic is in the ```StubbedReader``` class, which is to all intents and purposes just a hand-rolled mock or stub. Most of the development time went into that class, as well. It's still a hack, and would require modification to correspond with every future change to the product. I suspect this is characteristic of the approach. A codebase containing hundreds or even thousands of these things would cause teams to burn a lot of time working on code that isn't really part of the product.

The exploration suggests a "real" team that worked in this way would spend proportionally more time on test setup code than they normally would do. That means less time spent on value-add activities, lower process cycle efficiency, and lower throughput.

**After May 1** 

While my initial implementation of ```StubbedReader``` deviated from the pattern in that it was in a separate class, when I adjusted the structure of the code to align with the Nullable pattern the complexity moved into the infrastructure wrapper class, ```FileSystem```. The complexity is still there. As noted elsewhere, this appears to be due to the way Java works. 

If your organization uses Java, this may be a factor to consider when deciding on a standard approach. In conjunction with this, also bear in mind two general considerations that still seem valid even after I applied James' suggestions: 

- The use of Test Doubles _per se_ does not "cause" bad design. There are certainly other reasons to choose the Nullables pattern; simply avoiding mocks is not a good reason to do so. If you find yourself having to cope with complicated mock setups due to bad design in the production code, the "fix" may be to address the production code. You may also want to examine your test suite and see if it's necessary for test cases to be aware of underlying implementation details. This appears to be the general cause when people complain that they "break tests" when they refactor. The usual rule of thumb is test cases should focus on the observable behaviors of the system under test. This is the way a test suite guards against inadvertent behavior changes when we refactor - the _same tests_ are green before and after refactoring. The only exceptions should be the cases when having a test case "know" about underlying implementation details adds some form of value. This is rare.

- I still think that multiple test suites at different levels of abstraction, each isolated appropriately, offers better protection against errors than a suite of tests that runs against "real" instances of everything in the application, every time, at every level of detail. Trying to build a single test suite that covers everything at every level of detail every time it runs may not be advisable. It's like a Swiss Army knife - it _looks_ cool, but it's weak and fragile, and not particularly good at any of its many tasks.

![Swiss Army knife beyond the point of diminishing returns](images/swiss-army-knife.jpg)

