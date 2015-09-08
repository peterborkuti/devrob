package hu.bp.selfprogramming.modules;

import hu.bp.common.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class Experiment {

	public final ImmutableList<PrimitiveInteraction> experiment;

	public final int valence;

	public final String key;

	private long proclivity = 0;

	private int success = 0;

	private int tried = 0;

	public Experiment(PrimitiveInteraction i) {
		this(new ArrayList<PrimitiveInteraction>(Arrays.asList(i)));
	}

	public Experiment(PrimitiveInteraction i, boolean success) {
		this(new ArrayList<PrimitiveInteraction>(Arrays.asList(i)), success);
	}

	public Experiment(Experiment e) {
		this(e.experiment);
	}

	public Experiment(List<PrimitiveInteraction> experiment) {
		super();
		this.experiment =
			new ImmutableList.Builder<PrimitiveInteraction>()
				.addAll(experiment).build();

		StringBuilder key =
			new StringBuilder(this.experiment.size() * PrimitiveInteraction.length);

		int valence = 0;
		for (PrimitiveInteraction i : experiment) {
			valence += i.valence;
			key.append(i.interaction);
		}

		this.valence = valence;
		this.key = key.toString();
	}

	public Experiment(List<PrimitiveInteraction> experiment, boolean success) {
		this(experiment);

		this.success = (success) ? 1 : -1;
		this.tried = 1;
	}

	public Experiment(PrimitiveInteraction i1,
			PrimitiveInteraction i2) {
		this(Arrays.asList(i1, i2));
	}

	public boolean isPrimitiveInteraction() {
		return experiment.size() == 1;
	}

	/**
	 * Enacts this experiment in the given World and returns with 
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
	public Experiment enact(final World w, final PrimitiveInteractions pis,
			final List<Experiment> newExperiments) {

		List<PrimitiveInteraction> enactedExperimentList =
			new ArrayList<PrimitiveInteraction>();

		for (int i = 0; i < experiment.size(); i++) {
			boolean failed =
				enactPrimitiveInteraction(
					w, experiment.get(i), pis, enactedExperimentList,
					newExperiments);

		}

			if (failed && !isPrimitiveInteraction() && i < ) {
				if (i < experiment.size()) {
					Experiment failedExperiment =
						new Experiment(experiment.get(i - 1), experiment.get(i));
	
					newExperiments.addAll(
						getFailedSubExperiments(
							experiment.subList(0, i + 1), failedExperiment));
				}
			}

			newExperiments.addAll(
				getSubExperiments(enactedExperimentList, null, true));
		}

		//Just to be sure not escape Anything
		return new Experiment(enactedExperimentList);
	}

	private boolean enactPrimitiveInteraction(World w,
			PrimitiveInteraction intended,
			PrimitiveInteractions pis, List<PrimitiveInteraction> enactedExperimentList,
			List<Experiment> newExperiments) {

		String result = w.getResult(intended.experiment);

		PrimitiveInteraction enacted = pis.get(intended.experiment, result);

		enactedExperimentList.add(enacted);

		newExperiments.add(new Experiment(enacted, true));

		if (!intended.equals(enacted)) {
			newExperiments.add(new Experiment(intended, false));
		}

		return !intended.equals(enacted);
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
	private Collection<? extends Experiment> getFailedSubExperiments(
			List<PrimitiveInteraction> actedPartOfIintendedExperiment,
			Experiment failedExperiment) {

		assert(failedExperiment.experiment.size() == 2);

		return getSubExperiments(
			actedPartOfIintendedExperiment, failedExperiment.key, false);
	}

	public void updateTried() {
		tried++;
	}

	public void updateSuccess(boolean successful) {
		success += (successful) ? 1 : -1;
		if (success == 0) {
			proclivity = valence;
		}
		else {
			proclivity = success * valence;
		}
	}

	public long getProclivity() {
		return proclivity;
	}

	public boolean isSuccess() {
		return success >= 0;
	}

	public String toString() {
		return "[" + key + "(" + valence + "," + proclivity + ")]";
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Experiment)) {
			return false;
		}
		Experiment other = (Experiment) obj;
		if (key == null) {
			if (other.key != null) {
				return false;
			}
		} else if (!key.equals(other.key)) {
			return false;
		}
		return true;
	}


}
