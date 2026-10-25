package com.service.auth.config;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

/**
 * Limits wrong guesses on short verification codes (OTP, change password, change email/mobile).
 * After MAX_ATTEMPTS wrong guesses the caller must invalidate the stored code, forcing a new code to be requested,
 * so a 6 digit code cannot be brute forced.
 */
@Component
public class CodeAttemptLimiter {

	public static final int MAX_ATTEMPTS = 5;

	public static final String OTP = "OTP_";
	public static final String CHANGE_PASS = "CHANGEPASS_";
	public static final String CHANGE_EMAIL = "CHANGEEMAIL_";
	public static final String CHANGE_MOBILE = "CHANGEMOBILE_";

	private final ConcurrentHashMap<String, Integer> failures = new ConcurrentHashMap<>();

	/**
	 * Registers a wrong guess.
	 * @return true if the maximum is reached and the stored code must be invalidated
	 */
	public boolean registerFailure(String type, String username) {
		String key = type + username;
		int count = failures.merge(key, 1, Integer::sum);
		if (count >= MAX_ATTEMPTS) {
			failures.remove(key);
			return true;
		}
		return false;
	}

	public void reset(String type, String username) {
		failures.remove(type + username);
	}
}
