package mvc.doit.RentAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.doit.Rent.RentDao;
import mvc.doit.SuperAction.SuperAction;

public class Rent_searchAction implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		
		//�˻����� - �帣
		String jang = request.getParameter("searchSelect");
		
		//�˻����� - �˻��Է� ����
		String word = request.getParameter("searchWord");
		
		String pageNum = request.getParameter("pageNum");//������ ��ȣ

        if (pageNum == null) {
            pageNum = "1";
        }
        int pageSize = 8;//�� �������� ���� ����
        int currentPage = Integer.parseInt(pageNum);
        int startRow = (currentPage - 1) * pageSize + 1;//�� �������� ���۱� ��ȣ
        int endRow = currentPage * pageSize;//�� �������� ������ �۹�ȣ
        int count = 0;
       
        
        List articleList = null;		
		RentDao dbPro = RentDao.getInstance();
		count = dbPro.getSearchCount(jang,word);//��ü ���� ��
		
		
		if (count > 0) {
		 articleList = dbPro.getSearch(startRow,endRow,jang,word);//������ �� ���
		}
		
		
		request.setAttribute("pageNum", new Integer(pageNum));
        request.setAttribute("currentPage", new Integer(currentPage));
	    request.setAttribute("startRow", new Integer(startRow));
	    request.setAttribute("endRow", new Integer(endRow));
	    request.setAttribute("count", new Integer(count));
	    request.setAttribute("pageSize", new Integer(pageSize));
		request.setAttribute("articleList", articleList);
		
		
		//�ҷ��� ������ �̸� ����
		request.setAttribute("view_type", "search_re");
		
		//�˻� ��� ���� ����
		request.setAttribute("count", count);
		request.setAttribute("word", word);
		request.setAttribute("jang", jang);
		
		return "/d_rent/rent_wrap.jsp";
	}
	
	
	
}
