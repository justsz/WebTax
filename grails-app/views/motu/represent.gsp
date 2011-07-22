<html>
	<head>
		<meta name="layout" content="main" />
		<script type="text/javascript" src="http://www.google.com/jsapi"></script>
	</head>
	
	<body>
	
	<div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="Add MOTUs" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="search" action="search"><g:message code="Search"/></g:link></span>
            <span class="menuButton"><g:link class="represent" action="repForm"><g:message code="Represent"/></g:link></span>
    </div>
    
    
	
	
	
	
	<div style="float: left" class="list">
		<table>
			<g:each in="${reps}" status="j" var="rep">
			<thead>
				<tr>
                    <th>${type}</th>
<%--                    <g:each in="${sites}" var="site">--%>
                    
                    	<th>${sites[j]}</th>
<%--                    </g:each>--%>
				</tr>	
			</thead>
		
		
			<tbody>
			
				<g:each in="${rep.entrySet()}" status="i" var="entry">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">                        
								<td>${entry.key}</td>
								<td>${entry.value}</td>
                        </tr>
        		</g:each>
        	
        	</tbody>
        		
        	</g:each>
        </table>
        
        
	</div>
	<g:each in="${data}" status="i" var="datum">
        	<g:if test="${chart == 'Pie chart'}">
			<gvisualization:pieCoreChart elementId="chart${i}" title="${sites[i]}" width="${600}" height="${600}"  
 			columns="${[['string', 'taxonomicType'], ['number', 'count']]}" data="${datum}" />
 			</g:if>
 			<g:if test="${chart == 'Bar chart'}">
			<gvisualization:barCoreChart elementId="chart${i}" title="${sites[i]}" width="${600}" height="${1300}" 
 			columns="${[['string', 'taxonomicType'], ['number', 'count']]}" data="${datum}" />
 			</g:if>
 			<g:if test="${chart == 'Column chart'}">
			<gvisualization:columnCoreChart elementId="chart${i}" title="${sites[i]}" width="${1000}" height="${500}" 
 			columns="${[['string', 'taxonomicType'], ['number', 'count']]}" data="${datum}" />
 			</g:if>
       		<div  id="chart${i}"></div>
       		

       		<g:remoteLink action="printable" params="${[datum: i, chartType: chart, site:sites[i]]}">Print ${sites[i]}</g:remoteLink>
        </g:each>
	
<%--	<viewLink:thisView/>--%>
	<div id = 'linkToView'>
	<a href ='<g:createLink absolute="true" controller="motu" action="represent" params="${[sites:params.sites, threshold:params.threshold, cutoff:params.cutoff, type:params.type, chart:params.chart]}"/>'>Link to this page</a>
	</div>
	</body>



</html>