package com.tinytree.encry;

import com.tinytree.exception.EncryptException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Description：AES加密/解密实现类
 * @ClassName: EncryptAESImpl
 * @Author：tangyang
 * @Date：2015-12-24
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */
public class EncryptAESImpl implements Encrypt{
	protected EncryptAESImpl(){}

	@Override
	public byte[] decrypt(byte[] src, String... keys) throws EncryptException {
		if(src == null || keys == null || keys.length == 0){
			return null;
		}
		
		return docrypt(Cipher.DECRYPT_MODE, src, keys[0]);
	}

	@Override
	public byte[] encrypt(byte[] src, String... keys) throws EncryptException {
		if(src == null || keys == null || keys.length == 0){
			return null;
		}
		
		return docrypt(Cipher.ENCRYPT_MODE, src, keys[0]);
		
	}
	
	private byte[] docrypt(int type,byte[] inData,String seed) throws EncryptException {
//			KeyGenerator kgen = KeyGenerator.getInstance("AES");  
//            kgen.init(128, new SecureRandom(seed.getBytes()));  
//            SecretKey secretKey = kgen.generateKey();  
		if(seed.length() != 16){
			throw new EncryptException("AES_ECB_KEY_ERROR");
		}
		
		try {
			byte[] enCodeFormat = seed.getBytes(EncryptManager.charsetName);
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
		    Cipher cipher;
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		    cipher.init(type, key);  
		    byte[] encryptedData = cipher.doFinal(inData);  
		    return encryptedData;
		} 
		catch (Exception e) {
			throw new EncryptException(e);
		}
	    
	}

}
