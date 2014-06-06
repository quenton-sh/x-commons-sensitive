package x.commons.sensitive.impl;

import java.util.Set;

public class CharacterSet {

	private final boolean[] arr = new boolean[65535];
	
	public CharacterSet(Set<Character> chars) {
		for (char c : chars) {
			int code = (int) c;
			if (c < 65535) {
				arr[code] = true;
			}
		}
	}
	
	public boolean contains(char c) {
		int code = (int) c;
		if (code < 65535) {
			return arr[code];
		} else {
			return false;
		}
	}
}
