package hu.bp.sensorimotor;

import static hu.bp.common.Mood.BORED;
import static hu.bp.common.Mood.PAINED;
import static hu.bp.common.Mood.PLEASED;
import hu.bp.common.AbstractProgram;
import hu.bp.common.Mood;
import hu.bp.common.SimpleWorld;
import hu.bp.common.World;

import java.util.HashMap;
import java.util.Map;

public class Prog01 extends AbstractProgram {
	Mood mood;
	String experiment;
	World world;
	String result;
	Map<String,Integer> interactions = new HashMap<String, Integer>();

	@Override
	protected void init() {
		super.init();
		mood = BORED;
		world = new SimpleWorld();
		interactions.put("e1-r1", -1);
		interactions.put("e2-r2", 1);
		experiment = "e1";
	}

	@Override
	protected void doOneStep(int step) {
		if (mood == PAINED) {
			experiment = pickOtherExperiment(experiment);
		}

		result = world.getResult(experiment);

		Integer valence = getInteractionValence(experiment, result);

		if (valence >=0) {
			mood = PLEASED;
		}
		else {
			mood = PAINED;
		}

		System.out.println(step + ":" + this.toString());
		
	}

	private Integer getInteractionValence(String experiment2, String result2) {
		String key = experiment + "-" + result;
		if (interactions.containsKey(key)) {
			return interactions.get(key);
		}

		return 0;
	}

	public String toString() {
		String s = experiment + "-" + result + "," + mood;
		return "[" + s + "]";
	}

	private String pickOtherExperiment(String experiment) {
		if ("e1".equals(experiment)) {
			return "e2";
		}
		return "e1";
	}
}
