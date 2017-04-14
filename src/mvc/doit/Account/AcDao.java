package mvc.doit.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import mvc.doit.Online.OnBookDto;
import mvc.doit.Online.OnDao;
import mvc.doit.Rent.RentDao;


public class AcDao {

	private static AcDao instance = new AcDao();
    
    public static AcDao getInstance() {return instance; }
    
    private AcDao() {}
    
    private Connection getConnection() throws Exception {
      Context initCtx = new InitialContext();
      Context envCtx = (Context) initCtx.lookup("java:comp/env");
      DataSource ds = (DataSource)envCtx.lookup("jdbc/khdb");
      return ds.getConnection();
    }
	
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

	/*-------------------------------- 회원가입시 계좌 생성 -------------------------------------------*/
    public void insAccount(int d_no) throws Exception{
     String aco[] = new String[5];
     String acon = "";
     int d_noCheck = 0;
       try{
          conn = getConnection();
          
          pstmt = conn.prepareStatement(
        		  "select count(*) from d_account where d_no = " + d_no
        		  );
          rs = pstmt.executeQuery();
          if(rs.next()){
        	  if(rs.getInt(1) == 1){
        		  d_noCheck = rs.getInt(1);
        	  }
          }
          
          if(d_noCheck == 0){
	          String sql = "select * from d_member where d_no = " + d_no;
	          pstmt = conn.prepareStatement(sql);
	
	          rs = pstmt.executeQuery();
	          if(rs.next()){
	             //랜덤 코드 계좌 생성
	             RentDao rdao = RentDao.getInstance();
	
	             for(int i = 0; i < 3; i++){ //0.1.2.
	                if(i == 2){
	                   aco[i] = rdao.code_gen();
	                   acon += aco[i];
	                   break;
	                }
	                aco[i] = rdao.code_gen();
	                acon += aco[i]+"-";
	             }
	
	             sql = "insert into d_account values(account_seq.NEXTVAL,?, ?, sysdate) ";
	             pstmt = conn.prepareStatement(sql);
	             pstmt.setInt(1, rs.getInt("d_no")); //회원번호
	             pstmt.setString(2, acon);
	 
	             pstmt.executeUpdate();
	             
	          }
          }else{}
       }catch(Exception e){
          e.printStackTrace();
       }finally{
          if (rs != null) try { rs.close(); } catch(SQLException ex) {}
           if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
           if (conn != null) try { conn.close(); } catch(SQLException ex) {}
       }
    }
//    public String code_gen(){
//    	//랜덤 숫자 출력
//    	double br_double = Math.random();
//    	//최대 7자리 정수형 저장
//    	int br_int = (int)(br_double * 9999999 ) + 1;
//    	
//    	String br_code1 = Integer.toString(br_int);//완성된 랜덤 코드 저장
//    	
//    	return br_code1;
//    } 
//    
    
    
    
    /*-------------------------------- 회원가입시 계좌 생성 끝 -------------------------------------------*/	


    
    
/*-------------------------------- 계좌 불러오기 -------------------------------------------*/
    public AcDto getAccount(int d_no) throws Exception{

     AcDto adto = null;
        
       try{
         conn = getConnection();
         pstmt = conn.prepareStatement(
"select a.d_acno,a.d_no,a.d_acnum,a.d_cash,a.d_acregdate,l.d_lno,l.d_lsender,l.d_lreceiver,l.d_lbno,l.d_lbcode,l.d_ldivision,l.d_ldealtype,l.d_ldealresult,l.d_ldealmoney,l.d_ldate " +
" from d_account a, d_log l where a.d_no = l.d_lreceiver and  a.d_no = "+d_no+" order by d_ldate desc"
        		 );
         rs = pstmt.executeQuery();
         if(rs.next()){ //받는사람이 자신인 계좌와 로그 를 불러옴.
        	 adto = new AcDto();
        	 adto.setD_acno(rs.getInt("d_acno"));
        	 adto.setD_no(rs.getInt("d_no"));
        	 adto.setD_acnum(rs.getString("d_acnum"));
        	 adto.setD_acnum(rs.getString("d_cash"));
        	 adto.setD_acregdate(rs.getTimestamp("d_acregdate"));
        	 adto.setD_lno(rs.getInt("d_lno"));
        	 adto.setD_lsender(rs.getInt("d_lsender"));
        	 adto.setD_lreceiver(rs.getInt("d_lreceiver"));
        	 adto.setD_lbcode(rs.getString("d_lbno"));
        	 adto.setD_lbcode(rs.getString("d_lbcode"));
        	 adto.setD_ldealmoney(rs.getInt("d_ldivision"));
        	 adto.setD_ldealtype(rs.getInt("d_ldealtype"));
        	 adto.setD_ldealresult(rs.getInt("d_ldealresult"));
        	 adto.setD_ldealresult(rs.getInt("d_ldealmoney"));
        	 adto.setD_ldate(rs.getTimestamp("d_ldate"));
 
         }else{
             pstmt = conn.prepareStatement( // 자신의 계좌만 불러옴
            		 "select * from d_account where d_no = " + d_no 
            		 );
             rs = pstmt.executeQuery();
             if(rs.next()){
            	 adto = new AcDto();
            	 adto.setD_acno(rs.getInt("d_acno"));
            	 adto.setD_no(rs.getInt("d_no"));
            	 adto.setD_acnum(rs.getString("d_acnum"));
            	 adto.setD_acnum(rs.getString("d_cash"));
            	 adto.setD_acregdate(rs.getTimestamp("d_acregdate"));
            	 adto.setD_ldealmoney(0);
             }else{}
         }
                   
       }catch(Exception e){
          e.printStackTrace();
       }finally{
          if (rs != null) try { rs.close(); } catch(SQLException ex) {}
           if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
           if (conn != null) try { conn.close(); } catch(SQLException ex) {}
       }
       
       return adto;
    }

/*-------------------------------- 입금 출금하기 -------------------------------------------*/
    
    
    public void MyMoneyToAccout(int d_acMyMoney, int d_no, int d_acRequest) throws Exception{
        int userD_cash = 0;
          try{
            conn = getConnection();
           pstmt = conn.prepareStatement(
           		"select d_ldealmoney from d_log where d_lreceiver = "+ d_no
          		 );
           rs = pstmt.executeQuery();
           
           if(rs.next()){

        	   userD_cash = rs.getInt(1); //카운트 첫번째 행의 값을 출력하여 x에 대입

           }
            
            
            if(d_acRequest == 2){
            pstmt = conn.prepareStatement(
           		 "insert into d_log values(account_log.NEXTVAL, "+d_no+", "+d_no+", 0, 'd_aSelf', 2, 1, 1, "+d_acMyMoney+", sysdate)"                            
           		 );
            }else{
            	
            	pstmt = conn.prepareStatement(
            			"select * from d_account where d_no = "+d_no
            			);
            	rs = pstmt.executeQuery();
            	if(rs.next()){
            		userD_cash = rs.getInt(1); //회원읜 d_account의 총금액(잔액)
            	}
            	
            	
            	
            	if(d_acMyMoney > userD_cash){
	                pstmt = conn.prepareStatement(
	          		 "insert into d_log values(account_log.NEXTVAL, "+d_no+", "+d_no+", 0, 'd_aSelf', 2, 2, 1, -"+userD_cash+", sysdate)"                            
	          		 );
            	}else{
                    pstmt = conn.prepareStatement(
             		 "insert into d_log valuesaccount_log.NEXTVAL, "+d_no+", "+d_no+", 0, 'd_aSelf', 2, 2, 1, -"+d_acMyMoney+", sysdate)"                            
             		 );
            	}
            }
            
           pstmt.executeUpdate();

             
          }catch(Exception e){
             e.printStackTrace();
          }finally{
             if (rs != null) try { rs.close(); } catch(SQLException ex) {}
              if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
              if (conn != null) try { conn.close(); } catch(SQLException ex) {}
          }
   

       }  
    

//-------사용자의 잔액 불러오기 ------------------------------------------------
    public int getAccountSumMoney(int d_no) throws Exception{
      int d_lsummoney = 0;
      try{
         conn = getConnection();
         pstmt = conn.prepareStatement(
         		"select d_ldealmoney from d_log where d_lreceiver = "+ d_no
        		 );
         rs = pstmt.executeQuery();
         
         if(rs.next()){
        	 do{
        		 d_lsummoney += rs.getInt(1); //카운트 첫번째 행의 값을 출력하여 x에 대입
        	 }while(rs.next());
         }
         

      }catch(Exception e){
         e.printStackTrace();
      }finally{
         if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
         if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
         if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
      }
      return d_lsummoney;
   }
    
//----- 사용자의 거래 현황 count -----------------------------------------------
    public int dealSituationCount(int d_no) throws Exception{
        int x = 0;
        try{
           conn = getConnection();
           pstmt = conn.prepareStatement(
           		"select count(*) from d_log where d_lsender = "+ d_no
          		 );
           rs = pstmt.executeQuery();
           
           if(rs.next()){
          		 x = rs.getInt(1); //카운트 첫번째 행의 값을 출력하여 x에 대입
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
  
//----- 사용자의 거래 현황 List----------------------------------------------------------
    public List<AcDto> dealSituation(int d_no, int startRow, int endRow) throws Exception{
    	List<AcDto> AccountList = null;
        AcDto adto = null;
        int listD_lsummoney = 0;
          try{
            conn = getConnection();
            pstmt = conn.prepareStatement(
"select d_acno, d_no, d_acnum, d_cash, d_acregdate, d_lno, d_lsender, d_lreceiver, d_lbno,d_lbcode, d_ldealmoney, d_ldivision, d_ldealtype, d_ldealresult, to_char(d_ldate, 'yyyy-mm-dd HH:mm') AS d_ldateS, r " + 
"from (select d_acno, d_no, d_acnum, d_cash, d_acregdate, d_lno, d_lsender, d_lreceiver, d_lbno, d_lbcode, d_ldealmoney, d_ldivision, d_ldealtype, d_ldealresult, d_ldate, rownum r " + 
"from (select a.d_acno, a.d_no, a.d_acnum, a.d_cash, a.d_acregdate, l.d_lno, l.d_lsender, l.d_lreceiver, l.d_lbno, l.d_lbcode, l.d_ldealmoney, l.d_ldivision, l.d_ldealtype, l.d_ldealresult, l.d_ldate " +
"from d_log l, d_account a  where a.d_no = l.d_lreceiver and l.d_lreceiver = " + d_no + " order by d_ldate asc)) where r >= " + startRow + " and r <= " + endRow
           		 );
            rs = pstmt.executeQuery();
            
            if(rs.next()){
            	AccountList = new ArrayList<AcDto>();
            	do{
                  	 adto = new AcDto();
                   	 adto.setD_acno(rs.getInt("d_acno"));
                   	 adto.setD_no(rs.getInt("d_no"));
                   	 adto.setD_acnum(rs.getString("d_acnum"));
                   	 adto.setD_cash(rs.getInt("d_cash"));
                   	 adto.setD_acregdate(rs.getTimestamp("d_acregdate"));
                   	 adto.setD_lno(rs.getInt("d_lno"));
                   	 adto.setD_lsender(rs.getInt("d_lsender"));
                   	 adto.setD_lreceiver(rs.getInt("d_lreceiver"));
                   	 adto.setD_lbno(rs.getInt("d_lbno"));
                   	 adto.setD_lbcode(rs.getString("d_lbcode"));
                   	 adto.setD_ldivision(rs.getInt("d_ldivision"));
                   	 adto.setD_ldealmoney(rs.getInt("d_ldealmoney"));
                   	 adto.setD_ldealtype(rs.getInt("d_ldealtype"));
                   	 adto.setD_ldealresult(rs.getInt("d_ldealresult"));
                   	 adto.setD_ldateS(rs.getString("d_ldateS"));
                   	 adto.setListD_lsummoney(listD_lsummoney += rs.getInt("d_ldealmoney"));

                   	 AccountList.add(adto);
            	}while(rs.next());
            }
          }catch(Exception e){
             e.printStackTrace();
          }finally{
             if (rs != null) try { rs.close(); } catch(SQLException ex) {}
              if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
              if (conn != null) try { conn.close(); } catch(SQLException ex) {}
          }
          
          return AccountList;
       }
    

//---- 배송완료와 책등록 시 사용자에게 돈을 지불해야합니다. 그때 발쌩하는 Dao ---------------    
public void insertAccountLog(AcDto acDto) throws Exception{
      try{
    	  conn = getConnection();
			//회원이 관리자에게 돈 지불
			pstmt = conn.prepareStatement(
"insert into d_log values(account_log.NEXTVAL,?,?,?,?,?,?,?, "+acDto.getD_ldealmoney()+",sysdate)");
			pstmt.setInt(1, acDto.getD_lsender()); //261관리자
			pstmt.setInt(2, acDto.getD_lreceiver()); //회원(책판매자)
			pstmt.setInt(3, acDto.getD_lbno()); //0
			pstmt.setString(4, acDto.getD_lbcode()); //책코드
			pstmt.setInt(5, 1); //d_onBook
			pstmt.setInt(6, 3);	//송금이체							
			pstmt.setInt(7, 1); //거래완료
			//관리자에게 회원이 책을 사기때문에 회원->관리자 돈지불

			pstmt.executeUpdate();
		

			//회원이 관리자아게 돈 지불하여 회원자신의돈 차감
			pstmt = conn.prepareStatement(
"insert into d_log values(account_log.NEXTVAL,?,?,?,?,?,?,?, -"+acDto.getD_ldealmoney()+",sysdate)");
			pstmt.setInt(1, acDto.getD_lsender());
			pstmt.setInt(2, acDto.getD_lsender());
			pstmt.setInt(3, acDto.getD_lbno());
			pstmt.setString(4, acDto.getD_lbcode());
			pstmt.setInt(5, 1);
			pstmt.setInt(6, 4);								
			pstmt.setInt(7, 1);
			//관리자에게 회원이 책을 사기때문에 회원->관리자 돈지불

			pstmt.executeUpdate();        
      }catch(Exception e){
         e.printStackTrace();
      }finally{
         if (rs != null) try { rs.close(); } catch(SQLException ex) {}
          if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
          if (conn != null) try { conn.close(); } catch(SQLException ex) {}
      }
   } 

//---- 회원이 판매하는 책에 대한 관리자의 결제 진행 Dao ---------------    
public void updateAccountLog(int d_lno) throws Exception{
    try{
  	 
  	 //회원의 책판매 : d_s, 회원의 책구매 : d_p
      conn = getConnection();
      
      pstmt = conn.prepareStatement(
"update d_log set d_ldealresult = 1 where d_lno = "+d_lno
 		 );
      pstmt.executeUpdate();
      
    }catch(Exception e){
       e.printStackTrace();
    }finally{
       if (rs != null) try { rs.close(); } catch(SQLException ex) {}
        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
    }
 } 

/*---- 회원이 판매하는 책에 대한 결제의 전체 count -------------------------------------------*/
public int getD_sPayListCount() throws Exception{
	int x = 0;

    try{
     conn = getConnection();
     pstmt = conn.prepareStatement(
    		 "select count(*) from d_account a, d_log l where a.d_no = l.d_lsender and d_lbcode like 'd_b%'"
    		 );
     rs = pstmt.executeQuery();
     if(rs.next()){
    	 x = rs.getInt(1);
     }
               
   }catch(Exception e){
      e.printStackTrace();
   }finally{
      if (rs != null) try { rs.close(); } catch(SQLException ex) {}
       if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
       if (conn != null) try { conn.close(); } catch(SQLException ex) {}
   }
   
   return x;
}

//----- 회원의 책 판매 에 관한 결제의 전체  List ----------------------------------------------------------
public List<AcDto> getD_sPayList(int startRow, int endRow) throws Exception{
	List<AcDto> AccountList = null;
    AcDto adto = null;
      try{
        conn = getConnection();
        pstmt = conn.prepareStatement(
"select d_acno, d_no, d_acnum, d_acregdate, d_lno, d_lsender, d_lreceiver,d_lbcode, d_ldealmoney, d_ldealtype, d_ldealresult, to_char(d_ldate, 'yyyy-mm-dd HH:MM') AS d_ldateS, r " + 
"from (select d_acno, d_no, d_acnum, d_acregdate, d_lno, d_lsender, d_lreceiver, d_lbcode, d_ldealmoney, d_ldealtype, d_ldealresult, d_ldate, rownum r " + 
"from (select a.d_acno, a.d_no, a.d_acnum, a.d_acregdate, l.d_lno, l.d_lsender, l.d_lreceiver, l.d_lbcode, l.d_ldealmoney, l.d_ldealtype, l.d_ldealresult, l.d_ldate " +
"from d_log l, d_account a where a.d_no = l.d_lreceiver and d_lbcode like 'd_b%'   order by d_ldate asc)) where r >= " + startRow + " and r <= " + endRow
       		 );
        rs = pstmt.executeQuery();
        
        if(rs.next()){
        	AccountList = new ArrayList<AcDto>();
        	do{
              	 adto = new AcDto();
               	 adto.setD_acno(rs.getInt("d_acno"));
               	 adto.setD_no(rs.getInt("d_no"));
               	 adto.setD_acnum(rs.getString("d_acnum"));
               	 adto.setD_acregdate(rs.getTimestamp("d_acregdate"));
               	 adto.setD_lno(rs.getInt("d_lno"));
               	 adto.setD_lsender(rs.getInt("d_lsender"));
               	 adto.setD_lreceiver(rs.getInt("d_lreceiver"));
               	 adto.setD_lbcode(rs.getString("d_lbcode"));
               	 adto.setD_ldealmoney(rs.getInt("d_ldealmoney"));
               	 adto.setD_ldealtype(rs.getInt("d_ldealtype"));
               	 adto.setD_ldealresult(rs.getInt("d_ldealresult"));
               	 adto.setD_ldateS(rs.getString("d_ldateS"));
               	 AccountList.add(adto);
        	}while(rs.next());
        }
      }catch(Exception e){
         e.printStackTrace();
      }finally{
         if (rs != null) try { rs.close(); } catch(SQLException ex) {}
          if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
          if (conn != null) try { conn.close(); } catch(SQLException ex) {}
      }
      return AccountList;
   }

/*---- 회원이 판매하는 책에 대한 결제 전 count -------------------------------------------*/
public int getD_sNoPayListCount() throws Exception{
	int x = 0;

    try{
     conn = getConnection();
     pstmt = conn.prepareStatement(
    		 "select count(*) from d_account a, d_log l where a.d_no = l.d_lsender and d_lbcode like 'd_b%' and d_ldealresult = 0"
    		 );
     rs = pstmt.executeQuery();
     if(rs.next()){
    	 x = rs.getInt(1);
     }
               
   }catch(Exception e){
      e.printStackTrace();
   }finally{
      if (rs != null) try { rs.close(); } catch(SQLException ex) {}
       if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
       if (conn != null) try { conn.close(); } catch(SQLException ex) {}
   }
   
   return x;
}

//----- 회원의 책 판매 에 관한 결제 전 List ----------------------------------------------------------
public List<AcDto> getD_sNoPayList(int startRow, int endRow) throws Exception{
	List<AcDto> AccountList = null;
    AcDto adto = null;
      try{
        conn = getConnection();
        pstmt = conn.prepareStatement(
"select d_acno, d_no, d_acnum, d_acregdate, d_lno, d_lsender, d_lreceiver,d_lbcode, d_ldealmoney, d_ldealtype, d_ldealresult, to_char(d_ldate, 'yyyy-mm-dd HH:mm') AS d_ldateS, r " + 
"from (select d_acno, d_no, d_acnum, d_acregdate, d_lno, d_lsender, d_lreceiver, d_lbcode, d_ldealmoney, d_ldealtype, d_ldealresult, d_ldate, rownum r " + 
"from (select a.d_acno, a.d_no, a.d_acnum, a.d_acregdate, l.d_lno, l.d_lsender, l.d_lreceiver, l.d_lbcode, l.d_ldealmoney, l.d_ldealtype, l.d_ldealresult, l.d_ldate " +
"from d_log l, d_account a where a.d_no = l.d_lsender and d_lbcode like 'd_b%' and d_ldealresult = 0  order by d_ldate asc)) where r >= " + startRow + " and r <= " + endRow
       		 );
        rs = pstmt.executeQuery();
        
        if(rs.next()){
        	AccountList = new ArrayList<AcDto>();
        	do{
              	 adto = new AcDto();
               	 adto.setD_acno(rs.getInt("d_acno"));
               	 adto.setD_no(rs.getInt("d_no"));
               	 adto.setD_acnum(rs.getString("d_acnum"));
               	 adto.setD_acregdate(rs.getTimestamp("d_acregdate"));
               	 adto.setD_lno(rs.getInt("d_lno"));
               	 adto.setD_lsender(rs.getInt("d_lsender"));
               	 adto.setD_lreceiver(rs.getInt("d_lreceiver"));
               	 adto.setD_lbcode(rs.getString("d_lbcode"));
               	 adto.setD_ldealmoney(rs.getInt("d_ldealmoney"));
               	 adto.setD_ldealtype(rs.getInt("d_ldealtype"));
               	 adto.setD_ldealresult(rs.getInt("d_ldealresult"));
               	 adto.setD_ldateS(rs.getString("d_ldateS"));
               	 AccountList.add(adto);
        	}while(rs.next());
        }
      }catch(Exception e){
         e.printStackTrace();
      }finally{
         if (rs != null) try { rs.close(); } catch(SQLException ex) {}
          if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
          if (conn != null) try { conn.close(); } catch(SQLException ex) {}
      }
      return AccountList;
   }

/*---- 회원이 판매하는 책에 대한 결제완료 count -------------------------------------------*/
public int getD_sPayEndListCount() throws Exception{
	int x = 0;

    try{
     conn = getConnection();
     pstmt = conn.prepareStatement(
    		 "select count(*) from d_account a, d_log l where a.d_no = l.d_lsender and d_lbcode like 'd_b%' and d_ldealresult = 1"
    		 );
     rs = pstmt.executeQuery();
     if(rs.next()){
    	 x = rs.getInt(1);
     }
               
   }catch(Exception e){
      e.printStackTrace();
   }finally{
      if (rs != null) try { rs.close(); } catch(SQLException ex) {}
       if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
       if (conn != null) try { conn.close(); } catch(SQLException ex) {}
   }
   
   return x;
}

//----- 회원 책 판매에 관한 결제완료  List ----------------------------------------------------------
public List<AcDto> getD_sPayEndList(int startRow, int endRow) throws Exception{
	List<AcDto> AccountList = null;
    AcDto adto = null;
      try{
        conn = getConnection();
        pstmt = conn.prepareStatement(
"select d_acno, d_no, d_acnum, d_acregdate, d_lno, d_lsender, d_lreceiver,d_lbcode, d_ldealmoney, d_ldealtype, d_ldealresult, to_char(d_ldate, 'yyyy-mm-dd HH:mm') AS d_ldateS, r " + 
"from (select d_acno, d_no, d_acnum, d_acregdate, d_lno, d_lsender, d_lreceiver, d_lbcode, d_ldealmoney, d_ldealtype, d_ldealresult, d_ldate, rownum r " + 
"from (select a.d_acno, a.d_no, a.d_acnum, a.d_acregdate, l.d_lno, l.d_lsender, l.d_lreceiver, l.d_lbcode, l.d_ldealmoney, l.d_ldealtype, l.d_ldealresult, l.d_ldate " +
"from d_log l, d_account a where a.d_no = l.d_lsender and d_lbcode like 'd_b%' and d_ldealresult = 1 order by d_ldate asc)) where r >= " + startRow + " and r <= " + endRow
       		 );
        rs = pstmt.executeQuery();
        
        if(rs.next()){
        	AccountList = new ArrayList<AcDto>();
        	do{
              	 adto = new AcDto();
               	 adto.setD_acno(rs.getInt("d_acno"));
               	 adto.setD_no(rs.getInt("d_no"));
               	 adto.setD_acnum(rs.getString("d_acnum"));
               	 adto.setD_acregdate(rs.getTimestamp("d_acregdate"));
               	 adto.setD_lno(rs.getInt("d_lno"));
               	 adto.setD_lsender(rs.getInt("d_lsender"));
               	 adto.setD_lreceiver(rs.getInt("d_lreceiver"));
               	 adto.setD_lbcode(rs.getString("d_lbcode"));
               	 adto.setD_ldealmoney(rs.getInt("d_ldealmoney"));
               	 adto.setD_ldealtype(rs.getInt("d_ldealtype"));
               	 adto.setD_ldealresult(rs.getInt("d_ldealresult"));
               	 adto.setD_ldateS(rs.getString("d_ldateS"));
               	 AccountList.add(adto);
        	}while(rs.next());
        }
      }catch(Exception e){
         e.printStackTrace();
      }finally{
         if (rs != null) try { rs.close(); } catch(SQLException ex) {}
          if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
          if (conn != null) try { conn.close(); } catch(SQLException ex) {}
      }
      return AccountList;
   }


//---- 회원의 책 1권 구매시(X장바구니) 결제하는 상황 ---------------    
public void D_onBookValueUserToAdmin(AcDto acDto) throws Exception{
    try{
    	conn = getConnection();
			//회원이 관리자에게 돈 지불
			pstmt = conn.prepareStatement(
"insert into d_log values(account_log.NEXTVAL,?,?,?,?,?,?,?, "+acDto.getD_ldealmoney()+",sysdate)");
			pstmt.setInt(1, acDto.getD_lsender()); //261관리자
			pstmt.setInt(2, acDto.getD_lreceiver()); //회원(책판매자)
			pstmt.setInt(3, acDto.getD_lbno()); //0
			pstmt.setString(4, acDto.getD_lbcode()); //책코드
			pstmt.setInt(5, 1); //d_onBook
			pstmt.setInt(6, 3);	//송금이체							
			pstmt.setInt(7, 1); //거래완료
			//관리자에게 회원이 책을 사기때문에 회원->관리자 돈지불

			pstmt.executeUpdate();
		

			//회원이 관리자아게 돈 지불하여 회원자신의돈 차감
			pstmt = conn.prepareStatement(
"insert into d_log values(account_log.NEXTVAL,?,?,?,?,?,?,?, -"+acDto.getD_ldealmoney()+",sysdate)");
			pstmt.setInt(1, acDto.getD_lsender());
			pstmt.setInt(2, acDto.getD_lsender());
			pstmt.setInt(3, acDto.getD_lbno());
			pstmt.setString(4, acDto.getD_lbcode());
			pstmt.setInt(5, 1);
			pstmt.setInt(6, 4);								
			pstmt.setInt(7, 1);
			//관리자에게 회원이 책을 사기때문에 회원->관리자 돈지불

			pstmt.executeUpdate();        
    }catch(Exception e){
       e.printStackTrace();
    }finally{
       if (rs != null) try { rs.close(); } catch(SQLException ex) {}
        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
    }
 } 


    
}
