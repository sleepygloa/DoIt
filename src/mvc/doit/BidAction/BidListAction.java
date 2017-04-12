package mvc.doit.BidAction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.doit.Login.LoginDto;
import mvc.doit.ResellBean.BidbookDao;
import mvc.doit.ResellBean.BidbookDto;
import mvc.doit.ResellBean.ResellbookDao;
import mvc.doit.ResellBean.ResellintroDto;
import mvc.doit.SuperAction.SuperAction;

public class BidListAction implements SuperAction{

   @Override
   public String execute(HttpServletRequest request,
         HttpServletResponse response) throws Exception {
      // TODO Auto-generated method stub
      
      request.setCharacterEncoding("utf-8");
      
      HttpSession session = request.getSession();
      String id = (String)session.getAttribute("memId");
      
      int pageSize = 10;//��ȭ�� �Խù�����
      
      
      String pageNum = request.getParameter("pageNum");
      if(pageNum==null){
         pageNum = "1";
      }
      
      
      int currentPage = Integer.parseInt(pageNum); //��������ȣ
      int startRow = (currentPage -1) * pageSize +1; 
      int endRow = currentPage * pageSize +1; //������������ �������� ���������
      int count = 0;
      int number = 0;
      int sellerCount= 0;
      
      List articleList = null;
      BidbookDao article = BidbookDao.getInstance();
      ResellbookDao article1 = ResellbookDao.getInstance();
      
      
      //�˻�
      String search = request.getParameter("search");
      if(search == null){
         //��ü����
         count = article.getBidCount();
         if(count > 0){ //���� ������ BidListȣ��
            
            articleList = article.getBidList(startRow, endRow);
         }
      }else{
         count = article.getBidSearchCount(search);
         if(count >0){
            articleList = article.getBidSearch(search);
         }
      }
      
      number = count -(currentPage-1)*pageSize;
      
      LoginDto dto=article.getGrade(id);//ȸ��  ��� Ȯ���� ����
      ResellintroDto finish = article1.getFinishWriter(id);
      
      sellerCount =article.getBidderCount(id);
      
      request.setAttribute("currentPage", new Integer(currentPage));
        request.setAttribute("startRow", new Integer(startRow));
        request.setAttribute("endRow", new Integer(endRow));
        request.setAttribute("count", new Integer(count));
        request.setAttribute("pageSize", new Integer(pageSize));
      request.setAttribute("number", new Integer(number));
      request.setAttribute("articleList", articleList);
      request.setAttribute("id", id);
      request.setAttribute("finish", finish);
      request.setAttribute("sellerCount", sellerCount);
      request.setAttribute("dto", dto);
      
      return "/d_bid/bidList.jsp";
   }

}
