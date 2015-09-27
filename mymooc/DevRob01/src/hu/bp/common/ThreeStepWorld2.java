package hu.bp.common;

public class ThreeStepWorld2 extends NStepWorld {

	public ThreeStepWorld2() {
		super(3);
	}

	@Override
	public String logic(
			String experiment, String result,
			ArrayQueue<String> previousExperiments) {

		if (!experiment.equals(previousExperiments.get(0)) ||
			experiment.equals(previousExperiments.get(1))) {

			result = "r2";
		}

		return result;
	}

}
