package hu.bp.selfprogramming.modules;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
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
		Experiment exp1, exp2, exp3;
		List<Experiment> exp;
		Experience e;
		Map<String, Experiment> em = new HashMap<String, Experiment>();

		exp = ExperimentUtils.match(null, null);
		assertEquals("null list and experiment", 0, exp.size());

		exp = ExperimentUtils.match("", null);
		assertEquals("empty list and null experiment", 0, exp.size());

		e = new Experience(em);
		exp = ExperimentUtils.match("", e.getExperiments());
		assertEquals("empty list and empty experiment", 0, exp.size());

		exp = ExperimentUtils.match("e1r1e2r2", e.getExperiments());
		assertEquals(0, exp.size());

		exp1 = new Experiment(pis.createList("e1r1e1r2e1r1e1r1"));
		em.put(exp1.key, exp1);
		e = new Experience(em);
		exp = ExperimentUtils.match("e1r1e2r2", e.getExperiments());
		assertEquals(0, exp.size());

		exp = ExperimentUtils.match("e1r2e1r1e1r1", e.getExperiments());
		assertEquals(1, exp.size());
		assertTrue(exp.contains(exp1));
		assertEquals(1, exp.get(0).getMatch());

		exp = ExperimentUtils.match("e1r2e1r1e1r2", e.getExperiments());
		assertEquals(1, exp.size());
		assertTrue(exp.contains(exp1));
		assertEquals(2, exp.get(0).getMatch());

		exp = ExperimentUtils.match("e1r2e1r1e1r2e1r1", e.getExperiments());
		assertEquals(1, exp.size());
		assertTrue(exp.contains(exp1));
		assertEquals(3, exp.get(0).getMatch());

		//total fit, not good for predicting interactions
		exp = ExperimentUtils.match("e1r2e1r1e1r2e1r1e1r1", e.getExperiments());
		assertEquals(0, exp.size());

		/*
		Experiment exp2 = new Experiment(pis.createList("e1r2e1r2e1r2"));
		Experiment exp3 = new Experiment(pis.createList("e2r2e1r1e1r2e1r1e1r1e2r1"));
	
		Map<String, Experiment> em = new HashMap<String, Experiment>();
		em.put(exp1.key, exp1);
		em.put(exp2.key, exp2);
		em.put(exp3.key, exp3);

		Experience e = new Experience(em);

		List<Experiment> exp =
			ExperimentUtils.match("e2r2e2r2e1r1e1r2", e.getExperiments());

		*/
		
	}

}
