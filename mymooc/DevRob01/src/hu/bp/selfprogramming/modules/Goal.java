/**
 * 
 */
package hu.bp.selfprogramming.modules;

/**
 * Goal describes a data structure which consists of two primitive interactions:
 * The second is the goal interaction, the first is the starting interaction.
 * 
 * Goal will be used as the Experiences Map's key.
 * 
 * @author Peter Borkuti
 *
 */
public class Goal {
	public final PrimitiveInteraction start;
	public final PrimitiveInteraction goal;

	public final int hashValue;

	public Goal(PrimitiveInteraction start, PrimitiveInteraction goal) {
		super();
		this.start = start;
		this.goal = goal;

		hashValue =
			(start.interaction + goal.interaction).hashCode();
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

		if (!(obj instanceof Goal)) {
			return false;
		}

		Goal other = (Goal) obj;

		return this.hashValue == other.hashValue;
	}


}
