package com.tinytree.encry;

import com.tinytree.exception.EncryptException;

public interface Encrypt
{
	public byte[] encrypt(byte[] src,String... keys) throws EncryptException;
	
	public byte[] decrypt(byte[] src,String... keys) throws EncryptException, EncryptException;
	
}
