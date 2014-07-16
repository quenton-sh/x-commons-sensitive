package x.commons.sensitive;

public interface SensitiveWordsDetector {

	/**
	 * 检测给定文本中的敏感词 
	 * @param text
	 * @return 包含的敏感词或null
	 */
	public String detectSensitiveWords(String text);
	
}
