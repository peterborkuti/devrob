= Original MOOC source

In this directory is the source of programs created by the tutors of the IDEAL MOOC.

Here is the link for the original source:

https://developmental-learning-tutorial.googlecode.com/svn/trunk

= Summary of the lessons

(Merely a copy paste or a copy paste with some small modification of excerpts from the original text)

== the embodied paradigm

http://liris.cnrs.fr/ideal/mooc/lesson.php?n=011

=== glossary

* _agent_ is an autonomous entity which observes through sensors and acts upon an environment using actuators 
(https://en.wikipedia.org/wiki/Intelligent_agent)

* _embodied_ means that the *agent* must be a *part of reality*

* _agent's input_ is the robot's sensor data

* _experiment_ is an action taken by the agent

* _result_ of an experiment is the sensor's data after the experiment

=== theory

* The agent is not a passive observer of reality

* The agent constructs a *perception of reality* through *active interaction*.

* Traditional model considers agent's input as a representation of the World (the state of the environment)

* Embodied model considers agent's input as a result of an experiment initiated by the agent

* _result_ in embodied model is a function of an experiment _and_ the state of the reality, r=f(e,s)

* consequence: results of the same experiment can be different because the state of the environment can be different

=== programming

* an environment has no state, if experiment's results are not dependent from the state, only from the experiment
(It is possible only simulations, like in the first programming example)

* agent tries to anticipate a result of an experiment

* agent's mood is dependent of the goodness of the anticipation (SATISFIED,FRUSTRATED)

* agent must be motivated to take new experiences, even if it is SATISFIED (BORED after _n_ consecutive good anticipations)

* agent's behaviour implemented by _n_. If _n_ is big, agent is not motivated to explore the World, it is not curious.

* agent has a history of experiences and results (tuplets in memory)

* anticipation is based on this history

=== pseudocode

==== definitions

* experiments: e1 and e2
* results: r1 and r2
* world (stateless) :
  * e1 => r1
  * e2 => r2
* agent states
  * SATISFIED if anticipation is good
  * FRUSTRATED if anticipation is bad
  * BORED if the last 3 anticipation were good

==== code

```
01   experiment = e1
02   Loop(cycle++)
```
Implementation of motivation. If agent is bored, it chooses another experiment.
```
03      if (mood = BORED)
04         selfSatisfiedDuration = 0
05         experiment = pickOtherExperiment(experiment)
06      anticipatedResult = anticipate(experiment)
```
Here is the implementation of a stateless World. The outcome of an experiment is based on only
the experiment
```
07      if (experiment = e1)
08         result = r1
09      else
10         result = r2
```
Creating the history of experiment,result touples
```
11      recordTuple(experiment, result)
```
Setting agent's mood based on how good was the anticipation of the experiment
```
12      if (result = anticipatedResult)
13         mood = SELF-SATISFIED
14         selfSatisfiedDuration++
15      else
16         mood = FRUSTRATED
17         selfSatisfiedDuration = 0
```
Implementation of motivation. If agent is "too" good in anticipation, it gets bored
```
18      if (selfSatisfiedDuration > 3)
19         mood = BORED
```
print some stuff
```
20      print cycle, experiment, result, mood
```

==== outcome

```
0: e1r1 FRUSTRATED
1: e1r1 SELF-SATISFIED
2: e1r1 SELF-SATISFIED
3: e1r1 SELF-SATISFIED
4: e1r1 BORED
5: e2r2 FRUSTRATED
6: e2r2 SELF-SATISFIED
7: e2r2 SELF-SATISFIED
8: e2r2 SELF-SATISFIED
9: e2r2  BORED
10: e1r1 SELF-SATISFIED
```
