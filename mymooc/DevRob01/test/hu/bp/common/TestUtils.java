package hu.bp.common;

import hu.bp.selfprogramming.modules.Experiment;
import hu.bp.selfprogramming.modules.PrimitiveInteraction;
import hu.bp.selfprogramming.modules.PrimitiveInteractions;

import java.util.HashMap;
import java.util.Map;

public class TestUtils {
	public static String e1 = "e1";
	public static String e2 = "e2";
	public static String r1 = "r1";
	public static String r2 = "r2";

	public static String[] pos = {e1 + r1, e2 + r1};
	public static String[] neg = {e1 + r2, e2 + r2};

	public static PrimitiveInteractions pis = new PrimitiveInteractions(pos, neg);

	public static PrimitiveInteraction p11 = new PrimitiveInteraction(pos[0], 1);
	public static PrimitiveInteraction p21 = new PrimitiveInteraction(pos[1], 1);
	public static PrimitiveInteraction p12 = new PrimitiveInteraction(neg[0], -1);
	public static PrimitiveInteraction p22 = new PrimitiveInteraction(neg[1], -1);

	public static Map<String, Experiment> cExpList(String s) {
		String[] strExps = s.split(",");

		Map<String, Experiment> m = new HashMap<String, Experiment>();

		for (String exp: strExps) {
			m.put(exp, new Experiment(exp, pis));
		}

		return m;
	}


}
