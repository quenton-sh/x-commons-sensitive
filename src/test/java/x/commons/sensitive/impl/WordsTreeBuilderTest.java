package x.commons.sensitive.impl;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class WordsTreeBuilderTest {

	@Test
	public void test() {
		WordsTreeBuilder sug = new WordsTreeBuilder();
		Set<String> set = new HashSet<String>();
		set.add("abcde");
		set.add("acdf");
		set.add("bcf");
		set.add("bcg");
		set.add("cg");
		sug.addToTree(set);
		
		assertTrue(sug.getMinWordLen() == 2);
		
		Map<Character, Object> tree = sug.getWordsTree();
		assertTrue(tree.size() == 3);
		System.out.println(tree);
		
		sug.addToTree("abcd");
		
		assertTrue(sug.getMinWordLen() == 2);
		tree = sug.getWordsTree();
		assertTrue(tree.size() == 3);
		System.out.println(tree);
	}
}
