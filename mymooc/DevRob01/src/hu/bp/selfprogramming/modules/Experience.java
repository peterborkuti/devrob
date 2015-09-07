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
 * @author Peter Borkuti
 *
 */
public class Experience {
	private final Map<String, Experiment> experiments;

	public Experience(Map<String, Experiment> e) {
		experiments = e;
	}

	public Experience(List<PrimitiveInteraction> interactions) {
		experiments = new HashMap<String, Experiment>();

		for (PrimitiveInteraction i: interactions) {
			Experiment e = new Experiment(i);
			experiments.put(i.interaction, e);
		}
	}

	public Experience() {
		experiments = new HashMap<String, Experiment>();
	}

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
