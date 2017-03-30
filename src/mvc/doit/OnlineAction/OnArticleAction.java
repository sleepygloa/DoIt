package mvc.doit.OnlineAction;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.doit.Online.OnBookDto;
import mvc.doit.Online.OnBookIntroDto;
import mvc.doit.Online.OnDao;
import mvc.doit.SuperAction.SuperAction;

public class OnArticleAction implements SuperAction {
	public String execute(HttpServletRequest request, HttpServletResponse response)  throws Exception{
			
			String pagetype = request.getParameter("b"); //ȸ�� ���� ���������� ����, ������ �Ǹ�
				pagetype = request.getParameter("s"); //ȸ�� �Ǹ� ���������� ����, ������ ����
				
			OnDao dao = OnDao.getInstance();//DB����	
							
			int d_bno =  Integer.parseInt(request.getParameter("d_bno")); 
			
			String pageNum = request.getParameter("pageNum");//��������ȣ �ޱ�


			OnBookDto article =  dao.getOnBookArticle(d_bno);
			int d_bcode = article.getD_bcode();
			OnBookIntroDto obiDto = dao.Admin_OnBookIntro_load(d_bcode);
		     
			//�ǸŰ� ����Ʈ 
			List sellList = null;

			if(pagetype != null && pagetype=="buypage"){
		        sellList = dao.getD_bsellvalue(d_bno);//��ü ���� �� 
				request.setAttribute("sellList", sellList);		        
			}else{}
			

			request.setAttribute("d_bno", d_bno);				
			
			request.setAttribute("pageNum", pageNum);	       
			request.setAttribute("article", article);
			request.setAttribute("obiDto",obiDto);
			request.setAttribute("pagetype", pagetype);
			request.setAttribute("sellList", sellList);

			   
			return "/d_online/onArticle.jsp";
	}
}

