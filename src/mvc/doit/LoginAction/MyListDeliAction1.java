package mvc.doit.LoginAction;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Cart.CartDao;
import mvc.doit.SuperAction.SuperAction;

public class MyListDeliAction1 implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("UTF-8");
		
		//�Ķ���� ����
		String br_code = request.getParameter("br_code");//å�ڵ� �ҷ�����
		HttpSession session = request.getSession();
		int memNo = (int)session.getAttribute("memNo");
		
		//��ü ����
		List getList = null; //����Ʈ ��ü ����
		CartDao cdao = CartDao.getInstance(); //īƮ dao �ҷ���
		
		
		//�ش� å �ڵ��� ���� �ҷ�����
		getList = cdao.getOver(br_code); //��ۻ���, ���� ��¥, ����ڸ��
		
		//����� ��ܿ��� ù���� ���
		String firstMan = cdao.getFirstM(br_code);
		
		//������� ù���� ��� ���� ���
		
		
		//��ۻ��� ����
		int deli_info1 = (int)getList.get(0);
		
		String deli_info = "";
		
		switch(deli_info1){
			//��ۻ��°� 
			case 0 :
				deli_info ="��� ���� ���";
				break;
				
			case 1 :
				deli_info ="��� �غ�";
				break;
				
			case 2 :
				deli_info ="��� ��";
				break;
				
			case 3 :
				deli_info ="��� �Ϸ�";
				break;
			
			//�ƹ��͵� ���� ��
			default :
				deli_info = "�˼� ���� ����";
				break;
		}
		
		//�Ķ���� ����
		request.setAttribute("deli_info", deli_info); //��� ��� ���� ����
		request.setAttribute("firstMan", firstMan); //����� 1��° ȸ����ȣ
		request.setAttribute("memNo", memNo); //�α����� ȸ���� ��ȣ
		
		return "/d_login/my_list_deli1.jsp";
	}

}
