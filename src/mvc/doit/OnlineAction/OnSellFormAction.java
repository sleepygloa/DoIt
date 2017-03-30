package mvc.doit.OnlineAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Online.OnBookDto;
import mvc.doit.Online.OnBookIntroDto;
import mvc.doit.Online.OnDao;
import mvc.doit.SuperAction.SuperAction;

public class OnSellFormAction implements SuperAction {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 	request.setCharacterEncoding("utf-8");
	 	
	 	
	 	
	 	//���ǿ� �ִ� ID�� ������ ����.
	 	HttpSession session = request.getSession();
		String id = (String)session.getAttribute("memId");
		
		request.setAttribute("id", id);
		
		int d_bno = 0; // �׳� �������� ������ ��� �Ǹ��� �Ǹ� �������� �̵���.
		if(request.getParameter("d_bno") == null){
			return "/d_online/onBookReturn.jsp";
		}else{
			d_bno = Integer.parseInt(request.getParameter("d_bno"));
		}
		
		
		if(d_bno != 0){
			//1�۹�ȣ�� å�� �̸��� ã��, �ߺ��� å�̸����� å�� ������ ������ �ҷ��´�.
			//2�۹�ȣ�� ������ �̸��� å�� �� ������ å�� ������ �ҷ��´�.
			
			OnDao dao = OnDao.getInstance();
			OnBookDto article = dao.getOnBookArticle(d_bno);
			int d_bcode = article.getD_bcode();
			OnBookIntroDto obiDto = dao.Admin_OnBookIntro_load(d_bcode);
			
			request.setAttribute("obiDto", obiDto);
			request.setAttribute("article", article);
			
		 	String d_norder = obiDto.getD_norder();
//		 	d_norder = d_norder.replace("<br/>", "\r\n");
		 	
		 	String d_nintro = obiDto.getD_nintro();
//		 	d_nintro = d_nintro.replace("<br/>", "\r\n");
		 	
		 	request.setAttribute("d_norder", d_norder);
		 	request.setAttribute("d_nintro", d_nintro);
			
			
			
		};
		
		
		request.setAttribute("d_bno", d_bno);
	
	return "/d_online/onSellForm.jsp";
	}

}
