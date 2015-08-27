package hu.bp.constructivist.epistemology;

import static hu.bp.common.Mood.BORED;
import static hu.bp.common.Mood.PAINED;
import static hu.bp.common.Mood.PLEASED;
import hu.bp.common.AbstractProgram;
import hu.bp.common.Mood;
import hu.bp.common.TwoStepWorld;
import hu.bp.common.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Prog01 extends AbstractProgram {
	Mood mood;
	String experiment;
	World world;
	String result;
	private static final int PAIN_LIMIT = 1000;
	private static final int PLEASED_LIMIT = 2;
	private Map<String,Integer> valences = new HashMap<String, Integer>();
	private String enactedInteraction;
	private String contextInteraction;
	private Map<String, List<String>> memory = new HashMap<String, List<String>>();
	private String prevResult;
	private int counter;

	@Override
	protected void init() {
		super.init();
		mood = BORED;
		world = new TwoStepWorld();
		valences.put("e1-r1", 1);
		valences.put("e2-r2", -2);
		valences.put("e2-r1", 1);
		valences.put("e1-r2", -2);
		experiment = "e2";
		enactedInteraction = "";
		prevResult = "";
		counter = 0;
	}

	@Override
	protected void doOneStep(int step) {
		contextInteraction = enactedInteraction;
		prevResult = result;
		List<String> anticipations = anticipate(enactedInteraction);
		//Integer prevValence = getInteractionValence(experiment, prevResult);

		experiment = selectExperiment(experiment, anticipations, counter);

		result = world.getResult(experiment);

		enactedInteraction =getInteraction(experiment, result);

		Integer valence = /*prevValence +*/ getInteractionValence(experiment, result);

		if (valence >= 0) {
			if (mood != PLEASED) counter = 0;
			mood = PLEASED;
		}
		else {
			if (mood != PAINED) counter = 0;
			mood = PAINED;
		}

		counter++;

		learnCompositeInteraction(contextInteraction, enactedInteraction);

		System.out.println(step + ":" + this.toString());
	}

	private String selectExperiment(
			String experiment, List<String> anticipations, int counter) {

		boolean feelBigPain =	(mood == PAINED) && (counter > PAIN_LIMIT);
		boolean bored = (mood == PLEASED) && (counter > PLEASED_LIMIT);

		String selectedExperiment = "";

		for (String interaction: anticipations) {
			if (getInteractionValence(interaction) >= 0) {
				String parts[] = interaction.split("-");
				selectedExperiment = parts[0];

				break;
			}
		}

		if (feelBigPain || "".equals(selectedExperiment) || bored) {
			selectedExperiment =pickOtherExperiment(experiment, true);
		}

		return selectedExperiment;
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

	private List<String> anticipate(String interaction) {
		if (memory.containsKey(interaction)) {
			return memory.get(interaction);
		}

		return new ArrayList<String>();
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
