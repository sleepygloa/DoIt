package mvc.doit.CompanyAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.doit.Company.IntroDao;
import mvc.doit.Login.LoginDto;
import mvc.doit.SuperAction.SuperAction;

public class Intro3Action implements SuperAction {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		
		//------------0. ����κ� -------------------------------------------------------------		
		String pageNum = request.getParameter("pageNum");//������ ��ȣ
        if (pageNum == null) {
            pageNum = "1"; //1�������� 20���� å ������
        }
        int pageSize = 20;//�� �������� ���� ����
        int currentPage = Integer.parseInt(pageNum); //��������ȣ�� Int �� ������ ��
        int startRow = (currentPage - 1) * pageSize + 1;//�� �������� ���۱� ��ȣ, ���� �� ��ȣ 1
        int endRow = currentPage * pageSize;//�� �������� ������ �۹�ȣ, ������ ��ϱ۹�ȣ 20
        int count = 0; //���� �ʱ�ȭ
        int number = 0; //���� �ʱ�ȭ
		
		IntroDao idao = IntroDao.getInstance();
		LoginDto ldto = new LoginDto();
		
		//--------------- ������ List -------------------
		List adminList = null;
		
		count = idao.getAdminCount(); //������ ���� ��
		if(count > 0){
			adminList = idao.getAdminList();
		}
		

		
		
		number=count-(currentPage-1)*pageSize;//�۸�Ͽ� ǥ���� �۹�ȣ
		
        //�ش� �信�� ����� �Ӽ�
	    request.setAttribute("currentPage", new Integer(currentPage));
        request.setAttribute("startRow", new Integer(startRow));
        request.setAttribute("endRow", new Integer(endRow));
        request.setAttribute("count", new Integer(count));
        request.setAttribute("pageSize", new Integer(pageSize));
		request.setAttribute("number", new Integer(number));
        request.setAttribute("adminList", adminList);
		
		return "/d_company/intro3.jsp";
	}
}
