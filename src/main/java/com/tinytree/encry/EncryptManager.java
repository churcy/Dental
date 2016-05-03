package com.tinytree.encry;

import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.kungfu.dental.exception.EncryptException;

/**
 * @Description：加密/解密管理器，支持MD5、AES、RSA三种方式
 * @ClassName: EncryptManager
 * @Author：tangyang
 * @Date：2015-12-24
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */
public class EncryptManager {
	public static final String AES_TYPE = "AES";
	
	public static final String MD5_TYPE = "MD5";
	
	public static final String RSA_TYPE = "RSA";
	
	public static String charsetName = "UTF-8";
	
	private static BASE64Encoder base64Encoder = null;
	
	private static BASE64Decoder base64decoder = null;
	
	private Encrypt enc;
	
	private EncryptManager(){}
	
	private EncryptManager(String type){
		if(AES_TYPE.equalsIgnoreCase(type)){
			enc = new EncryptAESImpl();
		}
		else if(MD5_TYPE.equalsIgnoreCase(type)){
			enc = new EncryptMD5Impl();
		}
		else if(RSA_TYPE.equalsIgnoreCase(type)){
			enc = new EncryptRSAImpl();
		}
		
		base64Encoder = new BASE64Encoder();
		base64decoder = new BASE64Decoder();
	}
	
	private static class EncryptManagerHolder{
		private static EncryptManager aesInstance = new EncryptManager(AES_TYPE);
		
		private static EncryptManager md5Instance = new EncryptManager(MD5_TYPE);
		
		private static EncryptManager rsaInstance = new EncryptManager(RSA_TYPE);
	}
	
	public static EncryptManager getInstance(String type){
		if(AES_TYPE.equalsIgnoreCase(type)){
			return EncryptManagerHolder.aesInstance;
		}
		else if(MD5_TYPE.equalsIgnoreCase(type)){
			return EncryptManagerHolder.md5Instance;
		}
		else if(RSA_TYPE.equalsIgnoreCase(type)){
			return EncryptManagerHolder.rsaInstance;
		}
		else{
			return EncryptManagerHolder.aesInstance;
		}
	}
	
	public String encrypt(String src,String...keys) throws EncryptException  {
		if(enc == null || src == null){
			return null;
		}
		
		byte[] bEnc = null;
		try{
			bEnc = enc.encrypt(src.getBytes(charsetName), keys);
		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new EncryptException(e);
		}
		return bEnc == null ? null : base64Encoder.encode(bEnc);
	}
	
	public String decrypt(String src,String...keys) throws EncryptException{
		if(enc == null || src == null){
			return null;
		}
		
		byte[] chipSrc = null;//parseHexStr2Byte(src);
		try {
			chipSrc = base64decoder.decodeBuffer(src);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new EncryptException(e);
		}
		if(chipSrc == null || chipSrc.length == 0){
			throw new EncryptException("BASE64_DECODE_FAILED:"+src);
		}
		
		byte[] valueSrc = enc.decrypt(chipSrc, keys);
		
		if(valueSrc == null || valueSrc.length == 0){
			return null;
		}
		return new String(valueSrc);
	}
	
	public static String getKeyFromToken(String token){
		if(token == null || token.length() < 18){
			return null;
		}
		
		return token.substring(1,17);
	}
	
	public static String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++){  
            String hex = Integer.toHexString(buf[i] & 0xFF);  
            if (hex.length() == 1){  
                    hex = '0' + hex;  
            }  
            sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
	} 
	
	public static byte[] parseHexStr2Byte(String hexStr) {  
        if (hexStr.length() < 1)  {
        	 return null;  
        }
               
        byte[] result = new byte[hexStr.length()/2];  
        for (int i = 0;i< hexStr.length()/2; i++){  
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
            result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
	}  
    public static String base64Encoder(String src) throws Exception {  
        BASE64Encoder encoder = new BASE64Encoder();  
        return encoder.encode(src.getBytes("utf-8"));  
    }  
	
    public static String base64Decoder(String dest) throws Exception {  
        BASE64Decoder decoder = new BASE64Decoder();  
        return new String(decoder.decodeBuffer(dest), "utf-8");  
    }  
}
