package hu.bp.common;

public class FourStepWorld extends NStepWorld {

	public FourStepWorld() {
		super(4);
	}

	@Override
	public String logic(
			String experiment, String result,
			ArrayQueue<String> previousExperiments) {

		if (!experiment.equals(previousExperiments.get(0)) &&
			experiment.equals(previousExperiments.get(1)) &&
			experiment.equals(previousExperiments.get(2))) {

			result = "r2";
		}

		return result;
	}

}
