package mvc.doit.Delivery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class DeliveryDao {

private static DeliveryDao instance = new DeliveryDao();
    
    public static DeliveryDao getInstance() {return instance; }
    
    private DeliveryDao() {}
    
    private Connection getConnection() throws Exception {
      Context initCtx = new InitialContext();
      Context envCtx = (Context) initCtx.lookup("java:comp/env");
      DataSource ds = (DataSource)envCtx.lookup("jdbc/khdb");
      return ds.getConnection();
    }
	
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
	
//-----------����ڵ� �ο�, �ǸŽ�û���-----------����ڵ� �ο�, �ǸŽ�û���-----------����ڵ� �ο�, �ǸŽ�û���-----------����ڵ� �ο�, �ǸŽ�û���-----------����ڵ� �ο�, �ǸŽ�û��� 
//�ǸŽ�û�� �ۼ���, ����ڵ常 �ο��ϴ� dao------�ǸŽ�û�� �ۼ���, ����ڵ常 �ο��ϴ� dao------�ǸŽ�û�� �ۼ���, ����ڵ常 �ο��ϴ� dao------    
    
    //ȸ���� �����ڿ��� �Ǹ��Ҷ�
    public int setD_bdeliverycode(String d_id, int d_bcode){
    	int d_bdeliverycode = 0;
	      try{
	        conn=getConnection();
	         
	        pstmt = conn.prepareStatement(
"insert into d_bdelivery values("
+ "d_bdeliverycode_seq.NEXTVAL, ?, 0, sysdate, sysdate, sysdate, ?, 'admin' )");
	        pstmt.setInt(1, d_bcode);
	        pstmt.setString(2, d_id);
	        pstmt.executeUpdate();
	        
	        pstmt = conn.prepareStatement(
"select d_bdeliverycode from d_bdelivery where d_bcode = ? and d_bseller = ?"
);
	        pstmt.setInt(1, d_bcode);
	        pstmt.setString(2, d_id);
	        rs = pstmt.executeQuery();
	        if(rs.next()){
	        	DeliveryDto article = new DeliveryDto();
	        	d_bdeliverycode = rs.getInt("d_bdeliverycode");
	        }else{}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt !=null){try{pstmt.close();}catch(SQLException s){}}
			if(conn !=null){try{conn.close();}catch(SQLException s){}}	
		}		 
	      return d_bdeliverycode;
    }
    
    
}
