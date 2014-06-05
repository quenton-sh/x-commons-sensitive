package x.commons.sensitive.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import x.commons.sensitive.SensitiveWordsDetector;
import x.commons.sensitive.SensitiveWordsDetectorFactory;

public class DefaultSensitiveWordsDetectorFactory implements SensitiveWordsDetectorFactory {

	@Override
	public SensitiveWordsDetector getSensitiveWordsDetector(Set<String> sensitiveWords, Set<Character> ignorableChars) {
		return this.buildDefaultSensitiveWordsDetector(sensitiveWords, ignorableChars);
	}

	@Override
	public SensitiveWordsDetector getSensitiveWordsDetector(InputStream in, String encoding, Set<Character> ignorableChars)
			throws IOException {
		Set<String> sensitiveWords = this.readLines(in, encoding);
		return this.buildDefaultSensitiveWordsDetector(sensitiveWords, ignorableChars);
	}
	
	private Set<String> readLines(InputStream in, String encoding) throws IOException {
		BufferedReader br = null;
		try {
			Set<String> set = new HashSet<String>();
			br = new BufferedReader(new InputStreamReader(in, encoding));
			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.length() == 0) {
					continue;
				}
				set.add(line);
			}
			return set;
		} finally {
			IOUtils.closeQuietly(br);
		}
	}
	
	private SensitiveWordsDetector buildDefaultSensitiveWordsDetector(Set<String> sensitiveWords, Set<Character> ignorableChars) {
		WordsTreeBuilder builder = new WordsTreeBuilder();
		builder.addToTree(sensitiveWords);
		return new DefaultSensitiveWordsDetector(builder.getWordsTree(), builder.getMinWordLen(), ignorableChars);
	}

}
