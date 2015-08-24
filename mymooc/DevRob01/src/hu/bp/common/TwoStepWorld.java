package hu.bp.common;

public class TwoStepWorld implements World {

	String previousExperiment = "";

	@Override
	public String getResult(String experiment) {
		String retVal = "r2";

		if (previousExperiment.equals(experiment)) {
			retVal = "r1";
		}

		previousExperiment =experiment;

		return retVal;
	}

}
