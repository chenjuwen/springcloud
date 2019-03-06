package com.seasy.encryptionpolicy;

import java.security.MessageDigest;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Hex;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import com.seasy.common.ShiroConstants;
import com.seasy.utils.StringUtil;

public class DefaultEncryptionPolicy implements EncryptionPolicy {
	/**
	 * 加密明文密码
	 * @param password 明文密码
	 */
	@Override
	public EncryptionResult encrypt(String password) {
		if(StringUtil.isEmpty(password)){
			password = ShiroConstants.DEFAULT_PASSWORD;
		}
		
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[ShiroConstants.DIGESTS_SALT_SIZE];
	    random.nextBytes(salt);
	    
		String saltValue = Hex.encodeHexString(salt);
		
		return encrypt(password, saltValue);
	}

	/**
	 * 加密明文密码
	 * @param password 明文密码
	 * @param saltValue 盐值
	 */
	@Override
	public EncryptionResult encrypt(String password, String saltValue) {
		try{
			byte[] salt = Hex.decodeHex(saltValue);
			byte[] passwordByte = sha1(password.getBytes(), ShiroConstants.HASH_ALGORITHM, salt, ShiroConstants.DIGESTS_SHA1_ITERATIONS);
			String passwordStr = Hex.encodeHexString(passwordByte);
			
			EncryptionResult result = new EncryptionResult(saltValue, passwordStr);
			return result;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return new EncryptionResult(saltValue, password);
	}
	
	/**
	 * sha1加密
	 * 
	 * @param input 密码内容的字节数组
	 * @param algorithm 算法 SHA-1
	 * @param salt 盐值
	 * @param iterations 迭代次数
	 */
	private byte[] sha1(byte[] input, String algorithm, byte[] salt, int iterations){
	    try {
	      MessageDigest digest = MessageDigest.getInstance(algorithm);
	      if (salt != null) {
	    	  digest.update(salt);
	      }
	      
	      byte[] result = digest.digest(input);
	      for (int i = 1; i < iterations; i++) {
	    	  digest.reset();
	    	  result = digest.digest(result);
	      }
	      return result;
	    } catch (Exception ex) {
	      	ex.printStackTrace();
	      	return null;
	    }
	}
	
	/**
	 * 凭证匹配器
	 */
	@Override
	public CredentialsMatcher getCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ShiroConstants.HASH_ALGORITHM);
		matcher.setHashIterations(ShiroConstants.DIGESTS_SHA1_ITERATIONS);
		return matcher;
	}

}
