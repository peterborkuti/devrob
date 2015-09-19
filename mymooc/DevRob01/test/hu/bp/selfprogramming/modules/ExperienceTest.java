package hu.bp.selfprogramming.modules;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static hu.bp.common.TestUtils.*;

public class ExperienceTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testExperience() {
		fail("Not yet implemented");
	}

	@Test
	public void testExperienceListOfPrimitiveInteractionString() {
		fail("Not yet implemented");
	}

	@Test
	public void testExperienceMapOfStringExperiment() {
		fail("Not yet implemented");
	}

	@Test
	public void testExperienceMapOfStringExperimentString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBestExperiment() {
		String pastInteractions = "e1r1e2r2e1r1e2r1";
		Experiment e1 = new Experiment("e1r1e2r2", pis);
		Experiment e2 = new Experiment("e1r1e2r1e2r2", pis);
		Map<String, Experiment> store = new HashMap<String, Experiment>();
		store.put(e1.key, e1);
		store.put(e2.key, e2);

		Experience e = new Experience(store, pastInteractions);

		Experiment best = e.getBestExperiment(pis);

		assertEquals(best, e2);
		assertEquals(2, best.getMatch());
	}

	@Test
	public void testGetLast() {
		Experience e = new Experience();
		assertNull(e.getLast(pis));
		assertEquals(0, e.getInteractions().length());

		Experiment t1 = new Experiment(p11);
		e.learn(t1, null);
		assertEquals(e.getLast(pis), p11);
		assertEquals(PrimitiveInteraction.LENGTH, e.getInteractions().length());

		Experiment t2 = new Experiment(p12);
		e.learn(t2, null);
		assertEquals(e.getLast(pis), p12);
		assertEquals(2 * PrimitiveInteraction.LENGTH, e.getInteractions().length());

		Experiment t3 = new Experiment(p21);
		e.learn(t3, null);
		assertEquals(e.getLast(pis), p21);
		assertEquals(3 * PrimitiveInteraction.LENGTH, e.getInteractions().length());

	}

	@Test
	public void testLearnWithoutPis() {
		Experience e = new Experience();

		assertEquals(0, e.getExperiments().size());
		assertEquals(0, e.getInteractions().length());

		Experiment enacted = new Experiment(p11);
		e.learn(enacted, null);

		assertEquals(0, e.getExperiments().size());
		assertEquals(PrimitiveInteraction.LENGTH, e.getInteractions().length());
	}

	@Test
	public void testLearnWithPis() {
		Experience e = new Experience();

		Experiment enacted = new Experiment(p11);
		e.learn(enacted, pis);
		assertEquals(0, e.getExperiments().size());
		assertEquals(PrimitiveInteraction.LENGTH, e.getInteractions().length());
		assertEquals(p11.interaction, e.getInteractions());

		e.learn(enacted, pis);
		assertEquals(1, e.getExperiments().size());
		assertTrue(
			e.getExperiments().containsValue(
				new Experiment(p11.interaction + p11.interaction, pis)));
		assertEquals(p11.interaction + p11.interaction, e.getInteractions());

		List<Experiment> t1 = new ArrayList<Experiment>();
		Experiment newE = new Experiment(p11, p11);
		t1.add(newE);

		e.learn(enacted, pis);

		System.out.println("2." + e.toString());

		e.learn(enacted, pis);
		System.out.println("3." + e.toString());
	}

}
