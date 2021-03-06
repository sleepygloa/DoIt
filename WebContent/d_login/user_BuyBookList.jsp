<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" type="text/css"  href="/DoIt/css/online_admin.css?">
<!-- header import -->
<%@include file="../header.jsp" %>
<%--------------- 사이드 메뉴 include --------------%>
<jsp:include page="/d_login/side_my.jsp" />
	<article class="my_cont_wrap">
		<%-- <p id="AdminList_title"> ${id} 님의 구매리스트</p>
		<p id="AdminList_line"></p>
		--%>
				<%-- 본문시작 --%>
			<%-- 본문내용 --%>
			<article class="my_cont_wrap">
				<p class="my_title">
					마이 페이지
				</p>
				<p class="my_sub_title">
					마이페이지에서는 회원정보 조회, 수정, 탈퇴를 할 수 있으며 구매내역을 확인하실 수 있습니다.
					<span>회원정보 수정 : 회원정보에서 아이디는 수정 하실 수 없습니다.</span>
					<span>회원 탈퇴 : 회원탈퇴 이후에는 로그인 하실 수 없습니다.</span>
				</p>
				


				<%-- 회원정보 조회 테이블 상단 --%>
				<p class="L_title">
					<a>나의 대여 / 구매 목록</a>
				</p>
				<p class="my_sub_title" style="border:none; padding:0px; margin-top:10px;">
					<span>* 대여 / 구매 목록 중 원하시는 버튼을 눌러주세요.</span>
				</p>
				
				<ul class="my_list_tab">
					<li><a href="/DoIt/d_login/myList.do?cols=dr_rent">나의 대여목록</a></li>
					<li><a href="/DoIt/d_login/user_BuyBookList.do">나의 구매목록</a></li>
					<li><a href="#">나의 입찰내용</a></li>
				</ul>
				
				
		<!-- -------등록된 책이 없을때----------------------------- -->
		<c:if test="${count==0}">
			<table id="AdminList_Wrap">	
				<tr class="AdminList_tr">
					<td class="AdminList_num Admin_txtCen"><p class="Admin_RightLine">번호</p></td>
					<td class="AdminList_pic Admin_txtCen"><p class="Admin_RightLine">책 사진</p></td>
					<td class="AdminList_con Admin_txtCen">책 내용</td>
				</tr>
				<tr>
					<td class="Admin_txtCen" colspan="3"><br/>구매리스트가 없습니다.</td>
				</tr>
			</table>
		</c:if>

		<c:if test="${count > 0}">
		
		<table id="AdminList_Wrap">	
			<tr class="AdminList_tr">
				<td class="AdminList_num Admin_txtCen"><p class="Admin_RightLine">번호</p></td>
				<td class="AdminList_pic Admin_txtCen"><p class="Admin_RightLine">책 사진</p></td>
				<td class="AdminList_con Admin_txtCen"><p class="Admin_RightLine">책 내용</p></td>
				<td class="AdminList_Ins Admin_txtCen">취소</td>
				
			</tr>
			
			
	
			<c:forEach var="article"  items="${articleList}">	
				<form action="/DoIt/d_login/user_BuyBook_Cancel.do" method="post">
					<tr class="AdminList_tr">
						<td class="AdminList_num Admin_txtCen" > 
							<span class="big-font20">
								<c:out value="${number}"/>
								<c:set var="number" value="${number - 1}"/>
							</span>
						</td>
						<td class="AdminList_pic Admin_txtCen">
			 				<c:if test="${article.d_bpic == null}">
								<p>사진 정보가 없습니다.</p>
							</c:if>
							<c:if test="${article.d_bpic != null}">
								<img src="/DoIt/d_bpic/${article.d_bpic}" height="190px;" />
							</c:if>
						</td>
						<td class="AdminList_con">
							<div class="AdminList_con_list small-font8">
								<input type="hidden" name="d_bno" value="${article.d_bno}">
				 				책 이름 : ${article.d_bname} <br/>
				 				저자 : ${article.d_bauthor} <br/>
				 				출판사 : ${article.d_bpublisher} <br/>
				 				구매 날짜 : ${article.d_bdeldate} <br /><br />
				 				<input type="hidden" name="d_bcode" value="${article.d_bcode}">
				 				<input type="button" value="상세보기" class="AdminList_btn" onclick="window.location='/DoIt/d_login/user_BuyBookList_detail.do?d_bno=${article.d_bno}&d_bcode=${article.d_bcode}'"/>
				 				
							 </div>
						</td>
						<td class="AdminList_Ins Admin_txtCen"> 
							<c:if test="${article.d_bdelibery == 20}">
								<input type="submit" value="취소신청" class="AdminList_btn"/>
							</c:if>
							<c:if test="${article.d_bdelibery == 21}">
								<p>배송준비중</p>
							</c:if>
							<c:if test="${article.d_bdelibery == 22}">
								<p>배송 중</p>
							</c:if>
							<c:if test="${article.d_bdelibery == 23}">
								<p>배송완료</p>
							</c:if>
						</td>
					</tr>	
	
			
			 	</form>
			</c:forEach>
			
		</table>
		</c:if>	
		
		<div id="AdminList_PageNum">		
			<c:if test="${count > 0}">
				<c:set var="pageCount" value="${count / pageSize + ( count % pageSize == 0 ? 0 : 1)}"/>
				<c:set var="pageBlock" value="${10}"/>
				<fmt:parseNumber var="result" value="${currentPage / 10}" integerOnly="true" />
				<c:set var="startPage" value="${result * 10 + 1}" />
				<c:set var="endPage" value="${startPage + pageBlock-1}"/>
				<c:if test="${endPage > pageCount}">
					<c:set var="endPage" value="${pageCount}"/>
				</c:if> 
			          
			    
				<c:if test="${startPage > 10}">
					<a href="/DoIt/d_login/user_BuyBookList.do?pageNum=${startPage - 10 }">[이전]</a>
				</c:if>
			
				<c:forEach var="i" begin="${startPage}" end="${endPage}">
					<a href="/DoIt/d_login/user_BuyBookList.do?pageNum=${i}">[${i}]</a>
				</c:forEach>
			
				<c:if test="${endPage < pageCount}">
					<a href="/DoIt/d_login/user_BuyBookList.do?pageNum=${startPage + 10}">[다음]</a>
				</c:if>
			</c:if>
		</div>
		
	</article>



		
<!-- footer import -->
<%@include file="../footer.jsp" %>
		
		