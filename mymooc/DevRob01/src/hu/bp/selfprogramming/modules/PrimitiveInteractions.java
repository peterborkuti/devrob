package hu.bp.selfprogramming.modules;

import hu.bp.annotation.Immutable;
import hu.bp.common.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableCollection;
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
	public final ImmutableMap<String, PrimitiveInteraction> interactions;

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

	public PrimitiveInteractions(Set<PrimitiveInteraction> pis) {
		this(Arrays.asList(pis.toArray(new PrimitiveInteraction[0])));
	}

	public PrimitiveInteractions(String[] positive, String[] negative) {
		this(addSet(createSet(positive, 1), negative, -1));
	}

	private static Set<PrimitiveInteraction> createSet(String[] s, int valence) {
		Set<PrimitiveInteraction> pis = new HashSet<PrimitiveInteraction>();

		return addSet(pis, s, valence);
	}

	private static Set<PrimitiveInteraction> addSet(
			Set<PrimitiveInteraction> pis, String[] s, int valence) {

		for (String ss : s) {
			pis.add(new PrimitiveInteraction(ss, valence));
		}

		return pis;
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
		return get(experiment + result);
	}

	/**
	 * Returns with a PrimitiveInteraction based on the string concatenation
	 * of experience+result.
	 * 
	 * It basically adds the valence to an [experiment, result] touple
	 * @param experiment
	 * @param result
	 * @return the primitive interaction
	 */
	public PrimitiveInteraction get(String interaction) {
		PrimitiveInteraction i = interactions.get(interaction);

		//Don't let elements escape
		return new PrimitiveInteraction(i.experiment, i.result, i.valence);
	}

	/**
	 * Returns with a random primitive interaction
	 * 
	 * @return
	 */
	public PrimitiveInteraction getRandom() {
		ImmutableCollection<PrimitiveInteraction> keys = interactions.values();

		PrimitiveInteraction i = Utils.getRandomElement(keys.asList());

		//Don't let elements escape
		return new PrimitiveInteraction(i.experiment, i.result, i.valence);
	}

}
