package x.commons.sensitive.impl;

import java.util.Map;
import java.util.Set;

import x.commons.sensitive.SensitiveWordsDetector;

public class DefaultSensitiveWordsDetector implements SensitiveWordsDetector {
	
	private final Map<Character, Object> wordsTree;
	private final int minWordLen;
	private final Set<Character> ignorableChars;

	public DefaultSensitiveWordsDetector(Map<Character, Object> wordsTree, int minWordLen, Set<Character> ignorableChars) {
		this.wordsTree = wordsTree;
		this.minWordLen = minWordLen;
		this.ignorableChars = ignorableChars;
	}

	@SuppressWarnings("unchecked")
	private boolean matchFromIndex(int index, char[] chararr, Map<Character, Object> node) {
		if (index > chararr.length - 1) {
			return false;
		}
		char c = chararr[index];
		if (this.ignorableChars != null && this.ignorableChars.contains(c)) {
			// 递归
			return matchFromIndex(++index, chararr, node);
		} else {
			Object value = node.get(c);
			if (value == null) {
				return false;
			} else if (value instanceof EndingObject) {
				return true;
			} else {
				// 递归
				Map<Character, Object> nextNode = (Map<Character, Object>) value;
				return matchFromIndex(++index, chararr, nextNode);
			}
		}
	}

	@Override
	public boolean containsSensitiveWords(String text) {
		if (text == null) {
			return false;
		}
		text = text.trim();
		if (text.length() < minWordLen) {
			return false;
		}
		char[] chararr = text.toCharArray();
		for (int i = 0; i < chararr.length; i++) {
			if (matchFromIndex(i, chararr, wordsTree)) {
				return true;
			}
		}
		return false;
	}

}
