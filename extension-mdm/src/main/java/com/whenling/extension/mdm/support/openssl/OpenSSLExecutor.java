package com.whenling.extension.mdm.support.openssl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.text.StrBuilder;

import com.google.common.base.Joiner;

public class OpenSSLExecutor {

	private File baseDir;

	public OpenSSLExecutor(File baseDir) {
		this.baseDir = baseDir;
	}

	public int exec(String... args) {
		StrBuilder builder = new StrBuilder();

		builder.append("openssl");

		if (args != null && args.length > 0) {
			builder.append(" ").append(Joiner.on(" ").join(args));
		}

		try {
			return Runtime.getRuntime().exec(builder.toString(), (String[]) null, baseDir).waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public File getBaseDir() {
		return baseDir;
	}

}
