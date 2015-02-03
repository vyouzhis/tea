package org.ppl.io;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class DesEncrypter {
	Cipher ecipher;
	Cipher dcipher;

	public DesEncrypter() throws Exception {
		SecretKey key = KeyGenerator.getInstance("DES").generateKey();
		ecipher = Cipher.getInstance("DES");
		dcipher = Cipher.getInstance("DES");
		ecipher.init(Cipher.ENCRYPT_MODE, key);
		dcipher.init(Cipher.DECRYPT_MODE, key);
	}

	@SuppressWarnings("restriction")
	public String encrypt(String str) throws Exception {
		// Encode the string into bytes using utf-8
		byte[] utf8 = str.getBytes("UTF8");
		// Encrypt
		byte[] enc = ecipher.doFinal(utf8);
		// Encode bytes to base64 to get a string
		return new sun.misc.BASE64Encoder().encode(enc);
	}

	@SuppressWarnings("restriction")
	public String decrypt(String str) throws Exception {
		// Decode base64 to get bytes
		byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
		byte[] utf8 = dcipher.doFinal(dec);
		// Decode using utf-8
		return new String(utf8, "UTF8");
	}
}
