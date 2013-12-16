package com.giangnt.webapp.util;

import java.util.Random;

import org.springframework.security.crypto.codec.Base64;

public class NumberUtil {

	public static String encoded(String string) {
		byte[] bytesEncoded = Base64.encode(string.getBytes());
		return new String(bytesEncoded);
	}

	public static String decoded(String string) {
		byte[] bytesDecoded = Base64.decode(string.getBytes());

		return new String(bytesDecoded);
	}

	public NumberUtil() {
		super();
	}

	public static String randomString(int len) {
		final String AB = "01mnopqr23efgh9ABCDEFQ45678RSTUlstWXYZabcdGHIJKLMNOPijkuvwVxyz";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}
}
