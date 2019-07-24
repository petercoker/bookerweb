package com.booker.util;

public interface ICrypto {
	byte [] encrypt(byte [] data); // byte is easier to convert
	
	byte [] decrypt(byte [] data);
	
	
}
