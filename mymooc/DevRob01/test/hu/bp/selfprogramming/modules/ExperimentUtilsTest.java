package hu.bp.selfprogramming.modules;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static hu.bp.common.TestUtils.*;

public class ExperimentUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEnact() {
		fail("Not yet implemented");
	}

	@Test
	public void testEnactPrimitiveInteractionWorldPrimitiveInteractionPrimitiveInteractionsListOfPrimitiveInteractionListOfExperiment() {
		fail("Not yet implemented");
	}

	@Test
	public void testEnactPrimitiveInteractionWorldPrimitiveInteractionPrimitiveInteractionsListOfExperiment() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSubExperiments() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFailedSubExperiments() {
		fail("Not yet implemented");
	}

	@Test
	public void testMatch() {
		Experiment exp1 = new Experiment(pis.createList("e1r1e1r1"));
		Experiment exp2 = new Experiment(pis.createList("e1r1e1r2e1r2"));
		Experiment exp3 = new Experiment(pis.createList("e1r1e1r1e2r1"));
	
		Map<String, Experiment> em = new HashMap<String, Experiment>();
		em.put(exp1.key, exp1);
		em.put(exp2.key, exp2);
		em.put(exp3.key, exp3);

		Experience e = new Experience(em);

		ExperimentUtils.match("e2r2e2r2e1r1e1r2", e.getExperiments());
	}

}
