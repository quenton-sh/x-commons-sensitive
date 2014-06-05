package x.commons.sensitive;

public interface SensitiveWordsDetector {

	/**
	 * 检查给定文本中是否包含敏感词 
	 * @param text
	 * @return true 包含敏感词；false 不包含敏感词
	 */
	public boolean containsSensitiveWords(String text);
	
}
