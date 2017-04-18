package mvc.doit.CartAction;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Account.AcDao;
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
      int br_o = Integer.parseInt(request.getParameter("br_no"));
      
      //��ü ����
      CartDao cdao = CartDao.getInstance(); //īƮ dao
      RentDao rdao = RentDao.getInstance(); //������ ���� Dao
      AcDao adao = AcDao.getInstance(); //����, �α� Dao
      
      //��ü ����
      List getList = null; //����Ʈ ��ü ����
      
      //�ش� å �ڵ��� ���� �ҷ�����
      getList = cdao.getOver(br_code); //��ۻ���, ���� ��¥, ����ڸ��
      
      //��ۻ��� ����
      int deli_info1 = (int)getList.get(0);
      
      //����� ��ܿ��� ù���� ���
      String firstMan = cdao.getFirstM(br_code);
      
      boolean deli = false;
      deli = cdao.checkDeli(br_no);
      
      //����� �������� , ù��° ȸ���ΰ�
      if(deli_info1 == 5 || deli_info1 < 2 ){
         if(firstMan.equals(br_n)){
            if(deli){      //��
            cdao.OverDue(br_code);//����� 1�� ����
            cdao.delCode(br_code, br_no);//�ش� ȸ���� �ش� å�ڵ� ����
            adao.inLog(br_no, br_o, br_code, 0, 0);
            }else if(!deli){
            	adao.getDate(br_code);   //�뿩��¥
            	adao.comDate(br_code);   //��ü��
                long money=adao.getMoney(br_no, br_code); //��ü�ݾ�
                int cash=adao.getCash(br_no, br_code);  //��ü�ݾ��� �� �ݾ�
                if(cash>0){
                	adao.getAcco(br_no, br_code);    //������ ����
                	cdao.OverDue(br_code);//����� 1�� ����
                	cdao.delCode(br_code, br_no);//�ش� ȸ���� �ش� å�ڵ� ����
                	adao.inLog(br_no, br_o, br_code, 1, money);
                  }else{
                     System.out.println("�������ض�");
                  }
            }
         }
      }
      
        

      
      request.setAttribute("cols", "dr_rent");
      
      
      return "/d_login/myList.do";
   }

   
}