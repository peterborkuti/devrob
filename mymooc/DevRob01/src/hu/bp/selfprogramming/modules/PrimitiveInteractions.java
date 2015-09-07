package hu.bp.selfprogramming.modules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimitiveInteractions {
	private final Map<String, PrimitiveInteraction> interactions;

	public PrimitiveInteractions(List<PrimitiveInteraction> pis) {
		interactions = new HashMap<String, PrimitiveInteraction>();
		for (PrimitiveInteraction i : pis) {
			interactions.put(
				i.interaction,
				//Don't let caller to modify elements later
				new PrimitiveInteraction(i.experiment, i.result, i.valence));
		}
	}

	public PrimitiveInteraction get(String experiment, String result) {
		String key = experiment + result;
		PrimitiveInteraction i = interactions.get(key);

		//Don't let elements escape
		return new PrimitiveInteraction(experiment, result, i.valence);
	}

}
