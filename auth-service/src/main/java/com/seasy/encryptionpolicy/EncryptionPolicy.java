package com.seasy.encryptionpolicy;

import org.apache.shiro.authc.credential.CredentialsMatcher;

public interface EncryptionPolicy {
	public EncryptionResult encrypt(String password);
	public EncryptionResult encrypt(String password, String saltValue);
	public CredentialsMatcher getCredentialsMatcher();
}
