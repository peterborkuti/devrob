package hu.bp.selfprogramming;

import static hu.bp.common.Mood.BORED;
import static hu.bp.common.Mood.PAINED;
import static hu.bp.common.Mood.PLEASED;
import hu.bp.common.AbstractProgram;
import hu.bp.common.Mood;
import hu.bp.common.SelectedExperiment;
import hu.bp.common.ThreeStepWorld;
import hu.bp.common.World;
import hu.bp.pattern.PatternFinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Prog01 extends AbstractProgram {
	Mood mood;
	World world;
	private static final int PAIN_LIMIT = 2;
	private static final int PLEASED_LIMIT = 4;
	private static final int INTERACTION_CHAR_LENGTH = 4;
	private static final int EXPERIMENT_CHAR_LENGTH = 2;
	public Map<String,Float> valences;
	private String enactedInteractions;
	private int counter;
	public String[] possibleInteractions;
	private String lastEnactedPrimitiveInteraction;
	private int counterPleased,counterPanid;

	@Override
	protected void init() {
		super.init();
		mood = BORED;
		world = new ThreeStepWorld();
		valences = new HashMap<String, Float>();
		valences.put("e1r1", -1f);
		valences.put("e2r2", 1f);
		valences.put("e2r1", -1f);
		valences.put("e1r2", 1f);
		enactedInteractions = "";
		lastEnactedPrimitiveInteraction = "";
		counter = 0;
		possibleInteractions = valences.keySet().toArray(new String[0]);
	}

	@Override
	protected void doOneStep(int step) {
		Map<String, Integer> anticipations =
			anticipate(enactedInteractions, possibleInteractions,
				INTERACTION_CHAR_LENGTH);

		SelectedExperiment selExp =
			selectExperiment(anticipations, counter, lastEnactedPrimitiveInteraction);

		String intendedInteractions = checkSelectedExperiment(selExp);

		String enactedInteractionList = enact(intendedInteractions);

		enactedInteractions += enactedInteractionList;

		float valence = getInteractionValence(enactedInteractionList);

		if (valence >= 0) {
			if (mood != PLEASED) counter = 0;
			mood = PLEASED;
			counterPleased++;
		}
		else {
			if (mood != PAINED) counter = 0;
			mood = PAINED;
			counterPanid++;
		}

		counter++;

		// learnCompositeInteraction(contextInteraction, enactedInteraction);

		String it = enactedInteractionList;
		if (!enactedInteractionList.equals(intendedInteractions)) {
			it = intendedInteractions + "=>" + it;
		}
		System.out.println(
			(step + "   ").substring(0, 4) + ":" +
			"(" + counterPleased + "/" + counterPanid + ")" +
			(mood +"   ").substring(0, 8) + it + this.toString());
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

			//System.out.print("try:" + interactions);
			String result = world.getResult(experiment);

			lastEnactedPrimitiveInteraction = experiment + result;
			//System.out.println("enacted:" + lastEnactedPrimitiveInteraction);
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

	private String checkSelectedExperiment(SelectedExperiment experiment) {
		String interaction = experiment.experiment;

		boolean feelBigPain =	(mood == PAINED) && (counter > PAIN_LIMIT);
		boolean bored = (mood == PLEASED) && (counter > PLEASED_LIMIT);

		if ("".equals(interaction) || experiment.proclivity < 0 || feelBigPain) {
			interaction = getOtherRandomInteraction(interaction);
		}

		return interaction;
	}

	public SelectedExperiment selectExperiment(
			Map<String, Integer> anticipations, int counter, String lastInteraction) {
		System.out.println("selectExperiment - lastInteraction:" + lastInteraction);

		String[] interactionList =
			anticipations.keySet().toArray(new String[0]);

		float maxProclivity = Integer.MIN_VALUE;

		SelectedExperiment experiment =
			new SelectedExperiment("", maxProclivity, 0);

		// a list for experiments with the same proclivity
		// a random experiment will be choosen from this list
		ArrayList<SelectedExperiment> expList =
			new ArrayList<SelectedExperiment>();

		expList.add(experiment);

		for (String interaction: interactionList) {
			Float valence = getInteractionValence(interaction);
			Integer occurence = anticipations.get(interaction);
			Float proclivity = valence * ((occurence == 0)? 0.5f : occurence);

			// maximum selection
			if ((proclivity - maxProclivity) > 0.1) {
				maxProclivity = proclivity;
				expList.clear();
			}

			// store experiments with the same actual maximum proclivity
			if (Math.abs(proclivity - maxProclivity) <= 0.1) {
				experiment =
						new SelectedExperiment(interaction, proclivity, occurence);
				expList.add(experiment);
				/*
				System.out.println(
					"selectExperiment - candidates:" +
					StringUtils.join(
						expList.toArray(new SelectedExperiment[0]), ","));
				*/
			}
		}

		// choose a random experiment
		int index = (int) Math.floor(Math.random() * expList.size());
		return expList.get(index);
	}

	public static Map<String, Integer> anticipate(
		String interactions, String[] defaultInteractions, int len) {
		PatternFinder p = new PatternFinder(interactions, len);

		Map<String, Integer> proposes = new HashMap<String, Integer>();

		List<String> dInt = new ArrayList<String>(Arrays.asList(defaultInteractions));

		// default primitive interactions in random order to not to choose
		// the same in every time
		for (int i = 0; i < defaultInteractions.length; i++) {
			int index = (int) Math.floor(Math.random() * dInt.size());
			proposes.put(dInt.remove(index), 0);
		}

		// all the possible complex interactions, which can led to 
		// a default primitive interaction
		for (String interaction: defaultInteractions) {
			proposes.putAll(p.getAll(interaction));
		}

		List<String> tmp = new ArrayList<String>();

		for (String key: proposes.keySet()) {
			tmp.add("(" + proposes.get(key) + ")" + key);
		}

		System.out.println(
			"anticipations:" + StringUtils.join(tmp.toArray(new String[0]),","));

		return proposes;
	}

	private Float getInteractionValence(String interaction) {
		if ("".equals(interaction) ||
			(interaction.length() < INTERACTION_CHAR_LENGTH)) {
			return 0f;
		}

		float valence = 0;

		for (int i = 0; i < interaction.length() - 1; i += INTERACTION_CHAR_LENGTH) {
			String experiment = interaction.substring(i, i + INTERACTION_CHAR_LENGTH);

			assert(valences.containsKey(experiment));

			if (valences.containsKey(experiment)) {
				valence += valences.get(experiment);
			}
		}

		return valence;
	}

	private String getOtherRandomInteraction(String interaction) {
		List<String> possible =
			new ArrayList<String>(Arrays.asList(possibleInteractions));

		if (interaction.length() >= INTERACTION_CHAR_LENGTH) {
			possible.remove(interaction);
		}

		String[] array = possible.toArray(new String[0]);

		int r = (int) Math.floor(Math.random() * array.length);

		System.out.println("Choose random " + array[r] + " instead of " + interaction);

		return array[r];
	}

	public String toString() {
		return "[" + enactedInteractions + "]";
	}

}