package mvc.doit.Online;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import mvc.doit.Login.mySellingListDto;




public class OnDao {
	
	private static OnDao instance = new OnDao();
    
    public static OnDao getInstance() {return instance; }
    
    private OnDao() {}
    
    private Connection getConnection() throws Exception {
      Context initCtx = new InitialContext();
      Context envCtx = (Context) initCtx.lookup("java:comp/env");
      DataSource ds = (DataSource)envCtx.lookup("jdbc/khdb");
      return ds.getConnection();
    }
	
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
 //-------���� ���� å�� �� -------���� ���� å�� �� -------���� ���� å�� �� -------���� ���� å�� �� -------���� ���� å�� �� -------���� ���� å�� �� 
    public int getD_BTodayCount() throws Exception{
    	int x = 0; //å�� ��
    	try{
    		conn = getConnection();
    		pstmt = conn.prepareStatement(
"select count(*) from ( "+
"select d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher,d_bauthor, d_bgenre, d_bgenre2, d_blocation, d_bregistdate, d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, D_icode, d_id, d_bdeliverycode, to_char(d_bdate, 'yyyy-mm-dd') AS d_bdate from d_onBook) b, d_onSellList s " +
"where b.d_bcode = s.d_bcode and s.d_sfinish = 2 and d_bdate = (select to_char(sysdate, 'yyyy-mm-dd') as d_bdate from dual)"
);
    		rs = pstmt.executeQuery();
    		if(rs.next()){
    			x = rs.getInt(1); //ī��Ʈ ù��° ���� ���� ����Ͽ� x�� ����
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
	        if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
	        if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
	        if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
	     }
    	
    	return x;
    }
    
 //-------å DB�� ���ڵ� �� count-------å DB�� ���ڵ� �� count-------å DB�� ���ڵ� �� count-------å DB�� ���ڵ� �� count-------å DB�� ���ڵ� �� count 
   public int getD_BSellCount() throws Exception{
     int x = 0;
     try{
        conn = getConnection();
        pstmt = conn.prepareStatement(
//       		 "select count(*) from d_onBook b, d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2"
        		"select count(*) from (select b.* from d_onBook b, (SELECT max(d_bcode) As d_bcode  FROM d_onBook b  GROUP BY d_bname) s "+
                "where b.d_bcode = s.d_bcode) b, d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2"
       		 );
        rs = pstmt.executeQuery();
        if(rs.next()){
           x = rs.getInt(1); //ī��Ʈ ù��° ���� ���� ����Ͽ� x�� ����
        }
     }catch(Exception e){
        e.printStackTrace();
     }finally{
        if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
        if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
        if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
     }
     return x ;
  }
   
   
//-------ȸ���� å �ȱ����� ���� List-------ȸ���� å �ȱ����� ���� List-------ȸ���� å �ȱ����� ���� List-------ȸ���� å �ȱ����� ���� List  
  public List getD_BSellList(int startRow, int endRow) throws Exception{

     List articleList = null;
     
     try {
        conn = getConnection();
        pstmt = conn.prepareStatement(
//"select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,r "+
//"from (select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,rownum r "+
//"from (select b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation, b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.D_icode, b.d_id, b.d_bdeliverycode, b.d_bdate "+
//"from d_onBook b , d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2 )) where r >= "+startRow+" and r <= "+endRow
"select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,r "+
"from (select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,rownum r "+
"from (select b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation, b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.D_icode, b.d_id, b.d_bdeliverycode, b.d_bdate "+
"from (select b.* from d_onBook b, (SELECT max(d_bcode) As d_bcode  FROM d_onBook b  GROUP BY d_bname) s "+
"where b.d_bcode = s.d_bcode) b , d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2 )) where r >= "+startRow+" and r <= "+endRow        		
        		);
       		 
        rs = pstmt.executeQuery();
        
        if(rs.next()){
           articleList = new ArrayList();
           do{
              OnBookDto article = new OnBookDto();
              article.setD_bno(rs.getInt("d_bno"));
              article.setD_bcode(rs.getInt("d_bcode"));
              article.setD_bname(rs.getString("d_bname"));
              article.setD_bgrade(rs.getString("d_bgrade"));
              article.setD_bpublisher(rs.getString("d_bpublisher"));
              article.setD_bauthor(rs.getString("d_bauthor"));
              article.setD_bgenre(rs.getString("d_bgenre"));
              article.setD_bgenre2(rs.getString("d_bgenre2"));
              article.setD_blocation(rs.getString("d_blocation"));
              article.setD_bregistdate(rs.getString("d_bregistdate"));
              article.setD_bpic(rs.getString("d_bpic"));
              article.setD_bcount(rs.getInt("d_bcount"));
              article.setD_bvalue(rs.getInt("d_bvalue"));
              article.setD_bsellvalue(rs.getInt("d_bsellvalue"));
              article.setD_bpurchasevalue(rs.getInt("d_bpurchasevalue"));
              article.setD_icode(rs.getInt("D_icode"));
              article.setD_id(rs.getString("d_id"));                
              article.setD_bdeliverycode(rs.getInt("d_bdeliverycode"));
              article.setD_bdate(rs.getTimestamp("d_bdate"));
           
              articleList.add(article);
                          
           }while(rs.next());
        }
        
     }catch(Exception e){
        e.printStackTrace();
     }finally{
        if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
        if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
        if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
     }
     return articleList;
  }   


 //-------å�帣 ���ǿ� ���� å DB�� ���ڵ� �� count-------å�帣 ���ǿ� ���� å DB�� ���ڵ� �� count-------å�帣 ���ǿ� ���� å DB�� ���ڵ� �� count-------å�帣 ���ǿ� ���� å DB�� ���ڵ� �� count-------å�帣 ���ǿ� ���� å DB�� ���ڵ� �� count 
   public int getD_BSellCount(String d_bonFillter, int d_bonFillterReturn) throws Exception{
     int x = 0;
     try{
        conn = getConnection();
        String sql = "";
if(d_bonFillterReturn != 0 && d_bonFillterReturn >= 1 & d_bonFillterReturn <= 6){
//	sql += "select count(*) from d_onBook b, d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2 and "+ 
//			 " d_bgenre = '" +d_bonFillter+ "'"	;
	sql += "select count(*) from (select b.* from d_onBook b, (SELECT max(d_bcode) As d_bcode  FROM d_onBook b  GROUP BY d_bname) s "+
		    "where b.d_bcode = s.d_bcode and d_bgenre = '" +d_bonFillter+ "') b, d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2";	
}else if(d_bonFillterReturn != 0 && d_bonFillterReturn >= 7 & d_bonFillterReturn <= 10){
//	sql += "select count(*) from d_onBook b, d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2 and "+ 
//			 " d_bgenre2 = '" +d_bonFillter+ "'"	;
	sql += "select count(*) from (select b.* from d_onBook b, (SELECT max(d_bcode) As d_bcode  FROM d_onBook b  GROUP BY d_bname) s "+
		    "where b.d_bcode = s.d_bcode and d_bgenre2 = '" +d_bonFillter+ "') b, d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2";
}else if(d_bonFillterReturn != 0 && d_bonFillterReturn >= 11 & d_bonFillterReturn <= 12){
//	sql += "select count(*) from d_onBook b, d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2 and "+ 
//			 " d_bLocation = '" +d_bonFillter+ "'"	;
	sql += "select count(*) from (select b.* from d_onBook b, (SELECT max(d_bcode) As d_bcode  FROM d_onBook b  GROUP BY d_bname) s "+
		    "where b.d_bcode = s.d_bcode and d_bLocation = '" +d_bonFillter+ "') b, d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2";	

}else{};
        
        pstmt = conn.prepareStatement(sql);
        
    rs = pstmt.executeQuery();
    if(rs.next()){
       x = rs.getInt(1); //ī��Ʈ ù��° ���� ���� ����Ͽ� x�� ����
        }
     }catch(Exception e){
        e.printStackTrace();
     }finally{
        if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
        if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
        if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
     }
     return x ;
  }
   
   
//-------å �帣 ���ǿ� ���� ȸ���� å �ȱ����� ���� List-------å �帣 ���ǿ� ���� ȸ���� å �ȱ����� ���� List-------å �帣 ���ǿ� ���� ȸ���� å �ȱ����� ���� List-------å �帣 ���ǿ� ���� ȸ���� å �ȱ����� ���� List-------å �帣 ���ǿ� ���� ȸ���� å �ȱ����� ���� List  
  public List getD_BSellList(String d_bonFillter, int d_bonFillterReturn ,int startRow, int endRow) throws Exception{

     List articleList = null;
     
     try {
        conn = getConnection();
        String sql = "";
if(d_bonFillterReturn != 0 && d_bonFillterReturn >= 1 & d_bonFillterReturn <= 6){
//	sql +=  "select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,r "+
//			 "from (select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,rownum r "+
//			 "from (select b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation, b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.D_icode, b.d_id, b.d_bdeliverycode, b.d_bdate "+
//			 "from d_onBook b , d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2 and d_bgenre = '"+ d_bonFillter +"' )) where r >= "+startRow+" and r <= "+endRow;
	sql += "select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,r "+
			"from (select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,rownum r "+
			"from (select b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation, b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.D_icode, b.d_id, b.d_bdeliverycode, b.d_bdate "+
			"from (select b.* from d_onBook b, (SELECT max(d_bcode) As d_bcode  FROM d_onBook b  GROUP BY d_bname) s "+
			"where b.d_bcode = s.d_bcode and d_bgenre = '" +d_bonFillter+ "') b , d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2 )) where r >= "+startRow+" and r <= "+endRow;	
}else if(d_bonFillterReturn != 0 && d_bonFillterReturn >= 7 & d_bonFillterReturn <= 10){
//	sql += "select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,r "+
//			 "from (select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,rownum r "+
//			 "from (select b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation, b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.D_icode, b.d_id, b.d_bdeliverycode, b.d_bdate "+
//			 "from d_onBook b , d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2 and d_bgenre2 = '"+ d_bonFillter +"' )) where r >= "+startRow+" and r <= "+endRow;
	sql += "select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,r "+
			"from (select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,rownum r "+
			"from (select b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation, b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.D_icode, b.d_id, b.d_bdeliverycode, b.d_bdate "+
			"from (select b.* from d_onBook b, (SELECT max(d_bcode) As d_bcode  FROM d_onBook b  GROUP BY d_bname) s "+
			"where b.d_bcode = s.d_bcode and d_bgenre2 = '" +d_bonFillter+ "') b , d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2 )) where r >= "+startRow+" and r <= "+endRow;	
}else if(d_bonFillterReturn != 0 && d_bonFillterReturn >= 11 & d_bonFillterReturn <= 12){
//	sql += "select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,r "+
//			 "from (select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,rownum r "+
//			 "from (select b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation, b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.D_icode, b.d_id, b.d_bdeliverycode, b.d_bdate "+
//			 "from d_onBook b , d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2 and d_bLocation = '"+ d_bonFillter +"' )) where r >= "+startRow+" and r <= "+endRow;
	sql += "select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,r "+
			"from (select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,rownum r "+
			"from (select b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation, b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.D_icode, b.d_id, b.d_bdeliverycode, b.d_bdate "+
			"from (select b.* from d_onBook b, (SELECT max(d_bcode) As d_bcode  FROM d_onBook b  GROUP BY d_bname) s "+
			"where b.d_bcode = s.d_bcode and d_bLocation = '" +d_bonFillter+ "') b , d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2 )) where r >= "+startRow+" and r <= "+endRow;	

}else{};        
        
        pstmt = conn.prepareStatement(sql);
   		 
    rs = pstmt.executeQuery();
    
    if(rs.next()){
       articleList = new ArrayList();
       do{
          OnBookDto article = new OnBookDto();
          article.setD_bno(rs.getInt("d_bno"));
          article.setD_bcode(rs.getInt("d_bcode"));
          article.setD_bname(rs.getString("d_bname"));
          article.setD_bgrade(rs.getString("d_bgrade"));
          article.setD_bpublisher(rs.getString("d_bpublisher"));
          article.setD_bauthor(rs.getString("d_bauthor"));
          article.setD_bgenre(rs.getString("d_bgenre"));
          article.setD_bgenre2(rs.getString("d_bgenre2"));
          article.setD_blocation(rs.getString("d_blocation"));
          article.setD_bregistdate(rs.getString("d_bregistdate"));
          article.setD_bpic(rs.getString("d_bpic"));
          article.setD_bcount(rs.getInt("d_bcount"));
          article.setD_bvalue(rs.getInt("d_bvalue"));
          article.setD_bsellvalue(rs.getInt("d_bsellvalue"));
          article.setD_bpurchasevalue(rs.getInt("d_bpurchasevalue"));
          article.setD_icode(rs.getInt("D_icode"));
          article.setD_id(rs.getString("d_id"));                
          article.setD_bdeliverycode(rs.getInt("d_bdeliverycode"));
          article.setD_bdate(rs.getTimestamp("d_bdate"));
           
              articleList.add(article);
                          
           }while(rs.next());
        }
        
     }catch(Exception e){
        e.printStackTrace();
     }finally{
        if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
        if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
        if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
     }
     return articleList;
  }      
   

 
//-------���� �˻� å count-------���� �˻� å count-------���� �˻� å count-------���� �˻� å count-------���� �˻� å count-------���� �˻� å count-------���� �˻� å count
public int getD_BSelectCount(String select) throws Exception{
 int x = 0;
 try{
    conn = getConnection();
    pstmt = conn.prepareStatement(
//          "select count(*) from d_onBook "
   		 "select count(*) from d_onBook b, d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2 and "
   		 + "(b.d_bgenre like '%"+select +"%' or b.d_bgenre2 like '%"+select +"%' or b.d_blocation like '%"+select +"%' or b.d_bname like '%"+select +"%' or b.d_bpublisher like '%"+select +"%' or b.d_bauthor like '%"+select +"%')"
   		 );
    rs = pstmt.executeQuery();
    if(rs.next()){
       x = rs.getInt(1); //ī��Ʈ ù��° ���� ���� ����Ͽ� x�� ����
    }
 }catch(Exception e){
    e.printStackTrace();
 }finally{
    if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
    if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
    if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
 }
 return x ;
}


//-------���� �˻� List-------���� �˻� List-------���� �˻� List-------���� �˻� List-------���� �˻� List-------���� �˻� List-------���� �˻� List-------���� �˻� List-------���� �˻� List  
public List getD_BSelectList(String select, int startRow, int endRow) throws Exception{

 List articleList = null;
 
 try {
    conn = getConnection();
    pstmt = conn.prepareStatement(
    		
"select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,r "+
"from (select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,rownum r "+
"from (select b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation, b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.D_icode, b.d_id, b.d_bdeliverycode, b.d_bdate "+
"from d_onBook b , d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2 and " +
"(b.d_bgenre like '%"+select +"%' or b.d_bgenre2 like '%"+select +"%' or b.d_blocation like '%"+select +"%' or b.d_bname like '%"+select +"%' or b.d_bpublisher like '%"+select +"%' or b.d_bauthor like '%"+select +"%')" +
")) where  r >= "+startRow+" and r <= "+endRow
   		 );
    rs = pstmt.executeQuery();
    
    if(rs.next()){
       articleList = new ArrayList();
       do{
          OnBookDto article = new OnBookDto();
          article.setD_bno(rs.getInt("d_bno"));
          article.setD_bcode(rs.getInt("d_bcode"));
          article.setD_bname(rs.getString("d_bname"));
          article.setD_bgrade(rs.getString("d_bgrade"));
          article.setD_bpublisher(rs.getString("d_bpublisher"));
          article.setD_bauthor(rs.getString("d_bauthor"));
          article.setD_bgenre(rs.getString("d_bgenre"));
          article.setD_bgenre2(rs.getString("d_bgenre2"));
          article.setD_blocation(rs.getString("d_blocation"));
          article.setD_bregistdate(rs.getString("d_bregistdate"));
          article.setD_bpic(rs.getString("d_bpic"));
          article.setD_bcount(rs.getInt("d_bcount"));
          article.setD_bvalue(rs.getInt("d_bvalue"));
          article.setD_bsellvalue(rs.getInt("d_bsellvalue"));
          article.setD_bpurchasevalue(rs.getInt("d_bpurchasevalue"));
          article.setD_icode(rs.getInt("D_icode"));
          article.setD_id(rs.getString("d_id"));                
          article.setD_bdeliverycode(rs.getInt("d_bdeliverycode"));
          article.setD_bdate(rs.getTimestamp("d_bdate"));
       
          articleList.add(article);
                      
       }while(rs.next());
    }
    
 }catch(Exception e){
    e.printStackTrace();
 }finally{
    if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
    if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
    if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
 }
 return articleList;
}        
 
 
   
//-----å ������-----å ������-----å ������-----å ������-----å ������-----å ������-----å ������-----å ������-----å ������
//å ��ü DB�� �ҷ� LIst ���� ������ ��系���� ��µ��� �ʴ´�. 
    public OnBookDto getOnBookArticle(int d_bno) throws Exception{
       OnBookDto article = null;
       
       try{
          conn = getConnection();
          pstmt = conn.prepareStatement(
                "select * from d_onBook where d_bno = ?");
          pstmt.setInt(1, d_bno);
          rs = pstmt.executeQuery();
          
          if(rs.next()){
             article = new OnBookDto();
            article.setD_bno(rs.getInt("d_bno"));
            article.setD_bcode(rs.getInt("d_bcode"));
            article.setD_bname(rs.getString("d_bname"));
            article.setD_bgrade(rs.getString("d_bgrade"));
            article.setD_bpublisher(rs.getString("d_bpublisher"));
            article.setD_bauthor(rs.getString("d_bauthor"));
            article.setD_bgenre(rs.getString("d_bgenre"));
            article.setD_bgenre2(rs.getString("d_bgenre2"));
            article.setD_blocation(rs.getString("d_blocation"));
            article.setD_bregistdate(rs.getString("d_bregistdate"));
            article.setD_bpic(rs.getString("d_bpic"));
            article.setD_bcount(rs.getInt("d_bcount"));
            article.setD_bvalue(rs.getInt("d_bvalue"));
            article.setD_bsellvalue(rs.getInt("d_bsellvalue"));
            article.setD_bpurchasevalue(rs.getInt("d_bpurchasevalue"));
            article.setD_icode(rs.getInt("D_icode"));
            article.setD_id(rs.getString("d_id"));                
            article.setD_bdeliverycode(rs.getInt("d_bdeliverycode"));
            article.setD_bdate(rs.getTimestamp("d_bdate"));

                             
          }          
       }catch(Exception e){
          e.printStackTrace();
       }finally{ 
             if( rs != null ){ try{ rs.close(); }catch( SQLException se ){}};
             if( pstmt != null ){ try{ pstmt.close(); }catch( SQLException se ){}};
             if( conn != null ){ try{ conn.close(); }catch( SQLException se ){}};
       }
       return article;
    }
    

//-----�߰� �ǸŰ��� List-----�߰� �ǸŰ� List-----�߰� �ǸŰ� List-----�߰� �ǸŰ� List-----�߰� �ǸŰ� List-----�߰� �ǸŰ� List
    public List getD_bsellvalue(int d_bno) throws Exception{
        List sellList = null;
        
        try {
           conn = getConnection();
           pstmt = conn.prepareStatement(
"select d_bpurchasevalue from d_onBook where ="+ 
" (select d_bname from d_onBook where d_bno " + d_bno + ") order by asc"               
);           
           rs = pstmt.executeQuery();
           
           if(rs.next()){
        	   sellList = new ArrayList();
              do{
                 OnBookDto article = new OnBookDto();
                 article.setD_bpurchasevalue(rs.getInt("d_bpurchasevalue"));
                 sellList.add(article);
              }while(rs.next());
           }
           
        }catch(Exception e){
           e.printStackTrace();
        }finally{
           if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
           if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
           if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
        }
        return sellList;
     }
    
//---------å �ڵ常 �߰�---------å �ڵ常 �߰�---------å �ڵ常 �߰�---------å �ڵ常 �߰�---------å �ڵ常 �߰�---------å �ڵ常 �߰�    
//----------------------------------------------------------------------------------------------------------------
//-----------�Ǹ����� å ------------�Ǹ����� å dao------------�Ǹ����� å dao------------�Ǹ����� å dao------------�Ǹ����� å dao-
//-----------d_onBook�� å �ڵ常 �߰�-----------d_onBook�� å �ڵ常 �߰�-----------d_onBook�� å �ڵ常 �߰�-----------d_onBook�� å �ڵ常 �߰�
    public int setD_bcode(String d_id, OnBookDto onbookdto){
    	int d_bcode = 0;
    	int d_bno = 0;
	      try{
	        conn=getConnection();
	        pstmt = conn.prepareStatement(
"insert into d_onBook values("
+ "d_bno_seq.NEXTVAL, d_bcode_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, "
+ "?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate)"
);			//ù��
	        pstmt.setString(1, onbookdto.getD_bname());
	        pstmt.setString(2, onbookdto.getD_bgrade());
	        pstmt.setString(3, onbookdto.getD_bpublisher());
	        pstmt.setString(4, onbookdto.getD_bauthor());
	        pstmt.setString(5, onbookdto.getD_bgenre());
	        pstmt.setString(6, onbookdto.getD_bgenre2());
	        pstmt.setString(7, onbookdto.getD_blocation());
	        //������
	        pstmt.setString(8, onbookdto.getD_bregistdate());
	        pstmt.setString(9, onbookdto.getD_bpic());
	        pstmt.setInt(10, onbookdto.getD_bcount());
	        pstmt.setInt(11, onbookdto.getD_bvalue());
	        pstmt.setInt(12, onbookdto.getD_bsellvalue());
	        pstmt.setInt(13, onbookdto.getD_bpurchasevalue());
	        pstmt.setInt(14, onbookdto.getD_icode());
	        pstmt.setString(15, d_id);
	        pstmt.setInt(16, onbookdto.getD_bdeliverycode());
	        
//���⼭ ���� �ִ� ���� ������ ������ �⺻��, null, ����ð����� ���Ե˴ϴ�.  	        
	        pstmt.executeUpdate();
	        
//d_bno�� ������������ ���� �� ����� rownum r�� ������������ �߰��� , �ᱹ ���� �������� �߰��� ���� ���� �̴� sql
			
	        pstmt = conn.prepareStatement(
"select d_bcode from d_onBook where d_bno = " + 
"(select d_bno from (select d_bno, rownum r  from (select d_bno from d_onBook order by d_bno desc) where rownum = 1))" + 
" and d_id = '" + d_id+"'");
			rs = pstmt.executeQuery();
			
			if(rs.next()){
			d_bcode = rs.getInt("d_bcode");
			}else{};

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs !=null){try{rs.close();}catch(SQLException s){}}
			if(pstmt !=null){try{pstmt.close();}catch(SQLException s){}}
			if(conn !=null){try{conn.close();}catch(SQLException s){}}	
			
		}		
	      return d_bcode;
  } 
    
    
//-----------�ǸŽ�û ���� å count-----------�ǸŽ�û ���� å count-----------�ǸŽ�û ���� å count-----------�ǸŽ�û ���� å count   
    public int getD_bmySellingCount(String d_id) throws Exception{
        int x = 0;
        try{
           conn = getConnection();
           pstmt = conn.prepareStatement(
//�� ���̺��� å�ڵ尡 ����, ȸ�� �̸��� ����, ��ûȮ�� ���� �ʱⰪ(�˼���,�˼��ʿ�=0)�� ���� ���ڵ� ��        		   
"select count(*) from d_onSellList s, d_onBook b "+ 
" where s.d_bcode = b.d_bcode and s.d_id = b.d_id and s.d_id = '"+d_id+"' and s.d_sfinish = 0"

 );
           rs = pstmt.executeQuery();
           if(rs.next()){
              x = rs.getInt(1); //ī��Ʈ ù��° ���� ���� ����Ͽ� x�� ����
           }
        }catch(Exception e){
           e.printStackTrace();
        }finally{
           if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
           if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
           if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
        }
        return x ;
     }   

//---------�ǸŽ�û�� �ۼ�---------�ǸŽ�û�� �ۼ�---------�ǸŽ�û�� �ۼ�---------�ǸŽ�û�� �ۼ�---------�ǸŽ�û�� �ۼ�---------�ǸŽ�û�� �ۼ�    
    public void onSellList(OnSellListDto onSellListDto){
  		
	      try{
	         conn=getConnection();	       
	         pstmt = conn.prepareStatement("insert into d_onSellList values(" + 
	        		 							"d_onSellList_seq.NEXTVAL, ?, ?, ?, 0, ?)");
	    
	         pstmt.setInt(1, onSellListDto.getD_bcode());
	         pstmt.setString(2, onSellListDto.getD_id()); //ȸ�����̵�
	 		 pstmt.setInt(3, onSellListDto.getD_bdeliverycode()); //����ڵ�
	 		 pstmt.setTimestamp(4, onSellListDto.getD_sdate()); //��û �Ϸ� Ȯ��
	         
			pstmt.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			 if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
			if(pstmt !=null){try{pstmt.close();}catch(SQLException s){}}
			if(conn !=null){try{conn.close();}catch(SQLException s){}}	
		}		
    }    
    
//-----------�ǸŽ�û���� å list-----------�ǸŽ�û���� å list-----------�ǸŽ�û���� å list-----------�ǸŽ�û���� å list-----------�ǸŽ�û���� å list
    public List getD_bMySellingList(String d_id, int startRow, int endRow) throws Exception{

        List articleList = null;
        
        try {
           conn = getConnection();
           pstmt = conn.prepareStatement(
"select *  from "+ 
"(select b.*, s.d_sno, s.d_sdate, s.d_sfinish,  rownum r  from d_onSellList s, d_onBook b "+
"where s.d_bcode = b.d_bcode and s.d_id = b.d_id and s.d_id = '"+d_id+"' and s.d_sfinish = 0 order by d_bno asc) "+
"where r >= "+startRow+" and r <= "+endRow+" order by d_sno asc"
);
           rs = pstmt.executeQuery();
           
           if(rs.next()){
              articleList = new ArrayList();
              do{

            	 OnBookDto article = new OnBookDto();
                 article.setD_bno(rs.getInt("d_bno"));
                 article.setD_bcode(rs.getInt("d_bcode"));
                 article.setD_bname(rs.getString("d_bname"));
                 article.setD_bpublisher(rs.getString("d_bpublisher"));
                 article.setD_bauthor(rs.getString("d_bauthor"));
                 article.setD_bgenre(rs.getString("d_bgenre"));
                 article.setD_bgenre2(rs.getString("d_bgenre2"));
                 article.setD_blocation(rs.getString("d_blocation"));
                 article.setD_bregistdate(rs.getString("d_bregistdate"));
                 article.setD_bpic(rs.getString("d_bpic"));
                 article.setD_bcount(rs.getInt("d_bcount"));
                 article.setD_bvalue(rs.getInt("d_bvalue"));
                 article.setD_id(rs.getString("d_id"));                
                 article.setD_bdeliverycode(rs.getInt("d_bdeliverycode"));
                 article.setD_bdate(rs.getTimestamp("d_bdate"));
                 

                 
                 //�׿� �˼��׸��
                 article.setD_bgrade(rs.getString("d_bgrade"));
                 article.setD_bvalue(rs.getInt("d_bvalue"));
                 article.setD_bsellvalue(rs.getInt("d_bsellvalue"));
                 article.setD_bpurchasevalue(rs.getInt("d_bpurchasevalue"));
                 article.setD_icode(rs.getInt("D_icode"));
                 
            	              
                 article.setD_sno(rs.getInt("d_sno"));
                 article.setD_sdate(rs.getTimestamp("d_sdate"));
                 article.setD_sfinish(rs.getInt("d_sfinish")); //*****
                                  
                 articleList.add(article);

                             
              }while(rs.next());
           }
           
        }catch(Exception e){
           e.printStackTrace();
        }finally{
           if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
           if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
           if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
        }
        return articleList;
     }
   
  //-----------�ǸŽ�û �Ϸ� å count(�˼���)-----------�ǸŽ�û �Ϸ� å count(�˼���)-----------�ǸŽ�û �Ϸ� å count(�˼���)-----------�ǸŽ�û �Ϸ� å count(�˼���)   
    public int getD_bmySellingFinishCount(String d_id) throws Exception{
        int x = 0;
        try{
           conn = getConnection();
           pstmt = conn.prepareStatement(
//�� ���̺��� å�ڵ尡 ����, ȸ�� �̸��� ����, ��ûȮ�� ���� �ʱⰪ(�˼���,�˼��ʿ�=0)�� ���� ���ڵ� ��                 
"select count(*) from d_onSellList s, d_onBook b "+ 
" where s.d_bcode = b.d_bcode and s.d_id = b.d_id and s.d_id = '"+d_id+"' and s.d_sfinish != 0"
 );
           rs = pstmt.executeQuery();
           if(rs.next()){
              x = rs.getInt(1); //ī��Ʈ ù��° ���� ���� ����Ͽ� x�� ����
           }
        }catch(Exception e){
           e.printStackTrace();
        }finally{
           if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
           if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
           if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
        }
        return x ;
     }       
    
//-----------�ǸŽ�û�Ϸ�� å list-----------�ǸŽ�û�Ϸ�� å list-----------�ǸŽ�û�Ϸ�� å list-----------�ǸŽ�û�Ϸ�� å list-----------�ǸŽ�û�Ϸ�� å list
    public List getD_bMySellingFinishList(String d_id, int startRow, int endRow) throws Exception{

        List articleList = null;
        
        try {
           conn = getConnection();
           pstmt = conn.prepareStatement(
"select *  from "+ 
"(select b.*, s.d_sno, s.d_sdate, s.d_sfinish, rownum r  from d_onSellList s, d_onBook b "+
"where s.d_bcode = b.d_bcode and s.d_id = b.d_id and s.d_id = '"+d_id+"' and s.d_sfinish != 0 order by d_bno asc) "+
"where r >= "+startRow+" and r <= "+endRow+" order by d_sno asc"
);
           rs = pstmt.executeQuery();
           
           if(rs.next()){
              articleList = new ArrayList();
              do{

                OnBookDto article = new OnBookDto();
                 article.setD_bno(rs.getInt("d_bno"));
                 article.setD_bcode(rs.getInt("d_bcode"));
                 article.setD_bname(rs.getString("d_bname"));
                 article.setD_bpublisher(rs.getString("d_bpublisher"));
                 article.setD_bauthor(rs.getString("d_bauthor"));
                 article.setD_bgenre(rs.getString("d_bgenre"));
                 article.setD_bgenre2(rs.getString("d_bgenre2"));
                 article.setD_blocation(rs.getString("d_blocation"));
                 article.setD_bregistdate(rs.getString("d_bregistdate"));
                 article.setD_bpic(rs.getString("d_bpic"));
                 article.setD_bcount(rs.getInt("d_bcount"));
                 article.setD_bvalue(rs.getInt("d_bvalue"));
                 article.setD_id(rs.getString("d_id"));                
                 article.setD_bdeliverycode(rs.getInt("d_bdeliverycode"));
                 article.setD_bdate(rs.getTimestamp("d_bdate"));
                 

                 
                 //�׿� �˼��׸��
                 article.setD_bgrade(rs.getString("d_bgrade"));
                 article.setD_bvalue(rs.getInt("d_bvalue"));
                 article.setD_bsellvalue(rs.getInt("d_bsellvalue"));
                 article.setD_bpurchasevalue(rs.getInt("d_bpurchasevalue"));
                 article.setD_icode(rs.getInt("D_icode"));
                 
                             
                 article.setD_sno(rs.getInt("d_sno"));
                 article.setD_sdate(rs.getTimestamp("d_sdate"));
                 article.setD_sfinish(rs.getInt("d_sfinish")); //*****
                                  
                 articleList.add(article);

                             
              }while(rs.next());
           }
           
        }catch(Exception e){
           e.printStackTrace();
        }finally{
           if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
           if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
           if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
        }
        return articleList;
     }    
    
  //---------������ å DB�� ���ڵ� �� count-------������ å DB�� ���ڵ� �� count-------������ å DB�� ���ڵ� �� count-------������ å DB�� ���ڵ� �� count-------������ å DB�� ���ڵ� �� count 
    public int Admin_SellCount() throws Exception{
      int x = 0;
      try{
         conn = getConnection();
         pstmt = conn.prepareStatement("select count(*) from d_onBook ");
         rs = pstmt.executeQuery();
         if(rs.next()){
            x = rs.getInt(1); //ī��Ʈ ù��° ���� ���� ����Ͽ� x�� ����
         }
      }catch(Exception e){
         e.printStackTrace();
      }finally{
         if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
         if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
         if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
      }
      return x ;
   }    
    
  //-----------������ �ǸŽ�û ���� å list-----------������ �ǸŽ�û ���� å list-----------������ �ǸŽ�û ���� å list ----------������ �ǸŽ�û ���� å list-----------
    public List Admin_SellList(int start, int end) throws Exception{

        List articleList = null;
        
        try {
           conn = getConnection();
           pstmt = conn.prepareStatement("SELECT b.*, s.d_sno, s.d_bcode, s.d_id, s.d_sfinish FROM d_onSellList s, d_onBook b where "
           									+ "s.d_sfinish=0 and s.d_bcode = b.d_bcode order by d_sno asc");
     
           rs = pstmt.executeQuery();
           
           if(rs.next()){
              articleList = new ArrayList();
              do{
            	  OnBookDto dto = new OnBookDto();
            	  dto.setD_bno(rs.getInt("d_bno"));
                  dto.setD_bcode(rs.getInt("d_bcode"));
                  dto.setD_bname(rs.getString("d_bname"));
                  dto.setD_bpublisher(rs.getString("d_bpublisher"));
                  dto.setD_bauthor(rs.getString("d_bauthor"));
                  dto.setD_bgenre(rs.getString("d_bgenre"));
                  dto.setD_bgenre2(rs.getString("d_bgenre2"));
                  dto.setD_blocation(rs.getString("d_blocation"));
                  dto.setD_bregistdate(rs.getString("d_bregistdate"));
                  dto.setD_bpic(rs.getString("d_bpic"));
                  dto.setD_bcount(rs.getInt("d_bcount"));
                  dto.setD_bvalue(rs.getInt("d_bvalue"));
                  dto.setD_id(rs.getString("d_id"));                
                  dto.setD_bdeliverycode(rs.getInt("d_bdeliverycode"));
                  dto.setD_bdate(rs.getTimestamp("d_bdate"));
                  
            	  dto.setD_sno(rs.getInt("d_sno"));
            	  dto.setD_sfinish(rs.getInt("d_sfinish"));              
                 articleList.add(dto);
              
                             
              }while(rs.next());
           }
           
        }catch(Exception e){
           e.printStackTrace();
        }finally{
           if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
           if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
           if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
        }
        return articleList;
     }
    
  //----------------������ �˼��� å ����----------------������ �˼��� å ����---------------������ �˼��� å ����--------------������ �˼��� å ����-------------
    public OnBookDto Admin_Inspection(int d_bcode) throws Exception{

    	OnBookDto dto = null;
        
        try {
           conn = getConnection();
           pstmt = conn.prepareStatement("select b.*, s.* from d_onSellList s, d_onBook b  "
           								+ "where s.d_sfinish=0 and s.d_bcode=? and b.d_bcode=?");
           pstmt.setInt(1, d_bcode);
           pstmt.setInt(2, d_bcode);
                
           rs = pstmt.executeQuery();
           
           if(rs.next()){
 
              do{
            	  dto = new OnBookDto();
            	  dto.setD_bno(rs.getInt("d_bno"));
                  dto.setD_bcode(rs.getInt("d_bcode"));
                  dto.setD_bname(rs.getString("d_bname"));
                  dto.setD_bpublisher(rs.getString("d_bpublisher"));
                  dto.setD_bauthor(rs.getString("d_bauthor"));
                  dto.setD_bgenre(rs.getString("d_bgenre"));
                  dto.setD_bgenre2(rs.getString("d_bgenre2"));
                  dto.setD_blocation(rs.getString("d_blocation"));
                  dto.setD_bregistdate(rs.getString("d_bregistdate"));    
                  dto.setD_bpic(rs.getString("d_bpic"));
                  dto.setD_bcount(rs.getInt("d_bcount"));
                  dto.setD_bvalue(rs.getInt("d_bvalue"));
                  dto.setD_id(rs.getString("d_id"));                
                  dto.setD_bdeliverycode(rs.getInt("d_bdeliverycode"));
                  dto.setD_bdate(rs.getTimestamp("d_bdate"));
                  
            	  dto.setD_sno(rs.getInt("d_sno"));
            	  dto.setD_sfinish(rs.getInt("d_sfinish"));                  
                             
              }while(rs.next());
           }
           
        }catch(Exception e){
           e.printStackTrace();
        }finally{
           if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
           if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
           if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
        }
        return dto;
     } 
    
  //----------------������ �˼� DB ���----------------������ �˼� DB ���---------------������ �˼� DB ���--------------������ �˼� DB ���-------------������ �˼� DB ���
  //----------------������ �Ǹ��� ��û�Ϸ�-> �˼� (0 -> 1��) ----------------������ �Ǹ��� ��û�Ϸ�-> �˼� (0 -> 1��)---------------������ �Ǹ��� ��û�Ϸ�-> �˼� (0 -> 1��)--------------������ �Ǹ��� ��û�Ϸ�-> �˼� (0 -> 1��) 1��-------------

  public void Admin_Inspection_insert(OnInspectionDto dto){
  		
  		int x = -1;
  		try{
  			conn = getConnection();
  			pstmt = conn.prepareStatement("insert into d_Inspection values(d_Inspection_seq.NEXTVAL,?,d_Inspection_icode_seq.NEXTVAL,?,?,?,?,?,sysdate)");

  			
  			pstmt.setInt(1, dto.getD_bcode());
  			pstmt.setInt(2, dto.getD_iold());
  			pstmt.setInt(3, dto.getD_icover());
  			pstmt.setInt(4, dto.getD_ispine());
  			pstmt.setInt(5, dto.getD_ibind());
  			pstmt.setInt(6, dto.getD_itotal());
  			
  			pstmt.executeUpdate();
  			
  			pstmt = conn.prepareStatement("update d_onSellList set d_sfinish = 1 where d_bcode= ?");
  			pstmt.setInt(1, dto.getD_bcode());	
  			pstmt.executeUpdate();
  			

  		}catch(Exception e){
  			e.printStackTrace();
  		}finally{
  			 if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
  			if(pstmt != null){ try{pstmt.close();}catch(SQLException s){}}
  			if(conn != null){ try{conn.close();}catch(SQLException s){}}
  		}
  		
  	}


//-----------������ �ǸŽ�û �Ϸ� å list-----------������ �ǸŽ�û �Ϸ� å list-----------������ �ǸŽ�û �Ϸ� å list ----------������ �ǸŽ�û �Ϸ� å list-----------
public List Admin_InspectionList(int start, int end) throws Exception{

    List articleList = null;
    
    try {
       conn = getConnection();

       pstmt = conn.prepareStatement("SELECT d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher, d_bauthor, d_bgenre,d_bgenre2, d_blocation,"
          		+ "d_bregistdate,d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, D_icode, d_id, d_bdeliverycode, d_bdate,"
          		+ "d_sno, d_sfinish, d_sdate , r FROM "
          		+ "(SELECT d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher, d_bauthor, d_bgenre, d_bgenre2, d_blocation,"
          		+ "d_bregistdate, d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, D_icode, d_id, d_bdeliverycode, d_bdate, "
          		+ "d_sno,d_bdeliverycode  d_sfinish, d_sdate , rownum r FROM "
          		+ "(SELECT b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation,"
          		+ "b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.D_icode, b.d_id, b.d_bdeliverycode, b.d_bdate, "
          		+ "s.d_sno, s.d_sfinish, s.d_sdate FROM "
          		+ "d_onSellList s, d_onBook b where s.d_sfinish= 1 and s.d_bcode = b.d_bcode)order by d_sno asc) where r >= ? and r <=?");
       pstmt.setInt(1, start);
       pstmt.setInt(2, end);
 
       rs = pstmt.executeQuery();
       
       if(rs.next()){
          articleList = new ArrayList();
          do{
        	  OnBookDto dto = new OnBookDto();
        	  dto.setD_bno(rs.getInt("d_bno"));
              dto.setD_bcode(rs.getInt("d_bcode"));
              dto.setD_bname(rs.getString("d_bname"));
              dto.setD_bpublisher(rs.getString("d_bpublisher"));
              dto.setD_bauthor(rs.getString("d_bauthor"));
              dto.setD_bgenre(rs.getString("d_bgenre"));
              dto.setD_bgenre2(rs.getString("d_bgenre2"));
              dto.setD_blocation(rs.getString("d_blocation"));
              dto.setD_bregistdate(rs.getString("d_bregistdate"));
              dto.setD_bpic(rs.getString("d_bpic"));
              dto.setD_bcount(rs.getInt("d_bcount"));
              dto.setD_bvalue(rs.getInt("d_bvalue"));
              dto.setD_id(rs.getString("d_id"));                
              dto.setD_bdeliverycode(rs.getInt("d_bdeliverycode"));
              dto.setD_bdate(rs.getTimestamp("d_bdate"));
              
        	  dto.setD_sno(rs.getInt("d_sno"));
        	  dto.setD_sfinish(rs.getInt("d_sfinish"));              
             articleList.add(dto);
          
                         
          }while(rs.next());
       }
       
    }catch(Exception e){
       e.printStackTrace();
    }finally{
       if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
       if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
       if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
    }
    return articleList;
 }
   
   
//----------------������ ����� å ���� List----------------������ ����� å ���� List----------------������ ����� å ���� List--------------������ ����� å ���� List-------------
public OnBookDto Admin_Onbook(int d_bcode) throws Exception{

	OnBookDto dto = null;
  
  try {
     conn = getConnection();
     pstmt = conn.prepareStatement(
"select b.d_bno,b.d_bcode,b.d_bname,b.d_bpublisher,b.d_bauthor,b.d_bgenre,b.d_bgenre2,b.d_blocation,b.d_bregistdate, " + 
"b.d_bpic,b.d_bcount, b.d_bvalue, b.d_id, b.d_bdeliverycode, b.d_bdate, s.d_sno, s.d_sfinish, i.d_icode,i.d_iold,i.d_icover, " + 
"i.d_ispine, i.d_ibind, i.d_itotal ,i.d_idate from d_onSellList s, d_onBook b, d_Inspection i " + 
"where s.d_sfinish=1 and s.d_bcode=? and b.d_bcode=? and i.d_bcode=?"
);
     pstmt.setInt(1, d_bcode);
     pstmt.setInt(2, d_bcode);
     pstmt.setInt(3, d_bcode);
          
     rs = pstmt.executeQuery();
     
     if(rs.next()){

        do{
      	  dto = new OnBookDto();
      	  dto.setD_bno(rs.getInt("d_bno"));
            dto.setD_bcode(rs.getInt("d_bcode"));
            dto.setD_bname(rs.getString("d_bname"));
            dto.setD_bpublisher(rs.getString("d_bpublisher"));
            dto.setD_bauthor(rs.getString("d_bauthor"));
            dto.setD_bgenre(rs.getString("d_bgenre"));
            dto.setD_bgenre2(rs.getString("d_bgenre2"));
            dto.setD_blocation(rs.getString("d_blocation"));
            dto.setD_bregistdate(rs.getString("d_bregistdate"));    
            dto.setD_bpic(rs.getString("d_bpic"));
            dto.setD_bcount(rs.getInt("d_bcount"));
            dto.setD_bvalue(rs.getInt("d_bvalue"));
            dto.setD_id(rs.getString("d_id"));                
            dto.setD_bdeliverycode(rs.getInt("d_bdeliverycode"));
            dto.setD_bdate(rs.getTimestamp("d_bdate"));
            
      	  dto.setD_sno(rs.getInt("d_sno"));
      	  dto.setD_sfinish(rs.getInt("d_sfinish")); 
      	
      	  dto.setD_icode(rs.getInt("d_icode"));
      	  dto.setD_iold(rs.getInt("d_iold"));
      	  dto.setD_icover(rs.getInt("d_icover"));
      	  dto.setD_ispine(rs.getInt("d_ispine"));
      	  dto.setD_ibind(rs.getInt("d_ibind"));
      	  dto.setD_itotal(rs.getInt("d_itotal"));
      	  dto.setD_idate(rs.getTimestamp("d_idate"));
                       
        }while(rs.next());
     }
     
  }catch(Exception e){
     e.printStackTrace();
  }finally{
     if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
     if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
     if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
  }
  return dto;
  
}


//----------------������ å DB ���----------------������ å DB ���---------------������ å DB ���--------------������ å DB ���-------------������ å DB ���
//----------------������ �˼� -> å���( 1 -> 2��) ----------------������ �˼� -> å���( 1 -> 2��)---------------������ �˼� -> å���( 1 -> 2��)--------------������ �˼� -> å���( 1 -> 2��)-------------
public void Admin_OnBook_Update(OnBookDto dto, int d_bcode){

		try{
			conn = getConnection();
			pstmt = conn.prepareStatement("update d_onBook set d_bname =?, d_bgrade=?, d_bpublisher=?, d_bauthor=?, "
					+ "d_bgenre=?, d_bgenre2=?, d_blocation=?, d_bregistdate=?, d_bpic=?,d_bvalue=?, d_bsellvalue=?, "
					+ "d_bpurchasevalue=?, d_icode=? , d_bdate=sysdate where d_bcode=?  ");
			
			pstmt.setString(1, dto.getD_bname());
			pstmt.setString(2, dto.getD_bgrade());
			pstmt.setString(3, dto.getD_bpublisher());
			pstmt.setString(4, dto.getD_bauthor());
			pstmt.setString(5, dto.getD_bgenre());
			pstmt.setString(6, dto.getD_bgenre2());
			pstmt.setString(7, dto.getD_blocation());
			pstmt.setString(8, dto.getD_bregistdate());
			pstmt.setString(9, dto.getD_bpic());
			pstmt.setInt(10, dto.getD_bvalue());
			pstmt.setInt(11, dto.getD_bsellvalue());
			pstmt.setInt(12, dto.getD_bpurchasevalue());
			pstmt.setInt(13, dto.getD_icode());
			pstmt.setInt(14, d_bcode);

			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement("update d_onSellList set d_sfinish = 2 where d_bcode= ?");
			pstmt.setInt(1, d_bcode);	
			pstmt.executeUpdate();
			

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			 if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
			if(pstmt != null){ try{pstmt.close();}catch(SQLException s){}}
			if(conn != null){ try{conn.close();}catch(SQLException s){}}
		}
		
	}


//----------------������ å DB ���(����, �Ұ���)----------------������ å DB ���(����, �Ұ���)--------------������ å DB ���(����, �Ұ���)--------------������ å DB ���(����, �Ұ���)
public void Admin_OnBookIntro_insert(OnBookIntroDto dto, int d_bcode){
		
		try{
			conn = getConnection();
			pstmt = conn.prepareStatement("insert into d_onBookIntro values(d_onBookIntro_seq.NEXTVAL,?,?,?,sysdate)");

			
			pstmt.setInt(1, d_bcode);
			pstmt.setString(2, dto.getD_norder());
			pstmt.setString(3, dto.getD_nintro());
			
			pstmt.executeUpdate();
			

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			 if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
			if(pstmt != null){ try{pstmt.close();}catch(SQLException s){}}
			if(conn != null){ try{conn.close();}catch(SQLException s){}}
		}
	}

//--------------������ å DB �ҷ�����(����, �Ұ���)--------------������ å DB �ҷ�����(����, �Ұ���)--------------������ å DB �ҷ�����(����, �Ұ���)--------------������ å DB �ҷ�����(����, �Ұ���)
public OnBookIntroDto Admin_OnBookIntro_load(int d_bcode){
	OnBookIntroDto obiDto  = null;
	try{
		conn = getConnection();
		pstmt = conn.prepareStatement("select d_bcode, d_norder, d_nintro from d_onBookIntro where d_bcode = "+d_bcode);
		
		rs = pstmt.executeQuery();
		
		if(rs.next()){
			obiDto = new OnBookIntroDto();
			obiDto.setD_bcode(rs.getInt("d_bcode"));
			obiDto.setD_norder(rs.getString("d_norder"));
			obiDto.setD_nintro(rs.getString("d_nintro"));
		}

	}catch(Exception e){
		e.printStackTrace();
	}finally{
		 if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
		if(pstmt != null){ try{pstmt.close();}catch(SQLException s){}}
		if(conn != null){ try{conn.close();}catch(SQLException s){}}
	}
	return obiDto;
}


//-----------������ ��ϵ� å list-----------������ ��ϵ� å list-----------������ ��ϵ� å list ----------������ ��ϵ� å list-----------������ ��ϵ� å list
public List Admin_OnBookList(int start, int end) throws Exception{

  List articleList = null;
  
  try {
     conn = getConnection();

     pstmt = conn.prepareStatement("SELECT d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher, d_bauthor, d_bgenre,d_bgenre2, d_blocation,"
       		+ "d_bregistdate,d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, D_icode, d_id, d_bdeliverycode, d_bdate,"
       		+ "d_sno, d_sfinish, d_sdate ,d_norder, d_nintro, r FROM "
       		+ "(SELECT d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher, d_bauthor, d_bgenre, d_bgenre2, d_blocation,"
       		+ "d_bregistdate, d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, D_icode, d_id, d_bdeliverycode, d_bdate, "
       		+ "d_sno,d_bdeliverycode  d_sfinish, d_sdate ,d_norder, d_nintro, rownum r FROM "
       		+ "(SELECT b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation,"
       		+ "b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.D_icode, b.d_id, b.d_bdeliverycode, b.d_bdate, "
       		+ "s.d_sno, s.d_sfinish, s.d_sdate, n.d_norder, n.d_nintro FROM "
       		+ "d_onSellList s, d_onBook b, d_onBookIntro n where s.d_sfinish=2 and s.d_bcode = b.d_bcode and b.d_bcode= n.d_bcode)order by d_sno asc) where r >= ? and r <=?");
     
     pstmt.setInt(1, start);
     pstmt.setInt(2, end);
     rs = pstmt.executeQuery();
     
     if(rs.next()){
        articleList = new ArrayList();
        do{
      	  OnBookDto dto = new OnBookDto();
      	  dto.setD_bno(rs.getInt("d_bno"));
            dto.setD_bcode(rs.getInt("d_bcode"));
            dto.setD_bname(rs.getString("d_bname"));
            dto.setD_bpublisher(rs.getString("d_bpublisher"));
            dto.setD_bauthor(rs.getString("d_bauthor"));
            dto.setD_bgenre(rs.getString("d_bgenre"));
            dto.setD_bgenre2(rs.getString("d_bgenre2"));
            dto.setD_blocation(rs.getString("d_blocation"));
            dto.setD_bregistdate(rs.getString("d_bregistdate"));
            dto.setD_bpic(rs.getString("d_bpic"));
            dto.setD_bcount(rs.getInt("d_bcount"));
            dto.setD_bvalue(rs.getInt("d_bvalue"));
            dto.setD_id(rs.getString("d_id"));                
            dto.setD_bdeliverycode(rs.getInt("d_bdeliverycode"));
            dto.setD_bdate(rs.getTimestamp("d_bdate"));
            
      	  dto.setD_sno(rs.getInt("d_sno"));
      	  dto.setD_sfinish(rs.getInt("d_sfinish"));     
      	  
      	  dto.setD_norder(rs.getString("d_norder"));
      	  dto.setD_nintro(rs.getString("d_nintro"));
           articleList.add(dto);
        
                       
        }while(rs.next());
     }
     
  }catch(Exception e){
     e.printStackTrace();
  }finally{
     if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
     if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
     if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
  }
  return articleList;
}
 


//-----------������ ��ϵ� å �󼼺���-----------������ ��ϵ� å �󼼺���-----------������ ��ϵ� å �󼼺��� ----------������ ��ϵ� å �󼼺���-----------������ ��ϵ� å �󼼺���
public OnBookDto Admin_OnBook_Detail(int d_bcode) throws Exception{
	OnBookDto dto = null;
try {
   conn = getConnection();
   pstmt = conn.prepareStatement("SELECT b.*, s.d_sno, s.d_bcode, s.d_id, s.d_sfinish, n.*, i.* FROM d_onSellList s, d_onBook b, d_onBookIntro n, d_Inspection i  where "
   									+ "s.d_sfinish=2 and s.d_bcode =? and  b.d_bcode=? and n.d_bcode =? and i.d_bcode=?");
   pstmt.setInt(1, d_bcode);
   pstmt.setInt(2, d_bcode);
   pstmt.setInt(3, d_bcode);
   pstmt.setInt(4, d_bcode);

   rs = pstmt.executeQuery();
   
   if(rs.next()){
     
      do{
      	dto = new OnBookDto();
      	dto.setD_bno(rs.getInt("d_bno"));
          dto.setD_bcode(rs.getInt("d_bcode"));
          dto.setD_bname(rs.getString("d_bname"));
          dto.setD_bgrade(rs.getString("d_bgrade"));
          dto.setD_bpublisher(rs.getString("d_bpublisher"));
          dto.setD_bauthor(rs.getString("d_bauthor"));
          dto.setD_bgenre(rs.getString("d_bgenre"));
          dto.setD_bgenre2(rs.getString("d_bgenre2"));
          dto.setD_blocation(rs.getString("d_blocation"));
          dto.setD_bregistdate(rs.getString("d_bregistdate"));
          dto.setD_bpic(rs.getString("d_bpic"));
          dto.setD_bcount(rs.getInt("d_bcount"));
          dto.setD_bvalue(rs.getInt("d_bvalue"));
          dto.setD_bsellvalue(rs.getInt("d_bsellvalue"));
          dto.setD_bpurchasevalue(rs.getInt("d_bpurchasevalue"));
          dto.setD_id(rs.getString("d_id"));                
          dto.setD_bdeliverycode(rs.getInt("d_bdeliverycode"));
          dto.setD_bdate(rs.getTimestamp("d_bdate"));
          
    	  dto.setD_sno(rs.getInt("d_sno"));
    	  dto.setD_sfinish(rs.getInt("d_sfinish"));    
    	  
    	  dto.setD_icode(rs.getInt("d_icode"));
    	  dto.setD_iold(rs.getInt("d_iold"));
    	  dto.setD_icover(rs.getInt("d_icover"));
    	  dto.setD_ispine(rs.getInt("d_ispine"));
    	  dto.setD_ibind(rs.getInt("d_ibind"));
    	  dto.setD_itotal(rs.getInt("d_itotal"));
    	  dto.setD_idate(rs.getTimestamp("d_idate"));
    	 
    	  
    	  dto.setD_norder(rs.getString("d_norder"));
    	  dto.setD_nintro(rs.getString("d_nintro"));
      
                     
      }while(rs.next());
   }
   
}catch(Exception e){
   e.printStackTrace();
}finally{
   if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
   if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
   if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
}
return dto;

}
 
//----------------������ å  ����----------------������ å  ����---------------������ å  ����--------------������ å  ����-------------������ å  ����
public void Admin_OnBook_Modify(OnBookDto dto, int d_bcode){


		try{
			conn = getConnection();
			pstmt = conn.prepareStatement("update d_onBook set d_bname =?, d_bgrade=?, d_bpublisher=?, d_bauthor=?, "
					+ "d_bgenre=?, d_bgenre2=?, d_blocation=?, d_bregistdate=?, d_bpic=?,d_bvalue=?, d_bsellvalue=?, "
					+ "d_bpurchasevalue=?, d_icode=? , d_bdate=sysdate where d_bcode=?  ");
			
			pstmt.setString(1, dto.getD_bname());
			pstmt.setString(2, dto.getD_bgrade());
			pstmt.setString(3, dto.getD_bpublisher());
			pstmt.setString(4, dto.getD_bauthor());
			pstmt.setString(5, dto.getD_bgenre());
			pstmt.setString(6, dto.getD_bgenre2());
			pstmt.setString(7, dto.getD_blocation());
			pstmt.setString(8, dto.getD_bregistdate());
			pstmt.setString(9, dto.getD_bpic());
			pstmt.setInt(10, dto.getD_bvalue());
			pstmt.setInt(11, dto.getD_bsellvalue());
			pstmt.setInt(12, dto.getD_bpurchasevalue());
			pstmt.setInt(13, dto.getD_icode());
			pstmt.setInt(14, d_bcode);

			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement("update d_Inspection set d_iold=?,d_icover=?, d_ispine=?, "
					+ "d_ibind=?, d_itotal=?, d_idate=sysdate where d_bcode= ?");
			pstmt.setInt(1, dto.getD_iold());
			pstmt.setInt(2, dto.getD_icover());
			pstmt.setInt(3, dto.getD_ispine());
			pstmt.setInt(4, dto.getD_ibind());
			pstmt.setInt(5, dto.getD_itotal());
			pstmt.setInt(6, d_bcode);	
			pstmt.executeUpdate();
			
			
			pstmt = conn.prepareStatement("update d_onBookIntro set d_norder=?, d_nintro=?, d_ndate=sysdate "
					+ " where d_bcode= ? ");

			pstmt.setString(1, dto.getD_norder());
			pstmt.setString(2, dto.getD_nintro());
			pstmt.setInt(3, d_bcode);
			pstmt.executeUpdate();
			

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			 if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
			if(pstmt != null){ try{pstmt.close();}catch(SQLException s){}}
			if(conn != null){ try{conn.close();}catch(SQLException s){}}
		}
		
	}

//------------------å DB ����------------------å DB ����------------------å DB ����------------------å DB ����----------------å DB ����
public void Admin_OnBook_Delete(int d_bcode) throws Exception{

try{
   conn = getConnection();
   pstmt = conn.prepareStatement("delete from d_onBook where d_bcode=?");
   pstmt.setInt(1, d_bcode);	
   pstmt.executeUpdate();
   
   pstmt = conn.prepareStatement("delete from d_onSellList where d_bcode=?");
   pstmt.setInt(1, d_bcode);	
   pstmt.executeUpdate();
   
   pstmt = conn.prepareStatement("delete from d_Inspection where d_bcode=?");
   pstmt.setInt(1, d_bcode);	
   pstmt.executeUpdate();
   
   pstmt = conn.prepareStatement("delete from d_onBookIntro where d_bcode=?");
   pstmt.setInt(1, d_bcode);	
   pstmt.executeUpdate();
  
}catch(Exception e){
   e.printStackTrace();
}finally{
   if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
   if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
   if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
}

}

//--------���������� �ǸŽ�û����Ʈ���� �۹�ȣ�� ������ --------���������� �ǸŽ�û����Ʈ���� �۹�ȣ�� ������ --------���������� �ǸŽ�û����Ʈ���� �۹�ȣ�� ������ --------���������� �ǸŽ�û����Ʈ���� �۹�ȣ�� ������ 
public OnBookDto FindRandomArticle(String d_bname) throws Exception{
    OnBookDto article = null;
    
    try{
       conn = getConnection();
       pstmt = conn.prepareStatement(
             "select d_bcode from d_onBook where d_bname = "+ d_bname);
       
       rs = pstmt.executeQuery();
       
       if(rs.next()){
         article = new OnBookDto();
         article.setD_bcode(rs.getInt("d_bcode"));                    
       }          
    }catch(Exception e){
       e.printStackTrace();
    }finally{ 
          if( rs != null ){ try{ rs.close(); }catch( SQLException se ){}};
          if( pstmt != null ){ try{ pstmt.close(); }catch( SQLException se ){}};
          if( conn != null ){ try{ conn.close(); }catch( SQLException se ){}};
    }
    return article;
 }
 













//���� �޼ҵ� ��ȣ��   
}
    

