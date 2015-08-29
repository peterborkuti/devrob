package hu.bp.selfprogramming;

import static hu.bp.common.Mood.BORED;
import static hu.bp.common.Mood.PAINED;
import static hu.bp.common.Mood.PLEASED;
import hu.bp.common.AbstractProgram;
import hu.bp.common.Mood;
import hu.bp.common.TwoStepWorld;
import hu.bp.common.World;
import hu.bp.pattern.PatternFinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.text.html.MinimalHTMLWriter;

public class Prog01 extends AbstractProgram {
	Mood mood;
	String experiment;
	World world;
	String result;
	private static final int PAIN_LIMIT = 1000;
	private static final int PLEASED_LIMIT = 2;
	private Map<String,Integer> valences = new HashMap<String, Integer>();
	private String enactedInteractions;
	private Map<String, List<String>> memory = new HashMap<String, List<String>>();
	private String prevResult;
	private int counter;
	private String[] possibleInteractions;

	@Override
	protected void init() {
		super.init();
		mood = BORED;
		world = new TwoStepWorld();
		valences.put("e1-r1", -1);
		valences.put("e2-r2", 1);
		valences.put("e2-r1", -1);
		valences.put("e1-r2", 1);
		experiment = "e2";
		enactedInteractions = "";
		prevResult = "";
		counter = 0;
		possibleInteractions = valences.keySet().toArray(new String[0]);
	}

	@Override
	protected void doOneStep(int step) {
		Map<String, Integer> anticipations = anticipate(enactedInteractions);

		String intendedInteractions = selectExperiment(anticipations, counter);

		//result = world.getResult(experiment);

		String enactedInteractionList = enact(intendedInteractions);

		enactedInteractions += enactedInteractionList;

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

		System.out.println(step + ":" + this.toString());
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
				anticipations.get(interaction) * getInteractionValence(interaction);
			proclivities.put(interaction, proclivity);

			if (proclivity > maxProclivity) {
				maxProclivity = proclivity;
				bestInteractionList = interaction;
			}
		}

		if (feelBigPain || "".equals(bestInteractionList) || bored) {
			bestInteractionList = getRandomInteraction();
		}

		return bestInteractionList;
	}

	private void learnCompositeInteraction(
			String contextInteraction, String enactedInteraction) {

		if ("".equals(contextInteraction)) {
			return;
		}

		if (memory.containsKey(contextInteraction)) {
			List<String> interactions = memory.get(contextInteraction);

			if (!"".equals(enactedInteraction) && 
				!interactions.contains(enactedInteraction)) {

				interactions.add(enactedInteraction);
			}
		}
		else {
			List<String> interactions = new ArrayList<String>();

			if (!"".equals(enactedInteraction)) {
				interactions.add(enactedInteraction);
			}

			memory.put(contextInteraction, interactions);
		}
	}

	private Map<String, Integer> anticipate(String interactions) {
		PatternFinder p = new PatternFinder(interactions, 4);

		Map<String, Integer> proposes = new HashMap<String, Integer>();
		String[] definedInteractions = valences.keySet().toArray(new String[0]);
		for (String interaction: definedInteractions) {
			proposes.putAll(p.getAll(interaction));
		}

		return proposes;
	}

	private String getInteraction(String experiment, String result) {
		return experiment + "-" + result;
	}

	private Integer getInteractionValence(String experiment, String result) {
		String key = experiment + "-" + result;
		if (valences.containsKey(key)) {
			return valences.get(key);
		}

		return -1;
	}

	private Integer getInteractionValence(String interaction) {
		if ("".equals(interaction)) {
			return 0;
		}

		String parts[] = interaction.split("-");

		return getInteractionValence(parts[0], parts[1]);
	}

	private String getRandomInteraction() {
		int r = (int) Math.floor(Math.random() * possibleInteractions.length);

		return possibleInteractions[r];
	}

	public String toString() {
		String s = experiment + "-" + result + "," + mood;

		Set<String> set = memory.keySet();

		String mem = "";
		for (String key: set) {
			mem += " " + key + ":[";
			ArrayList<String> interactions =
				(ArrayList<String>) memory.get(key);

			for (String interaction: interactions) {
				mem += interaction + ",";
			}
			if (interactions.size() > 0) {
				mem = mem.substring(0, mem.length()-1);
			}
			mem += "]";
		}

		return "[" + s + ",{" + mem + "}" + "]";
	}

	private String pickOtherExperiment(String experiment, boolean panic) {
		if (panic) {
			return (Math.random() > 0.5) ? "e1" : "e2";
		}

		if ("e1".equals(experiment)) {
			return "e2";
		}

		return "e1";
	}
}
