package mvc.doit.CartAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Cart.CartDao;
import mvc.doit.Cart.CartListDto;
import mvc.doit.SuperAction.SuperAction;

//��ٱ��� �׼�
public class CartInsertAction1 implements SuperAction{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�ѱ����ڵ�
		request.setCharacterEncoding("UTF-8");
		
		//���� ���� ���
		HttpSession session = request.getSession();
		int br_no = (int)session.getAttribute("memNo"); //�α��� ȸ����ȣ �ҷ�����
		
		
		//��ٱ��ϰ� �� ��ġ �ľ� [ ������ / �����Ǹ� ] - start_cart
		String start_cart = request.getParameter("start_cart");//���� ��ġ
		String b_code = request.getParameter("b_code"); //������ br_code
		
		String col = null;	//������ ��ٱ��� �÷���
		String srt = ""; //������ url
		String colss = ""; //�뿩 ��� Ȯ��
		
		if(start_cart.equals("lib")){ //�������� ��� ����Ʈ�� ����
			srt = "/d_rent/list_cont.do";
			request.setAttribute("view_type", request.getParameter("view_type"));
			col = "d_rent"; 
			colss = "dr_rent";
		}else if(start_cart.equals("lib2")){ //���� ���� 
			srt = "/d_cart/headCartList.do";
			col = "d_rent"; 
			colss = "dr_rent";
		}
		
		CartDao cdo = CartDao.getInstance();
		
		//�ش� ���� ����� ��� : 5�� �����ΰ�
		int personC = cdo.getPerson(b_code);
		request.setAttribute("personC", personC); //����� ���� 
		
		
		if(personC < 6){
			
			//��ٱ��� ���� ���� �Ǵ�
			boolean check = cdo.checkASD(br_no);
			
			if(!check){ //��ٱ��� ���ڵ尡 ���� ���
				cdo.insASD(br_no);
			}//�ű� ���� �� ����
				
			//�� ��ٱ��� �� ���� Ȯ�� 
			int countC = cdo.countCart(br_no,col);
				
			//�ش絵���� �̹� �뿩 ��°� ? �뿩��� Ȯ��
			boolean checkRe = cdo.checkCart(br_no,colss,b_code);
				
			//�ش絵���� �̹� ��ٱ��Ͽ� �ִ°� ? ��ٱ��� ��� Ȯ��
			boolean checkRe2 = cdo.checkCart(br_no, col, b_code);
			
			// ��ٱ��� �� ������ 5�� �����ΰ�?
			// ����� ����� 5�� �����ΰ�?
			// ���� �뿩��Ͽ� �̹� �ִ°�?
			if(countC < 6 && !checkRe && !checkRe2 && personC < 6){
				cdo.insetCart(br_no,col,b_code);	
			}// -> ��ٱ��� �Է�
			
		
		}
		
		
		//���ǿ� ��ٱ��� ���Ӱ� ����
		if(col.equals("d_rent")){ //������ ���� ����
			session.removeAttribute("CartL");
			List CartL = cdo.getHeadCart(br_no, col);
			session.setAttribute("CartL", CartL);
		}else{ //�����Ǹ� ����
			session.removeAttribute("CartP");
			List CartP = cdo.getHeadCart(br_no, col);
		    session.setAttribute("CartP", CartP);
		}

	    
		
		
		return  srt;
	}
	
}
