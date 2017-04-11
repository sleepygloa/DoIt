package mvc.doit.ChatAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.SuperAction.SuperAction;

public class ChatAction implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("UTF-8");
		
		//�Ķ���� ����
		String cont = request.getParameter("cont"); //�Է��� ����
		HttpSession session = request.getSession();
		String memName = (String)session.getAttribute("memName"); //�̸�
		String memPic = (String)session.getAttribute("memPic"); //����
		if(memPic == null){
			memPic = "ma_img.jpg";
		}
		
		
		//��ü ����
		ChatDao cdao = ChatDao.getInstance();
		cdao.insMess(cont, memName, memPic);

		return "/d_manage/manager.do";
	}

}
