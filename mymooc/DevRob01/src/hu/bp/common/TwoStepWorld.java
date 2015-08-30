package hu.bp.common;

public class TwoStepWorld implements World {

	String previousExperiment = "";

	@Override
	public String getResult(String experiment) {
		String retVal = "r1";

		if (previousExperiment.equals(experiment)) {
			retVal = "r2";
		}

		previousExperiment =experiment;

		return retVal;
	}

}
