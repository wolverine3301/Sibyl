package particles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DoubleParticleTest {
	DoubleParticle p;
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	@Test
	void testDoubleParticle() {
		p = new DoubleParticle(3.14);
		assertEquals(3.14,p.value);
	}

	@Test
	void testGetValue() {
		assertEquals(3.14, p.getValue());
	}
	@Test
	void testSetValue() {
		p.setValue(0.000056);
		assertEquals(0.000056,p.value);
		
	}

	@Test
	void testDeepCopy() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testGetType() {
		assertEquals('d',p.type);
	}

	@Test
	void testResolveTypeString() {
		Particle d = Particle.resolveType("4.5");
		assertEquals('d',d.type); //test double from string
		Particle s = Particle.resolveType("4");
		assertEquals('i',s.type);
	}

	@Test
	void testResolveTypeObject() {
		fail();
	}

}
