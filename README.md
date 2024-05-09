# What is this about?

These are notes about a hands-on exploration of James Shore's [pattern language for testing](https://www.jamesshore.com/v2/projects/nullables/testing-without-mocks). 

In the first edition of _The Mythical Man-Month_, Fred Brooks advised us to "plan to throw one away. 
You will anyway.” In the second edition, he revised that advice. Instead, he suggests we continually iterate. (I suppose you could say he threw the first one away.)

In that spirit, this is the second [iteration of my attempt to understand](iteration-1.md) the pattern language. 
James offered considerable feedback on the first iteration of this exploration, and I feel as if I now 
have a better understanding of the model. The first iteration was clumsy for all the wrong reasons. 
Hopefully, this attempt will be clumsy for better reasons.

## Explorations 

- [Java](i2-java-walkthrough.md)
- Ruby - TBD
- F# - TBD
- others - TBD

## Initial assumptions 

James has developed a comprehensive model for test-driving and supporting software products. 
My interest is mainly in the differences between using isolated test cases with external dependencies 
faked out using mocks and stubs, and using Sociable Tests with external dependencies faked out using 
Nullables and Embedded Stubs.

I began this exploration as a way to learn James' model and to decide whether I want to add it to my 
personal toolkit for software development. I didn't start this as a True Believer who wanted to 
demonstrate it to others. I'm not trying to shoot it down, either; just trying to understand. 

You should know at the outset that I'm not "selling" this; I'm only exploring it. As I type these words, 
I don't know whether I will ultimately find this approach generally useful; or if I will find it useful 
for some languages and not others, due to characteristics of each language. I'm happy to share all my 
mistakes along the way, in case someone can derive value from that.

### Assumption 1 - isolated tests can fail to catch defects

James has written [a separate article](https://www.jamesshore.com/v2/projects/nullables/how-are-nullables-different-from-mocks) dedicated to the question of how Nullables differ from mocks. 
One assertion in the article is that isolated mock-based tests can't catch certain types of bugs, while 
Sociable Tests based on Nullables can. There's an unstated underlying assumption (both in this article 
and throughout the material about the pattern language) that teams who use isolated, mock-based tests 
don't write test suites at different scopes, and don't use any other methods of testing or test-driving 
their code. That isn't stated outright; it's implicit. It seems to me it's the only way this assertion 
could be true.

To be fair, I know there are teams that fall into that habit; but if we assume a team applies good 
practices for organizing test suites and writing test cases, I don't agree that this is a real 
"difference" between the two approaches. The "edges" of the application are, in fact, tested thoroughly. 
They just aren't tested using the identical test cases that check application behavior. I think that sort 
of test isolation is a _good_ thing, and not a problem to be solved.

---
Feedback from James on this point was along the lines of, "You do this, therefore it's not an issue _for you_."
Well, it isn't about me. Teams ought to learn how to design multi-level test suites in which the
individual test cases are well isolated. Like James, I've observed many teams that don't seem to know 
exactly what to do with unit tests and other executable tests. If people maintain the same mindset, 
switching to a different model probably won't help. If they improve their skills, then they can use any 
model or approach successfully. So we'll agree to disagree on this.
---

### Assumption 2 - refactoring breaks test cases

Another assertion is that Nullables don't break when you refactor. This question has been the subject of 
a lot of circular discussion. It seems to me if we write State-Based Tests, we won't break any tests by 
refactoring the production code. In fact, we _want_ to be able to run exactly the same test cases before 
and after the refactoring, without any changes to the test cases, as a way to detect inadvertent changes 
in application behavior. 

However, in cases when it makes sense to write an Interaction Test (and I think those cases are rare), 
if our team uses TDD then they will modify the affected test cases _before_ they refactor the production 
code. They will adjust the configuration of their Test Doubles to match the implementation they plan to arrive at through refactoring. 

This is nothing more complicated than the "red" step in the red-green-refactor cycle. The only way the 
refactoring could "break" test cases is if the team modified the production code before they modified the 
affected test cases. In that case, by definition they are not test-driving the refactoring, even if they 
insist that they "always" use TDD. That's a matter of skills or mindset or habit, not of tools or methods 
or architectural models.

---
Feedback from James was, again, "You do this, so it isn't an issue _for you_." Well, I didn't invent TDD. 
I often hear people claim that they use TDD, and when I sit down with them and see what they actually do, 
it isn't TDD. I agree with him that this is a common problem in the field, but I think it's a problem of  
skills or habits rather than a tools problem. If people bring old habits to the new model, they'll 
continue to experience the same outcomes.

It isn't personal. Either you change the test case first or you change the production code first. 

---

### Assumption 3 - it's okay to deploy a small amount of test code to production

Nearly all suggestions that people make for improving our software development and delivery practices 
seem to be aimed at increasing team productivity and/or simplifying the developer experience. James' 
approach seems to be aligned with this general priority. I could be wrong about that; we haven't 
discussed this aspect of it.

In my opinion, the primary concern for everyone involved with a software product ought to be the health 
of the production environment. There is no product until the code is live. Until that point, it's a work 
in progress. Making that progress smooth, low-stress, and relatively error-free is a great goal; but 
production is king. If protecting production means we have to invest a little more effort during 
development, then so be it.

Large companies are bombarded with exploits; typically tens of thousands per year or more. 
Financial institutions, the energy sector, and government agencies are prime targets for the most 
serious of all hackers - government-backed professionals working full-time to undermine the national 
economies of target countries. Ransomware attacks are kids' stuff by comparison. Cloud-based front-end 
applications are the doors and windows into those organizations, and (like the doors and windows on houses) they're often the weakest points of entry.

An Embedded Stub whose only purpose is to fake out an external dependency during testing is deployed with 
the production code, where it has no purpose. The unused code is present in the production environment, 
enjoying the same access privileges as the "real" application code. It's never called in the normal 
course of operations. It's a quiet little corner in memory where malware can do its work undisturbed. 
Granted, it isn't much...but hackers don't need much. Just a crack.

---
Feedback from James on this was blunt. "...I think your point 3 is an impossibly high bar to meet" and 
"I think you're overstating the security risk, and I challenge you to find a scenario where nullables 
pose a security risk that doesn't involve the attacker already having the ability to run arbitrary code." 

This is fair criticism. Let's put some context around this concern. 

The prime targets for professional hackers are the financial industry, the 
energy sector, and public services. If your application doesn't 
deal with personal financial information and can't affect physical infrastructure, 
then this is not a consideration. 

I'm not qualified to break into systems, so I can't meet the 
challenge directly. The good news is I'm not the sort of person you need to worry about. Professional 
hackers are working on it full-time, every day. 

I can think of a couple of deployment environments that mitigate the risk of having unused code present 
in production. Maybe you can think of more.

One is a cloud infrastructure in which immutable servers are used, whether applications are 
containerized or not, and the phoenix strategy is applied to cycle server instances regularly. 
These practices minimize the time available for hackers to figure out how to break in beyond their 
initial point of entry. The phoenix strategy ensures any malware installed in an instance will go 
away when that instance is destroyed and re-created from a clean base; on average, slightly less than 
half the time interval for cycling instances.

The other environment is the IBM System Z, commonly called "mainframe." This is a potential deployment 
environment for two of the languages included in this exploration - Java and COBOL. The Z runs traditional mainframe 
operating systems - z/OS, z/VSE, z/TPF, and the type 1 hypervisor z/VM, as well as Linux, including 
dedicated KVM LPARs. The system can be used as a cloud infrastructure, as well as for blending legacy 
assets with current technologies. 

These mainframe environments benefit from the hardware- and OS-based security features 
of the System Z. Some of the peformance optimizations specific to Java on this platform will cause 
methods that are never called to be removed from memory; so the Embedded Stubs won't stay in the 
runtime environment. I understand this is not the typical deployment environment for Java, but it is 
a supported environment. 

Note that any old "cloud" won't do, and a mainframe system that's still managed as if we were living 
in the year 1980 won't do, either. Operational standards and security features are constantly evolving. 
We need to keep up.
---



