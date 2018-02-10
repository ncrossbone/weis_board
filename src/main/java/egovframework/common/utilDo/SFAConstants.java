package egovframework.common.utilDo;

import java.util.HashMap;

public final class SFAConstants {
	
	public static String RESULT_COMMON_ERROR       = "commonError";
	public static String RESULT_FILEUPLOAD_ERROR   = "fileUploadError";
	public static String RESULT_FILE_STREAM        = "fileStream";
	public static String RESULT_VALIDATION_ERROR   = "validationError";
	public static String RESULT_AUTH_ERROR         = "authError";
	public static String RESULT_ON_WORK_ERROR      = "onWorkError";

	public static String SUCCESS           = "0000";
	public static String NO_DATA_FOUND     = "1001";
	public static String FAILED_TO_SAVE    = "1002";
	public static String FAILED_TO_DELETE  = "1003";
	public static String ALEADY_DATA       = "1004";
	public static String INVALID_DATA      = "1005";
	public static String INVALID_SQL       = "1006";
	public static String FAILED_TO_MODIFY  = "1007";
	public static String NO_ACCESS_RIGHT   = "2001";
	public static String INVALID_USER      = "2002";
	public static String INVALID_PASSWORD  = "2003";
	public static String INVALID_DEVICE    = "2004";
	public static String NO_REGIST_USER    = "2005";
	public static String DROP_MEMBER       = "2006";
	public static String DIS_SESSION       = "2007";
	public static String NO_AUTH     	   = "2008";
	public static String FALSE_TYPE        = "2009";
	public static String PARAM_NULL        = "9996";
	public static String INVALID_METHOD    = "9997";
	public static String INVALID_URL       = "9998";
	public static String ERROR_ETC         = "9999";
	public static String NO_CONTENT   = "3001";
	public static String SUCCESS_REG  = "3002";
	public static String SUCCESS_UDT  = "3003";
	public static String SUCCESS_DEL  = "3004";
	public static String CHECK_STATUS   = "4001";
	public static String SYSTEM_ERROR   = "9999";

	
	public static HashMap<Object, String[]> ERROR_MSG = new HashMap<Object, String[]>();
	static {
			ERROR_MSG.put(SUCCESS           , new String[] {"SUCCESS"         , "성공"});
			ERROR_MSG.put(NO_DATA_FOUND     , new String[] {"NO DATA FOUND"   , "데이터가 존재하지 않습니다."});
			ERROR_MSG.put(FAILED_TO_SAVE    , new String[] {"FAILED TO SAVE"  , "데이터 저장에 실패하였습니다."});
			ERROR_MSG.put(FAILED_TO_DELETE  , new String[] {"FAILED TO DELETE", "데이터 삭제에 실패하였습니다."});
			ERROR_MSG.put(ALEADY_DATA       , new String[] {"ALEADY DATA"     , "이미 등록된 데이터입니다."});
			ERROR_MSG.put(INVALID_DATA      , new String[] {"INVALID DATA"    , "잘못된데이터입니다."});
			ERROR_MSG.put(INVALID_SQL       , new String[] {"INVALID SQL"     , "잘못된 SQL문입니다."});
			ERROR_MSG.put(FAILED_TO_MODIFY  , new String[] {"FAILED_TO_MODIFY", "데이터 수정에 실패하였습니다."});
			ERROR_MSG.put(NO_ACCESS_RIGHT   , new String[] {"NO ACCESS RIGHT" , "접근권한이 없습니다."});
			ERROR_MSG.put(INVALID_USER      , new String[] {"INVALID USER"    , "사용자 정보가 일치하지 않습니다."});
			ERROR_MSG.put(FALSE_TYPE        , new String[] {"FALSE_TYPE"    , "재직 상태가 아닙니다."});
			ERROR_MSG.put(INVALID_PASSWORD  , new String[] {"INVALID PASSWORD", "비밀번호가 일치하지 않습니다."});
			ERROR_MSG.put(INVALID_DEVICE    , new String[] {"INVALID DEVICE"  , "분실된 단말기입니다. 관리팀에 연락해 주세요."});
			ERROR_MSG.put(NO_REGIST_USER    , new String[] {"NO REGIST USER"  , "사용자 정보가 등록 되 있지 않습니다. 회원가입을 해주십시오."});
			ERROR_MSG.put(DROP_MEMBER       , new String[] {"DROP_MEMBER"     , "탈퇴한 회원입니다."});
			ERROR_MSG.put(DIS_SESSION       , new String[] {"DIS_SESSION"     , "로그인이 필요합니다."});
			ERROR_MSG.put(PARAM_NULL        , new String[] {"PARAM_NULL"      , "수신 파라미터가 NULL 입니다."});
			ERROR_MSG.put(INVALID_METHOD    , new String[] {"INVALID METHOD"  , "부적절한 Method 호출"});
			ERROR_MSG.put(INVALID_URL       , new String[] {"INVALID URL"     , "잘못된 주소입니다."});
			ERROR_MSG.put(NO_AUTH         	, new String[] {"NO_AUTH"    	  , "권한이 없습니다."});
			ERROR_MSG.put(ERROR_ETC         , new String[] {"ERROR / ETC."    , "기타 오류"});
			ERROR_MSG.put(NO_CONTENT        , new String[] {"NO_CONTENT"      , "게시물이 없습니다."});
			ERROR_MSG.put(SUCCESS_REG       , new String[] {"SUCCESS_REG"     , "등록 하였습니다."});
			ERROR_MSG.put(SUCCESS_UDT       , new String[] {"SUCCESS_UDT"     , "수정 하였습니다."});
			ERROR_MSG.put(SUCCESS_DEL       , new String[] {"SUCCESS_DEL"     , "삭제 하였습니다."});
			ERROR_MSG.put(CHECK_STATUS      , new String[] {"CHECK_STATUS"    , "상태 값을 확인해 주시기 바랍니다."});
			ERROR_MSG.put(SYSTEM_ERROR      , new String[] {"SYSTEM_ERROR"    , "시스템 오류 발생."});
	};
}
