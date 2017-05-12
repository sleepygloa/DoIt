package mvc.doit.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import mvc.doit.Cart.CartDao;
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

	/*-------------------------------- ȸ�����Խ� ���� ���� -------------------------------------------*/
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
	             //���� �ڵ� ���� ����
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
	             pstmt.setInt(1, rs.getInt("d_no")); //ȸ����ȣ
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
//    	//���� ���� ���
//    	double br_double = Math.random();
//    	//�ִ� 7�ڸ� ������ ����
//    	int br_int = (int)(br_double * 9999999 ) + 1;
//    	
//    	String br_code1 = Integer.toString(br_int);//�ϼ��� ���� �ڵ� ����
//    	
//    	return br_code1;
//    } 
//    
    
    
    
    /*-------------------------------- ȸ�����Խ� ���� ���� �� -------------------------------------------*/	


    
    
/*-------------------------------- ���� �ҷ����� -------------------------------------------*/
    public AcDto getAccount(int d_no) throws Exception{

     AcDto adto = null;
        
       try{
         conn = getConnection();
         pstmt = conn.prepareStatement(
        		 "select * from d_account where d_no = ?"
/*"select a.d_acno,a.d_no,a.d_acnum,a.d_cash,a.d_acregdate,l.d_lno,l.d_lsender,l.d_lreceiver,l.d_lbno,l.d_lbcode,l.d_ldivision,l.d_ldealtype,l.d_ldealresult,l.d_ldealmoney,l.d_ldate " +
" from d_account a, d_log l where a.d_no = l.d_lreceiver and  a.d_no = "+d_no+" order by d_ldate desc"*/
        		 );
         pstmt.setInt(1, d_no);
         rs = pstmt.executeQuery();
         
         if(rs.next()){ //�޴»���� �ڽ��� ���¿� �α� �� �ҷ���.
        	 adto = new AcDto();
        	 /*
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
          */
            /*pstmt = conn.prepareStatement( // �ڽ��� ���¸� �ҷ���
            		 "select * from d_account where d_no = " + d_no 
            		 );
             rs = pstmt.executeQuery();
             if(rs.next()){
             */
            	 adto = new AcDto();
            	 adto.setD_acno(rs.getInt("d_acno"));
            	 //adto.setD_no(rs.getInt("d_no"));
            	 adto.setD_acnum(rs.getString("d_acnum")); //���¹�ȣ
            	 adto.setD_cash(rs.getInt("d_cash"));
            	 adto.setD_acregdate(rs.getTimestamp("d_acregdate"));
            	 //adto.setD_ldealmoney(0);
             //}else{}
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

/*-------------------------------- �Ա� / ����ϱ� -------------------------------------------*/
    public void MyMoneyToAccout(int d_acMyMoney, int d_no, int d_acRequest) throws Exception{ //��û�ݾ�, ȸ����ȣ, ����(�Ա��̳� ����̳�)
        int userD_cash = 0;
        AcDto adto = getAccount(d_no);
          try{
            conn = getConnection();
           pstmt = conn.prepareStatement(
           		"select d_ldealmoney from d_log where d_lreceiver = "+ d_no
          		 );
           rs = pstmt.executeQuery();
           
           if(rs.next()){
        	   userD_cash = rs.getInt(1); //ī��Ʈ ù��° ���� ���� ����Ͽ� x�� ���� 
           }
            
            
            if(d_acRequest == 2){ //�Ա�
            	//upTMon(adto.getD_cash()+d_acMyMoney,d_no);//�ѱݾ� ������Ʈ
            	pstmt = conn.prepareStatement(
           		 "insert into d_log values(account_log.NEXTVAL, "+d_no+", "+d_no+", 0, 'd_aSelf', 2, 1, 1, "+d_acMyMoney+","+(adto.getD_cash()+d_acMyMoney)+" , sysdate)"                            
           		 );
            }else{ //���
            	
            	pstmt = conn.prepareStatement(
            			"select * from d_account where d_no = "+d_no
            			);
            	rs = pstmt.executeQuery();
            	if(rs.next()){
            		userD_cash = rs.getInt(1); //ȸ���� d_account�� �ѱݾ�(�ܾ�)
            	}
            	
            	//�α� �Է�
            	if(d_acMyMoney > userD_cash){ //�ִ¸�ŭ ���
            		//upTMon(adto.getD_cash()-userD_cash,d_no);//�ѱݾ� ������Ʈ
	                pstmt = conn.prepareStatement(
	          		 "insert into d_log values(account_log.NEXTVAL, "+d_no+", "+d_no+", 0, 'd_aSelf', 2, 2, 1, -"+userD_cash+","+(adto.getD_cash()-userD_cash)+", sysdate)"                            
	          		 );
            	}else{ //���
            		//upTMon(adto.getD_cash()-d_acMyMoney,d_no);//�ѱݾ� ������Ʈ
                    pstmt = conn.prepareStatement(
             		 "insert into d_log values(account_log.NEXTVAL, "+d_no+", "+d_no+", 0, 'd_aSelf', 2, 2, 1, -"+d_acMyMoney+","+(adto.getD_cash()-d_acMyMoney)+", sysdate)"
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
    
//--------------------------------------------------�Ѿ� ������Ʈ ---------------------------------------//
    public void upTMon(int money, int d_no) throws Exception{ //�Է��� ��, ȸ����ȣ
    		int money2= 0;
        	try{
        		conn = getConnection();
        		String sql = "select d_cash from d_account where d_no = ?";
        		pstmt = conn.prepareStatement(sql);
        		pstmt.setInt(1, d_no);
        		
        		rs = pstmt.executeQuery();
        		
        		if(rs.next()){
        			money2 = rs.getInt("d_cash")+money;//�հ�
		        	sql = "update d_account set d_cash=? where d_no = ?";
		        	pstmt = conn.prepareStatement(sql);
		        	if(0 < money2){
		        		pstmt.setInt(1, money2);
        			}else{
        				pstmt.setInt(1, 0);
        			}
		        	pstmt.setInt(2, d_no);
	        		
	        		pstmt.executeUpdate();
        		}
        	}catch(Exception e){
        		e.printStackTrace();
        	}finally{
        		if( rs != null){ try{ rs.close(); }catch(SQLException se){} };
                if( pstmt != null){ try{ pstmt.close(); }catch(SQLException se){} };
                if( conn != null){ try{ conn.close(); }catch(SQLException se){} };
        	}
        	
    }
    
//-------������� �ܾ� �ҷ����� ------------------------------------------------
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
        		 d_lsummoney += rs.getInt(1); //ī��Ʈ ù��° ���� ���� ����Ͽ� x�� ����
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
    
//----- ������� �ŷ� ��Ȳ count -----------------------------------------------
    public int dealSituationCount(int d_no) throws Exception{
        int x = 0;
        try{
           conn = getConnection();
           pstmt = conn.prepareStatement(
           		"select count(*) from d_log where d_lsender = "+ d_no
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
  
  //----- ������� �ŷ� ��Ȳ List - log ----------------------------------------------------------
    public List<AcDto> dealSituation(int d_no, int startRow, int endRow) throws Exception{
       List<AcDto> AccountList = null;
        AcDto adto = null;
        int listD_lsummoney = 0;
          try{
            conn = getConnection();
            pstmt = conn.prepareStatement(
"select d_cash from d_account where d_no =" + d_no
                  );
            rs = pstmt.executeQuery();
            if(rs.next()){
               listD_lsummoney = rs.getInt(1);
            }
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
  
    
//----- ������� �ŷ� ��Ȳ2 List - log ----------------------------------------------------------  
public List<AcDto> getLog(int d_no, String column, int trade, int inout ,int startRow, int endRow) throws Exception{ //ȸ����ȣ, ������ �÷�����, �ŷ� ����
	List<AcDto> logList= null;
	String sql = "";
	try{
		conn = getConnection();
		
		if(trade != 3){ //�κ����
			sql = "select d_lno, d_lsender, d_lreceiver, d_lbno,d_lbcode, d_ldealmoney, d_ltomoney, d_ldivision, d_ldealtype, d_ldealresult, to_char(d_ldate, 'yyyy-mm-dd HH:mm') AS d_ldateS, r ";
			sql += " from (select d_lno, d_lsender, d_lreceiver, d_lbno, d_lbcode, d_ldealmoney, d_ldivision, d_ldealtype, d_ldealresult, d_ltomoney, d_ldate, rownum r ";
			sql += " from (select l.d_lno, l.d_lsender, l.d_lreceiver, l.d_lbno, l.d_lbcode, l.d_ldealmoney, l.d_ldivision, l.d_ldealtype, l.d_ldealresult, l.d_ltomoney, l.d_ldate  ";
			sql += " from d_log l, d_account a  order by d_ldate asc)) where r >=  "+startRow+"  and r <=  200 and "+column+"="+d_no+" and d_ldivision = "+trade+"order by d_ldateS";
		}else if(inout == 1){ //�Ա���ݳ���
			sql = "select d_lno, d_lsender, d_lreceiver, d_lbno,d_lbcode, d_ldealmoney, d_ltomoney, d_ldivision, d_ldealtype, d_ldealresult, to_char(d_ldate, 'yyyy-mm-dd HH:mm') AS d_ldateS, r ";
			sql += " from (select d_lno, d_lsender, d_lreceiver, d_lbno, d_lbcode, d_ldealmoney, d_ldivision, d_ldealtype, d_ldealresult, d_ltomoney, d_ldate, rownum r ";
			sql += " from (select l.d_lno, l.d_lsender, l.d_lreceiver, l.d_lbno, l.d_lbcode, l.d_ldealmoney, l.d_ldivision, l.d_ldealtype, l.d_ldealresult, l.d_ltomoney, l.d_ldate  ";
			sql += " from d_log l, d_account a  where l.d_lsender=a.d_no and l.d_ldealtype="+inout+" and l.d_lsender="+d_no+" order by d_ldate asc ) order by d_ldealtype asc ) where r >=  "+startRow+"  and r <= 200 order by d_ldateS ";
		}else if(inout ==2){
			sql = "select d_lno, d_lsender, d_lreceiver, d_lbno,d_lbcode, d_ldealmoney, d_ltomoney, d_ldivision, d_ldealtype, d_ldealresult, to_char(d_ldate, 'yyyy-mm-dd HH:mm') AS d_ldateS, r ";
			sql += " from (select d_lno, d_lsender, d_lreceiver, d_lbno, d_lbcode, d_ldealmoney, d_ldivision, d_ldealtype, d_ldealresult, d_ltomoney, d_ldate, rownum r ";
			sql += " from (select l.d_lno, l.d_lsender, l.d_lreceiver, l.d_lbno, l.d_lbcode, l.d_ldealmoney, l.d_ldivision, l.d_ldealtype, l.d_ldealresult, l.d_ltomoney, l.d_ldate  ";
			sql += " from d_log l, d_account a  where l.d_lsender=a.d_no and l.d_ldealtype="+inout+" and l.d_lsender="+d_no+" order by d_ldate asc ) order by d_ldealtype asc ) where r >=  "+startRow+"  and r <= 200 order by d_ldateS ";			
		}
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		if(rs.next()){
			logList = new ArrayList<AcDto>();
			do{
				AcDto adto = new AcDto();
				adto.setD_lno(rs.getInt("d_lno"));
				adto.setD_lsender(rs.getInt("d_lsender"));
				adto.setD_lreceiver(rs.getInt("d_lreceiver"));
				adto.setD_lbno(rs.getInt("d_lbno"));
				adto.setD_lbcode(rs.getString("d_lbcode"));
				adto.setD_ldivision(rs.getInt("d_ldivision"));
				adto.setD_ldealtype(rs.getInt("d_ldealtype"));
				adto.setD_ldealresult(rs.getInt("d_ldealresult"));
				adto.setD_ldealmoney(rs.getInt("d_ldealmoney"));
				adto.setD_ltomoney(rs.getInt("d_ltomoney"));
				adto.setD_ldateS(rs.getString("d_ldates"));
				logList.add(adto);
			}while(rs.next());
		}
		
		
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		if (rs != null) try { rs.close(); } catch(SQLException ex) {}
        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	}
	
	return logList;
}
    

//---- å��� �� ����ڿ��� ���� �����ؾ��մϴ�. �׶� �߽��ϴ� Dao ---------------    
public void insertAccountLog(AcDto acDto) throws Exception{
	int userD_cash = 0;
      try{
    	  conn = getConnection();
          pstmt = conn.prepareStatement(
             		"select d_cash from d_account where d_no = "+ acDto.getD_lreceiver()
            		 );
             rs = pstmt.executeQuery();
             
             if(rs.next()){
          	   userD_cash = rs.getInt(1); //ī��Ʈ ù��° ���� ���� ����Ͽ� x�� ���� 
             }

    	  
			//ȸ���� �����ڿ��� �� ����
			pstmt = conn.prepareStatement(
"insert into d_log values(account_log.NEXTVAL,?,?,?,?,?,?,?, "+acDto.getD_ldealmoney()+", "+(userD_cash - acDto.getD_ldealmoney())+",sysdate)");
			pstmt.setInt(1, acDto.getD_lsender()); //261������
			pstmt.setInt(2, acDto.getD_lreceiver()); //ȸ��(å�Ǹ���)
			pstmt.setInt(3, acDto.getD_lbno()); //0
			pstmt.setString(4, acDto.getD_lbcode()); //å�ڵ�
			pstmt.setInt(5, 1); //d_onBook
			pstmt.setInt(6, 3);	//�۱���ü							
			pstmt.setInt(7, 1); //�ŷ��Ϸ�
			//�����ڿ��� ȸ���� å�� ��⶧���� ȸ��->������ ������

			pstmt.executeUpdate();
		
	          pstmt = conn.prepareStatement(
	             		"select d_cash from d_account where d_no = "+ 261
	            		 );
	             rs = pstmt.executeQuery();
	             
	             if(rs.next()){
	          	   userD_cash = rs.getInt(1); //ī��Ʈ ù��° ���� ���� ����Ͽ� x�� ���� 
	             }
			
			

			//ȸ���� �����ھư� �� �����Ͽ� ȸ���ڽ��ǵ� ����
			pstmt = conn.prepareStatement(
"insert into d_log values(account_log.NEXTVAL,?,?,?,?,?,?,?, -"+acDto.getD_ldealmoney()+",  "+(userD_cash + acDto.getD_ldealmoney())+",sysdate)");
			pstmt.setInt(1, acDto.getD_lsender());
			pstmt.setInt(2, acDto.getD_lsender());
			pstmt.setInt(3, acDto.getD_lbno());
			pstmt.setString(4, acDto.getD_lbcode());
			pstmt.setInt(5, 1);
			pstmt.setInt(6, 4);								
			pstmt.setInt(7, 1);
			//�����ڿ��� ȸ���� å�� ��⶧���� ȸ��->������ ������

			pstmt.executeUpdate();        
      }catch(Exception e){
         e.printStackTrace();
      }finally{
         if (rs != null) try { rs.close(); } catch(SQLException ex) {}
          if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
          if (conn != null) try { conn.close(); } catch(SQLException ex) {}
      }
   } 

//---- å���� �� ����ڰ� ���� �����ؾ��մϴ�. �׶� �߽��ϴ� Dao ---------------    
public void insertAccountLogB(AcDto acDto) throws Exception{
	int userD_cash = 0;
    try{
  	  conn = getConnection();
        pstmt = conn.prepareStatement(
           		"select d_cash from d_account where d_no = "+ acDto.getD_lsender()
          		 );
           rs = pstmt.executeQuery();
           
           if(rs.next()){
        	   userD_cash = rs.getInt(1); //ī��Ʈ ù��° ���� ���� ����Ͽ� x�� ���� 
           }

  	  
			//ȸ���� �����ڿ��� �� ����
			pstmt = conn.prepareStatement(
"insert into d_log values(account_log.NEXTVAL,?,?,?,?,?,?,?, "+acDto.getD_ldealmoney()+", "+(userD_cash - acDto.getD_ldealmoney())+",sysdate)");
			pstmt.setInt(1, acDto.getD_lsender()); //ȸ��
			pstmt.setInt(2, acDto.getD_lreceiver()); //������
			pstmt.setInt(3, acDto.getD_lbno()); //0
			pstmt.setString(4, acDto.getD_lbcode()); //å�ڵ�
			pstmt.setInt(5, 1); //d_onBook
			pstmt.setInt(6, 3);	//�۱���ü							
			pstmt.setInt(7, 1); //�ŷ��Ϸ�
			//�����ڿ��� ȸ���� å�� ��⶧���� ȸ��->������ ������

			pstmt.executeUpdate();
		
	          pstmt = conn.prepareStatement(
	             		"select d_cash from d_account where d_no = "+ 261
	            		 );
	             rs = pstmt.executeQuery();
	             
	             if(rs.next()){
	          	   userD_cash = rs.getInt(1); //ī��Ʈ ù��° ���� ���� ����Ͽ� x�� ���� 
	             }
			
			

			//ȸ���� �����ھư� �� �����Ͽ� ȸ���ڽ��ǵ� ����
			pstmt = conn.prepareStatement(
"insert into d_log values(account_log.NEXTVAL,?,?,?,?,?,?,?, -"+acDto.getD_ldealmoney()+",  "+(userD_cash + acDto.getD_ldealmoney())+",sysdate)");
			pstmt.setInt(1, acDto.getD_lsender());
			pstmt.setInt(2, acDto.getD_lsender());
			pstmt.setInt(3, acDto.getD_lbno());
			pstmt.setString(4, acDto.getD_lbcode());
			pstmt.setInt(5, 1);
			pstmt.setInt(6, 4);								
			pstmt.setInt(7, 1);
			//�����ڿ��� ȸ���� å�� ��⶧���� ȸ��->������ ������

			pstmt.executeUpdate();        
    }catch(Exception e){
       e.printStackTrace();
    }finally{
       if (rs != null) try { rs.close(); } catch(SQLException ex) {}
        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
    }
 } 


//---- ȸ���� �Ǹ��ϴ� å�� ���� �������� ���� ���� Dao ---------------    
public void updateAccountLog(int d_lno) throws Exception{
    try{
  	 
  	 //ȸ���� å�Ǹ� : d_s, ȸ���� å���� : d_p
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

/*---- ȸ���� �Ǹ��ϴ� å�� ���� ������ ��ü count -------------------------------------------*/
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

//----- ȸ���� å �Ǹ� �� ���� ������ ��ü  List ----------------------------------------------------------
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

/*---- ȸ���� �Ǹ��ϴ� å�� ���� ���� �� count -------------------------------------------*/
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

//----- ȸ���� å �Ǹ� �� ���� ���� �� List ----------------------------------------------------------
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

/*---- ȸ���� �Ǹ��ϴ� å�� ���� �����Ϸ� count -------------------------------------------*/
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

//----- ȸ�� å �Ǹſ� ���� �����Ϸ�  List ----------------------------------------------------------
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


//---- ȸ���� å 1�� ���Ž�(X��ٱ���) �����ϴ� ��Ȳ ---------------    
public void D_onBookValueUserToAdmin(AcDto acDto) throws Exception{
    try{
    	conn = getConnection();
			//ȸ���� �����ڿ��� �� ����
			pstmt = conn.prepareStatement(
"insert into d_log values(account_log.NEXTVAL,?,?,?,?,?,?,?, "+acDto.getD_ldealmoney()+",0,sysdate)");
			pstmt.setInt(1, acDto.getD_lsender()); //261������
			pstmt.setInt(2, acDto.getD_lreceiver()); //ȸ��(å�Ǹ���)
			pstmt.setInt(3, acDto.getD_lbno()); //0
			pstmt.setString(4, acDto.getD_lbcode()); //å�ڵ�
			pstmt.setInt(5, 1); //d_onBook
			pstmt.setInt(6, 3);	//�۱���ü							
			pstmt.setInt(7, 1); //�ŷ��Ϸ�
			//�����ڿ��� ȸ���� å�� ��⶧���� ȸ��->������ ������

			pstmt.executeUpdate();
		

			//ȸ���� �����ھư� �� �����Ͽ� ȸ���ڽ��ǵ� ����
			pstmt = conn.prepareStatement(
"insert into d_log values(account_log.NEXTVAL,?,?,?,?,?,?,?, -"+acDto.getD_ldealmoney()+",0,sysdate)");
			pstmt.setInt(1, acDto.getD_lsender());
			pstmt.setInt(2, acDto.getD_lsender());
			pstmt.setInt(3, acDto.getD_lbno());
			pstmt.setString(4, acDto.getD_lbcode());
			pstmt.setInt(5, 1);
			pstmt.setInt(6, 4);								
			pstmt.setInt(7, 1);
			//�����ڿ��� ȸ���� å�� ��⶧���� ȸ��->������ ������

			pstmt.executeUpdate();        
    }catch(Exception e){
       e.printStackTrace();
    }finally{
       if (rs != null) try { rs.close(); } catch(SQLException ex) {}
        if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
        if (conn != null) try { conn.close(); } catch(SQLException ex) {}
    }
 } 



//-------------------------------------------------------------------- ������ ���� �� �α� ǥ��, ���� -------------------------------------------------------------------------------------//


//--------------------------------��ü�ϼ���ŭ 400�� �߰�--------------------------------------
public long getMoney(int d_no,String br_code) throws ParseException{
      CartDao cdao = CartDao.getInstance();
      long diffDays = comDate(br_code);
      long x,y = 0;
      try {
         if(diffDays>7L){            
         x = diffDays-7L;
         y = x*400L;
           }
      } catch (Exception e) {
         e.printStackTrace();
      }finally{
            if(rs != null)try{rs.close();}catch(SQLException ex){}
            if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
            if(conn != null)try{conn.close();}catch(SQLException ex){}
         }
      return y;
   }
   //--------------------------------��ü�ϼ���ŭ 400�� �߰� ��--------------------------------------
   
   //------------------------------------��ü�ݾ��� ���� update-----------------------------------------
   public int getCash(int d_no,String br_code) throws ParseException{
      int cash = 0;
      int result = 0;//��ü�ݾ�
      CartDao cdao = CartDao.getInstance();
      long ac = getMoney(d_no, br_code);
      try {
         conn = getConnection();
         String sql = "select d_cash from d_account where d_no = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, d_no);   
         rs = pstmt.executeQuery();         
         if(rs.next()){
            cash = rs.getInt(1);
            if(cash>400){
               result = (int)(cash -ac);   //����ĳ������ ��ü�� ����
             sql = "update d_account set d_cash="+result+" where d_no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, d_no);
            pstmt.executeUpdate();
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }finally{
            if(rs != null)try{rs.close();}catch(SQLException ex){}
            if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
            if(conn != null)try{conn.close();}catch(SQLException ex){}
         }
      
      return result;
   }
   //------------------------------------��ü�ݾ��� ���� update ��-----------------------------------------
   
   //--------------------------------------------- ���� �Ѿ� ������ ----------------------------------------------//
   public int getD_cash(String d_col, int d_no) throws Exception{// �÷��̸� ,ȸ����ȣ
	   int d_cash = 0;
	   try{
		   conn = getConnection();
		   String sql = "select"+d_col+"from d_account where d_no = ? ";
		   pstmt = conn.prepareStatement(sql);
		   pstmt.setInt(1, d_no);
		   
		   rs = pstmt.executeQuery();
		   if(rs.next()){
			   d_cash = (int)rs.getInt(d_col);
		   }
		   
	   }catch(Exception e){
		   e.printStackTrace();
	   }finally{
		   if(rs != null)try{rs.close();}catch(SQLException ex){}
           if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
           if(conn != null)try{conn.close();}catch(SQLException ex){}
	   }
	   
	   
	   return d_cash;
   }
 //--------------------------------------------- ���� �Ѿ� ������ ��----------------------------------------------//
   
   //----------------------��¥���� ���ϱ�-----------------------------------------
   public Date getDate(String br_code){
      Date str = null;
       try {
            conn = getConnection();//DB����
            String sql = "select br_over_date from b_rent_over where br_code=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, br_code);
            rs = pstmt.executeQuery();//���� ����
            if(rs.next()){
               str = rs.getDate(1);
            }
       }catch(Exception e){
            e.printStackTrace();
         }finally{
            if(rs != null)try{rs.close();}catch(SQLException ex){}
            if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
            if(conn != null)try{conn.close();}catch(SQLException ex){}
         }
      
      return str;
   }
   
   
   
   public long comDate(String br_code) throws ParseException{
      CartDao cdao = CartDao.getInstance();
       SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
      long diffDays = 0;
      
      Calendar cal = Calendar.getInstance();
      cal.setTime(getDate(br_code));
      String str = fm.format(cal.getTime());
      
      String start =str;                           //�뿩 ��¥
       String today = fm.format(new Date()); //���ó�¥
   
        Date startDate = fm.parse(start);
        Date todayDate = fm.parse(today);
        
        long diff = todayDate.getTime() - startDate.getTime();
        diffDays = diff / (24 * 60 * 60 * 1000);
      
      return diffDays;
   }
   
   //----------------------��¥���� ���ϱ� ��-----------------------------------------
   
   //-----------------------------������ �Ѱ��¿� ��ü�� �߰� - doit_aco --------------------------------------
   public int getAcco(int d_no,String br_code) throws ParseException{
      int acco,abc =0;
      CartDao cdao = CartDao.getInstance();
      long result = getMoney(d_no, br_code);
      try {
         conn = getConnection();//DB����
            String sql = "select * from doit_aco";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();//���� ����
            if(rs.next()){
               acco = rs.getInt(2);
               abc = acco+(int)result;
            }
            sql = "update doit_aco set d_lib ="+abc;
            pstmt = conn.prepareStatement(sql);
            pstmt.executeQuery();
      } catch (Exception e) {
         e.printStackTrace();
      }finally{
         if(rs != null)try{rs.close();}catch(SQLException ex){}
         if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
         if(conn != null)try{conn.close();}catch(SQLException ex){}
      }
      
      return abc;
   }

//------------------------------����� ���¿� ��ü�� �߰� ��--------------------------------------

   //-------------------------------�α� ���------------------------------------------
public void inLog(int d_no,int br_o ,String br_code, int dealresult ,long dealmoney) throws Exception{
	int d_n = getD_cash("d_cash",d_no);
	
   try {
      conn = getConnection();
      pstmt = conn.prepareStatement("insert into d_log values(account_log.nextval, "+d_no+", 20000,"+br_o+", ? ,0, 4,"+dealresult+","+dealmoney+", ? ,sysdate)");
      pstmt.setString(1, br_code+",");
      pstmt.setInt(2, d_n);
      pstmt.executeUpdate();
      
   } catch (Exception e) {
      e.printStackTrace();
   }finally{
         if(rs != null)try{rs.close();}catch(SQLException ex){}
         if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
         if(conn != null)try{conn.close();}catch(SQLException ex){}
      }
}
 //-------------------------------�α� ��� ��------------------------------------------


    
}
