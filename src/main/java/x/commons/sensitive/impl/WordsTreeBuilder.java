package x.commons.sensitive.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordsTreeBuilder {
	
	private Map<Character, Object> rootMap = new HashMap<Character, Object>();
	private int minWordLen = -1; // 最短敏感词长度
	
	public WordsTreeBuilder addToTree(String word) {
		if (word == null) {
			return this;
		}
		Set<String> set = new HashSet<String>(1);
		set.add(word);
		return this.addToTree(set);
	}
	
	public WordsTreeBuilder addToTree(Set<String> words) {
		this.doAddToTree(words);
		return this;
	}
	
	public Map<Character, Object> getWordsTree() {
		return this.rootMap;
	}
	
	public int getMinWordLen() {
		return this.minWordLen;
	}
	
	@SuppressWarnings("unchecked")
	private void doAddToTree(Set<String> words) {
		for (String word : words) {
			if (word == null) {
				continue;
			}
			this.updateMinWordLen(word);
			char[] chararr = word.toCharArray();
			Map<Character, Object> currentMap = this.rootMap;
			for (int i = 0; i < chararr.length; i++) {
				char c = chararr[i];
				Object value = currentMap.get(c);
				if (value == null) {
					// 当前字符不在树中：
					if (i == chararr.length - 1) {
						// 已达单词结尾：当前字符入树，后接终止符
						value = new EndingObject();
						currentMap.put(c, value);
						break;
					} else {
						// 未到单词结尾：当前字符入树，后接容器用于存放后续字符
						value = new HashMap<Character, Object>();
						currentMap.put(c, value);
						currentMap = (Map<Character, Object>) value;
					}
				} else if (value instanceof EndingObject) {
					// 遇到终止符：说明树中已有一个敏感词，其值为当前单词的前缀，以该较短敏感词为优先，直接退出
					break;
				} else {
					// 当前字符已在树中：
					if (i == chararr.length - 1) {
						// 已达单词结尾：下一字符置为终止符
						currentMap.put(c, new EndingObject());
						break;
					} else {
						// 未到单词结尾：直接准备检查下一字符
						currentMap = (Map<Character, Object>) value;
					}
				}
			}
		}
	}
	
	private void updateMinWordLen(String word) {
		int len = word.length();
		if (this.minWordLen < 0) {
			this.minWordLen = len;
		} else if (this.minWordLen > len) {
			this.minWordLen = len;
		}
	}
}
