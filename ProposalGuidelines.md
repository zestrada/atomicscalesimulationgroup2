# Introduction #
Course Projects Overview for Atomic-Scale Simulation

In next few weeks, we will form teams. The members will be chosen to balance the teams in terms of interests, programming ability and experience. You will be given a collective grade for the team project based equally on three separate components:

> Scientific Research. Each project should be research oriented, something concerning new developments in classical or quantum simulations and with a scientific component.
> Algorithm development. This could involve an optimization of an existing code or algorithm, a new implementation, some interesting science or the use of new computer architectures.
> Presentation. Instead of a report, we expect you to develop web pages to explain your project. This should not be exclusively a PDF report, but consist of linked html pages / wiki pages with graphics, including literature and web references. With your permission, we may use these pages in futures years as examples of class projects. You will also give a 15 minute oral presentation of your project at the end of the semester during the time allotted for the final exam.

Each team needs to turn in a one page "proposal" and give a 5 minute presentation to the class. See the calendar for the date for these presentations.

If you have any questions about the suitability of your project please get in touch with the instructor.

Below is a list of ideas reflecting some of our interests.
Some of them are now out-of-date. These lists are not intended to be exclusive. Your ideas may be better, more interesting to you, etc., and may be published if you did some extra work.

Please come and see the instructor as soon as you encounter a major problem with completing the project.
Potential project ideas

Here are some project ideas; see Previous projects to get other ideas of what's been completed.
Exploring phase space

There are many different approaches to finding transitions and possible ground states produced by a potential:

> Dimer method to basin hop, and compute barriers for hopping
> Nudged-elastic band or string method to find energy barriers
> Random structure search methods to find ground-state crystal structures

Molecular Dynamics

> Implement an accelerated dynamics method, like hyperMD in your MD code to simulate a rare-event, like diffusion
> Write NAMD lite: You've already written a very simple molecular dynamics code. Now modify (this is a lot of work..really rewrite) your code to be able to simulate biomolecules successfully like NAMD. The key pieces to this are:
> > Implement a better thermostat like Nose-Hoover chains so that your dynamics is reasonable
> > Figure out how to read in the appropriate forcefield (there is a professional code called MMTK that is written in python that may already do this for you...you may able to leverage this.
> > Because bonds involve constraints, you will need to figure out how to keep constraints functional in your code. Do this using the SHAKE method.
> > Simulate a small biomolecule!

Sophisticated monte carlo moves

The core of monte carlo is the ability to come up with different moves that more effectively move you through phase space. The top four suggestions here are (what I consider and am aware of) some of the most exciitng new developments in the field of simulating classical atomic systems that have been in the research literature recently. A good project would to implement and become a master of (some of) these methods. Alternatively, come up with your own idea.


> Understand and implement Eric Luijten's monte carlo cluster move. This has allowed people to simulate systems with widely varying sizes of particles, which in the past had been particularly difficult.
> Understand and implement the paper "Multiscale Monte Carlo for simple fluids" (A.C. Maggs) in your monte carlo code. This algorithm allows you to speed up calculations of liquids by doing "global moves"
> Implement optimized ensemble methods: Ensemble Optimization Techniques for the Simulation of Slowly Equilibrating Systems
> presampling
> Other sophisticated move/important sampling ideas:
> > Do the ising model using four different methods: normal technique, cluster moves, kinetic monte carlo, and worm algorithm. Compare these methods.
> > Implement Parallel tempering

Parallelization

For a long time, monte carlo was able to get faster simply because processors got faster. Now the increase in processing power is largely coming from having multiple processors. You could look into and implement methods that involve taking advantage of massive number of processors including:


> breaking up your system over space
> doing branch prediction with monte carlo
> coming up with heurestic methods to quickly equilibrate systems so you can avoid the equilibration time loss through parallelizing.
> coming up with monte carlo moves that parallelize over many processors
> your own insightful idea on how to parallelize things

Speeding up monte carlo

One (probably the best) way of speeding up monte carlo is coming up with sophisticated moves that move you through phase space quickly. Of course, it's not the only way. Other things might include:

> do importance sampling iteratively by approximating the answer and the iterating.
> implement order(N) methods.
> understand and implement methods of pre-rejection

polymers, polymers, polymers

To tackle polymers effectively requires an entirely different slew of monte carlo techniques than those that are well suited for classical atomic point gases. Understand these techniques and implement a monte carlo code that does a really good job of simulating polymer systems. Specifically look at things like:

> levi flights
> reptation
> rosenbluth sampling, etc.

Monte Carlo

> One of the most basic problems with atomic simulations is that the force fields (specifically empirical ones) are not very good. (For those of you who went to D. E. Shaw's latest talk on MD, this is what he said kept him up at night). One way to resolve this situation is for each monte carlo step (or md step) one can do a more accurate force calculation using electronic structure methods (like quantum monte carlo). One could do a project working to resolve this by either:
> > Getting the electronic forces from DFT. This mainly involves coupling together a monte carlo code with a DFT code.
> > Getting forces from Variational Monte Carlo. In this case, there will be some statistical errors on your result and consequently you have to be able to do monte carlo in the face of having such statistical errors. This would involve
> > > coupling to a monte carlo code
> > > understanding the work on CEIMC (Ceperley, et al) and Kuto on how to do markov chains with errors. Modify a monte carlo code to deal with statistical errors. (Maybe compare the two ways of dealing with errors)


> Free energy calculations are hard. There are a variety of different methods for dealing with them. Explore these methods and write some code that calculates the free energy of things.

Structural optimization

People spend a lot of time optimizing structures. Figure out how to do this well. Possible options include:

> do it using molecular dynamics and calculate forces
> use some sort of optimization technique like:
> > simulated annealing
> > genetic algorithms
> > database mining

> Come up with your own good idea on how to optimize structures.

Quantum monte carlo

> People use simulated annealing all the time to do optimization. There has been some recent work on "quantum simulated annealing" which uses the idea of a quantum system to get annealing to work. (See http://arxiv.org/abs/0712.1008) Implement this method and test it out. See if it works better then classical simulated annealing for some optimization problem.
> Using variational monte carlo, calculate the energy difference between two structures using correlated sampling.
> Modify an open source community code (like PIMC++ or qmcpack) to do something new and submit your modification to be included in the official release version.
> More ideas coming soon..I do quantum monte carlo so have lots of random ideas in this regard...if you are interested in doing a project on qmc, you might consider coming and chatting.

Other

> Do some other forms of optimization

Older project suggestions (Ceperley and Johnson)

There may have some codes from which you could use to start. A good project would be a novel application with one of these codes.

> ground state properties of molecules (MOLE)
> ground state properties of solids (QUCU)
> path integral calculations for bosons (UPI)
> classical MC and MD for 2 or 3 dimensional liquids, plasmas and polymers (CLAMPS)
> various other software on NCSA computers (we can help) find some.
> However, you will be responsible for figuring out how these codes work.

Grabbag (some overlap with below list)

> Implement an order(N) method (it goes like N asymptotically) for computing the long range Coulomb interaction and compare its timing and accuracy to the usual Ewald method. (find when it is worth using) Alternatively, do a literature search and find out what is being used in practice and compare the various order (N) methods. Is there any hope for Monte Carlo Order (N) methods where particles are moved one at a time instead of all together?

> Simulate a system of quadrapoles on an fcc lattice. This is a simple model for the rotation of solid hydrogen. One can do this either classically or quantum mechanically. Additionally one may want to consider para-ortho alloy. There should be many interesting phase transitions.

> Test the ideas of quasi-random numbers on a hard problem, i.e. an integral that is not feasible with a grid-based method) and compare to Monte Carlo efficiencies. (see Computers in Physics Nov. 1989 )

> In variational Monte Carlo, try doing a random walk in parameter space to find the minimum energy or variance. Try an determine good rules for moving the parameters. Alternatively, is the energy minimization or the variance minimization sharper? Which has the smallest errors for the optimal parameters?

> Visualize the liquid-solid interface of a simple system and learn something about the dynamics of melting and freezing.

> Try and directly calculate the difference in energy of a Li and Li+ atom using correlated sampling (or some related method) applied to variational wavefunctions and compare the efficiency to two separate calculations.

> Write a Path Integral code to simulate an isolated He or H2 molecule. Verify that the code gives the correct result in the high temperature and low temperature limit. Find out whether molecular dynamics or monte carlo is more efficient OR Study various approximations for the high temperature density matrix.

> Do a literature study of the special techniques that are used to simulate water with an aim to answering some of the following questions: Are quantum nuclear effects important? Is it important that the water molecule have internal motion? How well is the inter-water potential known? What experimental properties can and cannot be calculated from first principles? Is MC or MD better for calculating static properties of water? What special MC transition rules have been invented? Do you have any special finite-size effects in water? How big are they? I don't expect either a review article or for you to rewrite a review article that you find but a synthesis of the state of knowledge of how to simulate water.

> Model the growth of bacterial colonies using methods invloving random walks. Consider the diffusion of nutrients, movement of the bacteria, reproduction, and local communication. A good place to start is the article by Ben-Jacob et al., Nature 368, 46 (1994).

> Try and develop a multiparticle moving scheme for a simple monte carlo application that improves the rate of convergence over that of single particle moves. You could try a simple liquid or Ising model application, where it has been traditional to move the particles sequentially. Test whether moving the particles sequentially or at random works better.

> Try out the histogram method for computing detailed properties of a phase transition. (Ferrenberg and Landau, PRB 44, 5081, 1991) Compare to the method of Lee and Kosterlitz, Phys. Rev. B43, 3265 (1991).

> Further develop your MD code to treat an Argon surface.

> Implement one of the methods discussed in class to perform a temperature controled simulation. (Find something interesting to study using the temperature control.)

> Study strains on grain boundaries in Si using an empirical potential. You could also look at dislocations and vacancies.

Dislocations:

> Using a empirical potential, such as the Double-Yukawa, write a, or modify an existing, MD code to study dislocation motion in a 2-D, finite simulation cell. (2-D is recommended for simplicity as well as the ability to more easily visualize.) How do you impose shear strain into boundary conditions? What additional requirements are needed for boundary conditions? For example, one dislocation in cell produces very large strain fields which cannot be handles well in finite cell. How can you arrange another dislocation so that strain fields are short-ranged and finite cell works reasonable well?

Kinetic Monte Carlo: (Diffusion, Concentration Gradients, Phase Transitions, Surface Chemistry...)

> Simulate via KMC a binary alloy system to determine the changes in the local environmental structure due to vacancy migration. Because the global alloy composition cannot change the simulation will be in canonical ensemble. There should be many interesting phase transitions depending on vacancy-element interactions and their sizes, which are related to the concentration fluctuations in non-equilibrium. Kinetics will depend on the effective chemical potentials, but the equilibrium thermodynamics will depend only on the effective pair-interactions, as discussed in class. (See, e.g., Athenes et al., Acta Mater. 44, 4739 (1996)). This could also be performed to study kinetics and equilibrium structure of concentration gradients in multi-layer alloy.

> Implement a KMC to determine to 2-D surface diffusion coefficient, which may be obtained via MC and Boltzmann-Matano analysis. It may be implemented as a study of time correlation of the concentration fluctuations. (See, e.g., independently invented by Reed and Ehrlich at UIUC, Surface Science 105, 603 (1981), Bortz et al., J. Comput. Phys. 17, 10 (1975), and, intro paper by Clark, Raff and Scott, Computers in Physics 10, 584 (1996).)

> Using KMC, study the evolution a concentration gradient around an anti-phase boundary defect in an binary alloy, which may be mapped to an effective 2-D problem if entire planes parallel to defect are considered identically occupied.

> Surface Chemistry: Produce a hybrid MC code and simulation consisting of kinetic plus equilibrium moves which applies to systems with N different processes whose rates vary by orders of magnitude, like chemical vapor deposition. See intro paper by Clark, Raff and Scott, Computers in Physics 10, 584 (1996).

Simulated Annealing: (Modeling the Stock Market or Finding Global Minima)

> Find a Needle in a Haystack: Choose a function which contains a large number of multi-minima, perhaps with even large deviations of minina depths, but with only one global minima. Use the technique of simulated annealing to produce an efficient code for finding the "ground state", i.e. global minina, and compare to more standard Conjugant Gradient or Newton-Rapheson Techniques. See Numerical Recipes for short explanation of these latter techiques, if you are unfamiliar with them. See also, Andricioaei and Straub, Computers in Physics 10, 449 (1996) for intro article on CONFORMATIONAL OPTIMIZATION, which is the fancy name for this approach, where they give some suggestions for study, including a function of the type used in simulations and conformation of small L-J clusters.
> > Note that multi-state spin models (Potts, etc.) always produce such multi-minima states. Such models are used on the New York Stock Exchange to model derivate buying.
> > The Travelling Saleman Problem is in fact a problem of this type (but you cannot choose this one). (E.g, Yoshiyuki et al., Computers in Physics 10, 525 (1996)

Random Number Generators:


> Test the ideas of quasi-random numbers for a integration of a function which is not feasible with a grid-based method and compare to Monte Carlo efficiencies. (see Computers in Physics Nov. 1989 )

> For various psuedo-random number generators, use both tests of their quality as far as correlation and randomness as well as their efficiencies within a particular simulation algorithm, such as 2-D Ising Model, to pick a _Best Choice_ PRNG for your application. (For Portable PRNG, see Marsaglia and Zaman, Computers in Physics 8, 117 (1994); also, P. Coddington, _Tests of random number generators using Ising model simulations_, Int. J. of Mod. Phys. C, 7(3):295-303, 1996.)

Non-Equilibrium Phenomena: (Order-disorder, Spinodal Decompositions, Liquid-solid Trans., etc., using Langevin Simulations

> Study phase separation in a binary alloy (Cahn-Hillard type equations) using Langevin Dynamics. (See Ken Elder, _Computers in Physics_ 7, 27 (1993) and note that CLAMPS can do some types of Langevin dynamics.)

> Active-walker models used to simulate growth phenomena in open systems in non-equilibrium conditions. Large-scale versions of this are the expansion or shrinkage of river basins (see Environmental Simulation Idea above), or smaller-scale versions are chemical and biological systems, like cell evolution, snowflake formation, and so on. Study a type active-walker model, but not just one out of sample papers, even your own. (See, e.g., Lam and Pochy, Computers in Physics 7, 534 (1993).) or about bacterial colonies by Ben-Jacob et al., Nature 368, 46 (1994).

> We're all getting older so, perform simulation of biological aging using MC algorithm. See, for example, D. Stauffer article, Computers in Physics 10, 341 (1996), where he has suggested several topics for futher study. (Some of these are nothing more than trivially altering code and running: Refrain from such inactivity. You certainly can come with more interesting extensions.)

Phase Transition:

> Simulate the phase coexistence for complex molecules by producing a configurational-biased, grand-canonical MC program for L-J chain polymers. CLAMPS may be modified to do this, or write your own specialized and short version. A good discussion and some code can be found by Frenkel and Smit, Computers in Physics 11, 247 (1997).

> Study the equilibrium thermodynamics (MC or MD) of simple binary gas based on empirical potentials (I would use Double-Yukawa type but it is not necessary) on the surface of a sphere so that there are no Periodic Poundary Conditions. What, if anything happens around the liquid-solid interface , for example?

> Implement so-called _Cluster Algorithm_ for MC simulation, which flips multiple sites rather than single sites sequentially. Compare the MC efficiency from standard and cluster algorithm. See, e.g., Selke, Talapov, Scheir, "Cluster-flipping MC algorithm and correlation in a "good" RNG," JETP Lett. 58, 665 (1993). (For simplisicity, it may be best to due this for 2-D Ising Model. If so, then also compare what happens just below critical point for conifgurations using both algorithms.)

> Simulation of Polymer Chains on surface of Sphere by altering relevant parts of CLAMPS to change boundary conditions. (Check with Prof. Cepereley whether this is big job.) This acts like confined volume with artificial boundary conditions like bags or boxes and limits the complexity. Density of polymers can be controlled via sphere radius, for example. Does anything change by maintaining density while varying radius?

> Implement the Mori-Zwanzig-Daubechies (Wavelet) decomposition of Ising Model Monte Carlo Dynamics. (See, e.g., Phillies and Stott, Computers in Physics 9, 225 (1995).) But first answer, why would you want to do this?

> Implement MC of hard-sphere gas on a 2-D Sphere and study the entropy-driven phase transition. Determine the distribution of the velocities. Determine how PV=nRT is recovered, or not, from simulation. (see general discussion of problems in article by Gould, Tobochnik adn Colonna-Romana, Computers in Physics 11, 157 (1997)

Add Labels

> Terms of Use -- Privacy Policy
> © 2011 The Board of Trustees at the University of Illinois, College of Engineering
