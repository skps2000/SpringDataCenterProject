<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/lib/jstl.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>

<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css" />
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>

<table data-role="table" id="table-custom-2" data-mode="columntoggle" class="ui-body-d ui-shadow table-stripe ui-responsive" data-column-btn-theme="b" data-column-btn-text="Columns to display..." data-column-popup-theme="a">
         <thead>
           <tr class="ui-bar-d">
             <th data-priority="2">Rank</th>
             <th>Title</th>
<!--              <th data-priority="3">Year</th> -->
<!--              <th data-priority="1"><abbr title="Rotten Tomato Rating">Rating</abbr></th> -->
<!--              <th data-priority="5">Reviews</th> -->
           </tr>
         </thead>
         <tbody>
		<c:forEach var="item" items="${resultList}" varStatus="status">
           <tr>
             <th>${status.count}</th>
             <td><img src="${item}" data-rel="external"></td>
<!--              <td>1941</td> -->
<!--              <td>100%</td> -->
<!--              <td>74</td> -->
           </tr>
		</c:forEach>
         </tbody>
       </table>
       



</body>
</html>