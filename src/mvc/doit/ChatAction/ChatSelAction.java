package mvc.doit.ChatAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.SuperAction.SuperAction;

public class ChatSelAction implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("UTF-8");
	
		//��ü ����
		ChatDao cdao = ChatDao.getInstance();
		
		List chatList = cdao.getChat();
		request.setAttribute("chatList", chatList);
		
		return "/d_manage/chat_cont.jsp";
	}

}
