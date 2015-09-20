package hu.bp.selfprogramming.modules;

import static hu.bp.common.TestUtils.e1;
import static hu.bp.common.TestUtils.e2;
import static hu.bp.common.TestUtils.neg;
import static hu.bp.common.TestUtils.pis;
import static hu.bp.common.TestUtils.pos;
import static hu.bp.common.TestUtils.r1;
import static hu.bp.common.TestUtils.r2;
import static hu.bp.common.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;



public class PrimitiveInteractionsTest {

	@Before
	public void setUp() throws Exception {
	}

	private void testContains() {
		assertEquals(4, pis.interactions.size());

		assertTrue(
			pis.interactions.containsValue(p11));
		assertTrue(
			pis.interactions.containsValue(p21));
		assertTrue(
			pis.interactions.containsValue(p22));
		assertTrue(
			pis.interactions.containsValue(p12));

		assertTrue(pis.interactions.containsKey(pos[0]));
		assertTrue(pis.interactions.containsKey(neg[0]));
		assertTrue(pis.interactions.containsKey(pos[1]));
		assertTrue(pis.interactions.containsKey(neg[1]));

	}

	@Test
	public void testConstructorWithArray() {
		testContains();
	}

	@Test
	public void testConstructorWithList() {
		List<PrimitiveInteraction> l = new ArrayList<PrimitiveInteraction>();
		l.add(p11);
		l.add(p12);
		l.add(p21);
		l.add(p22);

		pis = new PrimitiveInteractions(l);

		testContains();
	}

	@Test
	public void testGetStringString() {
		assertEquals(p11, pis.get(e1,r1));
		assertEquals(p21, pis.get(e2, r1));
		assertEquals(p12, pis.get(e1, r2));
		assertEquals(p22, pis.get(e2, r2));
	}

	@Test
	public void testGetString() {
		assertEquals(p11, pis.get(pos[0]));
		assertEquals(p21, pis.get(pos[1]));
		assertEquals(p12, pis.get(neg[0]));
		assertEquals(p22, pis.get(neg[1]));
	}

	@Test
	public void testGetRandom() {
		PrimitiveInteraction p = pis.getRandom(null);

		assertNotNull(p);
		assertTrue(pis.interactions.containsValue(pis.getRandom(null)));

		List<PrimitiveInteraction> l = new ArrayList<PrimitiveInteraction>();
		l.add(p11);
		l.add(p12);

		PrimitiveInteractions mpis = new PrimitiveInteractions(l);

		Experiment e = new Experiment("e1r1e1r2", mpis);
		e.setMatch(1);
		assertEquals("E1R1|E1R2", e.getKey());
		assertEquals("e1r2", e.getAfterMatchedString());
		assertEquals(p11.interaction, mpis.getRandom(e).interaction);

		e = new Experiment("e1r2e1r1", mpis);
		e.setMatch(1);
		assertEquals("E1R2|E1R1", e.getKey());
		assertEquals("e1r1", e.getAfterMatchedString());
		assertEquals(p12.interaction, mpis.getRandom(e).interaction);

		e = new Experiment("e1r1e1r1", mpis);
		e.setMatch(1);
		assertEquals("E1R1|E1R1", e.getKey());
		assertEquals("e1r1", e.getAfterMatchedString());
		assertEquals(p12.interaction, mpis.getRandom(e).interaction);
	}

}
