package egovframework.cms.member;

public class MemberVo {
  
	/**
	 * DB 스키마
	 */
	private String user_mng_seq			  	= ""; //사용자관리번호/고유한 키(PK)
	private String user_id                	= ""; //사용자ID / 로그인 ID
	private String user_pw                	= ""; //사용자비밀번호/암호화 컬럼
	private String user_name              	= ""; //사용자이름/
	private String tel_no                 	= ""; //전화번호/
	private String fax_no                 	= ""; //팩스번호/
	private String hp_no                  	= ""; //이동전화/암호화 컬럼
	private String email                  	= ""; //이메일/암호화 컬럼
  	private String comp                   	= ""; //회사/소속회사
  	private String dept                   	= ""; //부서/근무부서
  	private String rnki                   	= ""; //직급/
  	private String posi                   	= ""; //직위/
  	private String user_autho_grade       	= ""; //로그인 사용자 권한등급/공통코드 "SYS000" 적용
  	private String data_use_yn            	= ""; //사용여부/Y-사용, N-미사용/디폴트(Y)
  	private String fist_regi_ip           	= ""; //최초등록IP/클라이언트의 IP
  	private String fist_regi_user_mng_seq 	= ""; //최초등록사용자/데이터를 최초로 등록한 사용자의 "사용자관리번호"
  	private String fist_regi_dttm         	= ""; //최초등록일시/년월일시분초
  	private String last_upd_ip            	= ""; //최종수정IP/수정작업이 발생한 클라이언트 IP
  	private String last_upd_user_mng_seq  	= ""; //최종수정사용자/데이터를 최종으로 수정한 사용자의 "사용자관리번호"
  	private String last_upd_dttm          	= ""; //최종수정일시/년월일시분초
  
  	/**
  	 * 조건 파라미터 
  	 */
  	private String res_cd          			= ""; //처리 코드
  	private String res_msg	         		= ""; //처리 메세지
	private String cookie_save_yn 			= ""; //로그인 정보 쿠키 저장 여부 체크
	private String pwd              		= ""; //패스워드 원본 데이터 
  	
	public String getUser_mng_seq() {
		return user_mng_seq;
	}
	public void setUser_mng_seq(String user_mng_seq) {
		this.user_mng_seq = user_mng_seq;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getTel_no() {
		return tel_no;
	}
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}
	public String getFax_no() {
		return fax_no;
	}
	public void setFax_no(String fax_no) {
		this.fax_no = fax_no;
	}
	public String getHp_no() {
		return hp_no;
	}
	public void setHp_no(String hp_no) {
		this.hp_no = hp_no;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getComp() {
		return comp;
	}
	public void setComp(String comp) {
		this.comp = comp;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getRnki() {
		return rnki;
	}
	public void setRnki(String rnki) {
		this.rnki = rnki;
	}
	public String getPosi() {
		return posi;
	}
	public void setPosi(String posi) {
		this.posi = posi;
	}
	public String getUser_autho_grade() {
		return user_autho_grade;
	}
	public void setUser_autho_grade(String user_autho_grade) {
		this.user_autho_grade = user_autho_grade;
	}
	public String getData_use_yn() {
		return data_use_yn;
	}
	public void setData_use_yn(String data_use_yn) {
		this.data_use_yn = data_use_yn;
	}
	public String getFist_regi_ip() {
		return fist_regi_ip;
	}
	public void setFist_regi_ip(String fist_regi_ip) {
		this.fist_regi_ip = fist_regi_ip;
	}
	public String getFist_regi_user_mng_seq() {
		return fist_regi_user_mng_seq;
	}
	public void setFist_regi_user_mng_seq(String fist_regi_user_mng_seq) {
		this.fist_regi_user_mng_seq = fist_regi_user_mng_seq;
	}
	public String getFist_regi_dttm() {
		return fist_regi_dttm;
	}
	public void setFist_regi_dttm(String fist_regi_dttm) {
		this.fist_regi_dttm = fist_regi_dttm;
	}
	public String getLast_upd_ip() {
		return last_upd_ip;
	}
	public void setLast_upd_ip(String last_upd_ip) {
		this.last_upd_ip = last_upd_ip;
	}
	public String getLast_upd_user_mng_seq() {
		return last_upd_user_mng_seq;
	}
	public void setLast_upd_user_mng_seq(String last_upd_user_mng_seq) {
		this.last_upd_user_mng_seq = last_upd_user_mng_seq;
	}
	public String getLast_upd_dttm() {
		return last_upd_dttm;
	}
	public void setLast_upd_dttm(String last_upd_dttm) {
		this.last_upd_dttm = last_upd_dttm;
	}
	public String getRes_cd() {
		return res_cd;
	}
	public void setRes_cd(String res_cd) {
		this.res_cd = res_cd;
	}
	public String getRes_msg() {
		return res_msg;
	}
	public void setRes_msg(String res_msg) {
		this.res_msg = res_msg;
	}
	public String getCookie_save_yn() {
		return cookie_save_yn;
	}
	public void setCookie_save_yn(String cookie_save_yn) {
		this.cookie_save_yn = cookie_save_yn;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}