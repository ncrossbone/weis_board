package egovframework.common.utilDo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class SecretUtils {

	public static final String ENC_TYPE_SHA_256 = "SHA-256";
	public static final String ENC_TYPE_SHA_1 = "SHA-1";
	public static final String MD5 = "MD5";
	
	
	/**
	 * SHA-256 암호화
	 * 
	 * @param str
	 * @return
	 */
	public static String encryptSha256(String str) {
		String SHA = "";
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(str.getBytes());
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			SHA = null;
		}
		return SHA;
	}
	
	/**
	 * 
	 * 암호화 모듈
	 * 
	 * @param str
	 * @param type
	 * @return
	 */
	public static String encrypt(String str, String type) {
		try {
			// Create MD5 Hash
			// TYPE : SHA-1, SHA-256
			if(ENC_TYPE_SHA_1.equals(type)){
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				md.update(str.getBytes());
				byte[] digest = md.digest();
				String result = DatatypeConverter.printHexBinary(digest);
				return result.toLowerCase();
			}else{
				MessageDigest digest = MessageDigest.getInstance(type);
				digest.update(str.getBytes());
				byte messageDigest[] = digest.digest();

				// Create Hex String
				StringBuffer hexString = new StringBuffer();
				for (int i = 0; i < messageDigest.length; i++)
					hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
				return hexString.toString();
			}
			

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
