/**
 * 
 */
package hu.bp.selfprogramming.modules;

import hu.bp.common.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Experience is a collection of experiments and a selection and learning mechanism
 * 
 * It is the knowledge of the robot about the environment(World)
 * @author Peter Borkuti
 *
 */
/**
 * @author Peter Borkuti
 *
 */
public class Experience {
	/**
	 * Memory of the robot about past experiments
	 */
	private final Map<String, Experiment> experiments
		= new HashMap<String, Experiment>();

	/**
	 * Serial memory of the past experiments in order of time
	 * The last primitive interaction is on the right side
	 */
	private final StringBuilder interactions = new StringBuilder();

	public Experience(Map<String, Experiment> e, String pastInteractions) {
		experiments = e;
		interactions.append(pastInteractions);
	}

	public Experience(Map<String, Experiment> e) {
		this(e, "");
	}

	public Experience(List<PrimitiveInteraction> newInteractions,
			String pastInteractions) {

		experiments = new HashMap<String, Experiment>();

		for (PrimitiveInteraction i: newInteractions) {
			Experiment e = new Experiment(i);
			experiments.put(i.interaction, e);
		}

		interactions = new StringBuilder(pastInteractions);
	}

	public Experience() {
		experiments = new HashMap<String, Experiment>();
		interactions = new StringBuilder();
	}

	/**
	 * Robot stores newExperiments in its memory
	 * 
	 * @param newExperiments
	 */
	public void learn(List<Experiment> newExperiments) {
		for (Experiment e: newExperiments) {
			System.out.println("learn" + e);
			updateExperiment(e);
		}
	}

	public Experiment getBestExperiment() {
		long maxProclivity = Integer.MIN_VALUE;
		List<Experiment> bestExperiences = new ArrayList<Experiment>();

		for (Experiment e: experiments.values()) {
			if (e.getProclivity() > maxProclivity) {
				bestExperiences.clear();
				maxProclivity = e.getProclivity();
			};
			if (maxProclivity == e.getProclivity()) {
				bestExperiences.add(new Experiment(e));
			}
		}

		Experiment e = Utils.getRandomElement(bestExperiences);

		//Do not let experiments escape
		if (e != null) {
			e = new Experiment(e);
		}

		return e;
	}

	private void updateExperiment(Experiment e) {
		Experiment experiment = experiments.get(e.key);

		if (experiment == null) {
			if ((e.valence >= 0 && !e.isPrimitiveInteraction())) {
				experiments.put(e.key, e);
			}
		}
		else if (!e.isPrimitiveInteraction()){
			experiment.updateTried();
			experiment.updateSuccess(e.isSuccess());
		}
	}

	public String toString() {
		String s = "";
		for (String key: experiments.keySet()) {
			s += (key + ":" + experiments.get(key)) + "\n";
		}

		return s;
	}

}
