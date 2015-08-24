package hu.bp.sensorimotor;

import static hu.bp.common.Mood.BORED;
import static hu.bp.common.Mood.FRUSTRATED;
import static hu.bp.common.Mood.PAINED;
import static hu.bp.common.Mood.PLEASED;
import static hu.bp.common.Mood.SATISFIED;
import hu.bp.common.AbstractProgram;
import hu.bp.common.Mood;
import hu.bp.common.SimpleWorld;
import hu.bp.common.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Prog02 extends AbstractProgram {
	Mood mood1, mood2;
	String experiment;
	World world;
	String result;
	Map<String,Integer> interactions = new HashMap<String, Integer>();
	private static final int SATISFIED_LIMIT = 3;

	private int satisfiedDuration;
	String anticipatedResult;
	Map<String,String> memory = new HashMap<String, String>();
	

	@Override
	protected void init() {
		super.init();
		mood1 = BORED;
		mood2 = PLEASED;
		world = new SimpleWorld();
		interactions.put("e1-r1", -1);
		interactions.put("e2-r2", 1);
		experiment = "e1";
		satisfiedDuration = 0;
	}

	@Override
	protected void doOneStep(int step) {
		if (mood2 == PAINED || mood1 == BORED) {
			experiment = pickOtherExperiment(experiment);
			satisfiedDuration = 0;
		}

		anticipatedResult = anticipate(experiment);
		result = world.getResult(experiment);
		recordTuple(experiment, result);

		if (result.equals(anticipatedResult)) {
			mood1 = SATISFIED;
			satisfiedDuration++;
		}
		else {
			mood1 = FRUSTRATED;
			satisfiedDuration = 0;
		}

		if (satisfiedDuration > SATISFIED_LIMIT) {
			mood1 = BORED;
		}

		Integer valence = getInteractionValence(experiment, result);
		
		if (valence >=0) {
			mood2 = PLEASED;
		}
		else {
			mood2 = PAINED;
		}
		

		System.out.println(step + ":" + this.toString());
		
	}

	private void recordTuple(String experiment, String result) {
		memory.put(experiment, result);
		
	}

	private String anticipate(String experiment) {
		if (memory.containsKey(experiment)) {
			return memory.get(experiment);
		}

		return "";
	}

	private Integer getInteractionValence(String experiment2, String result2) {
		String key = experiment + "-" + result;
		if (interactions.containsKey(key)) {
			return interactions.get(key);
		}

		return 0;
	}

	public String toString() {
		String s = experiment + "-" + result + "," + mood1 + "," + mood2;

		Set<String> set = memory.keySet();
		String mem = "";
		for (String key: set) {
			mem += key + ":" + memory.get(key) + ",";
		}

		return "[" + s + ",{" + mem + "}" + "]";
	}

	private String pickOtherExperiment(String experiment) {
		if ("e1".equals(experiment)) {
			return "e2";
		}
		return "e1";
	}
}
