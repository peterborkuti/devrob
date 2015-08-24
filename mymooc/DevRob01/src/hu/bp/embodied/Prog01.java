package hu.bp.embodied;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import hu.bp.common.AbstractProgram;
import hu.bp.common.Mood;
import hu.bp.common.SimpleWorld;
import hu.bp.common.World;
import static hu.bp.common.Mood.*;

public class Prog01 extends AbstractProgram {
	private static final int SATISFIED_LIMIT = 3;
	Mood mood;
	String experiment;
	World world;
	private int satisfiedDuration;
	String anticipatedResult;
	String result;
	Map<String,String> memory = new HashMap<String, String>();

	@Override
	protected void init() {
		super.init();
		mood = BORED;
		satisfiedDuration = 0;
		world = new SimpleWorld();
	}

	@Override
	protected void doOneStep(int step) {
		if (mood == BORED) {
			satisfiedDuration = 0;
			experiment = pickOtherExperiment(experiment);
		}
		anticipatedResult = anticipate(experiment);

		result = world.getResult(experiment);

		recordTuple(experiment, result);

		if (result.equals(anticipatedResult)) {
			mood = SATISFIED;
			satisfiedDuration++;
		}
		else {
			mood = FRUSTRATED;
			satisfiedDuration = 0;
		}

		if (satisfiedDuration > SATISFIED_LIMIT) {
			mood = BORED;
		}

		System.out.println(step + ":" + this.toString());
		
	}

	private void recordTuple(String experiment, String result) {
		memory.put(experiment, result);
		
	}

	public String toString() {
		String s = experiment + "-" + anticipatedResult +"/" + result + "," + mood;
		Set<String> set = memory.keySet();
		String mem = "";
		for (String key: set) {
			mem += key + ":" + memory.get(key) + ",";
		}
		return "[" + s + ",{" + mem + "}" + "]";
	}

	private String anticipate(String experiment) {
		if (memory.containsKey(experiment)) {
			return memory.get(experiment);
		}

		return "";
	}

	private String pickOtherExperiment(String experiment) {
		if ("e1".equals(experiment)) {
			return "e2";
		}
		return "e1";
	}
}
