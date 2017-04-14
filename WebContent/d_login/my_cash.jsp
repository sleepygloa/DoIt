<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


	<%--------------- header include --------------%>
	<jsp:include page="/header.jsp"></jsp:include>
	
	<%--------------- ���̵� �޴� include --------------%>
	<jsp:include page="/d_login/side_my.jsp"></jsp:include>
	
	
	
		<%-- �������� --%>
			<%-- �������� --%>
			<article class="my_cont_wrap">
				<p class="my_title">
					���� ������
				</p>
				<p class="my_sub_title">
					���������������� ȸ������ ��ȸ, ����, Ż�� �� �� ������ ���ų����� Ȯ���Ͻ� �� �ֽ��ϴ�.
					<span>ȸ������ ���� : ȸ���������� ���̵�� ���� �Ͻ� �� �����ϴ�.</span>
					<span>ȸ�� Ż�� : ȸ��Ż�� ���Ŀ��� �α��� �Ͻ� �� �����ϴ�.</span>
				</p>
				

				<%-- ȸ������ ��ȸ ���̺� ��� --%>
				<p class="L_title">
					<a>�� Cash ���� �� ����</a>
				</p>
				<p class="my_sub_title" style="border:none; padding:0px; margin-top:10px;">
					<span>* DOIT�� ��� ����(������,�Ǹ�,����,��ü��..)�� ĳ���� ���Ͽ� �̷�����ϴ�. ���ڶ��� �ʰ� �������ּ���.</span>
				</p>

				
				<%-- �������� --%>
				
				<%-- ���� ������ �� ������ �ִ� Cash --%>
				<table class="cash_tab" cellspacing="0">
					<colgroup>
						<col width="25%"><col width="25%">
						<col width="25%"><col width="25%">
					</colgroup>
					<thead>
						<tr>
							<th>���¹�ȣ</th>
							<th>��밡�� ��ĳ��</th>
							<th>�������� ��ĳ��</th>
							<th>�� ���� ��ĳ��</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${adto.d_acnum}</td>
							<td>? ��</td>
							<td>? ��</td>
							<td>${adto.d_cash}��</td>
						</tr>	
					</tbody>
					
				</table>
				
				<p style="margin:50px 0px 20px 0px; font-size:0.9em; font-weight:bold">* ���� �� Cash ��� �����Դϴ�.</p>
				
				<div Style="width:100%; height:600px; margin-bottom:150px; overflow:scroll;">
					<table cellspacing="0" class="cash_tab2">
						<colgroup>
							<col width="15%"><col width="5%"><col width="30%">
							<col width="10%"><col width="10%">
						</colgroup>
						<thead>
							<tr>
								<th>�������</th>
								<th>����</th>
								<th>�������</th>
								<th>�޴»��</th>
								<th>�󼼳���(��� ����)</th>
								<th>�ŷ��ݾ�</th>
								<th>�ŷ� �� �ܾ�</th>
							</tr>
						</thead>
						<tbody>
						
						<c:forEach var="account"  items="${accountList}">
							<tr>
								<td>${account.d_ldate}</td>
								<td>${account.d_ldivision}</td>
								<td>${account.d_lsender}</td>
								<td>${account.d_lreceiver}</td>
								<td>
									<c:if test="${account.d_ldealtype == 1}"> �Ա� </c:if>
									<c:if test="${account.d_ldealtype == 2}"> ��� </c:if>
									<c:if test="${account.d_ldealtype == 3}"> ������ü(����) </c:if>
									<c:if test="${account.d_ldealtype == 4}"> ������ü(����) </c:if>
								</td>
								<td>${account.d_ldealmoney}</td>
							</tr>	
						</c:forEach>
						</tbody>
					</table>
				</div>
			</article>
	
			
		
	<%--------------- footer include --------------%>
	<jsp:include page="/footer.jsp"></jsp:include>
	
	
	
	
	
	