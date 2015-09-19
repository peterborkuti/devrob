package hu.bp.selfprogramming.modules;

import hu.bp.annotation.Immutable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class Experiment {

	/**
	 * An experiment, which is a list of primitive interactions
	 */
	@Immutable
	public final ImmutableList<PrimitiveInteraction> experiment;

	/**
	 * The valence of this experiment in a real-life situation
	 * This is the sum of the valences of the intended primitive
	 * interactions from this experience
	 */
	private int valence;

	public int getValence() {
		return valence;
	}

	/**
	 * The concatenated list of interactions
	 * like "e1r2e1r1e2r1..."
	 */
	@Immutable
	public final String key;

	private long proclivity = 0;

	private int success = 0;

	
	/**
	 * It is used in proclivity's expression as a divisor
	 * so do not set it to 0!
	 */
	private int tried = 1;

	/**
	 * How much fits this experiment into the immediate past
	 * How many primitive interactions fits to each other from the
	 * past enacted interactions and from the left of this experiment
	 * 
	 * From this value as an index predicts this experiment
	 */
	private int match = 0;

	public Experiment(PrimitiveInteraction i) {
		this(new ArrayList<PrimitiveInteraction>(Arrays.asList(i)));
	}

	public Experiment(PrimitiveInteraction i, boolean success) {
		this(new ArrayList<PrimitiveInteraction>(Arrays.asList(i)), success);
	}

	public Experiment(Experiment e) {
		this(e.experiment, e.match);
	}

	/**
	 * Checks if experiment is worth to store
	 * (It's proposed experiment's value can be positive)
	 * @param e
	 * @return
	 */
	public static boolean isValuableExperiment(Experiment e) {
		int match = e.experiment.size() / 2;

		if (match < 1) {
			return false;
		}

		Experiment test = new Experiment(e.experiment, match);

		return test.valence >= 0;
	}

	public Experiment(List<PrimitiveInteraction> experiment) {
		this(experiment, 0);
	}

	public Experiment(List<PrimitiveInteraction> experiment, int match) {
		super();
		this.experiment =
			new ImmutableList.Builder<PrimitiveInteraction>()
				.addAll(experiment).build();

		StringBuilder key =
			new StringBuilder(this.experiment.size() * PrimitiveInteraction.LENGTH);

		for (int i = 0; i < experiment.size(); i++) {
			key.append(experiment.get(i).interaction);
		}

		this.key = key.toString();
		setMatch(match);
	}

	public Experiment(String interactions, PrimitiveInteractions pis) {
		this(pis.createList(interactions));
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

	public void updateTried() {
		tried++;
	}

	public void updateSuccess(boolean successful) {
		success += (successful) ? 1 : -1;
	}

	public long getProclivity() {
		return proclivity;
	}

	public boolean isSuccess() {
		return success >= 0;
	}

	public ImmutableList<PrimitiveInteraction> getMatchedList() {
		return experiment.subList(0, match);
	}

	public String getAfterMatchedString() {
		return key.substring(match * PrimitiveInteraction.LENGTH);
	}

	public ImmutableList<PrimitiveInteraction> getAfterMatchedList() {
		return experiment.subList(match, experiment.size());
	}

	public String getKey() {
		String k = key;

		if (match > 0) {
			int index = match * PrimitiveInteraction.LENGTH;
			k = (key.substring(0, index) + "|" + key.substring(index)).toUpperCase();
		}

		return k;
	}

	@Override
	public String toString() {
		return "[" + getKey() + "(" + valence + "," + proclivity + "," + match + ")]";
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

	/**
	 * From this value as an index predicts this experiment
	 * If it is 0, it was not matched yet
	 * @return index of the first future primitive interaction in this
	 * experiment
	 */
	public int getMatch() {
		return match;
	}

	/**
	 * Sets the match index of this experiment.
	 * 
	 * It also re-counts valence and proclivity
	 * @param match
	 */
	public void setMatch(int match) {
		this.match = match;
		countValence();
		this.proclivity = valence * match * success / tried;
	}

	public void countValence() {
		int valence = 0;

		for (int i = match; i < experiment.size(); i++) {
			valence += experiment.get(i).valence;
		}

		this.valence = valence;
	}

}
