package egovframework.common.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClobMap extends HashMap<String, Object>
{
	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = -3271860443764816604L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ClobMap.class);

	public Object put(String key, Object value)
	{
		if (value != null)
		{
			String valueStr = value.toString();
			if (valueStr.startsWith("COM.ibm.db2.jdbc.net.DB2Clob")
					|| valueStr.startsWith("COM.ibm.db2.jdbc.app.DB2Clob")
					|| valueStr.startsWith("COM.ibm.db2.app.Clob")
					|| valueStr.startsWith("com.ibm.db2.jcc.b.vd")
					|| valueStr.startsWith("com.ibm.db2.jcc.a.cc"))
			{
				String content = "";
				try
				{
					content = getClob(value);
				}
				catch (SQLException e)
				{
					LOGGER.error("SQLException: ", e);
				}
				return super.put(key, content);

				// for Oracle
			}
			else if (valueStr.toLowerCase().indexOf("clob") >= 0)
			{

				String content = "";
				try
				{
					content = getClob(value);
				}
				catch (SQLException e)
				{
					LOGGER.error("SQLException: ", e);
				}
				return super.put(key, content);
			}
			else
			{
				return super.put(key, value);
			}
		}
		else
		{
			return super.put(key, value);
		}
	}

	/**
	 * CLOB 객체를 String 으로 변환. DB2용
	 * 
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static String getClob(Object obj) throws SQLException
	{
		String content = "";
		Clob clob = (Clob) obj;
		Reader rd = clob.getCharacterStream();
		try
		{
			StringBuffer sb = new StringBuffer();
			char[] buf = new char[1024];
			int readcnt;
			while ((readcnt = rd.read(buf, 0, 1024)) != -1)
			{
				sb.append(buf, 0, readcnt);
			}

			content = sb.toString();
		}
		catch (IOException ie)
		{
			LOGGER.error("IOException: ", ie);
		}
		finally
		{
			if (rd != null)
			{
				try
				{
					rd.close();
				}
				catch (IOException e)
				{
					LOGGER.error("IOException: ", e);
				}
			}
		}
		return content;
	}
}