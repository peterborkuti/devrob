package hu.bp.common;

public abstract class AbstractProgram implements Program {

	@Override
	public void doProgram(int steps) {
		init();
		for (int step = 0; step < steps; step++) {
			doOneStep(step);
		}
		done();
	}

	protected abstract void doOneStep(int step);

	protected void done() {}

	protected void init() {};


}
