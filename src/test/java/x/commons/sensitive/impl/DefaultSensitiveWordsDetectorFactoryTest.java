package x.commons.sensitive.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import x.commons.sensitive.SensitiveWordsDetector;

public class DefaultSensitiveWordsDetectorFactoryTest {

	@Test
	public void test() throws IOException {
		InputStream in = this.getClass().getResourceAsStream("/sensitiveWords.txt");
		DefaultSensitiveWordsDetectorFactory sug = new DefaultSensitiveWordsDetectorFactory();
		SensitiveWordsDetector det = sug.getSensitiveWordsDetector(in, "UTF-8", null);
		
		boolean ret = det.containsSensitiveWords("包含敏感词1xx吗");
		assertTrue(ret);
		
		ret = det.containsSensitiveWords("包含敏感词1吗");
		assertTrue(!ret);
		
		ret = det.containsSensitiveWords("出售枪支弹药");
		assertTrue(ret);
		
		ret = det.containsSensitiveWords("防弹衣专卖");
		assertTrue(ret);
	}
}
