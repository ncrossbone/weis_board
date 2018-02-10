/**
 *  Class Name : EgovFileScrty.java
 *  Description : Base64인코딩/디코딩 방식을 이용한 데이터를 암.호.화./decoding하는 Business Interface class
 *  Modification Information
 *
 *     수정일         수정자                   수정내용
 *   -------    --------    ---------------------------
 *   2009.02.04    박지욱          최초 생성
 *
 *  @author 공통 서비스 개발팀 박지욱
 *  @since 2009. 02. 04
 *  @version 1.0
 *  @see
 *
 *  Copyright (C) 2009 by MOPAS  All right reserved.
 */
package egovframework.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class EgovFileScrty
{
	// 파일구분자
	static final char FILE_SEPARATOR = File.separatorChar;

	static final int BUFFER_SIZE = 1024;

	/**
	 * 파일을 암.호.화.하는 기능
	 *
	 * @param String source 암.호.화.할 파일
	 * @param String target
	 * @return boolean result 암.호.화.여부 True/False
	 * @exception Exception
	 */
	public static boolean encryptFile(String source, String target) throws Exception
	{
		// 암.호.화. 여부
		boolean result = false;

		String sourceFile = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		String targetFile = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File srcFile = new File(sourceFile);

		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		byte[] buffer = new byte[BUFFER_SIZE];

		try
		{
			if (srcFile.exists() && srcFile.isFile())
			{
				input = new BufferedInputStream(new FileInputStream(srcFile));
				output = new BufferedOutputStream(new FileOutputStream(targetFile));

				int length = 0;
				while ((length = input.read(buffer)) >= 0)
				{
					if (length < 0)
					{
						length = -99;
						break;
					}
					else
					{

						byte[] data = new byte[length];
						System.arraycopy(buffer, 0, data, 0, length);
						output.write(encodeBinary(data).getBytes());
						output.write(System.getProperty("line.separator").getBytes());
					}
				}
				if (-99 == length)
				{
					result = true;
				}
				else
				{
					result = true;
				}
			}
		}
		finally
		{
			try
			{
				if (input != null)
				{
					input.close();
				}
				if (output != null)
				{
					output.close();
				}
			}
			catch (IOException e)
			{
				System.out.println("IOException" + e);
			}
//			EgovResourceCloseHelper.close(input, output);
		}

		return result;
	}

	/**
	 * 파일을 복호화하는 기능
	 *
	 * @param String source 복호화할 파일
	 * @param String target 복호화된 파일
	 * @return boolean result 복호화여부 True/False
	 * @exception Exception
	 */
	public static boolean decryptFile(String source, String target) throws Exception
	{
		// 복호화 여부
		boolean result = false;

		String sourceFile = source.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		String targetFile = target.replace('\\', FILE_SEPARATOR).replace('/', FILE_SEPARATOR);
		File srcFile = new File(sourceFile);

		BufferedReader input = null;
		BufferedOutputStream output = null;

		//byte[] buffer = new byte[BUFFER_SIZE];
		String line = null;

		try
		{
			if (srcFile.exists() && srcFile.isFile())
			{
				input = new BufferedReader(new InputStreamReader(new FileInputStream(srcFile)));
				output = new BufferedOutputStream(new FileOutputStream(targetFile));

				while ((line = input.readLine()) != null)
				{
					byte[] data = null;
					if (null != line)
					{
						data = line.getBytes();
					}
					output.write(decodeBinary(new String(data)));
				}

				result = true;
			}
		}
		catch (IOException e)
		{
			System.out.println("IOException" + e);
		}
		finally
		{
			if (input != null)
			{
				input.close();
			}
			if (output != null)
			{
				output.close();
			}
//			EgovResourceCloseHelper.close(input, output);
		}

		return result;
	}

	/**
	 * 데이터를 암.호.화.하는 기능
	 *
	 * @param byte[] data 암.호.화.할 데이터
	 * @return String result 암.호.화.된 데이터
	 * @exception Exception
	 */
	public static String encodeBinary(byte[] data) throws Exception
	{
		if (data == null)
		{
			return "";
		}

		return new String(Base64.encodeBase64(data));
	}

	/**
	 * 데이터를 암.호.화.하는 기능
	 *
	 * @param String data 암.호.화.할 데이터
	 * @return String result 암.호.화.된 데이터
	 * @exception Exception
	 */
	@Deprecated
	public static String encode(String data) throws Exception
	{
		return encodeBinary(data.getBytes());
	}

	/**
	 * 데이터를 복호화하는 기능
	 *
	 * @param String data 복호화할 데이터
	 * @return String result 복호화된 데이터
	 * @exception Exception
	 */
	public static byte[] decodeBinary(String data) throws Exception
	{
		return Base64.decodeBase64(data.getBytes());
	}

	/**
	 * 데이터를 복호화하는 기능
	 *
	 * @param String data 복호화할 데이터
	 * @return String result 복호화된 데이터
	 * @exception Exception
	 */
	@Deprecated
	public static String decode(String data) throws Exception
	{
		return new String(decodeBinary(data));
	}

	/**
	 * p.w. SHA-256 인코딩 방식 적용
	 * 
	 * @param p.w. SHA-256 적용전 p.w.
	 * @param id salt로 사용될 사용자 ID 지정
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws Exception
	 */
	public static String encryptPassword(String password, String id) throws NoSuchAlgorithmException
	{
		if (password == null)
		{
			return "";
		}

		byte[] hashValue = null; // 해쉬값

		MessageDigest md = MessageDigest.getInstance("SHA-256");

		md.reset();
		md.update(id.getBytes());

		hashValue = md.digest(password.getBytes());

		return new String(Base64.encodeBase64(hashValue));
	}

	/**
	 * p.w. SHA-256 인코딩 방식 적용
	 * 
	 * @param data 암.호.화.할 p.w.
	 * @param salt Salt
	 * @return 암.호.화.된 p.w.
	 * @throws Exception
	 */
	public static String encryptPassword(String data, byte[] salt) throws Exception
	{
		if (data == null)
		{
			return "";
		}

		byte[] hashValue = null; // 해쉬값

		MessageDigest md = MessageDigest.getInstance("SHA-256");

		md.reset();
		md.update(salt);

		hashValue = md.digest(data.getBytes());

		return new String(Base64.encodeBase64(hashValue));
	}

	/**
	 * p.w.를 암.호.화.된 p.w. 검증(salt가 사용된 경우만 적용).
	 * 
	 * @param data 원 p.w.
	 * @param encoded 해쉬처리된 p.w.(Base64 인코딩)
	 * @return
	 * @throws Exception
	 */
	public static boolean checkPassword(String data, String encoded, byte[] salt) throws Exception
	{
		byte[] hashValue = null; // 해쉬값

		MessageDigest md = MessageDigest.getInstance("SHA-256");

		md.reset();
		md.update(salt);
		hashValue = md.digest(data.getBytes());

		return MessageDigest.isEqual(hashValue, Base64.decodeBase64(encoded.getBytes()));
	}
}