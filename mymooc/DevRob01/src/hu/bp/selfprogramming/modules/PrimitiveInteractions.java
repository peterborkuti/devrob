package hu.bp.selfprogramming.modules;

import hu.bp.annotation.Immutable;
import hu.bp.common.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

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

	public List<PrimitiveInteraction> createList(String interactions) {
		List<String> ints =
			Utils.splitBy(interactions, PrimitiveInteraction.LENGTH);

		List<PrimitiveInteraction> pis = new ArrayList<PrimitiveInteraction>();

		for (String i: ints) {
			pis.add(get(i));
		}

		return pis;
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
	 * If parameter is not null, returns with another random
	 * interaction than the experiment's first not matched
	 * interaction
	 * 
	 * @return
	 */
	public PrimitiveInteraction getRandom(Experiment e) {
		ImmutableCollection<PrimitiveInteraction> keys = interactions.values();

		List<PrimitiveInteraction> mKeys = Lists.newArrayList();
		mKeys.addAll(keys);

		if (e != null) {
			//the key of the first not matched prim. int.
			String notMatchedKey = e.getAfterMatchedString();
			String firstNotMatchedKey = notMatchedKey.substring(0, PrimitiveInteraction.LENGTH);
			mKeys.remove(get(firstNotMatchedKey));
		}

		PrimitiveInteraction i = Utils.getRandomElement(mKeys);

		//Don't let elements escape
		return new PrimitiveInteraction(i.experiment, i.result, i.valence);
	}

}
