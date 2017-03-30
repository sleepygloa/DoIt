package mvc.doit.OnlineAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.doit.Online.OnBookDto;
import mvc.doit.Online.OnDao;
import mvc.doit.SuperAction.SuperAction;

public class OnPurchaseInfoAction implements SuperAction {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 
		request.setCharacterEncoding("utf-8");

		
		int d_bno = 0;
		if(request.getParameter("d_bno") != null){
			d_bno = Integer.parseInt(request.getParameter("d_bno"));
		};

		
		
        List articleList = null; //����Ʈ �ʱ�ȭ
        OnDao dao = OnDao.getInstance();//DB����
		OnBookDto article = new OnBookDto();
        
		//�������������� �����Ѵٸ� d_bno ��, �������� List�� �����Ͽ��� d_bno 
		if(d_bno == 0){

		}else{
			article = dao.getOnBookArticle(d_bno);
		} 
			
		request.setAttribute("article", article);
		
	return "/d_online/onPurchaseInfo.jsp";
	
	} 
}
