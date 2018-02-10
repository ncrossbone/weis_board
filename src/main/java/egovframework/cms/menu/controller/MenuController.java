package egovframework.cms.menu.controller;

//
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

//
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
//

import egovframework.cms.member.MemberVo;
import egovframework.cms.menu.service.MenuService;
import egovframework.common.controller.DefaultController;

@Controller
@RequestMapping("/egov/cms")
public class MenuController extends DefaultController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "MenuService")
	private MenuService menuService;

	private String _menu_id = "";

	/**
	 * @see 왼쪽 frame 영역 분리
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/menu/leftFrame")
	public String leftFrame(Model model) throws Exception {

		try{
			List<Map<String, Object>> leftMenu0List = menuService.getMenuList(commandMap);
	  		
			model.addAttribute("leftMenu0List", leftMenu0List);
		}catch(Exception e){
			e.printStackTrace();
		}
		
  		return "cms/menu/leftFrame";
	}
}
