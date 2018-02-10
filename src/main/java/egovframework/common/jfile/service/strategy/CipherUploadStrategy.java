package egovframework.common.jfile.service.strategy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;

import egovframework.common.jfile.GlovalVariables;
import egovframework.common.jfile.exception.JFileException;
import egovframework.common.jfile.security.service.CipherService;
import egovframework.common.jfile.utils.SpringUtils;

public class CipherUploadStrategy implements UploadStrategy
{
	public void handle(InputStream in, OutputStream out) throws JFileException
	{
		CipherService service = (CipherService) SpringUtils.getBean(GlovalVariables.CIPHER_SERVICE_BEAN_NAME);
		try
		{
			service.encrypt(in, out);
		}
		catch (InvalidKeyException e1)
		{
			System.out.println("InvalidKeyException:" + e1);
			// e1.printStackTrace();
		}
		catch (NoSuchAlgorithmException e1)
		{
			System.out.println("NoSuchAlgorithmException:" + e1);
			// e1.printStackTrace();
		}
		catch (NoSuchPaddingException e1)
		{
			System.out.println("NoSuchPaddingException:" + e1);
			// e1.printStackTrace();
		}
		catch (BadPaddingException e1)
		{
			System.out.println("BadPaddingException:" + e1);
			// e1.printStackTrace();
		}
		catch (InvalidKeySpecException e1)
		{
			System.out.println("InvalidKeySpecException:" + e1);
			// e1.printStackTrace();
		}
		catch (InvalidAlgorithmParameterException e1)
		{
			System.out.println("InvalidAlgorithmParameterException:" + e1);
			// e1.printStackTrace();
		}
		catch (IOException e1)
		{
			System.out.println("IOException:" + e1);
			// e1.printStackTrace();
		}
		finally
		{
			try
			{
				if (in != null) in.close();
				if (out != null) out.close();
			}
			catch (IOException e)
			{
				System.out.println("IOException:" + e);
			}
		}
	}
}