import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

// Ok for demo applications
// DON'T upload any keys to github or something
public class blogCipher {
	
	private String getKey() {
		// for future, any other ways to get the key
		return "my!suPer!keY!234";
	}
	private String getVector() {
		// for future, any other ways to get the key
		// 16 bytes
		return "kwegfwuegfugfsds";
	}

	public String encrypt(String v) {
		try {
			IvParameterSpec iv = new IvParameterSpec(this.getVector().getBytes("UTF-8"));
			Key aesKey = new SecretKeySpec(this.getKey().getBytes("UTF-8"), "AES");
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.ENCRYPT_MODE, aesKey, iv);
	        byte[] encrypted = cipher.doFinal(v.getBytes("UTF-8"));
	        return new String(Base64.encodeBase64(encrypted));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public String decrypt(String v) {
		try {
			IvParameterSpec iv = new IvParameterSpec(this.getVector().getBytes("UTF-8"));
			Key aesKey = new SecretKeySpec(this.getKey().getBytes("UTF-8"), "AES");
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, aesKey, iv);
			String decrypted = new String(cipher.doFinal(Base64.decodeBase64(v)));
			return decrypted;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
