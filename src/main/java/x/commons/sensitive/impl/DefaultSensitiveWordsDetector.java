package x.commons.sensitive.impl;

import java.util.Map;
import java.util.Set;

import x.commons.sensitive.SensitiveWordsDetector;

public class DefaultSensitiveWordsDetector implements SensitiveWordsDetector {
	
	private final Map<Character, Object> wordsTree;
	private final int minWordLen;
	private final CharacterSet ignorableChars;

	public DefaultSensitiveWordsDetector(Map<Character, Object> wordsTree, int minWordLen, Set<Character> ignorableChars) {
		this.wordsTree = wordsTree;
		this.minWordLen = minWordLen;
		this.ignorableChars = ignorableChars == null ? null : new CharacterSet(ignorableChars);
	}

	@SuppressWarnings("unchecked")
	private boolean matchFromIndex(final int index, final char[] chararr, final Map<Character, Object> node, final StringBuilder matchedStr) {
		if (index > chararr.length - 1) {
			return false;
		}
		char c = chararr[index];
		if (this.ignorableChars != null && this.ignorableChars.contains(c)) {
			// 递归
			return matchFromIndex(index + 1, chararr, node, matchedStr);
		} else {
			Object value = node.get(c);
			if (value == null) {
				return false;
			} else if (value instanceof EndingObject) {
				matchedStr.append(c);
				return true;
			} else {
				// 递归
				matchedStr.append(c);
				Map<Character, Object> nextNode = (Map<Character, Object>) value;
				return matchFromIndex(index + 1, chararr, nextNode, matchedStr);
			}
		}
	}

	@Override
	public String detectSensitiveWords(String text) {
		if (text == null) {
			return null;
		}
		text = text.trim();
		if (text.length() < minWordLen) {
			return null;
		}
		final char[] chararr = text.toCharArray();
		final StringBuilder matchedStr = new StringBuilder();
		for (int i = 0; i < chararr.length; i++) {
			matchedStr.setLength(0); // reset
			if (matchFromIndex(i, chararr, wordsTree, matchedStr)) {
				return matchedStr.toString();
			}
		}
		return null;
	}

}
