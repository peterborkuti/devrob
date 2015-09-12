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

	@Immutable
	public final int valence;

	/**
	 * The concatenated list of interactions
	 * like "e1r2e1r1e2r1..."
	 */
	@Immutable
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
			new StringBuilder(this.experiment.size() * PrimitiveInteraction.LENGTH);

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
