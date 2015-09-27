package hu.bp.selfprogramming;

import static hu.bp.common.Mood.BORED;
import static hu.bp.common.Mood.PAINED;
import static hu.bp.common.Mood.PLEASED;
import hu.bp.common.AbstractProgram;
import hu.bp.common.FourStepWorld;
import hu.bp.common.Mood;
import hu.bp.common.SimpleWorld;
import hu.bp.common.ThreeStepWorld;
import hu.bp.common.ThreeStepWorld2;
import hu.bp.common.TwoStepWorld;
import hu.bp.common.World;
import hu.bp.selfprogramming.modules.Experience;
import hu.bp.selfprogramming.modules.Experiment;
import hu.bp.selfprogramming.modules.ExperimentUtils;
import hu.bp.selfprogramming.modules.PrimitiveInteraction;
import hu.bp.selfprogramming.modules.PrimitiveInteractions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Prog02 extends AbstractProgram {
	Mood mood;
	World world;
	private static final int PAIN_LIMIT = 2;
	private static final int PLEASED_LIMIT = 1000;
	private PrimitiveInteractions pis;
	private Experience experience;
	private int counter;
	private int counterPleased,counterPained;
	private Scanner scanner =  new Scanner(System.in);

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
		experience = new Experience();
		counter = 0;
	}

	@Override
	protected void doOneStep(int step) {
		System.out.println(experience);
		System.out.println("last interactions:" + experience.getLastInteractions());

		Experiment intendedExp = experience.getBestExperiment(pis);

		if (intendedExp == null) {
			System.out.println("intendedExp == null");
		}
		else {
			System.out.println("intendedExp:" + intendedExp);
		}
		int valence = 0;

		if (intendedExp == null || intendedExp.getValence() < 0) {
			PrimitiveInteraction intended = pis.getRandom(intendedExp);

			PrimitiveInteraction enacted = 
				ExperimentUtils.enact(intended, world, pis);

			valence = enacted.valence;

			System.out.println(
				intended.interaction + "=>" + enacted.interaction + ":" +
				valence);

			experience.learn(enacted, pis);
		}
		else {
			Experiment enacted =
				ExperimentUtils.enact(intendedExp, world, pis);

			//getValence use match!
			enacted.setMatch(intendedExp.getMatch());

			valence = enacted.getValence();

			System.out.println(
					intendedExp.getKey() + "=>" + enacted + ":" + valence);

			experience.learn(intendedExp, enacted, pis);
		}

		experience.clean();

		if (valence >= 0) {
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

		experience.printChangedExperiments();

		System.out.println("\n" + 
			(step + "   ").substring(0, 4) + ":" +
			"(Pleased:" + counterPleased + "/Pained:" + counterPained + ")" +
			(mood +"   ").substring(0, 8));

		scanner.nextLine();
	}


}