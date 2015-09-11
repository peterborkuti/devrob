package hu.bp.selfprogramming.modules;

import hu.bp.common.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	public static Experiment enact(final PrimitiveInteraction lastEnaction,
			final Experiment experiment,
			final World w, final PrimitiveInteractions pis,
			final List<Experiment> newExperiments) {

		List<PrimitiveInteraction> enactedExperimentList =
			new ArrayList<PrimitiveInteraction>();

		if (lastEnaction != null) {
			enactedExperimentList.add(lastEnaction);
		}

		PrimitiveInteraction prevEnaction = lastEnaction;

		boolean failed = false;

		for (int i = 0; !failed && i < experiment.experiment.size(); i++) {
			PrimitiveInteraction intended = experiment.experiment.get(i);

			failed =
				enactPrimitiveInteraction(
					w, intended, pis, enactedExperimentList,
					newExperiments);

			if (failed && prevEnaction != null) {
					Experiment failedExperiment =
						new Experiment(prevEnaction, intended);

					newExperiments.addAll(
						getFailedSubExperiments(
							experiment.experiment.subList(0, i + 1), failedExperiment));
			}

			newExperiments.addAll(
				getSubExperiments(enactedExperimentList, null, true));

			prevEnaction = intended;
		}

		//Just to be sure not escape Anything
		return new Experiment(enactedExperimentList);
	}

	/**
	 * Enacts a primitive interaction (intended).
	 * @param w
	 * @param intended the interaction to enact
	 * @param pis default primitive interactions
	 * @param enactedExperimentList
	 * @param newExperiments
	 * @return with true, if enacted interaction is the same as the intended
	 */
	public static boolean enactPrimitiveInteraction(final World w,
			final PrimitiveInteraction intended,
			final PrimitiveInteractions pis,
			final List<PrimitiveInteraction> enactedExperimentList,
			final List<Experiment> newExperiments) {

		PrimitiveInteraction enacted =
			enactPrimitiveInteraction(w, intended, pis, newExperiments);

		enactedExperimentList.add(enacted);

		return !intended.equals(enacted);
	}

	/**
	 * Enacts a primitive interaction (intended).
	 * @param w
	 * @param intended the interaction to enact
	 * @param pis default primitive interactions
	 * @param newExperiments
	 * @return the enacted interaction
	 */
	public static PrimitiveInteraction enactPrimitiveInteraction(final World w,
			final PrimitiveInteraction intended,
			final PrimitiveInteractions pis,
			final List<Experiment> newExperiments) {

		String result = w.getResult(intended.experiment);

		PrimitiveInteraction enacted = pis.get(intended.experiment, result);

		newExperiments.add(new Experiment(enacted, true));

		if (!intended.equals(enacted)) {
			newExperiments.add(new Experiment(intended, false));
		}

		return enacted;
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


}
