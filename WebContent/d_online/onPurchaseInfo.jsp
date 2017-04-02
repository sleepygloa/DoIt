<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- header import -->
<jsp:include page="/header.jsp" flush="false" /> 

<%--------------- 사이드 메뉴 include --------------%>
<%-- <jsp:include page="/d_online/onBookSide.jsp"></jsp:include> --%>


<!-- 1. 선택된 책에 대한 상세 내용이 이 페이지에서 해결된다. -->
<form action ="/DoIt/d_online/user_onBuyBook.do?d_bcode=${article.d_bcode}" method="post">
	<table border="1 solid black" width="1000px" class="d-center">
		<tr>
			<td colspan="2" width="1000px" height="80px">
				<span class="big-font35 d-bold d-left20">
					&nbsp;&nbsp;${article.d_bname}
				</span>
			</td>
		</tr>		
		<tr>
			<td width="300px" rowspan="2">
				<img src="\DoIt\d_bpic/${article.d_bpic}" width="300px" />
			</td>
			<td class="d-left">
				작가:${article.d_bauthor}<br />
				출판사:${article.d_bpublisher}<br />
				장르:${article.d_bgenre}<br />
				종류:${article.d_bgenre2}<br />
				국내외:${article.d_blocation}<br />
				책등급:${article.d_bgrade}<br />
			</td>

		</tr>
		<tr>
			<td>
				새상품 정보 : (s등급 책 보여주기)
				정가 : ${article.d_bvalue}<br />
				판매가 : ${article.d_bsellvalue} (정가대비 : ${valueToSellvaluePercent}% 할인)<br /> 
				마일리지 : 결제 Db의 결제 금액의 10% 적립 <br />
				<br />
				
				
				<table class="d-center" border="1px solid black" width="80%">
					<tr>
						<td width="50%">DoIt 중고(${count})</td>
						<td width="50%">직거래 중고</td>
					</tr>
					<tr>
						<td>${MinD_bsellvalue}원</td>
						<td>성준이네 연동</td>
					</tr>
				</table>
				<br />


			</td>
		</tr>
		<tr>
			<td class="d-center" colspan="2">
				<input class="btn btn-default d-w-30" type="button" value="장바구니" />
				<input class="btn btn-default d-w-30" type="submit" value="구매하기" />
				<input class="btn btn-default d-w-30" type="button" value="취소"
					onclick="window.location='/DoIt/d_online/onSellBook.do'" /> 
			</td>
		</tr>

	</table>



</form>

<div class="d-space20"></div>
			
<!-- 2. 이 페이지의 책과 관련된 책의 list가 정렬된다. -->
<table style="width:1000px" border="1 solid black" class="d-center">	
		<tr>
			<td colspan="2" width="600px">상품정보</td>
			<td class="d-center">등급</td>
			<td class="d-center">배송비</td>
			<td class="d-center">판매자</td>
		</tr>	
<!-- -------등록된 책이 없을 때----------------------------- -->		
<c:if test="${count==0}">
	<tr>
		<td colspan="6">
			<div class="d-center">
				보유한 책이 없습니다! <span class="code">도서관서비스</span>와 <span class="code">직접거래서비스</span>를 이용해주세요!!
			</div>
		</td>
	</tr>
</c:if>
<!-- -------등록된 책이 있을 때----------------------------- -->
<c:if test="${count!=0}">
	<c:forEach var="articleList" begin="0" end="${articleList.size()}" step="1" items="${articleList}">	
		<tr>
			<td width="100px"  height="160px">
 				<c:if test="${articleList.getD_bpic() == null}">
					<img src="/DoIt/images/ma_img.jpg"  width="100px" height="150px" class="thumbnail" />
				</c:if>
				<c:if test="${articleList.getD_bpic() != null}">
					<img src=
					"\DoIt\d_bpic/${articleList.getD_bpic()}" width="100px" height="150px"  class="thumbnail" />
				</c:if>
			</td>
			<td width="600px" class="d-w80 ">
<!--  책제목을 눌렀을 때 상세 페이지로 이동 ------------------------------------------------------------------------------ -->
 				
 				<span class="big-font25 d-bold d-l-padding10">
 				<a href="/DoIt/d_online/onPurchaseInfo.do?d_bcode=${articleList.getD_bcode()}">
 					<c:if test="${article.d_bcode == articleList.getD_bcode()}"><p class="red">
 						${articleList.getD_bname()}
 					</p><span class="code">현재보시는상품</span></c:if>
 					<c:if test="${article.d_bcode != articleList.getD_bcode()}">${articleList.getD_bname()}</c:if>
 				</a>	
 				</span><br /><br />
 				<small>
				판매가 : <span class="code">${articleList.getD_bsellvalue()}</span> 원 <br />
				출고기간 : 주문 접수 후 3일 이내에 출고<br /> (토/일 공휴일 제외)
				
				</small> 
			</td>
			<td width="100px">${articleList.getD_bgrade()}</td>
			<td width="100px">무료</td>
			<td width="100px">${articleList.getD_id()}</td>
		</tr>
	 
	</c:forEach>

</table>
</c:if>	


