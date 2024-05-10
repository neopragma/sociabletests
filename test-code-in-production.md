[README.md](README.me)

# The question of deploying test code to production

One concern I've had from the beginning of this exploration is the fact the pattern language calls for
us to deploy code to production that isn't used in production. The red flag that pops up is the
possibility hackers could use the unreferenced code as a patch area to introduce malware. After some
discussion on this point, my observations are as follows.

The prime targets for professional hackers are the financial industry, the
energy sector, and public services. These sectors are routinely and continuously attacked by
highly skilled programmers working for hostile governments. Their aim is to undermine or, if possible,
crash the economies of target countries. Client companies where I've worked have said they experience
something north of 75,000 attempts per year. Most of them don't get very far, but some do.
We really can't afford to take anything for granted in this regard.

James has said he sees this as a very small risk, and he thinks I overstate the problem. I hope
he's right. In any case, if your application doesn't process personal or financial information and
can't affect physical infrastructure, then this may not be a factor for you. Otherwise, the execution
environment for your application might provide significant protection from hacking.

You should think about where to deploy the application, if it works with sensitive information or can
affect infrastructure. Here I mean _real_ infrastructure and not software boundaries; something like a
nuclear power plant, a water treatment facility, or air traffic control systems. Everything isn't just
a webapp.

I can think of a couple of execution environments that mitigate the risk of having unused code present
in production. Maybe you can think of more.

One is a cloud infrastructure in which immutable servers are used, whether applications are
containerized or not, and the phoenix strategy is applied to cycle server instances regularly.
These practices minimize the time available for hackers to figure out how to break in beyond their
initial point of entry. The phoenix strategy ensures any malware installed in an instance will go
away when that instance is destroyed and re-created from a clean base; on average, slightly less than
half the time interval for cycling instances.

The other environment is the IBM System Z, commonly called "mainframe." This is a potential deployment
environment for two of the languages included in this exploration - Java and COBOL. Systems based on
the platform are involved in around 80% of financial transactions globally. They are also used in
the central banks of many countries and to support public services. The security features of the
platform have evolved to meet the demands of these sectors.

The Z runs traditional mainframe
operating systems - z/OS, z/VSE, z/TPF, and the type 1 hypervisor z/VM, as well as Linux, including
dedicated KVM LPARs. The system can be used as a cloud infrastructure, as well as for blending legacy
assets with current technologies.

These mainframe environments benefit from the hardware- and OS-based security features
of the System Z. Some of the peformance optimizations specific to Java on this platform will cause
methods that are never called to be removed from memory; so the Embedded Stubs won't stay in the
runtime environment very long after the initial class load. I understand this is not the typical
deployment environment for Java, but it is a supported environment and is relevant in sectors that
are at particular risk from cyberwarfare.

Note that any old "cloud" won't do, and a mainframe system that's still managed as if we were living
in the year 1980 won't do, either. Operational standards and security features are constantly evolving.
We need to keep up.
