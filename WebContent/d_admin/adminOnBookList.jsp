<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

		
		
		
		<!-- -------등록된 책이 없을때----------------------------- -->
		<c:if test="${count==0}">
			<table class="">	
				<tr>
					<td class="Admin_txtCen" colspan="3">판매 신청 리스트가 없습니다.</td>
				</tr>
			</table>
		</c:if>

		<c:if test="${count > 0}">
		<table class="pan_list1">	
			<c:forEach var="article"  items="${articleList}">	
					<tr>
						
						<%-- 번호 --%>
						<td> 
							<span>
								<c:out value="${number}"/>
								<c:set var="number" value="${number - 1}"/>
							</span>
						</td>
						
						<%-- 이미지 --%>
						<td>
			 				<c:if test="${article.d_bpic == null}">
								<p>사진 정보가 없습니다.</p>
							</c:if>
							<c:if test="${article.d_bpic != null}">
								<img src="/DoIt/d_bpic/${article.d_bpic}"  />
							</c:if>
						</td>
						
						<%-- 내용 --%>
						<td>
							<ul class="text_left list_con3">
								<li>회원 아이디 : ${article.d_id}</li>
								<li>책 이름 : ${article.d_bname} </li>
								<li>저자 : ${article.d_bauthor}  | 출판사 : ${article.d_bpublisher} </li>
								<li>판매신청 날짜 : ${article.d_bdate} </li>
				 				<li><button onclick="window.location='/DoIt/d_admin/adminOnBookList_detail.do?d_bcode=${article.d_bcode}'">상세보기</button></li>
							 </ul>
						</td>			
					

					
						<td>
							<c:if test="${article.d_sfinish == 2}">
								<p>등록완료</p>
							</c:if>
							<c:if test="${article.d_sfinish != 2}">
								<p>${article.d_sfinish}검수대기중</p>
							</c:if>
						</td>
					
					
				</tr>	

		
		</c:forEach>
		

	</table>
	</c:if>	
	
		<%-- 페이지네이션 --%>
		<p class="num_tag">		
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
					<a href="/DoIt/d_manage/manPart4.do?pageNum=${startPage - 10 }&sdfe=ssss">[ 이전 ]</a>
				</c:if>
			
				<c:forEach var="i" begin="${startPage}" end="${endPage}">
					<a href="/DoIt/d_manage/manPart4.do?pageNum=${i}&sdfe=ssss">[ ${i} ]</a>
				</c:forEach>
			
				<c:if test="${endPage < pageCount}">
					<a href="/DoIt/d_manage/manPart4.do?pageNum=${startPage + 10}&sdfe=ssss">[ 다음 ]</a>
				</c:if>
			</c:if>
		</p>
		