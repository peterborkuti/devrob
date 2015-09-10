package hu.bp.selfprogramming.modules;

import hu.bp.annotation.Immutable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * Stores all the possible Primitive Interactions ([experiment, result] touples)
 * and its valences (value for motivation)
 * 
 * If valence is positive, robot is motivated to choose this interaction
 * 
 * @author Peter Borkuti
 *
 */

@Immutable
public class PrimitiveInteractions {
	private final ImmutableMap<String, PrimitiveInteraction> interactions;

	public PrimitiveInteractions(List<PrimitiveInteraction> pis) {
		Map<String, PrimitiveInteraction>interactions =
			new HashMap<String, PrimitiveInteraction>();

		for (PrimitiveInteraction i : pis) {
			interactions.put(i.interaction, i);
		}

		this.interactions =
			new ImmutableMap.Builder<String, PrimitiveInteraction>().
				putAll(interactions).build();
	}

	/**
	 * Returns with a PrimitiveInteraction based on experiment and result.
	 * 
	 * It basically adds the valence to an [experiment, result] touple
	 * @param experiment
	 * @param result
	 * @return the primitive interaction
	 */
	public PrimitiveInteraction get(String experiment, String result) {
		String key = experiment + result;
		PrimitiveInteraction i = interactions.get(key);

		//Don't let elements escape
		return new PrimitiveInteraction(experiment, result, i.valence);
	}

}
