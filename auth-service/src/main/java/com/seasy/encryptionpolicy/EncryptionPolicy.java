package com.seasy.encryptionpolicy;

public interface EncryptionPolicy {
	public EncryptionResult encrypt(String password);
	public EncryptionResult encrypt(String password, String saltValue);
}
