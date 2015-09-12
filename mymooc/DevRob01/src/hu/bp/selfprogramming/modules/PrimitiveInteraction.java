package hu.bp.selfprogramming.modules;

import hu.bp.annotation.Immutable;

/**
 * Immutable class for primitive interaction
 * 
 * A primitive interaction is an [experiment, result] touple with valence
 * 
 * The valence motivate the robot to try this experience.
 * 
 * @author Peter Borkuti
 *
 */
@Immutable
public class PrimitiveInteraction {

	/**
	 * The length of the [experience, result] touple as a string
	 */
	public final static int LENGTH = 4;
	/**
	 * The experimet, like "e1", "e2", etc.
	 */
	public final String experiment;
	/**
	 * The result, like "r1", "r2", etc.
	 */
	public final String result;
	/**
	 * The valence of the interaction
	 */
	public final Integer valence;

	/**
	 * The interaction, which is an [experiment, result] touple coded to String
	 * Like "e1r1", "e1r2", etc.
	 */
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

	public PrimitiveInteraction(String interaction, int valence) {
		this(
			interaction.substring(0, LENGTH / 2),
			interaction.substring(LENGTH / 2),
			valence);
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
