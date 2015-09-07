package hu.bp.selfprogramming.modules;

public class PrimitiveInteraction {

	public final static int length = 4;
	public final String experiment;
	public final String result;
	public final Integer valence;

	public final String interaction;

	public final int hashValue;

	public PrimitiveInteraction(String experiment, String result,
			Integer valence) {
		super();
		this.experiment = experiment;
		this.result = result;
		this.valence = valence;

		interaction = experiment + result;

		hashValue = (interaction).hashCode();
	}

	@Override
	public int hashCode() {
		return hashValue;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PrimitiveInteraction)) {
			return false;
		}

		PrimitiveInteraction other = (PrimitiveInteraction) obj;

		return this.hashValue == other.hashValue;
	}



}
