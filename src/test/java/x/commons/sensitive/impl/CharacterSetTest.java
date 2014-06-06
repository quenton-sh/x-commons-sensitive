package x.commons.sensitive.impl;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class CharacterSetTest {

	@Test
	public void test() {
		Set<Character> set = new HashSet<Character>();
		set.add('#');
		set.add('%');
		
		CharacterSet cs = new CharacterSet(set);
		assertTrue(cs.contains('#'));
		assertTrue(cs.contains('%'));
		
		assertTrue(!cs.contains('a'));
		assertTrue(!cs.contains('!'));
		assertTrue(!cs.contains('@'));
	}
}
