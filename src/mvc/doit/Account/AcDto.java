package mvc.doit.Account;

import java.sql.Timestamp;

public class AcDto {
	//d_account dto
	private int d_acno;				//로그번호
	private int d_no;				//회원번호
	private String d_acnum;			//계좌번호
	private int d_cash;				//계좌총액
	private Timestamp d_acregdate;	//계좌 등록 날짜
	
	//d_log dto
	private int d_lno;				//로그번호
	private int d_lsender;			//보내는사람
	private int d_lreceiver;		//받는사람
	private int d_lbno;				//로그 책 번호
	private String d_lbcode;		//로그 책 코드
	private int d_ldivision;		//구분
	private int d_ldealtype;		//거래종류(방법)
	private int d_ldealresult;		//거래결과
	private int d_ldealmoney;		//거래금액	
	private int d_ltomoney; 		//거래당시 잔액
	private Timestamp d_ldate;		//거래 시간
	
//그외 사용하는 변수들
	private int d_lsummoney;		//잔액
	private String d_ldateS;		//거래시간 String 형식
	private int listD_lsummoney;	//계좌 List 당시 잔액
	

	
	
	
	
	
	public int getD_cash() {
		return d_cash;
	}
	public void setD_cash(int d_cash) {
		this.d_cash = d_cash;
	}
	public int getD_lbno() {
		return d_lbno;
	}
	public void setD_lbno(int d_lbno) {
		this.d_lbno = d_lbno;
	}
	public String getD_lbcode() {
		return d_lbcode;
	}
	public void setD_lbcode(String d_lbcode) {
		this.d_lbcode = d_lbcode;
	}
	public int getD_ldivision() {
		return d_ldivision;
	}
	public void setD_ldivision(int d_ldivision) {
		this.d_ldivision = d_ldivision;
	}
	public int getListD_lsummoney() {
		return listD_lsummoney;
	}
	public void setListD_lsummoney(int listD_lsummoney) {
		this.listD_lsummoney = listD_lsummoney;
	}
	public String getD_ldateS() {
		return d_ldateS;
	}
	public void setD_ldateS(String d_ldateS) {
		this.d_ldateS = d_ldateS;
	}
	public int getD_acno() {
		return d_acno;
	}
	public void setD_acno(int d_acno) {
		this.d_acno = d_acno;
	}
	public int getD_no() {
		return d_no;
	}
	public void setD_no(int d_no) {
		this.d_no = d_no;
	}
	public String getD_acnum() {
		return d_acnum;
	}
	public void setD_acnum(String d_acnum) {
		this.d_acnum = d_acnum;
	}
	public Timestamp getD_acregdate() {
		return d_acregdate;
	}
	public void setD_acregdate(Timestamp d_acregdate) {
		this.d_acregdate = d_acregdate;
	}
	public int getD_lno() {
		return d_lno;
	}
	public void setD_lno(int d_lno) {
		this.d_lno = d_lno;
	}
	public int getD_lsender() {
		return d_lsender;
	}
	public void setD_lsender(int d_lsender) {
		this.d_lsender = d_lsender;
	}
	public int getD_lreceiver() {
		return d_lreceiver;
	}
	public void setD_lreceiver(int d_lreceiver) {
		this.d_lreceiver = d_lreceiver;
	}

	public int getD_ldealmoney() {
		return d_ldealmoney;
	}
	public void setD_ldealmoney(int d_ldealmoney) {
		this.d_ldealmoney = d_ldealmoney;
	}
	
	public int getD_ltomoney() {
		return d_ltomoney;
	}
	public void setD_ltomoney(int d_ltomoney) {
		this.d_ltomoney = d_ltomoney;
	}
	public int getD_lsummoney() {
		return d_lsummoney;
	}
	public void setD_lsummoney(int d_lsummoney) {
		this.d_lsummoney = d_lsummoney;
	}
	public int getD_ldealtype() {
		return d_ldealtype;
	}
	public void setD_ldealtype(int d_ldealtype) {
		this.d_ldealtype = d_ldealtype;
	}
	public int getD_ldealresult() {
		return d_ldealresult;
	}
	public void setD_ldealresult(int d_ldealresult) {
		this.d_ldealresult = d_ldealresult;
	}
	public Timestamp getD_ldate() {
		return d_ldate;
	}
	public void setD_ldate(Timestamp d_ldate) {
		this.d_ldate = d_ldate;
	}
	
	
	
			
}
