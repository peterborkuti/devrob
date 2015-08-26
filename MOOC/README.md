### Table of Contents

  * [Original MOOC source](#original-mooc-source)
  * [Summary of the lessons](#summary-of-the-lessons)
    * [the embodied paradigm](#the-embodied-paradigm)
      * [glossary](#glossary)
      * [theory](#theory)
      * [programming](#programming)
      * [pseudocode](#pseudocode)
        * [definitions](#definitions)
        * [code](#code)
        * [outcome](#outcome)
    * [sensorimotor interactions](#sensorimotor-interactions)
      * [description](#description)
      * [glossary](#glossary-1)
      * [theory](#theory-1)
        * [summary](#summary)
      * [pseudocode](#pseudocode-1)
        * [definitions](#definitions-1)
        * [code](#code-1)


# Original MOOC source

In this directory is the source of programs created by the tutors of the IDEAL MOOC.

Here is the link for the original source:

https://developmental-learning-tutorial.googlecode.com/svn/trunk

# Summary of the lessons

(Merely a copy paste or a copy paste with some small modification of excerpts from the original text)

## the embodied paradigm

http://liris.cnrs.fr/ideal/mooc/lesson.php?n=011

### glossary

* _agent_ is an autonomous entity which observes through sensors and acts upon an environment using actuators 
(https://en.wikipedia.org/wiki/Intelligent_agent)

* _embodied_ means that the *agent* must be a *part of reality*

* _agent's input_ is the robot's sensor data

* _experiment_ is an action taken by the agent

* _result_ of an experiment is the sensor's data after the experiment

### theory

* The agent is not a passive observer of reality

* The agent constructs a *perception of reality* through *active interaction*.

* Traditional model considers agent's input as a representation of the World (the state of the environment)

* Embodied model considers agent's input as a result of an experiment initiated by the agent

* _result_ in embodied model is a function of an experiment _and_ the state of the reality, r=f(e,s)

* consequence: results of the same experiment can be different because the state of the environment can be different

### programming

* an environment has no state, if experiment's results are not dependent from the state, only from the experiment
(It is possible only simulations, like in the first programming example)

* agent tries to anticipate a result of an experiment

* agent's mood is dependent of the goodness of the anticipation (SATISFIED,FRUSTRATED)

* agent must be motivated to take new experiences, even if it is SATISFIED (BORED after _n_ consecutive good anticipations)

* agent's behaviour implemented by _n_. If _n_ is big, agent is not motivated to explore the World, it is not curious.

* agent has a history of experiences and results (tuplets in memory)

* anticipation is based on this history

### pseudocode

#### definitions

* experiments: e1 and e2
* results: r1 and r2
* world (stateless) :
  * e1 => r1
  * e2 => r2
* agent states
  * SATISFIED if anticipation is good
  * FRUSTRATED if anticipation is bad
  * BORED if the last 3 anticipation were good

#### code

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

#### outcome

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

## sensorimotor interactions

### description

The _sensorimotor paradigm_ suggests that input data 
should be taken in association with output data, 
by combining both of them into a single entity called a 
*sensorimotor interaction*.
i = ⟨e,r⟩: an interaction i is a tuple ⟨experiment, result⟩.

The _sensorimotor paradigm_ allows implementing a
type of motivation called *interactional motivation*

### glossary

* _to enact an interaction_ : to perform an experiment and receiving the result 
that compose a given interaction

* _to intend to enact_ interaction _⟨e,r⟩_ means that 
the agent *performs experiment* _e_ while *expecting result* _r_

* _actually enact_ interaction ⟨e,r'⟩ means agent receives result _r'_ instead of _r_

### theory

* In _reinforcement learning_ agent receives a reward 
if it reaches a desirable goal ("reward button" is pressed).

* In _reinforcement learning_ agent _appears_ to be motivated to
reach the goald defined.

* In _interactional motivation_ agent *has no predefined goal*

* In _interactional motivation_ agent *has predefined *interactions* 

* Every interaction has a _scalar valence_

* Agent has a policy to try interactions with a *positive valence*

#### summary

the _sensorimotor paradigm_ allows designing *self-motivated*
agents _without modeling the world as a predefined set of states_.
Instead, the agent is left alone to _construct its own model of the world through_
its individual _experience of interaction_

### pseudocode

#### definitions

* experiments: e1 and e2
* results: r1 and r2
* valences:
  * -1 not desirable interaction
  *  1 desirable interaction
* world (stateless) :
  * e1 => r1
  * e2 => r2
* agent states
  * PLEASED if interaction's valence is positive or 0
  * PAINED  if interaction's valence is negative

#### code

Setting the valences for interactions (e1,r1) and (e2,r2).
These valences are stored in memory
```
01   createPrimitiveInteraction(e1, r1, -1)
02   createPrimitiveInteraction(e2, r2, 1)
03   experiment = e1
04   While()
05      if (mood = PAINED)
06         experiment = getOtherExperiment(experiment)
```
Implementation of a stateless World.
The outcome of an experiment is based on only
the experiment
```
07      if (experiment = e1)
08         result = r1
09      else
10         result = r2
```
Retrieving the interaction to check it's valence
```
11      enactedInteraction = getInteraction(experiment, result)
```
Setting agent's mood based on interaction's valence
```
12      if (enactedInteraction.valence ≥ 0)
13         mood = PLEASED
14      else
15         mood = PAINED
```
print some stuff
```
16      print experiment, result, mood
```

#### outcome

```
0: e1r1 PAINED
1: e2r2 PLEASED
2: e2r2 PLEASED
3: e2r2 PLEASED
4: e2r2 PLEASED
5: e2r2 PLEASED
6: e2r2 PLEASED
```

## Constructivist epistemology

### glossary

(the first three are from http://www.businessdictionary.com/)
* _cognition_ is a mental processes involved in judging, knowing, *learning*, perceiving, recognizing, remembering, thinking, and understanding that lead to the awareness of the world around us.

* _constructivism_ is a concept that learning (cognition) is the result of *mental construction* students constructs their own understanding by reflecting on their personal experiences, and by relating the new knowledge with what they already know.

* _epistemology_ is the study of the grounds, nature, and origins of knowledge and the limits of human understanding

* _Regularities of interaction_ (in short, _regularities_) are patterns of interaction that occur consistently

### theory

Cognitive systems can *never know* _the world as such_, but only the world as it
_appears to them through sensorimotor interactions_.

*Knowledge* is constructed from _regularities of interactions_ rather than recorded from input data.

Designing a system that would construct complete knowledge of the world out there and exploit this model 
for the better is a part of AI's long-term objective.

### goal

implementing an agent that can detect simple sequential 
regularities and exploit them to
satisfy its rudimentary motivational system

### pseudocode

#### glossary

* _primitive interaction_ : experiment, result touple, i=(e,r)

* _composite interaction_ : interaction sequence (i<sub>t1</sub>, i<sub>t2</sub>)

* _pre-interaction_ : i<sub>t1</sub>

* _post-interaction_ : i<sub>t2</sub>

* _activated interaction_ : if  has been learned before
and i<sub>t1</sub> happens, all the (i<sub>t1</sub>, *) interaction-pairs 
will retrieve from memory. The post-interactions of the pairs are the _activated interactions_.

* _proclivity_ : a tendency to choose or do something regularly

#### theory

The agent must store the learned composite interactions
The agent must retrieve the activated interactions
The agent must select one from the activated interactions
For motivating the agent, scalar valences are used for primitive interactions
or/and for composite interactions

#### code

Store valences for primitive interactions

```
01  createPrimitiveInteraction(e1, r1, -1)
02  createPrimitiveInteraction(e1, r2, 1)
03  createPrimitiveInteraction(e2, r1, -1)
04  createPrimitiveInteraction(e2, r2, 1)
05  while()
```
Remember for the previous interaction
```
06     contextInteraction = enactedInteraction
```
Activate the post-interactions based on the enacted interaction
and create a list from them. These will be the _proposed_ interactions.
```
07     anticipations = anticipate(enactedInteraction)
```
Choose one from the activated interactions and get it's experiment.
This is the tricky part. The better/cleverer your selection mechanism,
The faster/smarter your agent is. 

You can choose simply the first
interaction with positive valence, but in this case, agent will not be
curious at all, and will not discover the world.

You can implement a complex selection
algorithm based on the possibility of the outcome of an experiment and
its result's valence (example code uses proclivity, 
which was multiplying valence with probability) like in the lessons's code.

My idea was to list all the possible interactions and if agent pains
or satisfied too much, select the next experiment which was not tried yet.

```
08     experiment = selectExperiment(anticipations)
```
Implementation of a two-state World. When the experiment is
the same as the previous, result is _r1_, otherwise _r2_.
```
09     if (experiment = previousExperiment)
10        result = r1
11     else
12        result = r2
13     previousExperiment = experiment
```
Get the valence of the experiment and set mood
```
14     enactedInteraction = getInteraction(experiment, result)
15     if (enactedInteraction.valence ≥ 0)
16        mood = PLEASED
17     else
18        mood = PAINED
```
Learn the composite interaction
```
19     learnCompositeInteraction(contextInteraction, enactedInteraction)
```

#### outcome

I wrote a simple selection mechanism.
It is chooses a random experience when
there is no activated interaction with positive valence.

In {} you can see the agent's memory:
pre-action:[ list of post-actions ]

##### outcome 1

The otcomes is the agent's state in the last line of the step.

Agent first tries e1. It's outcome is negative 
(because the World had no previous experience and my implementation gives 
r2 in this case) and memory was empty.
Because r2 valence's negative, it is pained.
```
0:[e1-r2,PAINED,{}]
```


Because in step 0 agent pained and memory was empty (the were no complex interaction stored)
It chose randomly e1 again.
Fortunately its result was r1 which is positive, moreover, agent has a complex interaction which
must be stored: (e1-r2, e1-r1).
```
1:[e1-r1,PLEASED,{ e1-r2:[e1-r1]}]
```


Agent had a history, but not with post-interaction e1-r1, so it randomly chose e2.
result was r2, which is negative, so agent feels PAIN, but its memory filled
with a new complex interaction: (e1-r1,e2-r2).
```
2:[e2-r2,PAINED,{ e1-r2:[e1-r1] e1-r1:[e2-r2]}]
```


Last interaction was e2-r2. It was stored in memory as a complex interaction's post-action 
Because it has negative result and it is not stored as a pre-action, agent
picks another experience than e2, which is e1.
The correspondent interaction would be e1-r2.
```
3:[e1-r2,PAINED,{ e2-r2:[e1-r2] e1-r2:[e1-r1] e1-r1:[e2-r2]}]
```

Last interaction was (e1-r2) and memory has a trace for this: (e1-r2,e1-r1) which has a positive
results in post-action, so agent happily chose e1-r1, and tried e1 experience, and it got r1,
so agent is happy!
```
4:[e1-r1,PLEASED,{ e2-r2:[e1-r2] e1-r2:[e1-r1] e1-r1:[e2-r2]}]
```

Last interaction was (e1,r1) and memory has (e1-r1) pre action with two post-actions:
e2-r2 and e1-r1. Agent chose e1, because it has a positive outcome (r1).
Tried e1 and got r1, so agent is very pleased now.
```
5:[e1-r1,PLEASED,{ e2-r2:[e1-r2] e1-r2:[e1-r1] e1-r1:[e2-r2,e1-r1]}]
```

Unfortunately, agent has no curiosity, so it will not explore the world, just chooses
e1 from now on:
```
6:[e1-r1,PLEASED,{ e2-r2:[e1-r2] e1-r2:[e1-r1] e1-r1:[e2-r2,e1-r1]}]
7:[e1-r1,PLEASED,{ e2-r2:[e1-r2] e1-r2:[e1-r1] e1-r1:[e2-r2,e1-r1]}]
```

#### outcome 2
Same algorith with the first action e2

```
0:[e2-r2,PAINED,{}]
1:[e2-r1,PLEASED,{ e2-r2:[e2-r1]}]
2:[e2-r1,PLEASED,{ e2-r1:[e2-r1] e2-r2:[e2-r1]}]
3:[e2-r1,PLEASED,{ e2-r1:[e2-r1] e2-r2:[e2-r1]}]
4:[e2-r1,PLEASED,{ e2-r1:[e2-r1] e2-r2:[e2-r1]}]
5:[e2-r1,PLEASED,{ e2-r1:[e2-r1] e2-r2:[e2-r1]}]
```

#### outcome 3
Algorithm uses BORED sate. If Agent PLEASED three times in consecutive, it chooses a random experience.
It means, that agent has *curiosity*, so it will build a decent model about the world, see
its memory trace.

```
0:[e2-r2,PAINED,{}]
1:[e1-r2,PAINED,{ e2-r2:[e1-r2]}]
2:[e1-r1,PLEASED,{ e1-r2:[e1-r1] e2-r2:[e1-r2]}]
3:[e1-r1,PLEASED,{ e1-r2:[e1-r1] e2-r2:[e1-r2] e1-r1:[e1-r1]}]
4:[e1-r1,PLEASED,{ e1-r2:[e1-r1] e2-r2:[e1-r2] e1-r1:[e1-r1]}]
5:[e2-r2,PAINED,{ e1-r2:[e1-r1] e2-r2:[e1-r2] e1-r1:[e1-r1,e2-r2]}]
6:[e1-r2,PAINED,{ e1-r2:[e1-r1] e2-r2:[e1-r2] e1-r1:[e1-r1,e2-r2]}]
7:[e1-r1,PLEASED,{ e1-r2:[e1-r1] e2-r2:[e1-r2] e1-r1:[e1-r1,e2-r2]}]
8:[e1-r1,PLEASED,{ e1-r2:[e1-r1] e2-r2:[e1-r2] e1-r1:[e1-r1,e2-r2]}]
9:[e1-r1,PLEASED,{ e1-r2:[e1-r1] e2-r2:[e1-r2] e1-r1:[e1-r1,e2-r2]}]
10:[e2-r2,PAINED,{ e1-r2:[e1-r1] e2-r2:[e1-r2] e1-r1:[e1-r1,e2-r2]}]
11:[e2-r1,PLEASED,{ e1-r2:[e1-r1] e2-r2:[e1-r2,e2-r1] e1-r1:[e1-r1,e2-r2]}]
12:[e2-r1,PLEASED,{ e2-r1:[e2-r1] e1-r2:[e1-r1] e2-r2:[e1-r2,e2-r1] e1-r1:[e1-r1,e2-r2]}]
13:[e2-r1,PLEASED,{ e2-r1:[e2-r1] e1-r2:[e1-r1] e2-r2:[e1-r2,e2-r1] e1-r1:[e1-r1,e2-r2]}]
14:[e2-r1,PLEASED,{ e2-r1:[e2-r1] e1-r2:[e1-r1] e2-r2:[e1-r2,e2-r1] e1-r1:[e1-r1,e2-r2]}]
15:[e1-r2,PAINED,{ e2-r1:[e2-r1,e1-r2] e1-r2:[e1-r1] e2-r2:[e1-r2,e2-r1] e1-r1:[e1-r1,e2-r2]}]
16:[e1-r1,PLEASED,{ e2-r1:[e2-r1,e1-r2] e1-r2:[e1-r1] e2-r2:[e1-r2,e2-r1] e1-r1:[e1-r1,e2-r2]}]
```
