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
			
	//----0.�Ѿ���� ��, Action���� ����� �������� -------------------------------------------------
			
			
			int d_bno = 0; //article ��ȣ �ޱ�
			if(request.getParameter("d_bno") != "0"){
				d_bno = Integer.parseInt(request.getParameter("d_bno")); 
			}
			String pageNum = request.getParameter("pageNum");//��������ȣ �ޱ�
	        int d_bvalue = 0;
			String d_bname = ""; //���� �ʱ�ȭ
			OnBookDto article = null;
			
    //---- 0. DB���� ------------------------------------
			OnDao dao = OnDao.getInstance();
			
	//---- 1. �۹�ȣ�� ������, �������� ���� ������ �ҷ���----------------------------
			OnBookIntroDto obiDto = null;
			String Check = "";
			if(d_bno == 0){
			}else{
				//������ �ǸŰ����� å�� ������ �ҷ���
					article = dao.getOnBookArticle(d_bno, Check);
				//������ �ƹ� å�� ������ �ҷ���
					if(article == null){
						Check = "d_bno";
						article = dao.getOnBookArticle(d_bno, Check);
					}
					int d_bcode = article.getD_bcode();
					obiDto = dao.Admin_OnBookIntro_load(d_bcode);//����, �Ұ��� �ҷ�����
			} 
			

	//---- 2. d_bname���� ������ �˻�, �׸��� �˼���޺� �����ǸŰ��� �����ִ� Dao-----------------------------------------------------
			d_bname = article.getD_bname();
			d_bvalue =  dao.getFindNameToValue(d_bname);

			int d_bpurchasevalueS = (int)((double)d_bvalue * 0.8)/100 * 100; //������ 80%������ �ʿ����� ���� ����
			int d_bpurchasevalueA = (int)((double)d_bvalue * 0.6)/100 * 100; //������ 60%������ �ʿ����� ���� ����
			int d_bpurchasevalueB = (int)((double)d_bvalue * 0.4)/100 * 100; //������ 40%������ �ʿ����� ���� ����

	//----- 3. ���� ������ ������, �ǸŰ����� å�� ���� ���� ������ �Ѱ�, �����ϱ� ���ý� alert�� ��� ����ڿ��� �˷��ݴϴ�. ------------------
			int sellCanCount = dao.getSellCanCount(d_bname);
			

			request.setAttribute("d_bno", d_bno);				
			request.setAttribute("pageNum", pageNum);	       
			request.setAttribute("article", article);
			request.setAttribute("obiDto",obiDto);

			request.setAttribute("d_bpurchasevalueS", d_bpurchasevalueS);
			request.setAttribute("d_bpurchasevalueA", d_bpurchasevalueA);
			request.setAttribute("d_bpurchasevalueB", d_bpurchasevalueB);
			
			request.setAttribute("sellCanCount", sellCanCount);

			   
			return "/d_online/onArticle.jsp";
	}
}

