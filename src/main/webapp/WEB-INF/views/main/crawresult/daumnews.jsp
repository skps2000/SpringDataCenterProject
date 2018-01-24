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
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>

<script type="text/javascript">

	var paramObj = new Object();
	var dataObj = new Object();
	var dataArr = [];
	
	var dataString;
	var dataInt;
	
	var splitString;
	var splitInt;
	
$(document).ready(function(){
	
 	dataString = '${tagsListString}';
	dataString = dataString.replace(/ /g,"");
	dataString = dataString.replace('[','');
	dataString = dataString.replace(']','');
	
 	dataInt = '${tagsListint}';
	dataInt = dataInt.replace(/ /g,"");
	dataInt = dataInt.replace('[','');
	dataInt = dataInt.replace(']','');
	
	splitString = dataString.split(',');
 	splitInt = dataInt.split(',');
	
	for (var i = 0; i < splitString.length; i++) {
		var obj = new Object();
		obj.name = splitString[i];
		obj.y = splitInt[i] *= 1;
		dataArr[i] = obj;
	}

		initHiChart(dataArr);
	
});


function initHiChart(dataArr){

			Highcharts.chart('container', {
			    chart: {
			        plotBackgroundColor: null,
			        plotBorderWidth: null,
			        plotShadow: false,
			        type: 'pie'
			    },
			    title: {
			        text: ''
			    },
			    tooltip: {
			        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
			    },
			    plotOptions: {
			        pie: {
			            allowPointSelect: true,
			            cursor: 'pointer',
			            dataLabels: {
			                enabled: true,
			                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
			                style: {
			                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
			                }
			            }
			        }
			    },
			    series: [{
			        name: 'Brands',
			        colorByPoint: true,
			        data: dataArr
			    }]
			});
		}

	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>
<div id="container" style="min-width: 95%; height: 400px; margin: 0 auto"></div>

<table data-role="table" id="table-custom-2" data-mode="columntoggle" class="ui-body-d ui-shadow table-stripe ui-responsive" data-column-btn-theme="b" data-column-btn-text="Columns to display..." data-column-popup-theme="a">
         <thead>
           <tr class="ui-bar-d">
             <th data-priority="2"></th>
             <th>Title</th>
           </tr>
         </thead>
         <tbody>
      	<c:forEach var="item" items="${tagsListString}" varStatus="status">
           <tr>
             <td>${item}</td>
             <th>${tagsListint[status.count-1]}</th>
           </tr>
		</c:forEach>
         </tbody>
       </table>
       
<table data-role="table" id="table-custom-2" data-mode="columntoggle" class="ui-body-d ui-shadow table-stripe ui-responsive" data-column-btn-theme="b" data-column-btn-text="Columns to display..." data-column-popup-theme="a">
         <thead>
           <tr class="ui-bar-d">
             <th data-priority="2"></th>
             <th>Title</th>
           </tr>
         </thead>
         <tbody>
		<c:forEach var="item" items="${resultList}" varStatus="status">
           <tr>
             <th></th>
             <td>${item}</td>
           </tr>
		</c:forEach>
         </tbody>
       </table>

</body>
</html>