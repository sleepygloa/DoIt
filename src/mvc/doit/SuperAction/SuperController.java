package mvc.doit.SuperAction;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import mvc.doit.SuperAction.SuperAction;


public class SuperController extends HttpServlet{
   private HashMap map= new HashMap();
   
   @Override
   public void init(ServletConfig config) throws ServletException {
      String test = config.getInitParameter("do");
      //pro
      Properties p =new Properties();
      FileInputStream f=null;
      try {
         f =new FileInputStream(test);
         p.load(f);
         Iterator iter =  p.keySet().iterator();
         while (iter.hasNext()) {
            String key = (String) iter.next();
            //�����ؼ� key ������ �������� �Ѿ
            String value = p.getProperty(key);
            //�ش��̸��� ����key�� value���� ����
            Class c =Class.forName(value);
            //�ε�
            Object obj = c.newInstance();
            map.put(key, obj);
            //propeties���� ��ŭ ��ü������ map�� put��
            
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      
   }//������ ���۵ǰ� ���� ����ɶ� �� 1���� ����
   //web.xml�� ���� �Ķ���͸� �� getInitParameter ���ؼ� �޾ӿ�
   
   @Override
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String uri= request.getRequestURI();
      //uri=key
      SuperAction sa = (SuperAction)map.get(uri);
      //uri�� �������� map���� get�ϰ� ex)mainAciotn�� SuperAction���� ����ȯ >>������
      try {
         String view = sa.execute(request, response);
         //�����ִ� execute�� �ش� action���� �Ű������� �޴´� >> return�� uri�ּҸ�  view�� ����
         RequestDispatcher rd = request.getRequestDispatcher(view);
         rd.forward(request, response);
         //�ش� view�������� forward��
      } catch (Exception e) {
         e.printStackTrace();
      }
      
   }

   
   
}