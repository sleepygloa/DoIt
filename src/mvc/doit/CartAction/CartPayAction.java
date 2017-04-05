package mvc.doit.CartAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Cart.CartDao;
import mvc.doit.Rent.RentDto;
import mvc.doit.SuperAction.SuperAction;

public class CartPayAction implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�ѱ� ���ڵ�
		request.setCharacterEncoding("UTF-8");
		
		//���� ���
		HttpSession session = request.getSession();
		int br_no = (int)session.getAttribute("memNo"); //�α��� ȸ����ȣ �ҷ�����
		List br_code = (List)session.getAttribute("CartL");
		
	
		///////////////////////////////////////////////////////////////////////////////////
		
		//���� �Ķ����
		String col = request.getParameter("cols");
		
		//dao ��üȭ
		CartDao cdo = CartDao.getInstance();

		
		if(col.equals("d_rent")){	
			
			//�뿩 ����� 5�� �����϶���
			if(cdo.getRentC(br_no) - 1 < 5){	
				
				//��ٱ��� ���� �뿩 �÷����� �̵�
				cdo.moveCart(br_no);
				
				//�뿩�� ����� ��ٱ��� ����
				//cdo.delCart(br_no, col);
				
				//����� ���� �߰�
				String [] getList = cdo.getBcode(br_no); //ȸ����ȣ �Է� ->������ �ִ� å �ڵ� ���
				for(int i = 1; i < getList.length; i++){
					cdo.insPerson(br_no, getList[i]);
				}
				
				
			}
			
			session.removeAttribute("CartL");
			List CartL = cdo.getHeadCart(br_no, col);
			session.setAttribute("CartL", CartL);

		}else{ //�����Ǹ� ����
			session.removeAttribute("CartP");
			List CartP = cdo.getHeadCart(br_no, col);
		    session.setAttribute("CartP", CartP);
		}

		
		
		
		return "/d_login/myInfo.do";
	}

	
	
}
