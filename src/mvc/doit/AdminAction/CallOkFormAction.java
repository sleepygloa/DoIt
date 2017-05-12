package mvc.doit.AdminAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import mvc.doit.Login.LoginDao;
import mvc.doit.Login.LoginDto;
import mvc.doit.ResellBean.ResellintroDao;
import mvc.doit.ResellBean.ResellintroDto;
import mvc.doit.SuperAction.SuperAction;

public class CallOkFormAction implements SuperAction{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		int rbook_introno = Integer.parseInt(request.getParameter("rbook_introno").trim());//�ش� �۹�ȣ
        String pageNum = request.getParameter("pageNum");//�ش� ������ ��ȣ

        ResellintroDao dbPro = ResellintroDao.getInstance();//DBó��
        ResellintroDto article =  dbPro.getArticle(rbook_introno);//�ش� �۹�ȣ�� ���� �ش� ���ڵ�
        
        //�ش� �信�� ����� �Ӽ�
        request.setAttribute("rbook_introno", new Integer(rbook_introno));
        request.setAttribute("pageNum", new Integer(pageNum));
        request.setAttribute("article", article);
        
		return "/d_admin/callOkForm.jsp";
	}

}
