package egovframework.cms.attach.model;


/**
 * 
 * @FileName    : FileInfo.java
 * @Project     : kr.co.aspn
 * @Date        : 2014. 09. 12.
 * @author      : PSJ
 * @Description : 파일정보 모델 객체 정의
 * @History     : 최초작성 ( 2014. 09. 12 )
 */
public class AttachVo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String atchfl_id			= "";
	private String atch_occr_tb_nm      = "";
	private String atch_occr_menu_id    = "";
	private String atch_occr_menu_nm    = "";
	private String atchfl_div           = "";
	private String atchfl_seq           = "";
	private String atchfl_div_nm        = "";
	private String atchfl_save_path     = "";
	private String atchfl_save_logic_nm = "";
	private String atchfl_physc_nm      = "";
	private String atchfl_extnm         = "";
	private long   atchfl_size          = 0L;

	public String getAtchfl_id() {
		return atchfl_id;
	}
	public void setAtchfl_id(String atchfl_id) {
		this.atchfl_id = atchfl_id;
	}
	public String getAtch_occr_tb_nm() {
		return atch_occr_tb_nm;
	}
	public void setAtch_occr_tb_nm(String atch_occr_tb_nm) {
		this.atch_occr_tb_nm = atch_occr_tb_nm;
	}
	public String getAtch_occr_menu_id() {
		return atch_occr_menu_id;
	}
	public void setAtch_occr_menu_id(String atch_occr_menu_id) {
		this.atch_occr_menu_id = atch_occr_menu_id;
	}
	public String getAtch_occr_menu_nm() {
		return atch_occr_menu_nm;
	}
	public void setAtch_occr_menu_nm(String atch_occr_menu_nm) {
		this.atch_occr_menu_nm = atch_occr_menu_nm;
	}
	public String getAtchfl_div() {
		return atchfl_div;
	}
	public void setAtchfl_div(String atchfl_div) {
		this.atchfl_div = atchfl_div;
	}
	public String getAtchfl_seq() {
		return atchfl_seq;
	}
	public void setAtchfl_seq(String atchfl_seq) {
		this.atchfl_seq = atchfl_seq;
	}
	public String getAtchfl_div_nm() {
		return atchfl_div_nm;
	}
	public void setAtchfl_div_nm(String atchfl_div_nm) {
		this.atchfl_div_nm = atchfl_div_nm;
	}
	public String getAtchfl_save_path() {
		return atchfl_save_path;
	}
	public void setAtchfl_save_path(String atchfl_save_path) {
		this.atchfl_save_path = atchfl_save_path;
	}
	public String getAtchfl_save_logic_nm() {
		return atchfl_save_logic_nm;
	}
	public void setAtchfl_save_logic_nm(String atchfl_save_logic_nm) {
		this.atchfl_save_logic_nm = atchfl_save_logic_nm;
	}
	public String getAtchfl_physc_nm() {
		return atchfl_physc_nm;
	}
	public void setAtchfl_physc_nm(String atchfl_physc_nm) {
		this.atchfl_physc_nm = atchfl_physc_nm;
	}
	public String getAtchfl_extnm() {
		return atchfl_extnm;
	}
	public void setAtchfl_extnm(String atchfl_extnm) {
		this.atchfl_extnm = atchfl_extnm;
	}
	public long getAtchfl_size() {
		return atchfl_size;
	}
	public void setAtchfl_size(long atchfl_size) {
		this.atchfl_size = atchfl_size;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}