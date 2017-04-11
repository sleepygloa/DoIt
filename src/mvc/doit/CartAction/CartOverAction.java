package mvc.doit.CartAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Cart.CartDao;
import mvc.doit.Rent.RentDao;
import mvc.doit.SuperAction.SuperAction;

public class CartOverAction implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("UTF-8");
		
		//�Ķ���� ����
		String br_code = request.getParameter("br_code"); //�ش� å �ڵ�
		HttpSession session = request.getSession();
		int br_no = (int)session.getAttribute("memNo"); //ȸ����ȣ
		String br_n = Integer.toString(br_no);
		
		//��ü ����
		CartDao cdao = CartDao.getInstance(); //īƮ dao
		RentDao rdao = RentDao.getInstance(); //������ ���� Dao
		
		//��ü ����
		List getList = null; //����Ʈ ��ü ����
		
		//�ش� å �ڵ��� ���� �ҷ�����
		getList = cdao.getOver(br_code); //��ۻ���, ���� ��¥, ����ڸ��
		
		//��ۻ��� ����
		int deli_info1 = (int)getList.get(0);
		
		//����� ��ܿ��� ù���� ���
		String firstMan = cdao.getFirstM(br_code);
		
		
		//����� �������� , ù��° ȸ���ΰ�
		if(deli_info1 == 5 || deli_info1 < 2 ){
			if(firstMan.equals(br_n)){
				//����� 1�� ����
				cdao.OverDue(br_code);
				
				//�ش� ȸ���� �ش� å�ڵ� ����
				cdao.delCode(br_code, br_no);
			}
		}
		
		request.setAttribute("cols", "dr_rent");
		
		
		return "/d_login/myList.do";
	}

	
}
