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
		
		String ret = det.detectSensitiveWords("包含敏感词1xx吗");
		assertEquals("敏感词1xx", ret);
		
		ret = det.detectSensitiveWords("包含敏感词1吗");
		assertTrue(ret == null);
		
		ret = det.detectSensitiveWords("出售枪支弹药");
		assertEquals("枪支弹药", ret);
		
		ret = det.detectSensitiveWords("这里防弹衣专卖");
		assertEquals("防弹衣", ret);
	}
}
