package x.commons.sensitive.impl;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultSensitiveWordsDetectorTest {
	
	private static DefaultSensitiveWordsDetector sug;
	
	@BeforeClass
	public static void init() {
		Set<String> sensitiveWords = new HashSet<String>();
		sensitiveWords.add("abcde");
		sensitiveWords.add("acdf");
		sensitiveWords.add("bcf");
		sensitiveWords.add("bcg");
		sensitiveWords.add("cg");
		
		Set<Character> ignorableChars = new HashSet<Character>();
		ignorableChars.add(' ');
		ignorableChars.add('$');
		ignorableChars.add('#');
		
		WordsTreeBuilder builder = new WordsTreeBuilder();
		builder.addToTree(sensitiveWords);
		Map<Character, Object> tree = builder.getWordsTree();
		int minWordLen = builder.getMinWordLen();
		sug = new DefaultSensitiveWordsDetector(tree, minWordLen, ignorableChars); 
	}
	
	@AfterClass
	public static void shutdown() {
		sug = null;
	}

	@Test
	public void test() {
		// very short
		String ret = sug.detectSensitiveWords("单");
		assertTrue(ret == null);
		
		ret = sug.detectSensitiveWords(null);
		assertTrue(ret == null);
		
		// prefix
		ret = sug.detectSensitiveWords("acdfxxxyyy");
		assertEquals("acdf", ret);
		
		// middle
		ret = sug.detectSensitiveWords("哈哈abcde啊啊");
		assertEquals("abcde", ret);
		
		// suffix
		ret = sug.detectSensitiveWords("哈哈cg");
		assertEquals("cg", ret);
		
		// other cases:
		ret = sug.detectSensitiveWords("哈哈bcf啊啊");
		assertEquals("bcf", ret);
		
		ret = sug.detectSensitiveWords("哈哈bcg啊啊");
		assertEquals("bcg", ret);
		
		ret = sug.detectSensitiveWords("哈哈bc啊啊");
		assertTrue(ret == null);
		
		ret = sug.detectSensitiveWords("哈哈bch啊啊");
		assertTrue(ret == null);
		
		// ignorable chars
		ret = sug.detectSensitiveWords(" b$c#");
		assertTrue(ret == null);
		
		ret = sug.detectSensitiveWords(" b$c# g");
		assertEquals("bcg", ret);
	}
}
