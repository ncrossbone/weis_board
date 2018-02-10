package egovframework.common.jfile;

import egovframework.common.util.EgovProperties;

/**
 *  클래스
 * @author 정호열
 * @since 2010.10.17
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일        수정자       수정내용
 *  -------       --------    ---------------------------
 *   2010.10.17   정호열       최초 생성
 *
 * </pre>
 */
public class JProperties
{
	public static final String getString(String key)
	{
		return EgovProperties.getProperty(key);
	}
}
