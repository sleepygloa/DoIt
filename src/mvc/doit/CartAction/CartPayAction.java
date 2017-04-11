package mvc.doit.CartAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Account.AcDto;
import mvc.doit.Cart.CartDao;
import mvc.doit.Delivery.DeliveryDto;
import mvc.doit.Login.LoginDto;
import mvc.doit.Online.OnDao;
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
		
		String buy = request.getParameter("buy");
		
	
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

		}else if(col.equals("d_sell") && buy.equals("cart") || buy.equals("buy") ){ //�����Ǹ� ��ٱ��Ͽ��� ����
			

		 	String d_id = request.getParameter("d_bbuyer");
		 	
		 	DeliveryDto Ddto = new DeliveryDto();
		 	Ddto.setD_bdelibery(20);
		 	Ddto.setD_bbuyer(d_id);
		 	Ddto.setD_brecipient(request.getParameter("d_brecipient"));
		 	Ddto.setD_brequested(request.getParameter("d_brequested"));
		 	Ddto.setD_bgradevalue(Integer.parseInt(request.getParameter("d_bgradevalue")));
		 	
		 	LoginDto LogDto = new LoginDto();
		 	LogDto.setD_addr(request.getParameter("d_addr"));
		 	LogDto.setUser_phone1(request.getParameter("user_phone1"));
		 	LogDto.setUser_phone2(request.getParameter("user_phone2"));
		 	LogDto.setUser_phone3(request.getParameter("user_phone3"));
		 	
		 	AcDto acDto = new AcDto();
		 	acDto.setD_lsender(br_no); 			//������ ���
		 	acDto.setD_lreceiver(261);		//�޴»��
		 	acDto.setD_ldealmoney(Integer.parseInt(request.getParameter("d_total")));	//�ŷ��ݾ�
		 	acDto.setD_ldealtype(0);		//�ŷ� ����
		 	acDto.setD_ldealresult(1);				//�ŷ� ��� 0:�ŷ�����, 1:�ŷ��Ϸ�, 2:�ŷ����
		 	
		 	
		 	if(buy.equals("cart")){
		 		cdo.moveCart_delivery(br_no, Ddto, LogDto,acDto, d_id);
		 		
		 	}else if(buy.equals("buy")){
		 		
		 		int d_bcode = Integer.parseInt(request.getParameter("d_bcode"));			 	
		 		Ddto.setD_bcode(d_bcode);
		 		
		 		OnDao dao = OnDao.getInstance();
			 	dao.User_onBuyBook_insert(Ddto, LogDto,acDto, d_bcode, d_id);
			 	
		 	}
			session.removeAttribute("CartP");
			List CartP = cdo.getHeadCart(br_no, col);
		    session.setAttribute("CartP", CartP);			
			
		}

		
		
		
		return "/d_login/myInfo.do";
	}

	
	
}
