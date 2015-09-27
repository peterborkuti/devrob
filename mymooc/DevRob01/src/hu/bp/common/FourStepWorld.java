package hu.bp.common;

public class FourStepWorld extends NStepWorld {

	public FourStepWorld() {
		super(4);
	}

	@Override
	public String logic(
			String experiment, String result,
			ArrayQueue<String> previousExperiments) {
		result = "r2";

		if (!experiment.equals(previousExperiments.get(0)) &&
			experiment.equals(previousExperiments.get(1)) &&
			experiment.equals(previousExperiments.get(2))) {

			result = "r1";
		}

		return result;
	}

}
