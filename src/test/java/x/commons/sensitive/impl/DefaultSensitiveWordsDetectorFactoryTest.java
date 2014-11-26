package x.commons.sensitive.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import x.commons.sensitive.SensitiveWordsDetector;

public class DefaultSensitiveWordsDetectorFactoryTest {

	@Test
	public void test() throws IOException {
		InputStream in1 = this.getClass().getResourceAsStream("/sensitiveWords.txt");
		InputStream in2 = this.getClass().getResourceAsStream("/sensitiveWords.txt");
		
		Set<String> ignorableWords = new HashSet<String>(Arrays.asList("忽略词"));
		
		DefaultSensitiveWordsDetectorFactory sug = new DefaultSensitiveWordsDetectorFactory();
		SensitiveWordsDetector det1 = sug.getSensitiveWordsDetector(in1, "UTF-8", ignorableWords, null);
		SensitiveWordsDetector det2 = sug.getSensitiveWordsDetector(in2, "UTF-8", null, null);
		
		String ret = det1.detectSensitiveWords("包含敏感词1xx吗");
		assertEquals("敏感词1xx", ret);
		ret = det2.detectSensitiveWords("包含敏感词1xx吗");
		assertEquals("敏感词1xx", ret);
		
		ret = det1.detectSensitiveWords("包含敏感词1吗");
		assertTrue(ret == null);
		ret = det2.detectSensitiveWords("包含敏感词1吗");
		assertTrue(ret == null);
		
		ret = det1.detectSensitiveWords("出售枪支弹药");
		assertEquals("枪支弹药", ret);
		ret = det2.detectSensitiveWords("出售枪支弹药");
		assertEquals("枪支弹药", ret);
		
		ret = det1.detectSensitiveWords("这里防弹衣专卖");
		assertEquals("防弹衣", ret);
		ret = det2.detectSensitiveWords("这里防弹衣专卖");
		assertEquals("防弹衣", ret);
		
		ret = det1.detectSensitiveWords("前缀-忽略词-后缀");
		assertTrue(ret == null);
		ret = det2.detectSensitiveWords("前缀-忽略词-后缀");
		assertEquals("忽略词", ret);
		
		ret = det1.detectSensitiveWords("前缀-# 注释词-后缀");
		assertTrue(ret == null);
		ret = det2.detectSensitiveWords("前缀-# 注释词-后缀");
		assertTrue(ret == null);
	}
}
