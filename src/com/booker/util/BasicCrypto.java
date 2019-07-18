package com.booker.util;

public class BasicCrypto implements ICrypto{

	@Override
	public byte[] encrypt(byte[] password) {
		byte[] enc = new byte[password.length];
		
		for (int i = 0; i < password.length; i++) {
			// check if the value is even index 
			// if its is add one to the value
			// if it is not subtract one from the value
			enc[i] = (byte)((i % 2 == 0) ? password[i] + 1 : password [i] - 1);
		}
		
		return enc;
	}

	@Override
	public byte[] decrypt(byte[] password) {
		byte[] dec = new byte[password.length];
		
		for (int i = 0; i < password.length; i++) {
			// check if the value is even index 
			// if its is not subtract one from the value
			// if it is add one to the value
			dec[i] = (byte)((i % 2 == 0) ? password[i] - 1 : password [i] + 1);
		}
		
		
		return dec;
	}

}
