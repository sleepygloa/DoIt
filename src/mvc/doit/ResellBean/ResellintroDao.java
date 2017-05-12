package mvc.doit.ResellBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import mvc.doit.Login.LoginDao;
import mvc.doit.Login.LoginDto;
import mvc.doit.SuperAction.SuperAction;
public class ResellintroDao implements SuperAction{

private static ResellintroDao instance = new ResellintroDao();
    
    public static ResellintroDao getInstance() {return instance; }
    
    private ResellintroDao() {}
    
    private Connection getConnection() throws Exception {
      Context initCtx = new InitialContext();
      Context envCtx = (Context) initCtx.lookup("java:comp/env");
      DataSource ds = (DataSource)envCtx.lookup("jdbc/khdb");
      return ds.getConnection();
    }
	
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
//--------------���ѽ�û-----------------�Ǹ��� ���ѽ�û--------------------------


	public boolean callForm(String d_id, String rbook_intro) {
		// TODO Auto-generated method stub
	
		boolean result = false;	// ���, ���ѽ�û ��
		       try{
		          conn = getConnection();
		          pstmt = conn.prepareStatement(
		                "update d_member set d_mech_grade=1 where d_id=?");
		          pstmt.setString(1, d_id);
		          pstmt.executeUpdate(); 
		          pstmt = conn.prepareStatement(
			            "insert into resellintro(rbook_introno,d_id,rbook_intro, rbook_reg_date)"
			            + " values(resellintro_seq.NEXTVAL,?,?,sysdate)");
					
					pstmt.setString(1, d_id);
					pstmt.setString(2, rbook_intro);
			          pstmt.executeUpdate(); 
		          result = true; //���ѽ�û �Ϸ�
		         
		       }catch(Exception e){
		             e.printStackTrace();
		          }finally{
		             if(rs != null){try{rs.close();}catch(SQLException s){}}
		             if(pstmt != null){try{pstmt.close();}catch(SQLException s){}}
		             if(conn != null){try{conn.close();}catch(SQLException s){}}
		          }
		          return result;
		       }
	
	


//---------------------���ѽ���-------------------


	public boolean callFormOk(String d_id) {
		// TODO Auto-generated method stub
		boolean result = false;	//���ѽ��� ��
		       try{
		          conn = getConnection();
		          pstmt = conn.prepareStatement(
		                "update d_member set d_mech_grade=2 where d_id=?");
		          pstmt.setString(1, d_id);
		          pstmt.executeUpdate(); 
		     
		          
		          result = true; //���ѽ���, ���ڵ� ���� �Ϸ�
		          
		          }catch(Exception e){
		             e.printStackTrace();
		          }finally{
		             if(rs != null){try{rs.close();}catch(SQLException s){}}
		             if(pstmt != null){try{pstmt.close();}catch(SQLException s){}}
		             if(conn != null){try{conn.close();}catch(SQLException s){}}
		          }
		          return result;
		      }
	
	//------------------���� ����
	//------------------���� ����
		public boolean callFormNo(String d_id) {
			// TODO Auto-generated method stub
			boolean result = false;	//���ѽ��� ��
			       try{
			          conn = getConnection();
			          pstmt = conn.prepareStatement(
			                "update d_member set d_mech_grade=0 where d_id=?");
			          pstmt.setString(1, d_id);
			          pstmt.executeUpdate(); 
			          conn = getConnection();
			          pstmt = conn.prepareStatement(
			                "delete from resellintro where d_id=?");
			          pstmt.setString(1, d_id);
			          pstmt.executeUpdate(); 
			          
			          result = true; //���ѽ���, ���ڵ� ���� �Ϸ�
			          
			          }catch(Exception e){
			             e.printStackTrace();
			          }finally{
			             if(rs != null){try{rs.close();}catch(SQLException s){}}
			             if(pstmt != null){try{pstmt.close();}catch(SQLException s){}}
			             if(conn != null){try{conn.close();}catch(SQLException s){}}
			          }
			          return result;
			       }
		
//-----------���ѽ�û 0�� �ƴ� ���� �̱�---------------------------------------------
	public int getArticleCount() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x=0;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select count(*) from resellintro");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x= rs.getInt(1); 
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return x; 
	}
	//______________---------------------------
	//���ѽ�û���� ��� �����̱�
	public int waitOkCount() throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x=0;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(
					"select count(*) from (select a.d_id, b.d_mech_grade from"
					+ " resellintro a, d_member b where a.d_id=b.d_id)where d_mech_grade=1");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x= rs.getInt(1); 
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return x; 
	}
	//-----------------------------
	//------------------------���ѽ��ΰԽ��� ���̴� ����-----------------
	
	public List getArticles(int start, int end) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List articleList=null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(
					"select d_id, rbook_intro, rbook_introno, rbook_reg_date,d_mech_grade, r from"
					+ " (select d_id, rbook_intro, rbook_introno, rbook_reg_date,d_mech_grade, rownum r from"
					+ " (select a.d_id, a.rbook_intro, a.rbook_introno, a.rbook_reg_date, b.d_mech_grade from"
					+ " resellintro a, d_member b where a.d_id=b.d_id)"
					+ " order by rbook_reg_date desc) where r >= ? and r <= ?");
							pstmt.setInt(1, start); 
							pstmt.setInt(2, end); 

							rs = pstmt.executeQuery();
							if (rs.next()) {
								articleList = new ArrayList(end); 
								do{ 
									ResellintroDto article= new ResellintroDto();
									
									article.setD_id(rs.getString("d_id"));
									article.setRbook_intro(rs.getString("rbook_intro"));
									article.setRbook_introno(rs.getInt("rbook_introno"));
									article.setRbook_reg_date(rs.getTimestamp("rbook_reg_date"));
									article.setD_mech_grade(rs.getInt("d_mech_grade"));
									articleList.add(article); 
								}while(rs.next());
							}
				} catch(Exception ex) {
					ex.printStackTrace();
				} finally {
					if (rs != null) try { rs.close(); } catch(SQLException ex) {}
					if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
					if (conn != null) try { conn.close(); } catch(SQLException ex) {}
				}

				
				return articleList;
			}
	
	//---------------------------
	//--------------���ο�û���� ����鸸 �Խ��ǿ� ���̴� ����
	public List waitOks(int start, int end) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List articleList=null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(
					"select d_id, rbook_intro, rbook_introno, rbook_reg_date,d_mech_grade, r from"
					+ " (select d_id, rbook_intro, rbook_introno, rbook_reg_date,d_mech_grade, rownum r from"
					+ " (select a.d_id, a.rbook_intro, a.rbook_introno, a.rbook_reg_date, b.d_mech_grade from"
					+ " resellintro a, d_member b where a.d_id=b.d_id)"
					+ " where d_mech_grade=1 order by rbook_reg_date desc) where r >= ? and r <= ?");
							pstmt.setInt(1, start); 
							pstmt.setInt(2, end); 

							rs = pstmt.executeQuery();
							if (rs.next()) {
								articleList = new ArrayList(end); 
								do{ 
									ResellintroDto article= new ResellintroDto();
									
									article.setD_id(rs.getString("d_id"));
									article.setRbook_intro(rs.getString("rbook_intro"));
									article.setRbook_introno(rs.getInt("rbook_introno"));
									article.setRbook_reg_date(rs.getTimestamp("rbook_reg_date"));
									article.setD_mech_grade(rs.getInt("d_mech_grade"));
									articleList.add(article); 
								}while(rs.next());
							}
				} catch(Exception ex) {
					ex.printStackTrace();
				} finally {
					if (rs != null) try { rs.close(); } catch(SQLException ex) {}
					if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
					if (conn != null) try { conn.close(); } catch(SQLException ex) {}
				}

				
				return articleList;
			}
	//--------------------------------------------
	
	public ResellintroDto getArticle(int rbook_introno) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResellintroDto article=null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(
			
			"select * from (select a.rbook_introno, a.d_id, a.rbook_intro, a.rbook_reg_date, b.d_mech_grade"
			+ " from resellintro a, d_member b where a.d_id=b.d_id)"
			+ "where rbook_introno = ?"); 
			pstmt.setInt(1, rbook_introno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				
				article = new ResellintroDto();
				article.setRbook_introno(rs.getInt("rbook_introno"));
				article.setD_id(rs.getString("d_id"));
				article.setRbook_intro(rs.getString("rbook_intro"));
				article.setRbook_reg_date(rs.getTimestamp("rbook_reg_date"));
				article.setD_mech_grade(rs.getInt("d_mech_grade"));
			
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return article;
	}
	
	//-----------------------------
	//-------------���δ�����ΰ͵鸸 ����
	public ResellintroDto waitOk(int rbook_introno) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResellintroDto article=null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(
			
			"select * from (select a.rbook_introno, a.d_id, a.rbook_intro, a.rbook_reg_date, b.d_mech_grade"
			+ " from resellintro a, d_member b where a.d_id=b.d_id)"
			+ "where d_mech_grade=1 and rbook_introno = ?"); 
			pstmt.setInt(1, rbook_introno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				
				article = new ResellintroDto();
				article.setRbook_introno(rs.getInt("rbook_introno"));
				article.setD_id(rs.getString("d_id"));
				article.setRbook_intro(rs.getString("rbook_intro"));
				article.setRbook_reg_date(rs.getTimestamp("rbook_reg_date"));
				article.setD_mech_grade(rs.getInt("d_mech_grade"));
			
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		
		return article;
	}
	//-----------------------------------------
	//----------------�����ڰ� ���̵� �˻�
	public int getSearchCount(String search) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x=0;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("select count(*) from resellintro where d_id like '%"+search+"%'");
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x= rs.getInt(1); 
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return x; 
	}
	//----------���� ���� �˻�
	public List getSearchArticles(String search) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List articleList=null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(
					"select * from (select a.d_id, a.rbook_reg_date, a.rbook_intro, a.rbook_introno, b.d_mech_grade"
					+ " from resellintro a, d_member b where a.d_id=b.d_id) where d_id like '%"+search+"%'");					
				
					rs = pstmt.executeQuery();
					if (rs.next()) {
						articleList = new ArrayList(); 
						do{ 
							ResellintroDto article= new ResellintroDto();
							
							article.setD_id(rs.getString("d_id"));
							article.setRbook_reg_date(rs.getTimestamp("rbook_reg_date"));
							article.setRbook_intro(rs.getString("rbook_intro"));
							article.setRbook_introno(rs.getInt("rbook_introno"));
							article.setD_mech_grade(rs.getInt("d_mech_grade"));
							articleList.add(article); 
						}while(rs.next());
					}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
			if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}

		
		return articleList;
	}
	//0----------------------------------
	
	
	//�Ǹ��� �Ұ��� �����Ҷ� ���̴°�
	   public ResellintroDto getIntro(String d_id) throws Exception{
	      Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	      ResellintroDto m_dto = null;
	      try{
	         conn = getConnection(); //1, 2�ܰ� �Է�
	         pstmt =conn.prepareStatement(
	               "select * from (select a.d_no, a.d_id, a.d_mech_grade, b.rbook_intro"
	                     + " from d_member a, resellintro b where a.d_id = b.d_id) where d_id= ?"
	               );
	               
	         pstmt.setString(1, d_id);
	         
	         rs = pstmt.executeQuery(); //result ���� Ȯ��
	         
	         if(rs.next()){
	            m_dto = new ResellintroDto();
	            m_dto.setD_no(rs.getInt("d_no"));
	            m_dto.setD_id(rs.getString("d_id"));
	            m_dto.setD_mech_grade(rs.getInt("d_mech_grade"));
	            m_dto.setRbook_intro(rs.getString("rbook_intro"));
	            }
	         
	      }catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         if(conn != null){
	            try{conn.close();}catch(SQLException s){}
	         }
	         if(rs != null){
	            try{rs.close();}catch(SQLException s){}
	         }
	         if(pstmt != null){
	            try{pstmt.close();}catch(SQLException s){}
	         }
	      }//finally ��
	      
	      return m_dto;
	   }

	   //=--------------�Ǹ��� �Ұ��� ����-------------
	   public void modiIntro(ResellintroDto article) throws Exception {
	      // TODO Auto-generated method stub
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      
	   
	             try{
	                conn = getConnection();
	                
	                pstmt = conn.prepareStatement(
	                      "update resellintro set rbook_intro=? where d_id=?");
	               
	               pstmt.setString(1, article.getRbook_intro());
	               pstmt.setString(2, article.getD_id());
	                   pstmt.executeUpdate(); 
	            
	             
	               
	             }catch(Exception e){
	                   e.printStackTrace();
	                }finally{
	                   if(rs != null){try{rs.close();}catch(SQLException s){}}
	                   if(pstmt != null){try{pstmt.close();}catch(SQLException s){}}
	                   if(conn != null){try{conn.close();}catch(SQLException s){}}
	                }
	           
	             }
	   
	   
 //------------------�Ű�� ���̵� ���� ��Ż
	 		public boolean reportDelete(String rbook_id) {
	 			// TODO Auto-generated method stub
	 			boolean result = false;	//���ѽ��� ��
	 			       try{
	 			          conn = getConnection();
	 			          pstmt = conn.prepareStatement(
	 			                "update d_member set d_mech_grade=0 where d_id=?");
	 			          pstmt.setString(1, rbook_id);
	 			          pstmt.executeUpdate(); 
	 			          conn = getConnection();
	 			          pstmt = conn.prepareStatement(
	 			                "delete from resellintro where d_id=?");
	 			          pstmt.setString(1, rbook_id);
	 			          pstmt.executeUpdate(); 
	 			         conn = getConnection();
	 			          pstmt = conn.prepareStatement(
	 			                "delete from resellreport where d_id=?");
	 			          pstmt.setString(1, rbook_id);
	 			          pstmt.executeUpdate(); 
	 			          
	 			          result = true; 
	 			          
	 			          }catch(Exception e){
	 			             e.printStackTrace();
	 			          }finally{
	 			             if(rs != null){try{rs.close();}catch(SQLException s){}}
	 			             if(pstmt != null){try{pstmt.close();}catch(SQLException s){}}
	 			             if(conn != null){try{conn.close();}catch(SQLException s){}}
	 			          }
	 			          return result;
	 			       }	   

	 		//---------------�Ǹ��� �Ұ��Ҷ� �󼼺��� ���̴°͵�--------------
	         public LoginDto sellerContent(int d_no) throws Exception {
	            Connection conn = null;
	            PreparedStatement pstmt = null;
	            ResultSet rs = null;
	            LoginDto article=null;
	            try {
	               conn = getConnection();
	               pstmt = conn.prepareStatement(
	               "select * from (select a.d_pic, a.d_no, a.d_id, a.d_name, a.d_phone, a.d_addr, a.d_mail, a.d_gender, b.rbook_intro, b.rbook_reg_date from"
	               + " d_member a, resellintro b where a.d_id=b.d_id) where d_no=?"
	               ); 
	               pstmt.setInt(1, d_no);
	               rs = pstmt.executeQuery();
	               
	               
	               if (rs.next()) {
	                  
	                  article = new LoginDto();
	                  article.setD_pic(rs.getString("d_pic"));
	                  article.setD_no(rs.getInt("d_no"));
	                  article.setD_id(rs.getString("d_id"));
	                  article.setD_name(rs.getString("d_name"));
	                  
	                  String pho = rs.getString("d_phone");
	                  String pho_cut[] = pho.split("-");
	                  article.setUser_phone1(pho_cut[0]);
	                  article.setUser_phone2(pho_cut[1]);
	                  article.setUser_phone3(pho_cut[2]);
	                  
	                  article.setD_addr(rs.getString("d_addr"));
	                  
	                  String e_mai = rs.getString("d_mail");
	                  String mail_cut[] = e_mai.split(" @ ");
	                  article.setUser_mail1(mail_cut[0]);
	                  article.setUser_mail2(mail_cut[1]);
	                  
	                  article.setD_gender(rs.getString("d_gender"));
	                  article.setRbook_intro(rs.getString("rbook_intro"));
	                  article.setRbook_reg_date(rs.getTimestamp("rbook_reg_date"));
	                  
	               
	               }
	            } catch(Exception ex) {
	               ex.printStackTrace();
	            } finally {
	               if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	               if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	               if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	            }
	            
	            return article;
	         }
	       //-----------------------�Ǹ��� �Ұ� �󼼺��� �Ҷ� �ؿ� �������� å��
	          public ResellbookDto sellerContentBook(String rbook_id) throws Exception {
	               Connection conn = null;
	               PreparedStatement pstmt = null;
	               ResultSet rs = null;
	               ResellbookDto dto=null;
	               try {
	                  conn = getConnection();
	                  pstmt = conn.prepareStatement(
	                  "select * from (select a.d_id, a.d_no, b.rbook_pic, b.rbook_name, b.rbook_price, b.rbook_price2, "
	                  + "b.rbook_bgread from d_member a, resellbook b where a.d_id=b.rbook_id) where d_id=?"
	                  ); 
	                  pstmt.setString(1, rbook_id);
	                  rs = pstmt.executeQuery();
	                  
	                  
	                  if (rs.next()) {
	                     
	                     dto = new ResellbookDto();
	                     dto.setD_id(rs.getString("d_id"));
	                     dto.setD_no(rs.getInt("d_no"));
	                     dto.setRbook_pic(rs.getString("rbook_pic"));
	                     dto.setRbook_name(rs.getString("rbook_name"));
	                     dto.setRbook_price(rs.getInt("rbook_price"));
	                     dto.setRbook_price2(rs.getInt("rbook_price2"));
	                     dto.setRbook_bgread(rs.getInt("rbook_bgread"));
	                     
	                  
	                  }
	               } catch(Exception ex) {
	                  ex.printStackTrace();
	               } finally {
	                  if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	                  if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	                  if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	               }
	               
	               return dto;
	            }
	         
	//-----�ش� �Ǹ��ڰ� �Ǹ��ϰ� �ִ� å ���� ī��Ʈ
	          public int sellerContentBookCount(int d_no) throws Exception {
	               Connection conn = null;
	               PreparedStatement pstmt = null;
	               ResultSet rs = null;
	               int x=0;
	               try {
	                  conn = getConnection();
	                  pstmt = conn.prepareStatement("select count(*) from "
	                        + "(select a.d_no, b.* from d_member a, resellbook b where a.d_id=b.rbook_id) where rbook_sell_check=0 and d_no=?");
	                  pstmt.setInt(1, d_no);
	                  rs = pstmt.executeQuery();
	                  if (rs.next()) {
	                     x= rs.getInt(1); 
	                  }
	               } catch(Exception ex) {
	                  ex.printStackTrace();
	               } finally {
	                  if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	                  if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	                  if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	               }
	               return x; 
	            }
	      
	
	   
	   //------------------------�ش��Ǹ��ڰ� �Ǹ��ϰ��ִ� å ����Ʈ-----------------
	   
	      public List sellerContentBooks(int d_no) throws Exception {
	         Connection conn = null;
	         PreparedStatement pstmt = null;
	         ResultSet rs = null;
	         List articleList=null;
	         try {
	            conn = getConnection();
	            pstmt = conn.prepareStatement(
	                  "select d_id, d_no, rbook_pic, rbook_name, rbook_price, rbook_price2, rbook_bgread, rbook_no, rbook_sell_check, r from"
	                  + " (select d_id, d_no, rbook_pic, rbook_name, rbook_price, rbook_price2, rbook_bgread, rbook_no, rbook_sell_check, rownum r from"
	                  + " (select a.d_id, a.d_no, b.rbook_pic, b.rbook_name, b.rbook_price, b.rbook_price2, b.rbook_no, b.rbook_bgread , b.rbook_sell_check from"
	                  + " d_member a, resellbook b where a.d_id=b.rbook_id)"
	                  + " where rbook_sell_check=0) where d_no=?");
	                     pstmt.setInt(1, d_no);

	                        rs = pstmt.executeQuery();
	                        if (rs.next()) {
	                           articleList = new ArrayList(); 
	                           do{ 
	                              ResellintroDto article= new ResellintroDto();
	                              
	                              article.setD_id(rs.getString("d_id"));
	                              article.setD_no(rs.getInt("d_no"));
	                              article.setRbook_name(rs.getString("rbook_name"));
	                              article.setRbook_no(rs.getInt("rbook_no"));
	                              article.setRbook_price(rs.getInt("rbook_price"));
	                              article.setRbook_price2(rs.getInt("rbook_price2"));
	                              article.setRbook_sell_check(rs.getInt("rbook_sell_check"));
	                              article.setRbook_bgread(rs.getInt("rbook_bgread"));
	                              articleList.add(article); 
	                           }while(rs.next());
	                        }
	               } catch(Exception ex) {
	                  ex.printStackTrace();
	               } finally {
	                  if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	                  if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	                  if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	               }

	               
	               return articleList;
	            }
	      
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
