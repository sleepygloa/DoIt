package mvc.doit.Online;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import mvc.doit.Account.AcDto;
import mvc.doit.Delivery.DeliveryDto;
import mvc.doit.Login.LoginDto;
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
"select d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher,d_bauthor, d_bgenre, d_bgenre2, d_blocation, d_bregistdate, d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, d_icode, d_id, to_char(d_bdate, 'yyyy-mm-dd') AS d_bdate from d_onBook) b, d_onSellList s " +
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
        		"select count(*) from (select b.* from d_onBook b, (SELECT min(d_bcode) As d_bcode  FROM d_onBook b  GROUP BY d_bname) s "+
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
   
   
//-------d_onBook ��üList--------------------------------------------------------------------------------------- 
  public List<OnBookDto> getD_BSellList(int startRow, int endRow)  throws Exception{
     List<OnBookDto> articleList = null;
     try {
        conn = getConnection();
        pstmt = conn.prepareStatement(
"select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,r "+
"from (select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,rownum r "+
"from (select b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation, b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.D_icode, b.d_id, b.d_bdeliverycode, b.d_bdate "+
"from (select b.* from d_onBook b, (SELECT min(d_bcode) As d_bcode  FROM d_onBook b  GROUP BY d_bname) s "+
"where b.d_bcode = s.d_bcode) b , d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2)) where r >= "+startRow+" and r <= "+endRow        		
        		);
       		 
        rs = pstmt.executeQuery();
        
        if(rs.next()){
           articleList = new ArrayList<OnBookDto>();
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
  
//-------d_onBook ��üList �� �� å�� ��� �ǸŰ� �˻� -----------------------------------------------------------------
  public int getBookNameToAvgSellValue(String getD_bname) throws Exception{
	  
     int avgSellValue = 0;
     int avgSellValueCount = 1;
     try {
    	 
        conn = getConnection();
        pstmt = conn.prepareStatement(
     			"select count(*) from d_onBook where  d_bname = '" + getD_bname + "'"
     		      			);
     		      	rs = pstmt.executeQuery();
     		      	if(rs.next()){
     		      		avgSellValueCount = rs.getInt(1);
     		      	}
     		                
     		        pstmt = conn.prepareStatement(
     		"select d_bsellvalue from d_onBook where d_bname = '" + getD_bname + "'"     		
     		        		);
     		        rs = pstmt.executeQuery();
     		        if(rs.next()){
     		        	do{
     		        		avgSellValue += rs.getInt(1);
     		        	}while(rs.next());
     		        }
     		        
     		        avgSellValue = ((avgSellValue / avgSellValueCount) / 100 *100) ;

     }catch(Exception e){
        e.printStackTrace();
     }finally{
        if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
        if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
        if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
     }
     return avgSellValue;
  }   


//-------å�帣 ���ǿ� ���� å DB�� ���ڵ� �� count-------å�帣 ���ǿ� ���� å DB�� ���ڵ� �� count-------å�帣 ���ǿ� ���� å DB�� ���ڵ� �� count-------å�帣 ���ǿ� ���� å DB�� ���ڵ� �� count-------å�帣 ���ǿ� ���� å DB�� ���ڵ� �� count 
   public int getD_BSellCount(String d_bonFillter, int d_bonFillterReturn) throws Exception{
     int x = 0;
     try{
        conn = getConnection();
        String sql = "";
if(d_bonFillterReturn != 0 && d_bonFillterReturn >= 1 & d_bonFillterReturn <= 6){
	sql += "select count(*) from (select b.* from d_onBook b, (SELECT min(d_bcode) As d_bcode  FROM d_onBook b  GROUP BY d_bname) s "+
		    "where b.d_bcode = s.d_bcode and d_bgenre = '" +d_bonFillter+ "') b, d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2";	
}else if(d_bonFillterReturn >= 7 & d_bonFillterReturn <= 10){
	sql += "select count(*) from (select b.* from d_onBook b, (SELECT min(d_bcode) As d_bcode  FROM d_onBook b  GROUP BY d_bname) s "+
		    "where b.d_bcode = s.d_bcode and d_bgenre2 = '" +d_bonFillter+ "') b, d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2";
}else if(d_bonFillterReturn >= 11 & d_bonFillterReturn <= 12){
	sql += "select count(*) from (select b.* from d_onBook b, (SELECT min(d_bcode) As d_bcode  FROM d_onBook b  GROUP BY d_bname) s "+
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
	sql += "select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,r "+
			"from (select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,rownum r "+
			"from (select b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation, b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.D_icode, b.d_id, b.d_bdeliverycode, b.d_bdate "+
			"from (select b.* from d_onBook b, (SELECT min(d_bcode) As d_bcode  FROM d_onBook b  GROUP BY d_bname) s "+
			"where b.d_bcode = s.d_bcode and d_bgenre = '" +d_bonFillter+ "') b , d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2 )) where r >= "+startRow+" and r <= "+endRow;	
}else if(d_bonFillterReturn >= 7 & d_bonFillterReturn <= 10){
	sql += "select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,r "+
			"from (select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,rownum r "+
			"from (select b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation, b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.D_icode, b.d_id, b.d_bdeliverycode, b.d_bdate "+
			"from (select b.* from d_onBook b, (SELECT min(d_bcode) As d_bcode  FROM d_onBook b  GROUP BY d_bname) s "+
			"where b.d_bcode = s.d_bcode and d_bgenre2 = '" +d_bonFillter+ "') b , d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2 )) where r >= "+startRow+" and r <= "+endRow;	
}else if(d_bonFillterReturn >= 11 & d_bonFillterReturn <= 12){
	sql += "select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,r "+
			"from (select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,rownum r "+
			"from (select b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation, b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.D_icode, b.d_id, b.d_bdeliverycode, b.d_bdate "+
			"from (select b.* from d_onBook b, (SELECT min(d_bcode) As d_bcode  FROM d_onBook b  GROUP BY d_bname) s "+
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
		"select count(*) from (select b.* from d_onBook b, (SELECT min(d_bcode) As d_bcode  FROM d_onBook b  GROUP BY d_bname) s "+
        "where b.d_bcode = s.d_bcode) b, d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2  and " +
		"(b.d_bgenre like '%"+select +"%' or b.d_bgenre2 like '%"+select +"%' or b.d_blocation like '%"+select +"%' or b.d_bname like '%"+select +"%' or b.d_bpublisher like '%"+select +"%' or b.d_bauthor like '%"+select +"%')"    		
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
public List<OnBookDto> getD_BSelectList(String select, int startRow, int endRow) throws Exception{

 List<OnBookDto> articleList = null;
 
 try {
    conn = getConnection();
    pstmt = conn.prepareStatement(   		   		 
"select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,r "+
"from (select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,rownum r "+
"from (select b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation, b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.D_icode, b.d_id, b.d_bdeliverycode, b.d_bdate "+
"from (select b.* from d_onBook b, (SELECT min(d_bcode) As d_bcode  FROM d_onBook b  GROUP BY d_bname) s "+
"where b.d_bcode = s.d_bcode) b , d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2  and" +
"(b.d_bgenre like '%"+select +"%' or b.d_bgenre2 like '%"+select +"%' or b.d_blocation like '%"+select +"%' or b.d_bname like '%"+select +"%' or b.d_bpublisher like '%"+select +"%' or b.d_bauthor like '%"+select +"%') " +
" )) where r >= "+startRow+" and r <= "+endRow        		    		
    		);
    rs = pstmt.executeQuery();
    
    if(rs.next()){
       articleList = new ArrayList<OnBookDto>();
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


//------select, �˻����� å�� ��ü�̸��� ã�� Dao --------------------------------------------------------------------------
public String findSelectToBookFullName(String select) throws Exception{

   String find = null;
   
   try {
      conn = getConnection();
      pstmt = conn.prepareStatement(                   
  "select d_bname from d_onBook where d_bname like '%"+select+"%'"                     
            );
      rs = pstmt.executeQuery();
      
      if(rs.next()){
         find = rs.getString("d_bname");
                        

      }
      
   }catch(Exception e){
      e.printStackTrace();
   }finally{
      if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
      if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
      if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
   }
   return find;
  } 
  
//---- å�� �������� ��ȯ ----- variable ������ Check ������ �޾� ������ �������� ���δٸ� ������� å������ �˻��մϴ�.
   public OnBookDto getOnBookArticle(int variable, String Check) throws Exception{
      OnBookDto article = null;
      String d_bnameCheck = null;
      try{
         conn = getConnection();
         //d_bno�� d_bname�� ã�� d_bname�� �ǸŰ����� å�� ������ ã��. (������ : )
         if(Check != null && Check == "none"){
             pstmt = conn.prepareStatement(
                     "select d_bname from d_onBook where d_bno = " + variable
                     );
               rs = pstmt.executeQuery();
               if(rs.next()){
                d_bnameCheck = rs.getString("d_bname");  
               }
               pstmt = conn.prepareStatement(
                     "select * from (select b.* from d_onBook b, d_onSellList s where b.d_bcode = s.d_bcode and b.d_bgrade != '���� �Ұ�' and s.d_sfinish = 2 and b.d_bcount = 1) where d_bname = '"+ d_bnameCheck +"'"
                     );
               rs = pstmt.executeQuery();
         //d_bno �� å�� ��� ������ ã���ϴ�.(�Ǹ����̰�, �ǸſϷ��, ������̰� �������)
         }else if(Check == "d_bno"){
             pstmt = conn.prepareStatement(
                     "select * from d_onBook where d_bno = " + variable
                     );
               rs = pstmt.executeQuery();
         }else if(Check == "d_bcode"){
             pstmt = conn.prepareStatement(
                     "select * from d_onBook where d_bcode = " + variable
                     );
               rs = pstmt.executeQuery();                
         //d_bcode�� �ǸŰ����� å������ �̽��ϴ�.
         }else if(Check == "d_bcode_oneBook"){
            pstmt = conn.prepareStatement(
                    "select * from d_onBook where d_bcode = " + variable + " and d_bgrade != '���ԺҰ�' and d_bcount = 1" 
                      );
               rs = pstmt.executeQuery();
          }else if(Check == "d_bno_oneBook"){
            pstmt = conn.prepareStatement(
                    "select * from d_onBook where d_bno = " + variable + " and d_bgrade != '���ԺҰ�' and d_bcount = 1" 
                          );       
                  rs = pstmt.executeQuery();
          }else{}

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
           article.setD_icode(rs.getInt("d_icode"));
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
    public List<OnBookDto> getD_bsellvalue(int d_bno) throws Exception{
        List<OnBookDto> sellList = null;
        
        try {
           conn = getConnection();
           pstmt = conn.prepareStatement(
"select d_bpurchasevalue from d_onBook where ="+ 
" (select d_bname from d_onBook where d_bno " + d_bno + ") order by asc"               
);           
           rs = pstmt.executeQuery();
           
           if(rs.next()){
        	   sellList = new ArrayList<OnBookDto>();
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

    //---- mySellingList ���� �Ǹ����� å ����Ʈ -------------------------------------------------------------------------------   
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
"where s.d_bcode = b.d_bcode and s.d_id = b.d_id and s.d_id = '"+d_id+"' and s.d_sfinish = 0) "+
"where r >= "+startRow+" and r <= "+endRow
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
    
//-------------�ֹ��Ҷ� å ����-----------�ֹ��Ҷ� å ����-----------�ֹ��Ҷ� å ����-----------�ֹ��Ҷ� å ����-----------�ֹ��Ҷ� å ����
    public OnBookDto User_onBuyBook(int d_bno, int d_bcode) throws Exception{

    	OnBookDto dto = null;
        try {
           conn = getConnection();
           
           if(d_bno != 0){
        	   pstmt = conn.prepareStatement("select * from d_onBook where d_bno=?");   
        	   pstmt.setInt(1, d_bno);
           }else{
        	   pstmt = conn.prepareStatement("select * from d_onBook where d_bcode=?");
        	   pstmt.setInt(1, d_bcode);
           }
           
           
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
        		dto.setD_bsellvalue(rs.getInt("d_bsellvalue"));
        		dto.setD_id(rs.getString("d_id"));                
        		dto.setD_bdeliverycode(rs.getInt("d_bdeliverycode"));
        		dto.setD_bdate(rs.getTimestamp("d_bdate"));
                 	
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

//-------------����� ����-----------����� ����-----------����� ����-----------����� ����----------����� ����-----------����� ����----------����� ����   
 public void User_onBuyBook_insert(DeliveryDto Ddto, LoginDto LogDto, AcDto acDto, int d_bcode, String d_id){
  		try{
  			conn = getConnection();
  			
  			//��� DB ���
  			pstmt = conn.prepareStatement("insert into d_bdelivery values(d_bdeliverycode_seq.NEXTVAL,?,?,?,?,?,sysdate)");

  			
  			pstmt.setInt(1, Ddto.getD_bcode());
  			pstmt.setInt(2,Ddto.getD_bdelibery());
  			pstmt.setString(3, Ddto.getD_bbuyer());
  			pstmt.setString(4, Ddto.getD_brecipient());
  			pstmt.setString(5, Ddto.getD_brequested());
  			pstmt.executeUpdate();
  			
  			// ȸ�� �ּ�, ��ȭ��ȣ ���� ���
  			pstmt = conn.prepareStatement("update d_member set d_addr = ?,d_phone=? where d_id= ?");
  			pstmt.setString(1, LogDto.getD_addr());
  			pstmt.setString(2, LogDto.getD_phone());
  			pstmt.setString(3, d_id);	
  			pstmt.executeUpdate();
  			
  			//å 1-> 0 , �Ǹ��� �� ���� å���� �ٲ�
  			pstmt = conn.prepareStatement("update d_onBook set d_bcount = 0 where d_bcode= ?");
  			pstmt.setInt(1, d_bcode);	
  			pstmt.executeUpdate();
  			
  			//�ŷ� ���� ���
  			pstmt = conn.prepareStatement("insert into d_log values(account_log.NEXTVAL,?,?,?,?,?,?,sysdate)");
			pstmt.setInt(1, acDto.getD_lsender());
			pstmt.setInt(2, acDto.getD_lreceiver());
			pstmt.setString(3, "d_d"+d_bcode);
			pstmt.setInt(4, acDto.getD_ldealmoney());
			pstmt.setInt(5, acDto.getD_ldealtype());
			pstmt.setInt(6, acDto.getD_ldealresult());
			
			pstmt.executeUpdate();
  			

  		}catch(Exception e){
  			e.printStackTrace();
  		}finally{
  			 if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
  			if(pstmt != null){ try{pstmt.close();}catch(SQLException s){}}
  			if(conn != null){ try{conn.close();}catch(SQLException s){}}
  		}
  		
  	}
 
//---------����� �ֹ�/�����ȸ count-------����� �ֹ�/�����ȸ count-------����� �ֹ�/�����ȸ count-------����� �ֹ�/�����ȸ count-------����� �ֹ�/�����ȸcount
 public int User_BuyBook_Count(String d_id) throws Exception{
   int x = 0;
   try{
      conn = getConnection();
      pstmt = conn.prepareStatement("select  count(*) from d_bdelivery  where d_bdelibery BETWEEN 0 AND 3 and d_bbuyer = ?");
      
      pstmt.setString(1, d_id);
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
 
//-----------����� ���� list-----------����� ���� list-----------����� ���� list ----------����� ���� list-----------
 public List User_BuyBookList(int start, int end, String id) throws Exception{

     List articleList = null;
     
     try {
        conn = getConnection();
        pstmt = conn.prepareStatement("SELECT d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher, d_bauthor, d_bgenre,d_bgenre2, d_blocation,"
          		+ "d_bregistdate,d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, d_icode, d_id, d_bdeliverycode, d_bdate,"
          		+ "d_bdelibery, d_bbuyer,d_brecipient,d_brequested ,d_bdeldate, r FROM"
          		+ "(SELECT d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher, d_bauthor, d_bgenre, d_bgenre2, d_blocation,"
          		+ "d_bregistdate, d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, d_icode, d_id, d_bdeliverycode, d_bdate,"
          		+ "d_bdelibery, d_bbuyer,d_brecipient,d_brequested ,d_bdeldate , rownum r FROM "
          		+ "(SELECT b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation,"
          		+ "b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.d_icode, b.d_id, b.d_bdeliverycode, b.d_bdate,"
          		+ "deli.d_bdelibery, deli.d_bbuyer, deli.d_brecipient, deli.d_brequested ,deli.d_bdeldate FROM "
          		+ "d_bdelivery deli, d_onBook b where  deli.d_bcode = b.d_bcode and deli.d_bdelibery BETWEEN 0 AND 3 and deli.d_bbuyer=?  )"
          		+ "order by d_bdeldate desc) where r >= ? and r <=?");

        
        pstmt.setString(1, id);
        pstmt.setInt(2, start);
        pstmt.setInt(3, end);
        
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

	           dto.setD_bdelibery(rs.getInt("d_bdelibery"));
	           dto.setD_bbuyer(rs.getString("d_bbuyer"));
	           dto.setD_brecipient(rs.getString("d_brecipient"));
	           dto.setD_brequested(rs.getString("d_brequested"));
	           dto.setD_bdeldate(rs.getTimestamp("d_bdeldate"));
	           
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
 
//-------------����� ���ų�������Ʈ ��������----------����� ���ų�������Ʈ ��������-----------����� ���ų�������Ʈ ��������-----------����� ���ų�������Ʈ ��������-----------����� ���ų�������Ʈ ��������
 public DeliveryDto User_onBuyBookList_detail(int d_bcode) throws Exception{

	 DeliveryDto dto = null;
     try {
        conn = getConnection();
        pstmt = conn.prepareStatement("select * from  d_bdelivery  where d_bcode=?");
        pstmt.setInt(1, d_bcode);
        rs = pstmt.executeQuery();
        
        
        if(rs.next()){
     	     
     	 do{
     		 
     		dto = new DeliveryDto();
     		dto.setD_bdeliverycode(rs.getInt("d_bdeliverycode"));
     		dto.setD_bcode(rs.getInt("d_bcode"));
     		dto.setD_bdelibery(rs.getInt("d_bdelibery"));
     		dto.setD_bbuyer(rs.getString("d_bbuyer"));
     		dto.setD_brecipient(rs.getString("d_brecipient"));
     		dto.setD_brequested(rs.getString("d_brequested"));
     		dto.setD_bdeldate(rs.getTimestamp("d_bdeldate"));
              	
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

//---------����� ��ҽ�û count-------����� ��ҽ�û count-------����� ��ҽ�û count-------����� ��ҽ�û count-------����� ��ҽ�û count
public int User_BuyBook_CancelFinish_Count(String d_id) throws Exception{
	 int x = 0;
	 try{
	    conn = getConnection();
	    pstmt = conn.prepareStatement("select  count(*) from d_bdelivery where d_bdelibery  BETWEEN 4 AND 5 and d_bbuyer = ?");
	    
	    pstmt.setString(1, d_id);
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

//-----------����� ��ҽ�û list-----------����� ��ҽ�û list-----------����� ��ҽ�û list ----------����� ��ҽ�û list-----------����� ��ҽ�û list-----------
public List User_BuyBook_CancelList(int start, int end, String id) throws Exception{

   List articleList = null;
   
   try {
      conn = getConnection();
      pstmt = conn.prepareStatement("SELECT d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher, d_bauthor, d_bgenre,d_bgenre2, d_blocation,"
        		+ "d_bregistdate,d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, d_icode, d_id, d_bdeliverycode, d_bdate,"
        		+ "d_bdelibery, d_bbuyer,d_brecipient,d_brequested ,d_bdeldate, r FROM"
        		+ "(SELECT d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher, d_bauthor, d_bgenre, d_bgenre2, d_blocation,"
        		+ "d_bregistdate, d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, d_icode, d_id, d_bdeliverycode, d_bdate,"
        		+ "d_bdelibery, d_bbuyer,d_brecipient,d_brequested ,d_bdeldate , rownum r FROM "
        		+ "(SELECT b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation,"
        		+ "b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.d_icode, b.d_id, b.d_bdeliverycode, b.d_bdate,"
        		+ "deli.d_bdelibery, deli.d_bbuyer, deli.d_brecipient, deli.d_brequested ,deli.d_bdeldate FROM "
        		+ "d_bdelivery deli, d_onBook b where  deli.d_bcode = b.d_bcode and deli.d_bdelibery BETWEEN 4 AND 5 and deli.d_bbuyer=?  )"
        		+ "order by d_bdeldate desc) where r >= ? and r <=?");

      
      pstmt.setString(1, id);
      pstmt.setInt(2, start);
      pstmt.setInt(3, end);
      
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

	           dto.setD_bdelibery(rs.getInt("d_bdelibery"));
	           dto.setD_bbuyer(rs.getString("d_bbuyer"));
	           dto.setD_brecipient(rs.getString("d_brecipient"));
	           dto.setD_brequested(rs.getString("d_brequested"));
	           dto.setD_bdeldate(rs.getTimestamp("d_bdeldate"));
	           
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
    
// ������      
    
//---------������ �Ǹ� ��û count-------������ å DB�� ���ڵ� �� count-------������ å DB�� ���ڵ� �� count-------������ å DB�� ���ڵ� �� count-------������ å DB�� ���ڵ� �� count 
    public int Admin_SellCount() throws Exception{
      int x = 0;
      try{
         conn = getConnection();
         pstmt = conn.prepareStatement("select count(*) from  d_onSellList  where d_sfinish=0 ");         
//         pstmt = conn.prepareStatement("select count(*) from d_onBook ");
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

//---------������ �˼� count (�ǸŽ�û �Ϸ�Ȱ͸�)-------������ �˼� count (�ǸŽ�û �Ϸ�Ȱ͸�)-------������ �˼� count (�ǸŽ�û �Ϸ�Ȱ͸�)-------������ �˼� count (�ǸŽ�û �Ϸ�Ȱ͸�)-------������ �˼� count (�ǸŽ�û �Ϸ�Ȱ͸�)
    public int Admin_InspectionCount() throws Exception{
      int x = 0;
      try{
         conn = getConnection();
         pstmt = conn.prepareStatement("select count(*) from  d_onSellList  where d_sfinish=1 ");
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

//---------������ å count (�ǸŽ�û �Ϸ�, �˼� ��ϵȰ͸�)-------������ å count (�ǸŽ�û �Ϸ�, �˼� ��ϵȰ͸�)-------������ å count (�ǸŽ�û �Ϸ�, �˼� ��ϵȰ͸�)-------������ å count (�ǸŽ�û �Ϸ�, �˼� ��ϵȰ͸�)-------������ å count (�ǸŽ�û �Ϸ�, �˼� ��ϵȰ͸�)
    public int Admin_OnBookCount() throws Exception{
      int x = 0;
      try{
         conn = getConnection();
         pstmt = conn.prepareStatement("select count(*) from  d_onSellList  where d_sfinish=2");
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
           pstmt = conn.prepareStatement("SELECT d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher, d_bauthor, d_bgenre,d_bgenre2, d_blocation,"
              		+ "d_bregistdate,d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, d_icode, d_id,d_bdeliverycode,d_bdate,"
               		+ "d_sno, d_sfinish, d_sdate , r FROM "
               		+ "(SELECT d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher, d_bauthor, d_bgenre, d_bgenre2, d_blocation,"
               		+ "d_bregistdate, d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, d_icode, d_id, d_bdeliverycode, d_bdate, "
               		+ "d_sno,d_bdeliverycode  d_sfinish, d_sdate , rownum r FROM "
               		+ "(SELECT b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation,"
               		+ "b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.d_icode, b.d_id, b.d_bdeliverycode, b.d_bdate, "
               		+ "s.d_sno, s.d_sfinish, s.d_sdate FROM "
               		+ "d_onSellList s, d_onBook b where s.d_sfinish=0  and s.d_bcode = b.d_bcode )order by d_sno asc) where r >= ? and r <=?");
           
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
 
//---------������ ���Ž�û å DB count-------������ ���Ž�û å DB count-------������ ���Ž�û å DB count-------������ ���Ž�û å DB count-------������ ���Ž�û å DB count
public int Admin_BuyBook_Count() throws Exception{
	 int x = 0;
	 try{
	    conn = getConnection();
	    pstmt = conn.prepareStatement("select  count(*) from d_bdelivery where d_bdelibery BETWEEN 0 AND 2");
	    
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

//-----------������ ���� list-----------������ ���� list-----------������ ���� list ----------������ ���� list-----------������ ���� list-----------
public List Admin_BuyBookList(int start, int end) throws Exception{

 List articleList = null;
 
 try {
    conn = getConnection();
    pstmt = conn.prepareStatement("SELECT d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher, d_bauthor, d_bgenre,d_bgenre2, d_blocation,"
    		+ "d_bregistdate,d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, d_icode, d_id, d_bdeliverycode, d_bdate,"
    		+ "d_bdelibery, d_bbuyer,d_brecipient,d_brequested ,d_bdeldate, r FROM"
    		+ "(SELECT d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher, d_bauthor, d_bgenre, d_bgenre2, d_blocation,"
    		+ "d_bregistdate, d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, d_icode, d_id, d_bdeliverycode, d_bdate,"
    		+ "d_bdelibery, d_bbuyer,d_brecipient,d_brequested ,d_bdeldate , rownum r FROM "
    		+ "(SELECT b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation,"
    		+ "b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.d_icode, b.d_id, b.d_bdeliverycode, b.d_bdate,"
    		+ "deli.d_bdelibery, deli.d_bbuyer, deli.d_brecipient, deli.d_brequested ,deli.d_bdeldate FROM "
    		+ "d_bdelivery deli, d_onBook b where  deli.d_bcode = b.d_bcode and deli.d_bdelibery BETWEEN 0 AND 2)"
    		+ "order by d_bno asc) where r >= ? and r <=?");

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
           
	           dto.setD_bdelibery(rs.getInt("d_bdelibery"));
	           dto.setD_bbuyer(rs.getString("d_bbuyer"));
	           dto.setD_brecipient(rs.getString("d_brecipient"));
	           dto.setD_brequested(rs.getString("d_brequested"));
	           dto.setD_bdeldate(rs.getTimestamp("d_bdeldate"));
	           
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

//---------������ ���ſϷ� å DB count-------������ ���ſϷ� å DB count-------������ ���ſϷ� å DB count-------������ ���ſϷ� å DB count-------������ ���ſϷ� å DB count
public int Admin_BuyBook_FinishList_Count() throws Exception{
	 int x = 0;
	 try{
	    conn = getConnection();
	    pstmt = conn.prepareStatement("select  count(*) from d_bdelivery where d_bdelibery = 3");
	    
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

//-----------������ ���ſϷ� list-----------������ ���ſϷ� list-----------������ ���ſϷ� list ----------������ ���ſϷ� list-----------������ ���ſϷ� list-----------
public List Admin_BuyBook_FinishList(int start, int end) throws Exception{

List articleList = null;

try {
  conn = getConnection();
  pstmt = conn.prepareStatement("SELECT d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher, d_bauthor, d_bgenre,d_bgenre2, d_blocation,"
  		+ "d_bregistdate,d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, d_icode, d_id, d_bdeliverycode, d_bdate,"
  		+ "d_bdelibery, d_bbuyer,d_brecipient,d_brequested ,d_bdeldate, r FROM"
  		+ "(SELECT d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher, d_bauthor, d_bgenre, d_bgenre2, d_blocation,"
  		+ "d_bregistdate, d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, d_icode, d_id, d_bdeliverycode, d_bdate,"
  		+ "d_bdelibery, d_bbuyer,d_brecipient,d_brequested ,d_bdeldate , rownum r FROM "
  		+ "(SELECT b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation,"
  		+ "b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.d_icode, b.d_id, b.d_bdeliverycode, b.d_bdate,"
  		+ "deli.d_bdelibery, deli.d_bbuyer, deli.d_brecipient, deli.d_brequested ,deli.d_bdeldate FROM "
  		+ "d_bdelivery deli, d_onBook b where  deli.d_bcode = b.d_bcode and deli.d_bdelibery =3)"
  		+ "order by d_bno desc) where r >= ? and r <=?");

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
         
	           dto.setD_bdelibery(rs.getInt("d_bdelibery"));
	           dto.setD_bbuyer(rs.getString("d_bbuyer"));
	           dto.setD_brecipient(rs.getString("d_brecipient"));
	           dto.setD_brequested(rs.getString("d_brequested"));
	           dto.setD_bdeldate(rs.getTimestamp("d_bdeldate"));
	           
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

//---------������ ��ҽ�û count-------������ ��ҽ�û count-------������ ��ҽ�û count-------������ ��ҽ�û count-------������ ��ҽ�û count
public int Admin_BuyBook_CancelList_Count() throws Exception{
	 int x = 0;
	 try{
	    conn = getConnection();
	    pstmt = conn.prepareStatement("select  count(*) from d_bdelivery where d_bdelibery = 4");
	    
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

//-----------������ ��ҽ�û list-----------������ ��ҽ�û list-----------������ ��ҽ�û list ----------������ ��ҽ�û list-----------������ ��ҽ�û list-----------
public List Admin_BuyBook_CancelList(int start, int end) throws Exception{

List articleList = null;

try {
conn = getConnection();
pstmt = conn.prepareStatement("SELECT d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher, d_bauthor, d_bgenre,d_bgenre2, d_blocation,"
		+ "d_bregistdate,d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, d_icode, d_id, d_bdeliverycode, d_bdate,"
		+ "d_bdelibery, d_bbuyer,d_brecipient,d_brequested ,d_bdeldate, r FROM"
		+ "(SELECT d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher, d_bauthor, d_bgenre, d_bgenre2, d_blocation,"
		+ "d_bregistdate, d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, d_icode, d_id, d_bdeliverycode, d_bdate,"
		+ "d_bdelibery, d_bbuyer,d_brecipient,d_brequested ,d_bdeldate , rownum r FROM "
		+ "(SELECT b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation,"
		+ "b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.d_icode, b.d_id, b.d_bdeliverycode, b.d_bdate,"
		+ "deli.d_bdelibery, deli.d_bbuyer, deli.d_brecipient, deli.d_brequested ,deli.d_bdeldate FROM "
		+ "d_bdelivery deli, d_onBook b where  deli.d_bcode = b.d_bcode and deli.d_bdelibery =4)"
		+ "order by d_bno asc) where r >= ? and r <=?");

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
       
	           dto.setD_bdelibery(rs.getInt("d_bdelibery"));
	           dto.setD_bbuyer(rs.getString("d_bbuyer"));
	           dto.setD_brecipient(rs.getString("d_brecipient"));
	           dto.setD_brequested(rs.getString("d_brequested"));
	           dto.setD_bdeldate(rs.getTimestamp("d_bdeldate"));
	           
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

//---------������ ��ҿϷ� count-------������ ��ҿϷ� count-------������ ��ҿϷ� count-------������ ��ҿϷ� count-------������ ��ҿϷ� count
public int Admin_BuyBook_CancelFinish_Count() throws Exception{
	 int x = 0;
	 try{
	    conn = getConnection();
	    pstmt = conn.prepareStatement("select  count(*) from d_bdelivery where d_bdelibery = 5");
	    
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

//-----------������ ��ҽ�û list-----------������ ��ҽ�û list-----------������ ��ҽ�û list ----------������ ��ҽ�û list-----------������ ��ҽ�û list-----------
public List Admin_BuyBook_CancelFinishList(int start, int end) throws Exception{

List articleList = null;

try {
conn = getConnection();
pstmt = conn.prepareStatement("SELECT d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher, d_bauthor, d_bgenre,d_bgenre2, d_blocation,"
		+ "d_bregistdate,d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, d_icode, d_id, d_bdeliverycode, d_bdate,"
		+ "d_bdelibery, d_bbuyer,d_brecipient,d_brequested ,d_bdeldate, r FROM"
		+ "(SELECT d_bno, d_bcode, d_bname, d_bgrade, d_bpublisher, d_bauthor, d_bgenre, d_bgenre2, d_blocation,"
		+ "d_bregistdate, d_bpic, d_bcount, d_bvalue, d_bsellvalue, d_bpurchasevalue, d_icode, d_id, d_bdeliverycode, d_bdate,"
		+ "d_bdelibery, d_bbuyer,d_brecipient,d_brequested ,d_bdeldate , rownum r FROM "
		+ "(SELECT b.d_bno, b.d_bcode, b.d_bname, b.d_bgrade, b.d_bpublisher, b.d_bauthor, b.d_bgenre, b.d_bgenre2, b.d_blocation,"
		+ "b.d_bregistdate, b.d_bpic, b.d_bcount, b.d_bvalue, b.d_bsellvalue, b.d_bpurchasevalue, b.d_icode, b.d_id, b.d_bdeliverycode, b.d_bdate,"
		+ "deli.d_bdelibery, deli.d_bbuyer, deli.d_brecipient, deli.d_brequested ,deli.d_bdeldate FROM "
		+ "d_bdelivery deli, d_onBook b where  deli.d_bcode = b.d_bcode and deli.d_bdelibery =5)"
		+ "order by d_bno asc) where r >= ? and r <=?");

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
     
	           dto.setD_bdelibery(rs.getInt("d_bdelibery"));
	           dto.setD_bbuyer(rs.getString("d_bbuyer"));
	           dto.setD_brecipient(rs.getString("d_brecipient"));
	           dto.setD_brequested(rs.getString("d_brequested"));
	           dto.setD_bdeldate(rs.getTimestamp("d_bdeldate"));
	           
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

//------------------�̸��� ���� �̸��� ã��Dao, ���� �� ������ List count------------------�̸��� ���� �̸��� ã��Dao, ���� �� ������ List count------------------�̸��� ���� �̸��� ã��Dao, ���� �� ������ List count------------------�̸��� ���� �̸��� ã��Dao, ���� �� ������ List count
public int getFindNameToNameCount(String d_bname) throws Exception{
    int x = 0;
    try{
       conn = getConnection();
       pstmt = conn.prepareStatement(
       		"select count(*) from (select * from d_onBook where d_bname like '%"+d_bname+"%' and d_bgrade != '���ԺҰ�' and d_bgrade != 'c' and d_bcount = 1)"
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
  

//-------------------�̸��� ���� �̸��� ã�� Dao, ���� ���������� List-------------------�̸��� ���� �̸��� ã�� Dao, ���� ���������� List-------------------�̸��� ���� �̸��� ã�� Dao, ���� ���������� List-------------------�̸��� ���� �̸��� ã�� Dao, ���� ���������� List
public List<OnBookDto> getFindNameToName(String d_bname, int startRow, int endRow) throws Exception{
	   List<OnBookDto> articleList = null;
	    try {
	       conn = getConnection();
	       pstmt = conn.prepareStatement(
   "select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,r "+
   "from (select d_bno,d_bcode,d_bname,d_bgrade,d_bpublisher,d_bauthor,d_bgenre,d_bgenre2,d_blocation,d_bregistdate,d_bpic,d_bcount,d_bvalue,d_bsellvalue,d_bpurchasevalue,D_icode,d_id,d_bdeliverycode,d_bdate,rownum r "+
   "from (select * from d_onBook where d_bname like '%"+d_bname+"%' and d_bgrade != '���ԺҰ�' and d_bgrade != 'c' and d_bcount = 1)) where  r >= "+startRow+" and r <= "+endRow
   );
	       rs = pstmt.executeQuery();
	       
	       if(rs.next()){
	          articleList = new ArrayList<OnBookDto>();
	          do{
	        	OnBookDto dto = new OnBookDto();
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


//--------d_bname���� �������� �˻��ϴ� dao(���Ż�������)--------d_bname���� �������� �˻��ϴ� dao(���Ż�������)--------d_bname���� �������� �˻��ϴ� dao(���Ż�������)
public int getFindNameToMinSellValue(String d_bname) throws Exception{
    int x = 0;
    try{
       conn = getConnection();
       pstmt = conn.prepareStatement(
       		"select min(d_bsellvalue) from d_onBook where d_bname like '%"+d_bname+"%' and d_bgrade != '���ԺҰ�'"
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


//--------d_bname���� �������� �˻��ϴ� ��޿� ���� �Ǹſ���ݾ� (å����������)--------d_bname���� �������� �˻��ϴ� ��޿� ���� �Ǹſ���ݾ� (å����������)--------d_bname���� �������� �˻��ϴ� ��޿� ���� �Ǹſ���ݾ� (å����������)
public int getFindNameToValue(String d_bname) throws Exception{
  int x = 0;
  try{
     conn = getConnection();
     pstmt = conn.prepareStatement(
     		"select d_bvalue from d_onBook where d_bname like '%"+d_bname+"%' and d_bgrade != '���ԺҰ�'"
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


//--------------- ȸ�� ��� ���� Dao ---------- å�ڵ�� ���̵� �ҷ�����, ���̵�� �Ǹ��� å count ��ȯ�ϴ� dao-------------------------------------------

//---- å�� ����ϴ� ������, ��ۿϷ�Ǵ� �������� �������� �˰����� �Ϸᰡ �Ǿ�����, ��, ����ڰ� �Ǹſ� ������ �ϷḦ �ϰԵɶ�(�ްԵɶ�) �����մϴ�.
//---- ȸ���� ����� �����ϰ�, �״�� ����, ����� �ø��� �Ǵ��ϴ� Dao�Դϴ�.
public String getUserSellPurchaseCountToGrade(int d_bcode, String d_id, String Check) throws Exception{
	  int sellCount = 0;
	  int purchaseCount = 0;
	  String d_idCheck = null;
	  int d_nom_grade = 0;
	  try{
	     conn = getConnection();
	     
	     if(Check == "d_bcode"){
   //---- d_onBook���� ȸ���� ��� �Ǹ��� å�ڵ�� ȸ���� id�� �˻���
	    	 pstmt = conn.prepareStatement(
	    			 "select d_id from d_onBook where d_bcode = " + d_bcode
	    			 );
	    	 rs = pstmt.executeQuery();
	    	 if(rs.next()){
	 //---- d_member���� ȸ���� �α��������� id�� �޾ƿ�.
	    		 d_idCheck = rs.getString(1);
	    	 }else{ }
	     }else{
	    	 d_idCheck = d_id;//���ǿ��� �޾ƿð�� admin�� ���̵��� �� ����.	
	     }
			     
   //---- ȸ���� id�� d_onBook���� ȸ���� �Ǹ��� å�� ���� �����ݴϴ�.
		     pstmt = conn.prepareStatement(
		    		 "select count(*) from d_onBook where d_id = '" + d_idCheck + "'"
		    		 );
		     rs = pstmt.executeQuery();
		     if(rs.next()){
		    	 sellCount = rs.getInt(1); //ī��Ʈ ù��° ���� ���� ����Ͽ� x�� ����
		     }else{}
   //---- ȸ���� id�� d_onSellList���� ������ ���� ���� �ݴϴ�.
	    	 pstmt = conn.prepareStatement(
	    			 "select count(*) from d_bdelivery where d_bbuyer = '" + d_idCheck +"'"
					);
	    	 rs = pstmt.executeQuery();
	    	 if(rs.next()){
	    		 purchaseCount = rs.getInt(1);
	    	 }else{}
	 //---- ȸ���� id�� d_member���� ȸ���� ����� �ҷ��ɴϴ�.
	    	 pstmt = conn.prepareStatement(
	    			 "select d_nom_grade from d_member where d_id = '" + d_idCheck + "'" 
					);
	    	 rs = pstmt.executeQuery();
	    	 if(rs.next()){
	    		 d_nom_grade = rs.getInt("d_nom_grade");
	    		 
	    	 }else{}
	     //������� ȸ���� d_id�� ã�Ұ�, d_id�� �Ǹ���å�� ��, ������ å�� ��, ����� ��ȯ�߽��ϴ�.

	     //ȸ���� 20�� �̻� �����ϰ�, 10�� �̻� �Ǹ��������� 50�� �̻� �����ϰ� 30���̻� �Ǹ������� �� ���� ȸ���� ����� �÷��ݴϴ�.
	     //ȸ������� �⺻�� 0, å�� ������ �ִ� å���� 1, å �� �д� å���� 2 �Դϴ�. 
	     //ȸ������ ������ �޼����� ������ ����� 01�� 12 �Դϴ�.
	     switch (d_nom_grade) {
		     case 0 :	   
			    	 if(purchaseCount >= 2 && sellCount >= 2){
				    	 pstmt = conn.prepareStatement(
				    			 "update d_member SET d_nom_grade = 11 where d_id = '" + d_idCheck + "'"
				    			 );
				    	 pstmt.executeUpdate();
				    	 Check = "01";
				     }
		    	 break;
		    	 
		     case 11 :	   
			    	 pstmt = conn.prepareStatement(
			    			 "update d_member SET d_nom_grade = 1 where d_id = '" + d_idCheck + "'"
			    			 );
			    	 pstmt.executeUpdate();
			    	 Check = "11";

			    	 break;

		     case 1 :
			    	 if(purchaseCount >= 4 && sellCount >= 4 ){
				    	 pstmt = conn.prepareStatement(
				    			 "update d_member SET d_nom_grade = 22 where  d_id = '" + d_idCheck + "'"
				    			 );
				    	 pstmt.executeUpdate();	    
				    	 Check = "12";
				     }
		    	 break;

		     case 22 :	   
			    	 pstmt = conn.prepareStatement(
			    			 "update d_member SET d_nom_grade = 2 where  d_id = '" + d_idCheck + "'"
			    			 );
			    	 pstmt.executeUpdate();
			    	 Check = "22";

			    	 break;		    	 
		    	 
		     case 2 :
		    	 	Check = "2";
		    	 break;		    	 
	     }
	  }catch(Exception e){
	     e.printStackTrace();
	  }finally{
	     if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
	     if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
	     if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
	  }
	  return Check ;
	}

//----------------- ȸ�� ��� ���� Dao ---------- ȸ���� ������ ���� ������ ID�� ��� ���� �ҷ��� --------------------------------------

public int getIdToGrade(String d_id) throws Exception{
	 int x = 0;
	 try{
	    conn = getConnection();
	    pstmt = conn.prepareStatement(
	    		"select d_nom_grade from d_member where d_id = '" + d_id + "'"
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


//------������ å�� �ǸŰ����� å�� �� ��ȯ---------------------------------------------------------------------------------------------------
public int getSellCanCount(String d_bname) throws Exception{
  int x = 0;
  try{
     conn = getConnection();
     pstmt = conn.prepareStatement(
     		"select count(*) from (select b.* from d_onBook b, d_onSellList s "+
             "where b.d_bcode = s.d_bcode) b, d_onSellList s where b.d_bcode = s.d_bcode and s.d_sfinish = 2 and b.d_bcount = 1 and b.d_bname = '"+d_bname+"'"
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

//---- ȸ�� ID�� ȸ�� NO�� ã�� Dao -------
public int findIdToNo(String d_id) throws Exception{
	 int x = 0;
	 try{
	    conn = getConnection();
	    pstmt = conn.prepareStatement(
	    		"select d_no from d_member where d_id = '" + d_id + "'"
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






//���� �޼ҵ� ��ȣ��   
}
    

