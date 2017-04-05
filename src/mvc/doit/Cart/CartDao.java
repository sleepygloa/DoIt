package mvc.doit.Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import mvc.doit.Rent.RentDao;
import mvc.doit.Rent.RentDto;
import mvc.doit.SuperAction.SuperAction;

public class CartDao implements SuperAction{

	private static CartDao instance =  new CartDao();  
	
	public static CartDao getInstance(){ return instance;}
	
	private CartDao(){}
	
	//1, 2�ܰ� ����
	private Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
	    Context envCtx = (Context) initCtx.lookup("java:comp/env");
	    DataSource ds = (DataSource)envCtx.lookup("jdbc/khdb");
	    return ds.getConnection();
	}
		
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	
	//------------------------------------------ �뿩 ��� ���� �ҷ����� ----------------------------------------//
	

	//------------------------------------------ �뿩 ��� ���� �ҷ����� �� ----------------------------------------//
	
	
	//----------------------------------------------- ��ٱ��� �޼��� ----------------------------------------//
	
	
	//---------------------------------------------- ��ٱ��� ���� ���� ----------------------------//
	public boolean checkASD(int br_no) throws Exception {
		boolean checkASD = false;
		try{
			conn = getConnection();
			String sql = "select * from d_cart where d_no = "+br_no;
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				checkASD = true;
			}else if(!rs.next()){
				checkASD = false;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException ex){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
			if(conn != null)try{conn.close();}catch(SQLException ex){}
		}
		
		
		return checkASD;
		
	}
	
	
	//---------------------------------------------- ��ٱ��� ���� ���� ��---------------------------//
	
	//---------------------------------------------- ��ٱ��� �ű� ����---------------------------//
	public void insASD(int br_no) throws Exception{
		try{
			conn = getConnection();
			String sql = "insert into d_cart values (cart_no.nextval,"+br_no+", ',', ',', ',', ',')";
			pstmt = conn.prepareStatement(sql);
				
			pstmt.executeUpdate();

			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException ex){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
			if(conn != null)try{conn.close();}catch(SQLException ex){}
		}
	}
	
	//---------------------------------------------- ��ٱ��� �ű� ���� ��---------------------------//
	
	//-------------------------------------- ��ٱ��� ���� �ľ� ---------------------------------------------//
	public int countCart(int br_no, String col) throws Exception{
		String column = col; //ī��Ʈ�� �÷��� ����

		int countC = 0;
		try{
			conn = getConnection();
			String sql = "select "+column+" from d_cart where d_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, br_no);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				String count_cont = rs.getString(1);
				String count[] = count_cont.split(","); // , ������ �ڸ���
				
				countC = count.length; //ī��Ʈ ����
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException ex){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
			if(conn != null)try{conn.close();}catch(SQLException ex){}
		}
		
		
		return countC;
	}
	
	//-------------------------------------- ��ٱ��� ���� �ľ� ��---------------------------------------------//
	
	//--------------------------------------- �뿩 ���� �ľ� ------------------------------------------------------------//
	public int countLib(int br_no) throws Exception{
		int countLib = 0;
		try{
			conn = getConnection();
			String sql = "select br_rent from b_rent_over where d_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, br_no);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				String cont = rs.getString(1);
				String cont2[] = cont.split(",");
				
				countLib = cont2.length;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException ex){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
			if(conn != null)try{conn.close();}catch(SQLException ex){}
		}
		
		
		return countLib;
		
	}
	
	//--------------------------------------- �뿩 ���� �ľ� ��------------------------------------------------------------//
	
	//---------------------------------------------- ��ٱ��Ͽ� ������ �ִ��� Ȯ�� ----------------------------------------------//
	public boolean checkCart(int br_no, String col,String b_code) throws Exception{
		boolean checkRe = false;
		
		try{
			conn = getConnection();
			String sql = "select "+col+" from d_cart where d_no= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, br_no);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				String ListCont[] = rs.getString(1).split(",");
				boolean checkN = false;
				for(int i = 0; i < ListCont.length; i++){
					checkN = ListCont[i].equals(b_code);
					if(checkN){
						checkRe = true;
						break;
					}else{
						checkRe = false;
					}
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException ex){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
			if(conn != null)try{conn.close();}catch(SQLException ex){}
		}
		
		return checkRe;
	}
	
	
	//---------------------------------------------- ��ٱ��Ͽ� ������ �ִ��� Ȯ�� ��----------------------------------------------//
	
	
	
	//---------------------------------------------- ��ٱ��� �Է� ----------------------------------------------//
	public void insetCart(int br_no, String col,String b_code) throws Exception{
		
		try{
			conn = getConnection();
			String sql = "select "+col+" from d_cart where d_no= "+br_no;
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()){ 
				String d_result = null;
				String result = rs.getString(1); //���� ���빰 �ҷ�����
				result += b_code+"," ; //���� ���빰�� å�ڵ� �߰�
				
				sql = "update d_cart set "+col+" = ? where d_no= ?"; //���Ӱ� ������Ʈ
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, result);
				pstmt.setInt(2, br_no);
				
				pstmt.executeUpdate();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException ex){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
			if(conn != null)try{conn.close();}catch(SQLException ex){}
		}
		
	}
	
	//---------------------------------------------- ��ٱ��� �Է� �� ----------------------------------------------//
	
	//---------------------------------------------- �뿩 ��� ���� ------------------------------------------------//
	public int getRentC(int br_no) throws Exception{
		int RentC = 0;
		try{
			conn = getConnection();
			String sql = "select dr_rent from d_cart where d_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, br_no);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				String cont = rs.getString(1);
				String co_cont[] = cont.split(",");
				
				RentC = co_cont.length;
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException ex){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
			if(conn != null)try{conn.close();}catch(SQLException ex){}
		}
		
		
		return RentC;
	}
	
	
	//---------------------------------------------- �뿩 ��� ���� �� ------------------------------------------------//
	
	//---------------------------------------------- ��ٱ��� ���� -> �뿩�� ����Ʈ�� �̵� --------------------------//
	public void moveCart(int br_no) throws Exception{

		try{
			conn = getConnection();
			String sql = "select d_rent, dr_rent from d_cart where d_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, br_no);
			
			rs = pstmt.executeQuery();
			
			String [] d = null ;
			String [] dr = null ;
			if(rs.next()){
				d = rs.getString(1).split(",");
				dr = rs.getString(2).split(",");
			}
			
			int count = 0;
			String input = "";
			
			
			if(d.length !=0 ){
				sql = "update d_cart set d_rent = ? , dr_rent = ? where d_no = ?";
				pstmt = conn.prepareStatement(sql);
				
				//�뿩 ����� �Ѱ��� ������ 
				if(dr.length==0){ 
					count = 6;
					for( int i = 0 ; i < count ; i++){
						input += (d[i]+",");
						if(d.length-1 == i) {
							break;
						}
					}
					d = null;
					String da = ",";
					// input��  update
					pstmt.setString(1, da);
					pstmt.setString(2, input);
					
					
				//�뿩 ����� �Ѱ��̻� ������	
				}else{
					count = 7-dr.length;
					input = rs.getString(2); //���� ��ٱ���
					for( int i = 1 ; i < count ; i++){
						input += (d[i]+",");				//                ''2345                      34561
						d[i]="";
						if(d.length-1 == i) {
							break;
						}
					}
					// update   d
					String all = ",";
					for(int i = 0; i < d.length; i++){
						if(!d[i].equals("")){
							all += d[i]+",";
						}
					}
					pstmt.setString(1, all);
					pstmt.setString(2, input);
				}
				pstmt.setInt(3, br_no);
				pstmt.executeUpdate();
			}
			
			
			
			//System.out.println("���==>>"+input);
			
			
			/*if(rs.next()){
				String cont1 = rs.getString(1);//��ٱ��� ���
				String co_cont1[] = cont1.split(",");
				for(int x = 1; x < co_cont1.length; x++){
					conList.add(co_cont1[x]);
				}
				
				String cont2 = rs.getString(2); //���� �뿩 ���
				//String co_cont2[] = cont2.split(",");
				all += cont2;
				
				//�뿩����� 5���� �ɶ� ���� �뿩��� �߰�
				for(int i = 1; i < 6-RentC; i++){
					if(conList.isEmpty()){
						break;
					}else{
						all += conList.get(0)+",";
						conList.remove(0); //�Է��� ���� ����
					}
	
				}
				
				// ��ٱ��� ���Ӱ� �Է�
				if(!conList.isEmpty()){
					for(int i = 0; i<conList.size(); i++ ){
						newS = conList.get(i)+","; //���� ���� ����
					}
				}
				
				sql = "update d_cart set d_rent = ?, dr_rent = ? where d_no = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newS);
				pstmt.setString(2, all);
				pstmt.setInt(3, br_no);
				
				pstmt.executeUpdate();
				
			}//rs if�� ��
*/			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException ex){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
			if(conn != null)try{conn.close();}catch(SQLException ex){}
		}
		
	}
	
	//---------------------------------------------- ��ٱ��� ���� -> �뿩�� ����Ʈ�� �̵� ��--------------------------//
	
	//---------------------------------------------- ��ٱ��� �ʱ�ȭ ---------------------------------------------------//
	public void delCart(int br_no, String col) throws Exception{
		
		//��ٱ��� ������Ʈ �� ����
		String all = ","; 
		
		try{
			conn = getConnection();
			String sql ="select d_rent, dr_rent from d_cart where d_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, br_no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				
				//��ٱ��� ����
				String cont1 = rs.getString(1);
				String co_cont1[] = cont1.split(",");
				
				//�뿩ǰ�� ����
				String cont2 = rs.getString(2);
				String co_cont2[] = cont2.split(",");
				
				//��ٱ��� ����� ���Ͽ� ���ο� ��������
				for(int i = 1; i < co_cont1.length; i++){
					for(int x = 1; x < co_cont2.length; x++){
						if(!co_cont1[i].equals(co_cont2[x])){
							all += co_cont1[i]+",";
						}
					}
				}
				
				sql = "update d_cart set d_rent = ? where d_no = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, all);
				pstmt.setInt(2, br_no);
				
				pstmt.executeUpdate();
				
			}
				
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException ex){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
			if(conn != null)try{conn.close();}catch(SQLException ex){}
		}
		
	}
	
	//---------------------------------------------- ��ٱ��� �ʱ�ȭ ��---------------------------------------------------//
	
	//------------------------------------------ �ش� ���� ����� ��� : ����� ---------------------------------------//
	public int getPerson(String br_code) throws Exception{
		int person = 0;
		try{
			conn = getConnection();
			String sql ="select br_waiter from b_rent_over where br_code = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, br_code);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				String cont = rs.getString(1);
				String cont2[] = cont.split(",");
				
				person = cont2.length;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException ex){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
			if(conn != null)try{conn.close();}catch(SQLException ex){}
		}
		
		return person;
		
	}
	
	//------------------------------------------ �ش� ���� ����� ��� : ����� �� ---------------------------------------//
	
	//------------------------------------------ �ش� ��������ڿ� �̹� �ִ°� ---------------------------------------------//
	public boolean checkBo(int br_no,String br_code)throws Exception{
		boolean checkBo = false; // false = �ִ� / true = ����.
		
		String b_no = Integer.toString(br_no);
		try{
			conn = getConnection();
			String sql = "select br_waiter from b_rent_over where br_code = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, br_code);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				String b_waiter = rs.getString(1);
				String b_cont[] = b_waiter.split(",");
				
				for(int i = 0; i < b_cont.length; i++){
					if( b_cont[i].equals(b_no) ){
						checkBo = false;
						break;
					}else{
						checkBo = true; //����.
					}
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException ex){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
			if(conn != null)try{conn.close();}catch(SQLException ex){}
		}
		
		return checkBo;
	}
	
	//------------------------------------------ �ش� ��������ڿ� �̹� �ִ°� ---------------------------------------------//
	
	
	//------------------------------------------ ȸ���� �뿩�� ������ �뿩 ��Ͽ� �ִ� ������� List�� ���� ----------------------//
	public String[] getBcode(int br_no) throws Exception{
		String cont2[] = null;
		try{
			conn = getConnection();
			String sql="select dr_rent from d_cart where d_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, br_no);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				String cont = rs.getString(1);
				cont2 = cont.split(",");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException ex){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
			if(conn != null)try{conn.close();}catch(SQLException ex){}
		}
		
		
		return cont2;
	}
	
	//---------------------------- ȸ���� �뿩�� ������ �뿩 ��Ͽ� �ִ� ������� List�� ���� -----------------------------------//
	
	
	//------------------------------------------- ����� �Է� : 5�������϶� ----------------------------------------------------------//
	public void insPerson(int br_no,String br_code) throws Exception{
		String sql = "";
		
		try{
			conn = getConnection();
			sql = "select br_waiter from b_rent_over where br_code = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, br_code);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				String cont = rs.getString(1);
				String cont2[] = cont.split(",");
				String cont3 = ",";
				
				for(int i = 0; i < cont2.length; i++){
					cont3 += cont2[i]+",";
				}
				
				//����� ��� Ȯ�� �� 0���� ��� ���� �뿩 �ð� �Է� �� ����� �߰�
				int person = getPerson(br_code);
				String cont4 = cont3+br_no+",";
					conn = getConnection();
					
					if(person == 0){
						sql = "update b_rent_over set br_waiter = ? , br_over_date = sysdate where br_code = ?";
					}else{
						sql = "update b_rent_over set br_waiter = ? where br_code = ?";
					}
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, cont4);
					pstmt.setString(2, br_code);
					pstmt.executeUpdate();
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException ex){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
			if(conn != null)try{conn.close();}catch(SQLException ex){}
		}
		
	}
	//------------------------------------------- ����� �Է� �� : 5�� ������ ��----------------------------------------------------------//
	
	//-----------------------------��ٱ��� ��� + �뿩 ��� ���� ���(Ȯ����)------------------------------------------------//
	public List getOver(String br_code){
	      List Extends = null;
	      
	      try {
	         conn = getConnection();//DB����
	         String sql = "select br_deli,br_over_date,br_waiter from b_rent_over where br_code=?";
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, br_code);
	         rs = pstmt.executeQuery();//���� ����
	         
	         if(rs.next()){
	        	Extends = new ArrayList();
	        	Extends.add(rs.getInt("br_deli"));
	        	Extends.add(rs.getTimestamp("br_over_date"));
	        	Extends.add(rs.getString("br_waiter"));
	            
	         }
	      } catch (Exception e) {

	      }finally{
	         if (rs != null) try { rs.close(); } catch(SQLException ex) {}
	         if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
	         if (conn != null) try { conn.close(); } catch(SQLException ex) {}
	      }
	      
	      return Extends;
	   }
	
	//-----------------------------��ٱ��� ��� + �뿩 ��� ���� ���(Ȯ����)------------------------------------------------//
	
	//--------------------------------------- �����ڵ� ������ȣ�� ��ȯ ------------//
	public String getRentNo(String b_code){
		String br_no = "";
		try{
			conn = getConnection();
			String sql = "select br_no from b_rent where br_code = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, b_code);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				br_no = Integer.toString(rs.getInt("br_no"));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException ex){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
			if(conn != null)try{conn.close();}catch(SQLException ex){}
		}
		
		return br_no;
	}
	//--------------------------------------- �����ڵ� ������ȣ�� ��ȯ ------------//
	
	//----------------------------------------------- ��ٱ��� ��� -----------------------------------------------//
	public List getHeadCart(int br_no, String cols) throws Exception {
		List HCList = null;
		
		try{
			conn = getConnection();
			String sql = "select "+cols+" from d_cart where d_no = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, br_no);
			
			rs = pstmt.executeQuery();

			if(rs.next()){
				HCList = new ArrayList();
				
				String result = rs.getString(1); //������ / �����Ǹ� �� �Ѱ����� ���� ����
				String reList[] = result.split(",");
					
				List Extend = new ArrayList();
				for(int i =1; i < reList.length; i++){
					///////////////////////////// ������ ���� //////////////////////
					//������ ��ٱ���
					if(cols.equals("d_rent")){
						//���������ڵ� -> ������ȣ(���ڿ�) ��ȯ
						CartDao cdao = CartDao.getInstance();
						String br_n = cdao.getRentNo(reList[i]);
						
						//���� ���� �̱�
						RentDao rdao = RentDao.getInstance();
						RentDto rdto = rdao.getDetail(br_n);
						HCList.add(rdto);
					//������ �뿩 ���
					}else if(cols.equals("dr_rent")){
						//���� �����ڵ� -> ���� ��ȣ ��ȯ
						CartDao cdao = CartDao.getInstance();
						String br_n2 = cdao.getRentNo(reList[i]);

						//���� ���� �̱�
						RentDao rdao = RentDao.getInstance();
						RentDto rdto = rdao.getDetail(br_n2);
						
						//---------���� �߰� ����(Ȯ����) �̱�
						Extend = cdao.getOver(reList[i]);
						
						int deli = (int)Extend.get(0); //��ۻ���
						Timestamp datee = (Timestamp)Extend.get(1); //���� �뿩 �ð�(��¥)
						
						rdto.setBr_deli(deli); //��ۻ��� �߰�
						rdto.setBr_waiter(cdao.getPerson(reList[i]));//����� ī��Ʈ �߰�
						rdto.setBr_over_date(datee);
						
						HCList.add(rdto);
					}
					
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException ex){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
			if(conn != null)try{conn.close();}catch(SQLException ex){}
		}
		
		return HCList;
		
	}
	
	
	//----------------------------------------------- ��ٱ��� ��� ��-----------------------------------------------//
	
	//----------------------------------------------- ��ٱ��� ���� -------------------------------------------------//
	public void deleteCart(int br_no, String br_code, String col){
		
		String coun = "";
		
		String Ncont = ","; //���Ӱ� ������ �迭 ����
		try{
			conn = getConnection();
			String sql="select "+col+" from d_cart where d_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, br_no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){ //����� �ִٸ�
				coun = rs.getString(1);
				String count[] = coun.split(","); //�迭 �ڸ�
				
				for(int i = 1; i < count.length; i++){
					if(count[i].equals(br_code)){
						continue;
					}else{
						Ncont += count[i]+",";
					}
				}
				
				sql = "update d_cart set "+col+" = ? where d_no= ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, Ncont);
				pstmt.setInt(2, br_no);
				
				pstmt.executeUpdate();
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException ex){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
			if(conn != null)try{conn.close();}catch(SQLException ex){}
		}
		
	}
	
	//----------------------------------------------- ��ٱ��� ���� �� ----------------------------------------------//
	
	
	//----------------------------------------------- ����� ��� ù���� ��� -------------------------------------//
	public String getFirstM(String br_code) throws Exception{
		String firstMan = "";
		try{
			conn = getConnection();
			String sql = "select br_waiter from b_rent_over where br_code = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, br_code);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				String cont = rs.getString(1);
				String co_cont[] = cont.split(",");
				
				firstMan = co_cont[1];
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null)try{rs.close();}catch(SQLException ex){}
			if(pstmt != null)try{pstmt.close();}catch(SQLException ex){}
			if(conn != null)try{conn.close();}catch(SQLException ex){}
		}
		
		
		return firstMan;
	}
	
	
	//----------------------------------------------- ����� ��� ù���� ��� ��-------------------------------------//
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}


























