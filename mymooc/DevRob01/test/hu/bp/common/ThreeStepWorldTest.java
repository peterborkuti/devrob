package hu.bp.common;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ThreeStepWorldTest {

	World w;
	@Before
	public void setUp() throws Exception {
		w = new ThreeStepWorld();
	}

	@Test
	public void testAllR1() {
		assertEquals(w.getResult("r1"), "r1");
		assertEquals(w.getResult("r1"), "r1");
		assertEquals(w.getResult("r1"), "r1");
		assertEquals(w.getResult("r1"), "r1");
		assertEquals(w.getResult("r1"), "r1");
		assertEquals(w.getResult("r1"), "r1");
		assertEquals(w.getResult("r1"), "r1");
	}

	@Test
	public void testAllR2() {
		assertEquals(w.getResult("r2"), "r1");
		assertEquals(w.getResult("r2"), "r1");
		assertEquals(w.getResult("r2"), "r1");
		assertEquals(w.getResult("r2"), "r1");
		assertEquals(w.getResult("r2"), "r1");
		assertEquals(w.getResult("r2"), "r1");
		assertEquals(w.getResult("r2"), "r1");
	}

	@Test
	public void testAlternate() {
		assertEquals(w.getResult("r2"), "r1");
		assertEquals(w.getResult("r1"), "r1");
		assertEquals(w.getResult("r2"), "r1");
		assertEquals(w.getResult("r1"), "r1");
		assertEquals(w.getResult("r2"), "r1");
		assertEquals(w.getResult("r1"), "r1");
		assertEquals(w.getResult("r2"), "r1");
	}

	@Test
	public void testR1R2R2() {
		assertEquals(w.getResult("r1"), "r1");
		assertEquals(w.getResult("r2"), "r1");
		assertEquals(w.getResult("r2"), "r2");
		assertEquals(w.getResult("r1"), "r1");
		assertEquals(w.getResult("r2"), "r1");
		assertEquals(w.getResult("r2"), "r2");
		assertEquals(w.getResult("r1"), "r1");
	}

	@Test
	public void testR2R1R1() {
		assertEquals(w.getResult("r2"), "r1");
		assertEquals(w.getResult("r1"), "r1");
		assertEquals(w.getResult("r1"), "r2");
		assertEquals(w.getResult("r2"), "r1");
		assertEquals(w.getResult("r1"), "r1");
		assertEquals(w.getResult("r1"), "r2");
		assertEquals(w.getResult("r2"), "r1");
	}

	@Test
	public void testBest() {
		assertEquals(w.getResult("r1"), "r1");
		assertEquals(w.getResult("r2"), "r1");
		assertEquals(w.getResult("r2"), "r2");
		assertEquals(w.getResult("r1"), "r1");
		assertEquals(w.getResult("r1"), "r2");
		assertEquals(w.getResult("r2"), "r1");
		assertEquals(w.getResult("r2"), "r2");
	}
}
