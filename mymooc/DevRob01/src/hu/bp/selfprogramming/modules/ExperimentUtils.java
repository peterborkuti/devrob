package hu.bp.selfprogramming.modules;

import hu.bp.common.ExperimentException;
import hu.bp.common.Utils;
import hu.bp.common.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ExperimentUtils {

	/**
	 * Enacts the experiment in the given World and returns with 
	 * a list of experiments which must be learned.
	 * If a returned experiment has a success field with value 1, it must be 
	 * reinforced, if it has a value -1, (it was failed) must be weaken.
	 * The returned lists will contain this whole experiment also (if it was successful).
	 * 
	 * The first primitive interaction will NOT be enacted if this is a complex interaction
	 * , because that was the end of the previous experiment.
	 * 
	 * @param w
	 * @return
	 */
	public static Experiment enact(
			final Experiment experiment,
			final World w, final PrimitiveInteractions pis) {

		List<PrimitiveInteraction> enactedExperimentList =
			new ArrayList<PrimitiveInteraction>();

		if (experiment.getMatch() <= 0) {
			throw new ExperimentException(
				"Can not enact experiment with zero match. " +
				"Use enact(PrimitiveInteraction,..).");
		}

		/* the experiment's first some experiment is has been enacted lastly
		 * see experiment.match field
		 */
		enactedExperimentList.addAll(experiment.getMatchedList());

		for (int i = experiment.getMatch(); i < experiment.experiment.size(); i++) {

			PrimitiveInteraction intended = experiment.experiment.get(i);

			PrimitiveInteraction enacted = enact(intended, w, pis);

			enactedExperimentList.add(enacted);

			if (!intended.equals(enacted)) {
				break;
			}
		}

		//Just to be sure not escape Anything
		return new Experiment(enactedExperimentList);
	}

	public static PrimitiveInteraction enact(final PrimitiveInteraction intended,
		final World w, final PrimitiveInteractions pis) {

		String result = w.getResult(intended.experiment);

		return pis.get(intended.experiment, result);
	}


	public static List<Experiment> getSubExperiments(
			List<PrimitiveInteraction> list, String filter, boolean success) {

		List<Experiment> l = new ArrayList<Experiment>();
		for (int len = 2; len <= list.size(); len++) {
			for (int i = 0; i < list.size() - len; i++) {
				Experiment e = new Experiment(list.subList(i, i + len), success);
				if ((filter == null) || (e.key.indexOf(filter) == -1)) {
					l.add(e);
				}
			}
		}

		return l;
	}

	
	/**
	 * @param enactedExperiment
	 * @param failedExperiment the last two primitive interactions.
	 * The first was OK, the last is failed
	 * @return
	 */
	public static Collection<? extends Experiment> getFailedSubExperiments(
			List<PrimitiveInteraction> actedPartOfIintendedExperiment,
			Experiment failedExperiment) {

		assert(failedExperiment.experiment.size() == 2);

		return getSubExperiments(
			actedPartOfIintendedExperiment, failedExperiment.key, false);
	}

	/**
	 * Find matching experiments for the given primitive interactions
	 * 
	 * An experiment matches to a series of primitive interactions, if
	 * the last interactions fits to the beginning of an experiment.
	 * 
	 * In this situation, an experiment is good for anticipate future
	 * interactions. The better the fit, the better the anticipation is
	 * 
	 * The returned experiments will be set its match field with the number of
	 * fitted primitive interactions
	 * 
	 * The proposed primitive interactions star from the experiment's match value
	 * as an index
	 * 
	 * Usage: 
	 * @param key the enacted primitive interactions
	 * @param experiments the stored experiments
	 * @return list of best-fit experiments
	 */
	public static List<Experiment> match(String key,
			Map<String, Experiment> experiments) {

		List<Experiment> found = new ArrayList<Experiment>();

		if (key == null || "".equals(key) || experiments == null ||
				experiments.size() == 0) {

			return found;
		}

		String[] holes = experiments.keySet().toArray(new String[0]);

		for (String hole: holes) {
			int size =
				Utils.bestFitSize(key, hole);

			if ((size > 0) &&
				// if there is no not-match part on the right side of the
				// hole (fits all), it is not valuable to predict interactions
				(size < hole.length())) {
				Experiment e = new Experiment(experiments.get(hole));
				e.setMatch(size / PrimitiveInteraction.LENGTH);
				found.add(e);
			}
		}

		return found;
	}


}
