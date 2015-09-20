package hu.bp.selfprogramming.modules;

import static org.junit.Assert.*;
import hu.bp.common.TestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestSuite;

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
	public void testMatchRealWorld() {
		String strExp =
			"e1r1e2r1e2r2e2r2," +
			"e1r1e2r1e2r2," +
			"e2r1e2r2e2r2," +
			"e2r1e1r1e2r1e2r2," +
			"e2r1e2r2," +
			"e2r2e2r2";
		String interactions =
			"e1r1e2r1e2r2e2r2";

		List<Experiment> found =
			ExperimentUtils.match(interactions, TestUtils.cExpList(strExp));

		assertEquals(found.size(), 1);
		assertEquals("E2R2|E2R2", found.get(0).getKey());
	}

	@Test
	public void testMatch() {
		List<Experiment> exp;

		exp = ExperimentUtils.match(null, null);
		assertEquals("null list and experiment", 0, exp.size());

		exp = ExperimentUtils.match("", null);
		assertEquals("empty list and null experiment", 0, exp.size());

		exp = ExperimentUtils.match("", TestUtils.cExpList(""));
		assertEquals("empty list and empty experiment", 0, exp.size());

		exp = ExperimentUtils.match("e1r1e2r2", TestUtils.cExpList(""));
		assertEquals(0, exp.size());

		exp = ExperimentUtils.match(
			"e1r1e2r2", TestUtils.cExpList("e1r1e1r2,e1r1e1r1"));
		assertEquals(0, exp.size());

		exp = ExperimentUtils.match(
			"e1r2e1r1e1r1", TestUtils.cExpList("e1r1e1r2,e1r1e1r1e2r2"));
		assertEquals(3, exp.size());
		assertTrue(exp.contains(new Experiment("e1r1e1r1e2r2", pis)));
		assertTrue(exp.contains(new Experiment("e1r1e1r2", pis)));
		//There is another element whit the same key


	}

}
