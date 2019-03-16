package com.seasy.utils;

import java.util.Date;

import org.apache.commons.codec.binary.Base64;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JWTUtil {
	public static final String SECRET_KEY = "abcd1234"; //秘钥
	public static final long TOKEN_EXPIRE_TIME = 5 * 60 * 1000; //token过期时间
	public static final long REFRESH_TOKEN_EXPIRE_TIME = 10 * 60 * 1000; //refreshToken过期时间
	private static final String ISSUER = "seasy"; //签发人

    /**
     * 生成签名
     */
	public static String generateToken(String username){
        Date now = new Date();
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY); //算法
        
		String token = JWT.create()
			.withIssuer(ISSUER) //签发人
			.withIssuedAt(now) //签发时间
			.withExpiresAt(new Date(now.getTime() + TOKEN_EXPIRE_TIME)) //过期时间
			.withClaim("username", username) //保存身份标识
			.sign(algorithm);
		return token;
	}
	
	/**
	 * 验证token
	 */
	public static boolean verify(String token, String username){
	    try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY); //算法
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer(ISSUER)
					.withClaim("username", username)
					.build();
			verifier.verify(token);
			return true;
	    } catch (Exception ex){
	    	ex.printStackTrace();
	    }
	    return false;
	}
	
	public static boolean verify(String token){
	    try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY); //算法
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer(ISSUER)
					.build();
			verifier.verify(token);
			return true;
	    } catch (Exception ex){
	    	ex.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * 获取指定claim
	 */
	public static String getClaimFromToken(String token, String claimName){
		try{
			return JWT.decode(token).getClaim(claimName).asString();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 从token获取username
	 */
	public static String getUsername(String token){
		try{
			return JWT.decode(token).getClaim("username").asString();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获取原始头部信息
	 */
	public static String getOriginalHeader(String token){
		String encodedHeader = JWT.decode(token).getHeader();
		String originalHeader = new String(Base64.decodeBase64(encodedHeader));
		return originalHeader;
	}

	/**
	 * 获取原始负载信息
	 */
	public static String getOriginalPayload(String token){
		String encodedPayload = JWT.decode(token).getPayload();
		String originalPayload = new String(Base64.decodeBase64(encodedPayload));
		return originalPayload;
	}
	
	/**
	 * 验证token是否有效：没有被篡改等
	 */
	public static boolean checkToken(String token){
		byte[] headers = JWT.decode(token).getHeader().getBytes();
		byte[] payloads = JWT.decode(token).getPayload().getBytes();
		String signature = JWT.decode(token).getSignature();

		Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET_KEY); //算法
		byte[] bytes = algorithm.sign(headers, payloads);
		String newSignature = Base64.encodeBase64URLSafeString(bytes);
		return newSignature.endsWith(signature);
	}
    
	public static void main(String[] args) {
		String token = JWTUtil.generateToken("admin");
		System.out.println(token);
		System.out.println(getOriginalHeader(token));
		System.out.println(getOriginalPayload(token));
		System.out.println(checkToken(token));
		
		String username = JWTUtil.getClaimFromToken(token, "username");
		System.out.println(username);
		
		System.out.println(JWTUtil.verify(token, username));
	}

}
