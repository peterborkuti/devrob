package hu.bp.common;


public abstract class NStepWorld implements World {

	private final int step;
	private final ArrayQueue<String> previousExperiments;

	public NStepWorld(int step) {
		previousExperiments = new ArrayQueue<String>(step - 1);
		this.step = step;
	}

	public abstract String logic(
		String experiment, String defaultResult,
		ArrayQueue<String> previousExperiments);

	@Override
	public String getResult(String experiment) {
		assert(experiment != null && !"".equals(experiment));

		String result = "r1";

		if (previousExperiments.size() >= step -1) {
			result = logic(experiment, result, previousExperiments);
		}

		previousExperiments.add(experiment);

		return result;
	}

}
