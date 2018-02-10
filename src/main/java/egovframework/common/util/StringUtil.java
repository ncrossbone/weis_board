package egovframework.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StringUtil
{
	private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

	/**
	 * org.apache.struts2.util.StrutsUtil
	 * 
	 * @param obj
	 * @return
	 */
	public static String htmlEncode( Object obj ) {
		if( obj == null ) {
			return null;
		}

		return htmlEncode( obj.toString(), true, false );
	}

	// public String urlEncode(String s) {
	// public String urlDecode(String s) {

	/**
	 * <pre>
	 * 숫자값을 2자리 문자열로 바꾼다.
	 * 1 은 01, 12 는 12, ...
	 * </pre>
	 */
	public static String toStrZero( int number ) {
		return toStrZero( number, 2 );
	}

	/**
	 * <pre>
	 * int형의 &lt;code&gt;number&lt;/code&gt;를 &lt;code&gt;length&lt;/code&gt; 길이 만큼의 문자열로 변환한다.
	 * length 보다 작은 길이의 숫자일때 &quot;0&quot;을 붙혀주기 위해 사용.
	 * </pre>
	 * 
	 * <pre>
	 * toStrZero( 123, 5 ); // return &quot;00123&quot;;
	 * </pre>
	 * 
	 * @param number
	 *          포맷할 값
	 * @param length
	 *          0을 붙힐 문자열의 총 길이
	 * @return
	 */
	public static String toStrZero( int number, int length ) {
		String num = String.valueOf( number );
		String result = num;

		for( int i = num.length(); i < length; i++ ) {
			result = "0" + result;
		}

		return result;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String toStrDate( String date ) {
		return toStrDate( date, "%Y.%M.%D" );
	}

	/**
	 * 단순 년월일을 저장하는 경우 DATE/TIMESTAMP가 아닌 CHAR(8)을 쓰기로 결정. VIEW 에서 출력할때 포맷을 변경하기 쉽도록 사용한다.
	 * 
	 * @param date
	 *          숫자로만 구성된 날짜 문자열( yymmdd 나 yyyymmdd 형식만 가능)
	 * @param format
	 *          포맷형식( %Y:년도4자리, %y:년도2자리, %M, %D, %m, %d )
	 * @return
	 */
	public static String toStrDate( String date, String format ) {

		// 
		String value, yyyy, yy, mm, dd;

		value = date.replaceAll( "[^0-9]", "" );

		// 끝에서부터 2자씩 자르자. // 메서드를 찾긴 그렇고 우선 이대로 두고 나중에 변경한다.
		int pos;
		pos = value.length();
		
		if( pos < 6 )
			return "";
		
		dd = value.substring( pos - 2, pos );
		pos -= 2;
		mm = value.substring( pos - 2, pos );
		pos -= 2;
		yy = value.substring( 0, pos );

		if( yy.length() == 2 ) {
			if( Integer.parseInt( yy ) >= 50 )
				yyyy = "19" + yy;
			else
				yyyy = "20" + yy;
		}
		else {
			yyyy = yy;
			yy = yy.substring( 1 );
		}

		format = format.replaceFirst( "\\%Y", yyyy );
		format = format.replaceFirst( "\\%y", yy );
		format = format.replaceFirst( "\\%M", mm );
		format = format.replaceFirst( "\\%m", String.valueOf( Integer.parseInt( mm ) ) );
		format = format.replaceFirst( "\\%D", dd );
		format = format.replaceFirst( "\\%d", String.valueOf( Integer.parseInt( dd ) ) );

		return format;

	}

	public static String toStrDate( Date date, String format ) {
		// 이거 나중에 시간내서 바꾸자.
		return new SimpleDateFormat( format ).format( date );
	}
	
	public static String getCurDateStr(String formatString) {
        SimpleDateFormat formatter  = new SimpleDateFormat(formatString);
        Date currentTime= new Date();
        return formatter.format(currentTime);
    }
	

	public static int toInt( String string ) {
		return toInt( string, 0 );
	}

	public static int toInt( Object string, int defaultInt ) {
		int returnVal = 0;
		if(string == null){
			return defaultInt;
		}else{
			returnVal = toInt( string.toString() , defaultInt );//웹취약점 조치
		}
		return returnVal;
	}

	public static int toInt( Object object ) {
		return toInt( noNull( object ), 0 );
	}

	/**
	 * Integer.parseInt() 가 귀찮아서, 파싱예외시 기본값으로 처리
	 * 
	 * @param string
	 * @return
	 */
	public static int toInt( String string, int defaultInt ) {
		/*
		 * if( string == null || string.equals("") ) return 0; return Integer.parseInt( string );
		 */
		try {
			return Integer.parseInt( string );
		}
		catch( NumberFormatException e ) {
			return defaultInt;
		}
	}

	public static int toInt( long aLong ) {
		return (int) aLong;
	}

	public static long toLong( int anInt ) {
		return (long) anInt;
	}

	public static long toLong( String aLong ) {
		if( aLong == null ) {
			return 0;
		}

		return Long.parseLong( aLong );
	}

	public static long toLong( Object aLong ) {
		if( aLong == null || aLong.toString().equals( "" ) ) {
			return 0;
		}
		return Long.parseLong( aLong.toString() );
	}
	public static double toDouble( int anInt ) {
		return (long) anInt;
	}
	
	public static double toDouble( String aDouble ) {
		if( aDouble == null ) {
			return 0;
		}
		
		return Double.parseDouble( aDouble );
	}
	
	public static double toDouble( Object aDouble ) {
		if( aDouble == null || aDouble.toString().equals( "" ) ) {
			return 0;
		}
		return Double.parseDouble( aDouble.toString() );
	}

	public static double toDouble(String str, double defaultValue)
	{
		try
		{
			return Double.parseDouble(str);
		}
		catch (NumberFormatException e)
		{
			System.err.print("Double Value Error : " + str);
		}
		return defaultValue;
	}

	public static String toString( long aLong ) {
		return Long.toString( aLong );
	}

	public static String toString( int anInt ) {
		return Integer.toString( anInt );
	}

	/**
	 * Escape html entity characters and high characters (eg "curvy" Word quotes). Note this method can also be used to encode XML.
	 * 
	 * @param s
	 *          the String to escape.
	 * @param encodeSpecialChars
	 *          if true high characters will be encode other wise not.
	 * @return the escaped string
	 */
	public final static String htmlEncode( String s, boolean nl2br, boolean encodeSpecialChars ) {
		s = noNull( s );

		StringBuffer str = new StringBuffer();

		for( int j = 0; j < s.length(); j++ ) {
			char c = s.charAt( j );

			// encode standard ASCII characters into HTML entities where needed
			if( c < '\200' ) {
				switch( c ) {
				case '"':
					str.append( "&quot;" );
					break;

				case '&':
					str.append( "&amp;" );
					break;

				case '<':
					str.append( "&lt;" );
					break;

				case '>':
					str.append( "&gt;" );
					break;

				case '\n':
					str.append( "<br/>" );
					break;

				default:
					str.append( c );
				}
			}
			// encode 'ugly' characters (ie Word "curvy" quotes etc)
			else if( encodeSpecialChars && ( c < '\377' ) ) {
				String hexChars = "0123456789ABCDEF";
				int a = c % 16;
				int b = ( c - a ) / 16;
				String hex = "" + hexChars.charAt( b ) + hexChars.charAt( a );
				str.append( "&#x" + hex + ";" );
			}
			// add other characters back in - to handle charactersets
			// other than ascii
			else {
				str.append( c );
			}
		}

		return str.toString();
	}

	/**
	 * Return <code>string</code>, or <code>defaultString</code> if <code>string</code> is <code>null</code> or <code>""</code>. Never returns <code>null</code>.
	 * 
	 * <p>
	 * Examples:
	 * </p>
	 * 
	 * <pre>
	 * // prints &quot;hello&quot;
	 * String s=null;
	 * System.out.println(TextUtils.noNull(s,&quot;hello&quot;);
	 * 
	 * // prints &quot;hello&quot;
	 * s=&quot;&quot;;
	 * System.out.println(TextUtils.noNull(s,&quot;hello&quot;);
	 * 
	 * // prints &quot;world&quot;
	 * s=&quot;world&quot;;
	 * System.out.println(TextUtils.noNull(s, &quot;hello&quot;);
	 * </pre>
	 * 
	 * @param string
	 *          the String to check.
	 * @param defaultString
	 *          The default string to return if <code>string</code> is <code>null</code> or <code>""</code>
	 * @return <code>string</code> if <code>string</code> is non-empty, and <code>defaultString</code> otherwise
	 * @see #stringSet(java.lang.String)
	 */
	public final static String noNull( String string, String defaultString ) {
		return ( stringSet( string ) ) ? string : defaultString;
	}

	/**
	 * Return <code>string</code>, or <code>""</code> if <code>string</code> is <code>null</code>. Never returns <code>null</code>.
	 * <p>
	 * Examples:
	 * </p>
	 * 
	 * <pre>
	 * // prints 0
	 * String s = null;
	 * System.out.println( TextUtils.noNull( s ).length() );
	 * 
	 * // prints 1
	 * s = &quot;a&quot;;
	 * System.out.println( TextUtils.noNull( s ).length() );
	 * </pre>
	 * 
	 * @param string
	 *          the String to check
	 * @return a valid (non-null) string reference
	 */
	public final static String noNull( Object string ) {
		return noNull( string, "" );
	}

	/**
	 * Check whether <code>string</code> has been set to something other than <code>""</code> or <code>null</code>.
	 * 
	 * @param string
	 *          the <code>String</code> to check
	 * @return a boolean indicating whether the string was non-empty (and non-null)
	 */
	public final static boolean stringSet( String string ) {
		return ( string != null ) && !"".equals( string );
	}

	public final static Long noNull( Long vLong, Long defaultLong ) {
		return ( longSet( vLong ) ) ? vLong : defaultLong;
	}

	public final static Long noNull( Long vLong ) {
		return noNull( vLong, 0L );
	}

	public final static boolean longSet( Long vLong ) {
		return ( vLong != null ) && ( vLong != 0L );
	}

	public final static String noNull( Object string, String defaultString ) {
		String returnVal = "";
		if( string == null ){
			return defaultString;
		}else{
			returnVal = noNull( string.toString(), defaultString );//웹취약점 조치
		}
		return returnVal;
	}

	/**
	 * <p>
	 * Checks if a String is empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty(&quot;&quot;)        = true
	 * StringUtils.isEmpty(&quot; &quot;)       = false
	 * StringUtils.isEmpty(&quot;bob&quot;)     = false
	 * StringUtils.isEmpty(&quot;  bob  &quot;) = false
	 * </pre>
	 * 
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer trims the String. That functionality is available in isBlank().
	 * </p>
	 * 
	 * @param str
	 *          the String to check, may be null
	 * @return <code>true</code> if the String is empty or null
	 */
	public static boolean isEmpty( String str ) {
		return str == null || str.length() == 0;
	}

	/**
	 * <p>
	 * Checks if a String is whitespace, empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank(&quot;&quot;)        = true
	 * StringUtils.isBlank(&quot; &quot;)       = true
	 * StringUtils.isBlank(&quot;bob&quot;)     = false
	 * StringUtils.isBlank(&quot;  bob  &quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *          the String to check, may be null
	 * @return <code>true</code> if the String is null, empty or whitespace
	 * @since 2.0
	 */
	public static boolean isBlank( String str ) {
		int strLen;
		if( str == null || ( strLen = str.length() ) == 0 ) {
			return true;
		}
		for( int i = 0; i < strLen; i++ ) {
			if( ( Character.isWhitespace( str.charAt( i ) ) == false ) ) {
				return false;
			}
		}
		return true;
	}

	public static boolean isBlank( Object object ) {

		if( object == null )
			return true;
		else
			return isBlank( object.toString() );

	}

	/**
	 * <p>
	 * Checks if the String contains only unicode letters.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String ("") will return <code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isAlpha(null)   = false
	 * StringUtils.isAlpha(&quot;&quot;)     = true
	 * StringUtils.isAlpha(&quot;  &quot;)   = false
	 * StringUtils.isAlpha(&quot;abc&quot;)  = true
	 * StringUtils.isAlpha(&quot;ab2c&quot;) = false
	 * StringUtils.isAlpha(&quot;ab-c&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *          the String to check, may be null
	 * @return <code>true</code> if only contains letters, and is non-null
	 */
	public static boolean isAlpha( String str ) {
		if( str == null ) {
			return false;
		}
		int sz = str.length();
		for( int i = 0; i < sz; i++ ) {
			if( Character.isLetter( str.charAt( i ) ) == false ) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the String contains only unicode digits. A decimal point is not a unicode digit and returns false.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String ("") will return <code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isNumeric(null)   = false
	 * StringUtils.isNumeric(&quot;&quot;)     = true
	 * StringUtils.isNumeric(&quot;  &quot;)   = false
	 * StringUtils.isNumeric(&quot;123&quot;)  = true
	 * StringUtils.isNumeric(&quot;12 3&quot;) = false
	 * StringUtils.isNumeric(&quot;ab2c&quot;) = false
	 * StringUtils.isNumeric(&quot;12-3&quot;) = false
	 * StringUtils.isNumeric(&quot;12.3&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *          the String to check, may be null
	 * @return <code>true</code> if only contains digits, and is non-null
	 */
	public static boolean isNumeric( String str ) {
		if( str == null ) {
			return false;
		}
		int sz = str.length();
		for( int i = 0; i < sz; i++ ) {
			if( Character.isDigit( str.charAt( i ) ) == false ) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the String contains only whitespace.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String ("") will return <code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.isWhitespace(null)   = false
	 * StringUtils.isWhitespace(&quot;&quot;)     = true
	 * StringUtils.isWhitespace(&quot;  &quot;)   = true
	 * StringUtils.isWhitespace(&quot;abc&quot;)  = false
	 * StringUtils.isWhitespace(&quot;ab2c&quot;) = false
	 * StringUtils.isWhitespace(&quot;ab-c&quot;) = false
	 * </pre>
	 * 
	 * @param str
	 *          the String to check, may be null
	 * @return <code>true</code> if only contains whitespace, and is non-null
	 * @since 2.0
	 */
	public static boolean isWhitespace( String str ) {
		if( str == null ) {
			return false;
		}
		int sz = str.length();
		for( int i = 0; i < sz; i++ ) {
			if( ( Character.isWhitespace( str.charAt( i ) ) == false ) ) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 문자열 자르기
	 * 
	 * @param src
	 * @param length
	 * @return
	 */
	public static String leftKor( String src, int length ) {
		return strCut( src, "", length, 0, true, true );
	}

	/**
	 * 문자열 자르기
	 * 
	 * @param src
	 * @param length
	 * @param ignoreTag
	 * @param addDot
	 * @return
	 * @see strCut(String szText, String szKey, int nLength, int nPrev, boolean isNotag, boolean isAdddot)
	 */
	public static String leftKor( String src, int length, boolean ignoreTag, boolean addDot ) {
		return strCut( src, "", length, 0, ignoreTag, addDot );
	}

	/**
	 * 바이트 단위 문자열 자르기 {@link http://loea.tistory.com/entry/javajsp-에서-바이트단위로-문자열-자르기한글깨짐없이}
	 * 
	 * @param szText
	 *          대상 문자열
	 * @param szKey
	 *          시작위치로 할 키워드
	 * @param nLength
	 *          자를 길이
	 * @param nPrev
	 *          키워드 위치에서 얼마나 이전길이만큼 포함할 것인가
	 * @param isNotag
	 *          태그를 없앨것인가
	 * @param isAdddot
	 *          긴문자열일 경우 "..."을 추가할 것인가
	 * @return
	 */
	public static String strCut( String szText, String szKey, int nLength, int nPrev, boolean isNotag, boolean isAdddot ) {

		String r_val = szText;
		int oF = 0, oL = 0, rF = 0, rL = 0;
		int nLengthPrev = 0;
		Pattern p = Pattern.compile( "<(/?)([^<>]*)?>", Pattern.CASE_INSENSITIVE ); // 태그제거 패턴

		if( isNotag ) {
			r_val = p.matcher( r_val ).replaceAll( "" );
		} // 태그 제거
		r_val = r_val.replaceAll( "&amp;", "&" );
		r_val = r_val.replaceAll( "(!/|\r|\n|&nbsp;)", "" ); // 공백제거

		try {
			byte[] bytes = r_val.getBytes( "UTF-8" ); // 바이트로 보관
			if( szKey != null && !szKey.equals( "" ) ) {
				nLengthPrev = ( r_val.indexOf( szKey ) == -1 ) ? 0 : r_val.indexOf( szKey ); // 일단 위치찾고
				nLengthPrev = r_val.substring( 0, nLengthPrev ).getBytes( "MS949" ).length; // 위치까지길이를 byte로 다시 구한다
				nLengthPrev = ( nLengthPrev - nPrev >= 0 ) ? nLengthPrev - nPrev : 0; // 좀 앞부분부터 가져오도록한다.
			}

			// x부터 y길이만큼 잘라낸다. 한글안깨지게.
			int j = 0;

			if( nLengthPrev > 0 )
				while( j < bytes.length ) {
					if( ( bytes[j] & 0x80 ) != 0 ) {
						oF += 2;
						rF += 3;
						if( oF + 2 > nLengthPrev ) {
							break;
						}
						j += 3;
					}
					else {
						if( oF + 1 > nLengthPrev ) {
							break;
						}
						++oF;
						++rF;
						++j;
					}
				}

			j = rF;

			while( j < bytes.length ) {
				if( ( bytes[j] & 0x80 ) != 0 ) {
					if( oL + 2 > nLength ) {
						break;
					}
					oL += 2;
					rL += 3;
					j += 3;
				}
				else {
					if( oL + 1 > nLength ) {
						break;
					}
					++oL;
					++rL;
					++j;
				}
			}

			//TODO: 확인해서 수정
			if( bytes.length < rL )
				rL = bytes.length;

			r_val = new String(bytes, rF, rL, "UTF-8"); // charset 옵션

			if (isAdddot && rF + rL + 3 <= bytes.length)
			{
				r_val += "...";
			} // ...을 붙일지말지 옵션
		}
		catch (UnsupportedEncodingException e)
		{
			LOGGER.error("UnsupportedEncodingException: ", e);
		}

		return r_val;
	}

	public static boolean isImage( Object fileNm ) {

		String[] IMG_EXT = new String[] { ".jpg", ".jpeg", ".png", ".gif", ".bmp" };

		String imageNm = StringUtil.noNull( fileNm );

		if( imageNm.equals( "" ) )
			return false;

		// TODO: 아씨 대소문자 구분하네.. 지저분하니 정규식으로 바꾸자.
		for( int i = 0; i < IMG_EXT.length; i++ )
			if( imageNm.toLowerCase().endsWith( IMG_EXT[i] ) )
				return true;

		return false;
	}

	/**
	 * 데이터베이스에서 넣은 VARCHAR 타입의 날짜를 포맷하기<br/>
	 * 8자리, 12자리, 14자리에 대해 시:분:초 까지 리턴
	 * @param stringDt
	 * @return yyyy.MM.dd hh:mm:ss 형식
	 */
	public static String convertDt( Object stringDt ) {
		if( stringDt == null )
			return "";
		
		String result = stringDt.toString();
		
		result = result.replaceAll( "\\D", "" );
		
		if( result.length() < 8 )
			return "";
		
		//TODO:날짜+시각까지 저장한 경우가 있으면? 데이터베이스에 시각을 저장하지 않음.
		
		//TODO:필요한 경우 format을 추가로 던질 수 있게 지정. yyyy년 mm월 dd일 등
		if( result.length() == 8 )
			return result.substring( 0, 4 ) + "." + result.substring( 4, 6 ) + "." + result.substring( 6, 8 );
		else if( result.length() == 12 )
			return result.substring( 0, 4 ) + "." + result.substring( 4, 6 ) + "." + result.substring( 6, 8 ) + " " + result.substring( 8, 10 ) + ":" + result.substring( 10, 12 );
		else
			return result.substring( 0, 4 ) + "." + result.substring( 4, 6 ) + "." + result.substring( 6, 8 ) + " " + result.substring( 8, 10 ) + ":" + result.substring( 10, 12 ) + ":" + result.substring( 12, 14 );
			
	}
	
	/**
	 * 
	 * @param stringDt
	 * @return hh:mm:ss 형식
	 */
	public static String convertTm( Object stringDt ) {
		if( stringDt == null )
			return "";
		
		String result = stringDt.toString();
		
		result = result.replaceAll( "\\D", "" );
		
		if( result.length() < 4 )
			return "";
		
		//TODO:필요한 경우 format을 추가로 던질 수 있게 지정.
		
		if( result.length() == 4 )
			return result.substring( 0, 2 ) + ":" + result.substring( 2, 4 );
		else if( result.length() == 6 )
			return result.substring( 0, 2 ) + ":" + result.substring( 2, 4 ) + ":" + result.substring( 4, 6 );
		else
			return "";
			
	}
	
	
	
	
	/**
	 * 
	 * @param date1
	 * @param date2
	 * @param format
	 * @return
	 */
	public static Integer dateDiff( Date date1, Date date2, String format ) {
		if( date1 == null || date2 == null )
			return null;
		
		long time1 = ((Date) date1).getTime();
		long time2 = ((Date) date2).getTime();
		long diff = time1 - time2;
		
		if( format.equals( "d")) {
			return Math.round( ((float) diff) / 1000 / 60 / 60 / 24 );
		}
		else if( format.equals( "h")) {
			return Math.round( ((float) diff) / 1000 / 60 / 60 );
		}
		else if( format.equals( "mi")) {
			return Math.round( ((float) diff) / 1000 / 60 );
		}
		else if( format.equals( "s")) {
			return Math.round( ((float) diff) / 1000 );
		}
		else
			return null;
		
	}
	
	/**
	 * 전화번호 등 데이터베이스에 하이픈(-)이 들어간 값을 수정 폼 등에서 쪼개 넣을 경우<br/>
	 *
	 * @param item	원본 맵
	 * @param itemNm 파싱할 키
	 * @return 파싱된 맵
	 */
	public static HashMap<String, Object> splitDash( HashMap<String, Object> item, String itemNm ) {
		// TODO:기본값을 하이픈(-)으로 두고, 토큰도 입력받을 수 있게 하자.
		HashMap<String, Object> myMap = new HashMap<String, Object>();
		// 원본도 살리자.
		myMap.put( itemNm, item.get( itemNm ) );
		
		if( item.containsKey( itemNm )) {
			String[] telArray = noNull(item.get( itemNm )).split( "-" );
			for( int i = 0; i < telArray.length; i++ ) {
				myMap.put( itemNm + (i + 1), telArray[i] );
			}
		}
		
		return myMap;
	}
	
	/**
	 * splitDash 의 반대 버전
	 * @param item
	 * @param itemNm
	 * @return
	 * @deprecated	보류. 사용방법이 별로 안좋다.
	 */
	public static String mergeDash( HashMap<String, Object> item, String itemNm ) {
		// 날짜를 1, 2, 3으로 던져야하나? 고민해보자.
		String myStr = "";
		
		// 최대 10개 루프
		for( int i = 1; i < 10; i++ ) {
			if( !item.containsKey( itemNm + i ) )
				break;
			myStr += noNull(item.get( itemNm + i )) ;
		}
		
		// 마지막에 붙은 하이픈 제거
		if( myStr.length() > 0 )
			myStr = myStr.substring( 0, myStr.length() - 1 );
		
		return myStr;
	}
	
	
	/**
	 * JSTL 나누기 소수가져오기.
	 * @param item
	 * @param itemNm
	 * @return
	 */
	public static Float divideFloat( Float upVal, Float downVal ) {
		if(downVal==0 || downVal==null || upVal==null) 
			return (float)0;
		return upVal/downVal;
	}
	
	/**
	 * encode XOR
	 * @param val
	 * @return
	 */
	public static String encodeXor( String val ) {
		int xorKey = 3;	// 내맘
		
		String result = "";
		
		for( int i = 0; i < val.length(); i++ ) {
			result += Character.toString( (char) (val.charAt( i ) ^ xorKey) );
		}

		return result;
	}
	
	/**
	 * Base64 인코딩
	 * 
	 * @param input
	 * @return
	 * @deprecated 사용하지말 것
	 */
	public static String base64Encode( String input ) {

		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		byte[] b1 = input.getBytes();
		String result = encoder.encode( b1 );

		return result;

	}



	/**
	 * Base64 디코딩
	 * 
	 * @param input
	 * @return 
	 * @throws IOException
	 * @deprecated 사용하지말 것
	 */
	public static String base64Decode( String input ) throws IOException {
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		byte[] b1 = decoder.decodeBuffer( input );
		String result = new String( b1 );

		return result;

	}
	
	public static Date dateAdd( Date date1, String flag, int value ) {
		
		if( date1 == null)
			return null;
		
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime( date1 );
		
		if( flag.equalsIgnoreCase( "d" ) )
			cal1.add( Calendar.DATE, value );
		else if( flag.equalsIgnoreCase( "h" ))
			cal1.add( Calendar.HOUR_OF_DAY, value );
		
		return cal1.getTime();
		
	}
	
	public static String replaceAll( String input, String regex, String replacement ) {
		if( !(input instanceof String) )
			return null;
		
		return input.replaceAll( regex, replacement );
	}
	
	public static boolean isMatch(CharSequence input, String regex) {
    return Pattern.compile(regex).matcher(input).matches();
  }
	
	/**
	 * 자바스크립트의 escape 구현
	 * @param src
	 * @return
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	/**
	 * 자바스크립트의 unescape 구현
	 * @param src
	 * @return
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	/**
	 * <pre>
	 * 랜덤번호 추출하기
	 * </pre>
	 * 
	 * @param int length
	 * @return String auth
	 */
	public static String getAuthNumber(int length)
	{
		Random rnd = new Random();
		String auth = "";

		for (int i = 0; i < length; i++)
		{
			rnd.setSeed(new Date().getTime()); //seed를 예측 불가능한 long타입으로 설정  
			auth = auth + (rnd.nextInt()%10);
		}

		return auth;
	}

	public static String todayText()
	{
		String day = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		return day;
	}
	/**
	 * 요일 가져오기
	 * @param src
	 * @param length
	 * @return
	 */
	public static String getDay( String someday) {
		someday = someday.replace("-", "");
		someday = someday.replace(".", "");
		if(someday == null || (someday.length() != 8) ){
			return "";
		}
		String []weeks2 = {"일","월","화","수","목","금","토"};
		Calendar c2= Calendar.getInstance();

		c2.set(Integer.parseInt(someday.substring(0,4))
				,Integer.parseInt(someday.substring(4,6))-1
				,Integer.parseInt(someday.substring(6,8)) );

		return weeks2[c2.get(Calendar.DAY_OF_WEEK)-1];
	}
	
	public static String getDay( Object yyyy, Object mm, Object dd) {
		String y = yyyy.toString();
		String m = mm.toString();
		String d = dd.toString();
		
		if(m.length() <2)
			m = "0"+m;
		if(d.length() <2)
			d = "0"+d;
		String ymd = y+m+d;
		return getDay(ymd);
		
	}
	// 며칠후의 날을 구한다.
	public static String getNextDay( int day) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,day);
		String curYear = StringUtil.toString(cal.get(Calendar.YEAR));
		String nowMonth = StringUtil.toStrZero(cal.get(Calendar.MONTH)+1);
		String nowDay = StringUtil.toStrZero(cal.get(Calendar.DATE));
		return curYear+nowMonth+nowDay;
		
	}
	
	//해당년월의 마지막 날짜를 구한다.
	public static String getLastDayOfMonth(String yearMonth){  
		  String year = yearMonth.substring(0,4);
		  String month = yearMonth.substring(4,6);
		  
		  int _year = Integer.parseInt(year);
		  int _month = Integer.parseInt(month);
		  
		  Calendar calendar = Calendar.getInstance();
		  calendar.set(_year, (_month-1), 1); //월은 0부터 시작  
		  String lastDay = String.valueOf(calendar.getActualMaximum(Calendar.DATE));
		  System.out.println("lastDay of present month  : " + lastDay);
		  
		  return lastDay;
		 }

	/**
	 * 구분자로 구성된 문자열을 문자배열로 변환한다.
	 * 
	 * @param str 문자열
	 * @param delimiter 구분자
	 */
	public static String[] toStringArray(String str, String delimiter) {
        String[] result = null;
        result =  str.split(delimiter);
		return result;
	}

	/**
     * 문자열 앞에 문자를 채운다
     * 
     * @param s
     * @param chr
     *            null이면 "0"으로 변경
     * @param len
     * @return
     */
    public static String leftPad(String s, int len, String chr) {
        if (s == null || "".equals(s))
            return "";
        if (chr == null || "".equals(chr))
            chr = "0";
        if (s.length() >= len)
            return s.substring(0, len);

        for (int i = s.length(); i < len; i++) {
            s = chr + s;
        }
        return s;
    }

    /**
     * 주어진 문자로 길이만큼 채운 후 돌려준다.
     * 
     * @param i 정수
     * @param chr 채울 문자열
     *            null이면 "0"으로 변경
     * @param len 채울 개수
     * @return
     */
    public static String leftPad(int i, int len, String chr) {
        return leftPad(i + "", len, chr);
    }

    /**
     * 주어진 문자로 길이만큼 "0"으로 채운 후 돌려준다.
     * 
     * @param s
     * @param len 채울 개수
     * @return
     */
    public static String leftPad(String s, int len) {
        return leftPad(s, len, null);
    }

    /**
     * 주어진 문자로 길이만큼 "0"으로 채운 후 돌려준다.
     * 
     * @param i
     * @param len 채울 개수
     * @return
     */
    public static String leftPad(int i, int len) {
        return leftPad(i + "", len, null);
    }


	/**
	 * 포멧 변경 (1234-56-78 - > 12345678)
	 * 
	 * @param obj
	 * @return
	 */
	public static String formatConvert(Object obj, String gubun)
	{
		String returnVal = "";
		returnVal = StringUtil.noNull(obj).replaceAll(gubun, "");
		return returnVal;
	}

	/**
	 * 16진 문자열을 byte 배열로 변환한다.
	 */
	public static byte[] hexToByteArray(String hex)
	{
		if (hex == null || hex.length() % 2 != 0)
		{
			return new byte[] {};
		}

		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < hex.length(); i += 2)
		{
			byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
			bytes[(int) Math.floor(i / 2)] = value;
		}
		return bytes;
	}
	
	
	public static String randomValue() {
	  return randomValue("C",6);
	}
	//원하는 비밀번호 구성 및 원하는 길이의 랜덤 비밀번호 생성 (매개변수 : 구성, 사이즈)
  public static String randomValue(String type, int cnt) {
    Random rnd = new Random();
    rnd.setSeed(new Date().getTime()); //seed를 예측 불가능한 long타입으로 설정
    
    StringBuffer strPwd = new StringBuffer();
    char str[] = new char[1];
    // 특수기호 포함
    if (type.equals("P")) {
      for (int i = 0; i < cnt; i++) {
        str[0] = (char) ((rnd.nextInt(9) * 94) + 33);
        strPwd.append(str);
      }
      // 대문자로만
    } else if (type.equals("A")) {
      for (int i = 0; i < cnt; i++) {
        str[0] = (char) ((rnd.nextInt(9) * 26) + 65);
        strPwd.append(str);
      }
      // 소문자로만
    } else if (type.equals("S")) {
      for (int i = 0; i < cnt; i++) {
        str[0] = (char) ((rnd.nextInt(9) * 26) + 97);
        strPwd.append(str);
      }
      // 숫자형으로
    } else if (type.equals("I")) {
      int strs[] = new int[1];
      for (int i = 0; i < cnt; i++) {
        strs[0] = (int) (rnd.nextInt(9) * 9);
        strPwd.append(strs[0]);
      }
      // 소문자, 숫자형
    } else if (type.equals("C")) {
      for (int i = 0; i < cnt; i++) {
        if (rnd.nextBoolean()) {
          strPwd.append((char) ((int) (rnd.nextInt(26)) + 97));
        } else {
          strPwd.append((rnd.nextInt(10)));
        }
      }
    }
    return strPwd.toString();
  }
  

  public static String joinIp(HttpServletRequest request) {
    String ip = request.getHeader("X-FORWARDED-FOR"); 
	     
    if (ip == null || ip.length() == 0) {
    	ip = request.getHeader("Proxy-Client-IP");
    }

    if (ip == null || ip.length() == 0) {
    	ip = request.getHeader("WL-Proxy-Client-IP"); 
    }

    if (ip == null || ip.length() == 0) {
    	ip = request.getRemoteAddr();
    }
    
    return ip;
  }

}