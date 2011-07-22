<html>
	<head>
		<script type="text/javascript" src="http://www.google.com/jsapi"></script>
	</head>
<%--	<% println datum[8][3] %>--%>
	
	
	<body>
<%--		<gvisualization:imagePieChart elementId="chart" labels="value" title="${site}"    width="${750}" height="${400}"--%>
<%-- 			columns="${[['string', 'taxonomicType'], ['number', 'count']]}" data="${session.data[datum]}" />--%>
<%-- 			--%>
<%-- 			<div id="chart"></div>--%>
 			
 			<g:if test="${chartType == 'Pie chart'}">
			<gvisualization:imagePieChart elementId="chart" title="${site}" width="${750}" height="${400}"  
 			columns="${[['string', 'taxonomicType'], ['number', 'count']]}" data="${session.data[datum]}" />
 			</g:if>
 			<g:if test="${chartType == 'Bar chart'}">
			<gvisualization:imageBarChart elementId="chart" title="${site}" width="${600}" height="${1000}" 
 			columns="${[['string', 'taxonomicType'], ['number', 'count']]}" data="${session.data[datum]}" />
 			</g:if>
 			<g:if test="${chartType == 'Column chart'}">
			<gvisualization:imageBarChart elementId="chart" isVertical="${true }" title="${site}" width="${1000}" height="${500}" showCategoryLabels="${false}"  
 			columns="${[['string', 'taxonomicType'], ['number', 'count']]}" data="${session.data[datum]}" />
 			</g:if>
       		<div style="float: left" id="chart"></div>
 			
	<div id = 'linkToView'>
	<a href ='<g:createLink absolute="true" controller="motu" action="printable" params="${[datum:datum, site:site, chartType:chartType]}"/>'>Link to this page</a>
	</div>
	</body>
</html>