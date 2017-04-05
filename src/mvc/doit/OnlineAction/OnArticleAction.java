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
			
			//----�Ѿ���� ��, Action���� ����� �������� -------------------------------------------------
			String pagetype = request.getParameter("b"); //ȸ�� ���� ���������� ����, ������ �Ǹ�
				pagetype = request.getParameter("s"); //ȸ�� �Ǹ� ���������� ����, ������ ����
			
			int d_bno = 0; //article ��ȣ �ޱ�
			if(request.getParameter("d_bno") != "0"){
				d_bno = Integer.parseInt(request.getParameter("d_bno")); 
			}
			String pageNum = request.getParameter("pageNum");//��������ȣ �ޱ�
	        int d_bvalue = 0;
			String d_bname = ""; //���� �ʱ�ȭ
			OnBookDto article = null;
			
	        //-----------------
			OnDao dao = OnDao.getInstance();//DB����	
			if(d_bno == 0){
			}else{
					article = dao.getOnBookArticle(d_bno);		
			} 
			int d_bcode = article.getD_bcode();
			OnBookIntroDto obiDto = dao.Admin_OnBookIntro_load(d_bcode);//����, �Ұ��� �ҷ�����
		     
			//�ǸŰ� ����Ʈ 
			List sellList = null;

			if(pagetype != null && pagetype=="buypage"){
		        sellList = dao.getD_bsellvalue(d_bno);//��ü ���� �� 
				request.setAttribute("sellList", sellList);		        
			}else{}
			
			


			//d_bname���� �ݾ��� �̾� ��޺� ����ݾ��� �����ִ� Dao-----------------------------------------------------
			d_bname = article.getD_bname();
			d_bvalue =  dao.getFindNameToValue(d_bname);

			int d_bpurchasevalueS = (int)((double)d_bvalue * 0.8)/100 * 100; //������ 80%������ �ʿ����� ���� ����
			int d_bpurchasevalueA = (int)((double)d_bvalue * 0.6)/100 * 100; //������ 60%������ �ʿ����� ���� ����
			int d_bpurchasevalueB = (int)((double)d_bvalue * 0.4)/100 * 100; //������ 40%������ �ʿ����� ���� ����


			request.setAttribute("d_bno", d_bno);				
			request.setAttribute("pageNum", pageNum);	       
			request.setAttribute("article", article);
			request.setAttribute("obiDto",obiDto);
			request.setAttribute("pagetype", pagetype);
			request.setAttribute("sellList", sellList);
			request.setAttribute("d_bpurchasevalueS", d_bpurchasevalueS);
			request.setAttribute("d_bpurchasevalueA", d_bpurchasevalueA);
			request.setAttribute("d_bpurchasevalueB", d_bpurchasevalueB);

			   
			return "/d_online/onArticle.jsp";
	}
}

