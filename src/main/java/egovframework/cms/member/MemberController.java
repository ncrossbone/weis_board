package egovframework.cms.member;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import egovframework.cms.member.service.MemberService;
import egovframework.common.controller.DefaultController;
/**
 * 관리자 회원 컨트롤러 클래스를 정의한다.
 * @author ICT융합사업부 개발팀 방지환
 * @since 2016.07.26
 * @version 1.0
 * @see <pre>
 * 
 *  == 개정이력(Modification Information) ==
 *  수정일           수정자          수정내용
 *  ---------    --------    ---------------------------
 *  2015.01.26   방지환          최초 생성
 * </pre>
 */
@Controller
public class MemberController extends DefaultController {

  private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "MemberService")
	private MemberService memberService;
}
