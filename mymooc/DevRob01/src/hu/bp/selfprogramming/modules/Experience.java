/**
 * 
 */
package hu.bp.selfprogramming.modules;

import hu.bp.common.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * Experience is a collection of experiments and a selection and learning mechanism
 * 
 * It is the knowledge of the robot about the environment(World)
 * @author Peter Borkuti
 *
 */
public class Experience {
	private static final int MAX_EXPERIMENT_SIZE = 7;

	/**
	 * Memory of the robot about past experiments
	 * The key of the map is the Experiment.key
	 * @see Experiment.key
	 */
	private final Map<String, Experiment> experiments
		= new HashMap<String, Experiment>();

	/**
	 * Serial memory of the past experiments in order of time
	 * The last primitive interaction is on the right side
	 */
	private final StringBuilder interactions = new StringBuilder();

	/**
	 * The length (number of primitive interactions) of the
	 * longest learned experiment
	 */
	private int maxExperimentLength = 0;

	public Experience() { }

	public Experience(Map<String, Experiment> e, String pastInteractions) {
		experiments.putAll(e);
		interactions.append(pastInteractions);
	}

	public Experience(Map<String, Experiment> e) {
		this(e, "");
	}

	public Experience(List<PrimitiveInteraction> newInteractions,
			String pastInteractions) {

		Experiment newExperiment = new Experiment(newInteractions);
		experiments.put(newExperiment.key, newExperiment);

		interactions.append(pastInteractions);
	}

	/**
	 * Robot stores newExperiments in its memory
	 * @param enactedExperiment 
	 * 
	 * @param newExperiments
	 */
	/*
	public void learn(Experiment enactedExperiment, List<Experiment> newExperiments) {
		//System.out.println("learn:" + enactedExperiment + "," + newExperiments);
		if (enactedExperiment == null) {
			return;
		}

		interactions.append(enactedExperiment.key);

		if (newExperiments == null) {
			return;
		}

		for (Experiment e: newExperiments) {
			//System.out.println("learn" + e);
			updateExperiment(e);
		}
	}
	*/

	/**
	 * Robot stores newExperiments in its memory
	 * @param enactedExperiment 
	 * 
	 * @param newExperiments
	 */
	public void learn(Experiment enactedExperiment, PrimitiveInteractions pis) {
		if (enactedExperiment == null) {
			return;
		}

		interactions.append(enactedExperiment.key);

		if (interactions.length() < PrimitiveInteraction.LENGTH * 2) {
			return;
		}

		if (pis == null) {
			return;
		}

		System.out.println("Learn:" + enactedExperiment);

		int startPos = interactions.length() - 2 * PrimitiveInteraction.LENGTH;
		int endPos =
			Math.max(
				0,
				interactions.length() - MAX_EXPERIMENT_SIZE *
					PrimitiveInteraction.LENGTH);

		for (int i = startPos; i >= endPos; i -= PrimitiveInteraction.LENGTH) {
			Experiment e =
				new Experiment(
					interactions.substring(i, interactions.length()), pis);

			updateExperiment(e);
		}
	}

	public Experiment getBestExperiment(PrimitiveInteractions pis) {
		List<Experiment> found =
			ExperimentUtils.match(getInteractions(), experiments);

		long maxProclivity = Integer.MIN_VALUE;
		List<Experiment> bestExperiences = new ArrayList<Experiment>();

		for (Experiment e: found) {
			if (e.getProclivity() > maxProclivity) {
				bestExperiences.clear();
				maxProclivity = e.getProclivity();
			}

			if (maxProclivity == e.getProclivity()) {
				bestExperiences.add(new Experiment(e));
			}
		}

		if (bestExperiences.size() > 0) {
			Experiment e = Utils.getRandomElement(bestExperiences);

			//Do not let experiments escape
			return new Experiment(e);
		}

		return null;
	}

	private void updateExperiment(Experiment e) {
		if (e == null || e.isPrimitiveInteraction() ||
			e.experiment.size() > MAX_EXPERIMENT_SIZE) {

			return;
		}

		Experiment experiment = experiments.get(e.key);

		if (experiment == null) {
			experiments.put(e.key, e);

			//remember for the maximum length of experiment
			if (maxExperimentLength < e.experiment.size()) {
				maxExperimentLength = e.experiment.size();
			}
		}
		else {
			experiment.updateTried();
			experiment.updateSuccess(e.isSuccess());
		}
	}

	/**
	 * Gets the last enacted primitive interaction
	 * 
	 * @param pis
	 * @return null if there was not interaction stored
	 */
	public PrimitiveInteraction getLast(PrimitiveInteractions pis) {
		if (interactions.length() < PrimitiveInteraction.LENGTH) {
			return null;
		}

		return pis.get(
			interactions.substring(
				interactions.length() - PrimitiveInteraction.LENGTH));
	}

	public String getInteractions() {
		return interactions.toString();
	}

	public ImmutableMap<String, Experiment> getExperiments() {
		return 
			new ImmutableMap.Builder<String, Experiment>()
				.putAll(experiments).build();
	}

	public String toString() {
		String s = "Experience: maxLen:" + maxExperimentLength + "\n";
		s += "interactions:" + interactions + "\n";
		int i = 0;
		for (String key: experiments.keySet()) {
			s += (++i) + ". " + (experiments.get(key)) + "\n";
		}

		return s;
	}

}
