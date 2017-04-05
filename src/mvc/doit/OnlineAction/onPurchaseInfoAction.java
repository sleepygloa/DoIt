package mvc.doit.OnlineAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Online.OnBookDto;
import mvc.doit.Online.OnDao;
import mvc.doit.SuperAction.SuperAction;

public class OnPurchaseInfoAction implements SuperAction {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	 
		request.setCharacterEncoding("utf-8");

//------------0. ����κ� -------------------------------------------------------------		
		String pageNum = request.getParameter("pageNum");//������ ��ȣ
        if (pageNum == null) {
            pageNum = "1"; //1�������� 10���� å ������
        }
        int pageSize = 10;//�� �������� ���� ����
        int currentPage = Integer.parseInt(pageNum); //��������ȣ�� Int �� ������ ��
        int startRow = (currentPage - 1) * pageSize + 1;//�� �������� ���۱� ��ȣ, ���� �� ��ȣ 1
        int endRow = currentPage * pageSize;//�� �������� ������ �۹�ȣ, ������ ��ϱ۹�ȣ 10
        int count = 0; //���� �ʱ�ȭ
        int number = 0; //���� �ʱ�ȭ		
		
		
		
		String d_bname = "";
		int d_bno = 0;
		if(request.getParameter("d_bno") != null){
			d_bno = Integer.parseInt(request.getParameter("d_bno"));
		};
		int d_bcode = 0;
		if(request.getParameter("d_bcode") != null){
			d_bcode = Integer.parseInt(request.getParameter("d_bcode"));
		}
		
		
        List articleList = null; //����Ʈ �ʱ�ȭ
        OnDao dao = OnDao.getInstance();//DB����
		OnBookDto article = new OnBookDto();
        
		//���ٻ�Ȳ�� �����ϴ� Dao , d_bno�϶�, d_bcode�϶�--------------------------
		if(d_bno == 0){
			if(d_bcode == 0){
			}else{
				article = dao.getOnBookArticleD_bcode(d_bcode);	
			}
		}else{
				article = dao.getOnBookArticle(d_bno);		
		} 
		//List�� �̴� Dao-----------------------------------------------------
		d_bname = article.getD_bname();
		count =  dao.getFindNameToNameCount(d_bname);
		articleList = dao.getFindNameToName(d_bname, startRow, endRow);//å�� �̸��� ���� å���� List 
		
		//������ �˻�
		int MinD_bsellvalue = dao.getFindNameToMinSellValue(d_bname);
		
		//������� ���� �Ǵ� ����		
		int valueToSellvaluePercent = 
(int)(100 - 	((double)article.getD_bsellvalue() / (double)article.getD_bvalue() * 100));
		
		//������� �ǸŰ�
		//ȸ���� ����� �ҷ��ɴϴ�.
		HttpSession  session = request.getSession();
		String d_id = (String)session.getAttribute("memId");
		int gradeToSellValue = article.getD_bsellvalue();
		int gradeCheck = dao.getIdToGrade(d_id);
				if(		  gradeCheck == 0) {
					gradeToSellValue = gradeToSellValue; 
				}else if (gradeCheck == 1) {
					gradeToSellValue = (int)((double)gradeToSellValue * 0.95);
				}else if (gradeCheck == 2) {
					gradeToSellValue = (int)((double)gradeToSellValue * 0.9);
				}else{}
				
        //�ش� �信�� ����� �Ӽ�
	    request.setAttribute("currentPage", new Integer(currentPage));
        request.setAttribute("startRow", new Integer(startRow));
        request.setAttribute("endRow", new Integer(endRow));
        request.setAttribute("count", new Integer(count));
        request.setAttribute("pageSize", new Integer(pageSize));
		request.setAttribute("number", new Integer(number));
		request.setAttribute("article", article);
		request.setAttribute("articleList", articleList);
		
		request.setAttribute("MinD_bsellvalue",MinD_bsellvalue);
		request.setAttribute("valueToSellvaluePercent", valueToSellvaluePercent);
		request.setAttribute("gradeToSellValue", gradeToSellValue);
		
	return "/d_online/onPurchaseInfo.jsp";
	
	} 
}
