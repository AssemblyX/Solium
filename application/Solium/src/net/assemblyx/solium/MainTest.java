package net.assemblyx.solium;

import org.junit.Test;

public class MainTest {

	@Test
	public void testSetJSON() {
		Main main = new Main();
		main.setJSON(this.getClass().getResourceAsStream("input.def"));
	}

}
