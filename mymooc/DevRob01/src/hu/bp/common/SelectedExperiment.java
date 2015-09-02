package hu.bp.common;

public class SelectedExperiment {
	public final String experiment;
	public final Float proclivity;
	public final Integer occurences;

	public SelectedExperiment(String experiment, Float proclivity,
			Integer occurences) {
		super();
		this.experiment = experiment;
		this.proclivity = proclivity;
		this.occurences = occurences;
	}

}
