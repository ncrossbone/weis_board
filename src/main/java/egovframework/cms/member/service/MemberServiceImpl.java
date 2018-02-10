package egovframework.cms.member.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import egovframework.cms.attach.model.AttachVo;
import egovframework.cms.member.MemberVo;
import egovframework.common.mapper.CommonMapper;
import egovframework.common.utilDo.FileHandler;
import egovframework.common.utilDo.SFAConstants;
import egovframework.common.utilDo.SecretUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("MemberService")
public class MemberServiceImpl extends EgovAbstractServiceImpl implements MemberService  {

	
	@Resource(name = "CommonMapper")
	private CommonMapper commonMapper;
	
	private String namespace = "memberMapper.";

	/**
	 * 회원 로그인 정보
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public MemberVo memberLogin(Map<String, Object> param, MemberVo memberVo) throws Exception {
		String inputPwd     = SecretUtils.encryptSha256(memberVo.getUser_pw());
//		String inputPwd     = memberVo.getUser_pw();

		param.put("mId", namespace+"memberLogin");
		memberVo = (MemberVo) commonMapper.get2(param);

		if(memberVo == null) {
			memberVo = this.setResultPacket(SFAConstants.NO_REGIST_USER, memberVo);
		} else {
			if(memberVo.getUser_pw() == null || !memberVo.getUser_pw().equals(inputPwd)) {
				//비번이 맟지않을시
				memberVo = this.setResultPacket(SFAConstants.INVALID_PASSWORD, memberVo);
			} else if("N".equals(memberVo.getData_use_yn())) {
				//관리권한
				memberVo = this.setResultPacket(SFAConstants.DROP_MEMBER, memberVo);
			} else {
				//로그인 성공시
				memberVo = this.setResultPacket(SFAConstants.SUCCESS, memberVo);
			}
		}

		return memberVo;
	}

	/**
	 * 회원 정보 리스트
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public List<Map<String,Object>> getMemberList(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"getMemberList");
		List<Map<String,Object>> menuList = (List<Map<String,Object>>) commonMapper.getList(param);

		return menuList;
	}

	/**
	 * 회원 정보 item
	 * @param Map
	 * @exception Exception Exception
	 */
	@Override
	public Map<String,Object> getMemberItem(Map<String, Object> param) throws Exception {
		param.put("mId", namespace+"getMemberItem");
		Map<String,Object> menuItem = (Map<String,Object>) commonMapper.get(param);
		
		return menuItem;
	}
	
	public int memberUpdate(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"memberUpdate");
		int resVal = (int) commonMapper.update(param);
		
		return resVal;
	}
	
	public int memberInsert(Map<String, Object> param) throws Exception{
		param.put("mId", namespace+"memberInsert");
		int resVal = (int) commonMapper.insert(param);
		
		return resVal;
	}
	
	public MemberVo setResultPacket(String retCd, MemberVo memberVo){
		if(memberVo == null){
			memberVo = new MemberVo();
		}
		
		memberVo.setRes_cd(retCd);
		memberVo.setRes_msg(SFAConstants.ERROR_MSG.get(retCd)[1]);
		
		return memberVo;
	}
	
	public int memberFileTest(HttpServletRequest request, Map<String, Object> param) throws Exception{

		//insert 공급사 성공
		@SuppressWarnings("unused")
		List<AttachVo> fileList = FileHandler.uploadFiles(request, "fileList");
		
		return 1;
	}
}


