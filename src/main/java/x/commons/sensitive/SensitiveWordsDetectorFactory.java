package x.commons.sensitive;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public interface SensitiveWordsDetectorFactory {

	public SensitiveWordsDetector getSensitiveWordsDetector(
			Set<String> sensitiveWords, Set<String> ignorableWords, Set<Character> ignorableChars);

	public SensitiveWordsDetector getSensitiveWordsDetector(InputStream in,
			String encoding, Set<String> ignorableWords, Set<Character> ignorableChars) throws IOException;
}
