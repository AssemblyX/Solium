package net.assemblyx.solium;

import static org.junit.Assert.*;

import org.junit.Test;

public class MainTest {

	@Test
	public void test() {
		Main main = new Main();
		main.runapp(this.getClass().getResourceAsStream("input.def"));
	}

}
