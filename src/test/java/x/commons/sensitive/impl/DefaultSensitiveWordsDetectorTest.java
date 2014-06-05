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
		boolean ret = sug.containsSensitiveWords("单");
		assertTrue(!ret);
		
		ret = sug.containsSensitiveWords(null);
		assertTrue(!ret);
		
		// prefix
		ret = sug.containsSensitiveWords("acdfxxxyyy");
		assertTrue(ret);
		
		// middle
		ret = sug.containsSensitiveWords("哈哈abcde啊啊");
		assertTrue(ret);
		
		// suffix
		ret = sug.containsSensitiveWords("哈哈cg");
		assertTrue(ret);
		
		// other cases:
		ret = sug.containsSensitiveWords("哈哈bcf啊啊");
		assertTrue(ret);
		
		ret = sug.containsSensitiveWords("哈哈bcg啊啊");
		assertTrue(ret);
		
		ret = sug.containsSensitiveWords("哈哈bc啊啊");
		assertTrue(!ret);
		
		ret = sug.containsSensitiveWords("哈哈bch啊啊");
		assertTrue(!ret);
		
		// ignorable chars
		ret = sug.containsSensitiveWords(" b$c#");
		assertTrue(!ret);
		
		ret = sug.containsSensitiveWords(" b$c# g");
		assertTrue(ret);
	}
}
