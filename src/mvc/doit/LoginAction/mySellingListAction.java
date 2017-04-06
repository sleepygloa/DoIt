package mvc.doit.LoginAction;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Online.OnBookDto;
import mvc.doit.Online.OnDao;
import mvc.doit.Online.OnSellListDto;
import mvc.doit.SuperAction.SuperAction;

public class mySellingListAction implements SuperAction {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
	//---- ���� ���� --------------------------------------------------
		HttpSession session = request.getSession();
		String d_id = (String)session.getAttribute("memId"); 
		String pageNum = request.getParameter("pageNum");//������ ��ȣ
        if (pageNum == null) {
            pageNum = "1"; //1�������� 5���� å ������
        }
        int pageSize = 5;//�� �������� ���� ���� //�ѹ��� �Ǹ����� å�� ������ ���� �ʴٰ� �����Ͽ� 5���� ����
        int currentPage = Integer.parseInt(pageNum); //��������ȣ�� Int �� ������ ��
        int startRow = (currentPage - 1) * pageSize + 1;//�� �������� ���۱� ��ȣ, ���� �� ��ȣ 1
        int endRow = currentPage * pageSize;//�� �������� ������ �۹�ȣ, ������ ��ϱ۹�ȣ 5
        int count = 0; //���� �ʱ�ȭ
        int number = 0; //���� �ʱ�ȭ

        
        List articleList = null; //����Ʈ �ʱ�ȭ
        OnDao dao = OnDao.getInstance();//DB����
        
        count = dao.getD_bmySellingCount(d_id);//��ü ���� �� 

        //å�� ��ũ�� Ÿ�� �ǸŽ�û����� �ۼ�������
        if(count > 0){

            articleList = dao.getD_bMySellingList(d_id, startRow, endRow);//���� �������� �ش��ϴ� �� ���
        }
        
        //�Խñ��� pagesize ���� Ŭ��, �� 1���������� ������ ������ ������ ��ȣ ���
        if (count > 0) {
            int pageCount = count / pageSize + ( count % pageSize == 0 ? 0 : 1);
    		 
            int startPage = (int)(currentPage/10)*10+1;
    		int pageBlock=10;
            int endPage = startPage + pageBlock-1;
            if (endPage > pageCount) endPage = pageCount;
            
            request.setAttribute("pageCount", pageCount);
            request.setAttribute("startPage", startPage);
            request.setAttribute("endPage", endPage);
            
        }

		number=count-(currentPage-1)*pageSize;//�۸�Ͽ� ǥ���� �۹�ȣ
        //�ش� �信�� ����� �Ӽ�
	    request.setAttribute("currentPage", new Integer(currentPage));
        request.setAttribute("startRow", new Integer(startRow));
        request.setAttribute("endRow", new Integer(endRow));
        request.setAttribute("count", new Integer(count));
        request.setAttribute("pageSize", new Integer(pageSize));
		request.setAttribute("number", new Integer(number));
        request.setAttribute("articleList", articleList);
		
	
		
		return "/d_login/mySellingList.jsp";
	}
}
