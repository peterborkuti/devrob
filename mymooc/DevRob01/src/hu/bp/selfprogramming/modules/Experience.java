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
	/**
	 * There will be no experiment stored which longer than this number
	 * It is for avoiding memory and/or performance issues
	 */
	public static final int MAX_EXPERIMENT_SIZE = 7;

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

	public Experience(List<PrimitiveInteraction> newInteractions,
			String pastInteractions) {

		Experiment newExperiment = new Experiment(newInteractions);
		experiments.put(newExperiment.key, newExperiment);

		interactions.append(pastInteractions);
	}

	public Experience(Map<String, Experiment> e) {
		this(e, "");
	}

	public Experience(Map<String, Experiment> e, String pastInteractions) {
		experiments.putAll(e);
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

	public Experiment getBestExperiment(PrimitiveInteractions pis) {
		List<Experiment> found =
			ExperimentUtils.match(getInteractions(), experiments);

		long maxValence = Integer.MIN_VALUE;
		List<Experiment> bestExperiences = new ArrayList<Experiment>();

		for (Experiment e: found) {
			if (e.getValence() > maxValence) {
				bestExperiences.clear();
				maxValence = e.getValence();
			}

			if (maxValence == e.getValence()) {
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

	public ImmutableMap<String, Experiment> getExperiments() {
		return 
			new ImmutableMap.Builder<String, Experiment>()
				.putAll(experiments).build();
	}

	public String getInteractions() {
		return interactions.toString();
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

	public String getLastInteractions() {
		return
			interactions.substring(
				Math.max(
					0,
					interactions.length() -
						MAX_EXPERIMENT_SIZE * PrimitiveInteraction.LENGTH));
	}

	/**
	 * Robot stores newExperiments in its memory
	 * @param enactedExperiment 
	 * 
	 * @param newExperiments
	 */
	public void learn(Experiment intended, Experiment enacted, PrimitiveInteractions pis) {
		if (enacted == null) {
			return;
		}

		if (pis == null) {
			return;
		}

		if (intended == null) {
			return;
		}

		for (PrimitiveInteraction pi : enacted.getAfterMatchedList()) {
			learn(pi, pis);
		}

		//TODO: weaken failed experiments

	}

	public void learn(PrimitiveInteraction enacted, PrimitiveInteractions pis) {
		if (enacted == null) {
			return;
		}

		interactions.append(enacted.interaction);

		if (interactions.length() < PrimitiveInteraction.LENGTH * 2) {
			return;
		}

		if (pis == null) {
			return;
		}

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

	public String toString() {
		String s = "Experience: maxLen:" + maxExperimentLength + "\n";
		s += "interactions:" + getLastInteractions() + "\n";
		int i = 0;
		for (String key: experiments.keySet()) {
			s += (++i) + ". " + (experiments.get(key)) + "\n";
		}

		return s;
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

}
