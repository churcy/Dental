package com.tinytree.encry;

import com.tinytree.exception.EncryptException;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description：MD5加密实现类
 * @ClassName: TokenManager
 * @Author：tangyang
 * @Date：2015-12-24
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */
public class EncryptMD5Impl implements Encrypt{
	protected EncryptMD5Impl(){}
	
	@Override
	public byte[] decrypt(byte[] src, String... key) throws EncryptException {
		throw new EncryptException("MD5_DECODE_ERROR");
	}
	
	@Override
	public byte[] encrypt(byte[] src, String... keys) throws EncryptException {
		byte[] btInput = src;
		if(btInput == null || btInput.length == 0){
			return null;
		}
        MessageDigest mdInst = null;
		try {
			mdInst = MessageDigest.getInstance("MD5");
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new EncryptException(e);
		}
        mdInst.update(btInput);
        byte[] md = mdInst.digest();
        return md;
	}
	
	public static String hexSHA1(String value) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(value.getBytes("utf-8"));
			byte[] digest = md.digest();
			return byteToHexString(digest);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	public static String byteToHexString(byte[] bytes) {
		return String.valueOf(Hex.encodeHex(bytes));
	}

}
