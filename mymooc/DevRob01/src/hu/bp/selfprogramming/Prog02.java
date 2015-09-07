package hu.bp.selfprogramming;

import static hu.bp.common.Mood.BORED;
import static hu.bp.common.Mood.PAINED;
import static hu.bp.common.Mood.PLEASED;
import hu.bp.common.AbstractProgram;
import hu.bp.common.Mood;
import hu.bp.common.ThreeStepWorld;
import hu.bp.common.World;
import hu.bp.selfprogramming.modules.Experience;
import hu.bp.selfprogramming.modules.Experiment;
import hu.bp.selfprogramming.modules.PrimitiveInteraction;
import hu.bp.selfprogramming.modules.PrimitiveInteractions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Prog02 extends AbstractProgram {
	Mood mood;
	World world;
	private static final int PAIN_LIMIT = 2;
	private static final int PLEASED_LIMIT = 4;
	private PrimitiveInteractions pis;
	private Experience experience;
	private int counter;
	private int counterPleased,counterPained;
	private List<PrimitiveInteraction> memory;

	@Override
	protected void init() {
		super.init();
		mood = BORED;
		world = new ThreeStepWorld();
		List<PrimitiveInteraction> valences;
		valences = new ArrayList<PrimitiveInteraction>();
		valences.add(new PrimitiveInteraction("e1","r1", -1));
		valences.add(new PrimitiveInteraction("e2","r1", -1));
		valences.add(new PrimitiveInteraction("e1","r2", 1));
		valences.add(new PrimitiveInteraction("e2","r2", 1));
		pis = new PrimitiveInteractions(valences);
		experience = new Experience(valences);
		counter = 0;
		memory = new ArrayList<PrimitiveInteraction>();
	}

	@Override
	protected void doOneStep(int step) {

		Experiment experiment = experience.getBestExperiment();

		List<Experiment> newExperiences = new ArrayList<Experiment>();

		Experiment enactedExperiment = experiment.enact(world, pis, newExperiences);

		experience.learn(newExperiences);

		memory.addAll(enactedExperiment.experiment);

		experience.learn(Experiment.getSubExperiments(memory, null, true));

		if (enactedExperiment.valence >= 0) {
			if (mood != PLEASED) counter = 0;
			mood = PLEASED;
			counterPleased++;
		}
		else {
			if (mood != PAINED) counter = 0;
			mood = PAINED;
			counterPained++;
		}

		counter++;

		// learnCompositeInteraction(contextInteraction, enactedInteraction);

		String it = enactedExperiment.key;
		if (!enactedExperiment.equals(experiment)) {
			it = experiment + "=>" + it;
		}
		System.out.println(
			(step + "   ").substring(0, 4) + ":" +
			"(" + counterPleased + "/" + counterPained + ")" +
			(mood +"   ").substring(0, 8) + it);
		System.out.println(experience);
	}

}