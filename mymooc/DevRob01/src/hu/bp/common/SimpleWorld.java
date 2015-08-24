package hu.bp.common;

public class SimpleWorld implements World {

	@Override
	public String getResult(String experiment) {
		if ("e1".equals(experiment)) {
			return "r1";
		}

		return "r2";
	}

}
