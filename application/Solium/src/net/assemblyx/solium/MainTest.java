package net.assemblyx.solium;

import org.junit.Test;

public class MainTest {

	@Test
	public void testSetJSON() {
		Main main = new Main();
		main.loopInput(this.getClass().getResourceAsStream("input.def"));
	}

}
