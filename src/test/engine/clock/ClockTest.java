package test.engine.clock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import engine.clock.Clock;

public class ClockTest {

	@Test
	public void test() {
		Clock c = Clock.getInstance();
		assertEquals(0, c.elapsedMillis());
		assertFalse(c.running());
		c.start();
		assertTrue(c.running());
		
		pause(2000);
		assertEquals(2000, c.elapsedMillis(), 10);
		
		c.pause();
		pause(2000);
		assertEquals(2000, c.elapsedMillis(), 10);
		
		c.start();
		pause(2000);
		assertEquals(4000, c.elapsedMillis(), 10);
		
		c.stop();
		assertEquals(0, c.elapsedMillis(), 10);
	}
	
	
	private void pause(long milli) {
		try {
			Thread.sleep(milli);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
