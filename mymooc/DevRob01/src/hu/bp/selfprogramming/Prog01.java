package hu.bp.selfprogramming;

import static hu.bp.common.Mood.BORED;
import static hu.bp.common.Mood.PAINED;
import static hu.bp.common.Mood.PLEASED;
import hu.bp.common.AbstractProgram;
import hu.bp.common.Mood;
import hu.bp.common.TwoStepWorld;
import hu.bp.common.World;
import hu.bp.pattern.PatternFinder;

import java.util.HashMap;
import java.util.Map;

public class Prog01 extends AbstractProgram {
	Mood mood;
	World world;
	private static final int PAIN_LIMIT = 1000;
	private static final int PLEASED_LIMIT = 5;
	private static final int INTERACTION_CHAR_LENGTH = 4;
	private static final int EXPERIMENT_CHAR_LENGTH = 2;
	private Map<String,Integer> valences = new HashMap<String, Integer>();
	private String enactedInteractions;
	private int counter;
	private String[] possibleInteractions;

	@Override
	protected void init() {
		super.init();
		mood = BORED;
		world = new TwoStepWorld();
		valences.put("e1r1", -1);
		valences.put("e2r2", 1);
		valences.put("e2r1", -1);
		valences.put("e1r2", 1);
		enactedInteractions = "";
		counter = 0;
		possibleInteractions = valences.keySet().toArray(new String[0]);
	}

	@Override
	protected void doOneStep(int step) {
		Map<String, Integer> anticipations = anticipate(enactedInteractions);

		String intendedInteractions = selectExperiment(anticipations, counter);

		//result = world.getResult(experiment);

		String enactedInteractionList = enact(intendedInteractions);

		boolean learn = false;

		//if ((!enactedInteractionList.equals(intendedInteractions))) {
			learn = true;
			enactedInteractions += enactedInteractionList;
		//}

		Integer valence = getInteractionValence(enactedInteractionList);

		if (valence >= 0) {
			if (mood != PLEASED) counter = 0;
			mood = PLEASED;
		}
		else {
			if (mood != PAINED) counter = 0;
			mood = PAINED;
		}

		counter++;

		// learnCompositeInteraction(contextInteraction, enactedInteraction);

		System.out.println(
			step + ":" + intendedInteractions + "(" + learn +")" +
			this.toString());
	}

	private String enact(String interactions) {
		assert((interactions.length() % INTERACTION_CHAR_LENGTH) == 0);
		assert(interactions.length() > 0);

		if (interactions.length() < INTERACTION_CHAR_LENGTH) {
			return "";
		}

		if (interactions.length() == INTERACTION_CHAR_LENGTH) {
			String experiment =
				interactions.substring(0, EXPERIMENT_CHAR_LENGTH);

			String result = world.getResult(experiment);

			return experiment + result;
		}

		String intendedPreInteraction =
			interactions.substring(0, INTERACTION_CHAR_LENGTH);

		String enactedPreInteraction = enact(intendedPreInteraction);

		if (intendedPreInteraction.equals(enactedPreInteraction)) {
			String postInteractions =
				interactions.substring(INTERACTION_CHAR_LENGTH);

			return intendedPreInteraction + enact(postInteractions);
		}

		// intendedPreInteraction's result was not what we want
		// so process is stopped
		return enactedPreInteraction;
	}

	private String selectExperiment(
			Map<String, Integer> anticipations, int counter) {

		boolean feelBigPain =	(mood == PAINED) && (counter > PAIN_LIMIT);
		boolean bored = (mood == PLEASED) && (counter > PLEASED_LIMIT);

		Map<String, Integer> proclivities = new HashMap<String, Integer>();

		String[] interactionList =
			anticipations.keySet().toArray(new String[0]);
		Integer maxProclivity = Integer.MIN_VALUE;
		String bestInteractionList = "";

		for (String interaction: interactionList) {
			Integer proclivity =
				anticipations.get(interaction) *
				getInteractionValence(interaction);

			proclivities.put(interaction, proclivity);

			if (proclivity > maxProclivity) {
				maxProclivity = proclivity;
				bestInteractionList = interaction;
			}
		}

		if (feelBigPain || "".equals(bestInteractionList) || bored ||
				maxProclivity < 0) {
			bestInteractionList = getRandomInteraction();
		}

		return bestInteractionList;
	}

	private Map<String, Integer> anticipate(String interactions) {
		PatternFinder p = new PatternFinder(interactions, INTERACTION_CHAR_LENGTH);

		Map<String, Integer> proposes = new HashMap<String, Integer>();
		String[] definedInteractions = valences.keySet().toArray(new String[0]);
		for (String interaction: definedInteractions) {
			proposes.putAll(p.getAll(interaction));
		}

		return proposes;
	}

	private Integer getInteractionValence(String interaction) {
		if ("".equals(interaction) || interaction.length() < INTERACTION_CHAR_LENGTH) {
			return 0;
		}

		int valence = 0;

		for (int i = 0; i < interaction.length() - 1; i += INTERACTION_CHAR_LENGTH) {
			String experiment = interaction.substring(i, i + INTERACTION_CHAR_LENGTH);

			assert(valences.containsKey(experiment));

			if (valences.containsKey(experiment)) {
				valence += valences.get(experiment);
			}
		}

		return valence;
	}

	private String getRandomInteraction() {
		int r = (int) Math.floor(Math.random() * possibleInteractions.length);

		return possibleInteractions[r];
	}

	public String toString() {
		return "[" + mood + ",{" + enactedInteractions + "}" + "]";
	}

}