package com.tinytree.encry;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;

import com.kungfu.dental.exception.EncryptException;

/**
 * @Description：RSA加密/解密实现类
 * @ClassName: EncryptRSAImpl
 * @Author：tangyang
 * @Date：2015-12-24
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */
public class EncryptRSAImpl implements Encrypt{
	
	private static final String KEY_ALGORITHM = "RSA";
	
	 /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
 
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
     
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    
    protected EncryptRSAImpl(){}

	@Override
	public byte[] decrypt(byte[] src, String... keys) throws EncryptException{
		byte[] decryptedData = null;
		try{
			byte[] keyBytes = new BASE64Decoder().decodeBuffer(keys[0]);
	        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
	        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	        cipher.init(Cipher.DECRYPT_MODE, privateK);
	        
	        int inputLen = src.length;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        byte[] cache;
	        int i = 0;
	        // 对数据分段解密
	        while (inputLen - offSet > 0) {
	            if (inputLen - offSet > MAX_DECRYPT_BLOCK){
	                cache = cipher.doFinal(src, offSet, MAX_DECRYPT_BLOCK);
	            } 
	            else{
	                cache = cipher.doFinal(src, offSet, inputLen - offSet);
	            }
	            out.write(cache, 0, cache.length);
	            i++;
	            offSet = i * MAX_DECRYPT_BLOCK;
	        }
	        decryptedData = out.toByteArray();
	        out.close();
		}
		catch(Exception e){
			throw new EncryptException(e);
		}
		
        return decryptedData;
	}

	@Override
	public byte[] encrypt(byte[] src, String... keys) throws EncryptException{
		 byte[] encryptedData = null;
		try{
			byte[] keyBytes =new BASE64Decoder().decodeBuffer(keys[0]);
	        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	        Key publicK = keyFactory.generatePublic(x509KeySpec);
	        // 对数据加密
	        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	        cipher.init(Cipher.ENCRYPT_MODE, publicK);
	        int inputLen = src.length;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        byte[] cache;
	        int i = 0;
	        // 对数据分段加密
	        while (inputLen - offSet > 0){
	            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
	                cache = cipher.doFinal(src, offSet, MAX_ENCRYPT_BLOCK);
	            }
	            else {
	                cache = cipher.doFinal(src, offSet, inputLen - offSet);
	            }
	            out.write(cache, 0, cache.length);
	            i++;
	            offSet = i * MAX_ENCRYPT_BLOCK;
	        }
	        encryptedData = out.toByteArray();
	        out.close();
		}
		catch(Exception e){
			throw new EncryptException(e);
		}
		
        return encryptedData;
	}

}
