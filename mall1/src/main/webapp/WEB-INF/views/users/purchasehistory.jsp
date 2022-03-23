<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>users/purchasehistory.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css" >
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
	<nav class="nav justify-content-end" style="--bs-breadcrumb-divider: ''; background-color: #e3f2fd;">
      <ul class="breadcrumb">
         <li class="breadcrumb-item">
            <a class="nav-link" href="${pageContext.request.contextPath }/">Home</a>
         </li>
         <li class="breadcrumb-item ">
			<a class="nav-link" href="${pageContext.request.contextPath}/users/private/info.do">마이페이지</a>
		</li>
		<li class="breadcrumb-item active">
			<a class="nav-link disabled" href="">환불페이지</a>
		</li>
      </ul>
   </nav>
		<h1>구매내역입니다.</h1>
	<table>
		<thead>
			<tr>
				<th>제목</th>
				<th>가격</th>
				<th>리뷰점수</th>
				<th>개봉일</th>
				<th>구매내역 삭제</th>
				<th>환불요청</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tmp" items="${requestScope.list }">
				<tr>
					<td>${tmp.title }</td>
					<td>${tmp.price }</td>
					<td>${tmp.score }</td>
					<td>${tmp.releaseDate }</td>
					<td><a href="javascript:deleteConfirm('${tmp.num }')">삭제</a></td>
					<td><a href="javascript:refund(${tmp.num })">환불요청</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
		<div class="page-ui clearfix">
		<ul>
			<c:if test="${startPageNum ne 1 }">
				<li>
					<a href="purchasehistory.do?pageNum=${startPageNum-1 }">Prev</a>
				</li>
			</c:if>
			<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }">
				<li>
					<c:choose>
						<c:when test="${pageNum eq i }">
							<a class="active" href="purchasehistory.do?pageNum=${i }">${i }</a>
						</c:when>
						<c:otherwise>
							<a href="purchasehistory.do?pageNum=${i }">${i }</a>
						</c:otherwise>
					</c:choose>
				</li>
			</c:forEach>
			<c:if test="${endPageNum lt totalPageCount}">
				<li>
					<a href="purchasehistory.do?pageNum=${endPageNum+1 }">Next</a>
				</li>
			</c:if>
		</ul>
	</div>
		<div style="clear:both;"></div>
		
<script>
	function deleteConfirm(num){
		const isDelete=confirm("삭제하시면 환불받을수 없습니다! \n 그래도 진행하시겠습니까?");
		if(isDelete){
			location.href="delete_list.do?num="+num;
		}
	}
	
	function refund(num) {
		const isRefund=confirm("환불요청 하시겠습니까?");
		if(isRefund){
			alert("환불요청 되었습니다.");
			location.href="check.do?num="+num;
		}
	}
</script>
</body>
</html>