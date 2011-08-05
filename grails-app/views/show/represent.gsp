<html>
	<head>
		<meta name="layout" content="main" />
		<script type="text/javascript" src="http://www.google.com/jsapi"></script>
	</head>
	
	<body>
	<g:navigationBar dataset="${dataset}" />
    
    
	
	
	
	
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
       		

       		<g:remoteLink controller="output" action="printable" params="${[datum: datum, chartType: chart, site:sites[i], dataset:dataset]}">PNG version of ${sites[i]}</g:remoteLink>
        </g:each>
	
<%--	<viewLink:thisView/>--%>


	<div id = 'linkToView'>
		Link to this page:
		<input onclick="this.select(); pageTracker._trackEvent('new-done-click','short-click');" readonly="readonly" class="readonly" 
		value="<g:createLink absolute="true" controller="show" action="represent" params="${[sites:params.sites, threshold:params.threshold, cutoff:params.cutoff, type:params.type, chart:params.chart, keyPhrase:params.keyPhrase, dataset:params.dataset]}"/>"/>
	</div>
	<g:form controller="output" action="downloadRepresentView">
			<g:hiddenField name="separator" value="csv" />
			<g:hiddenField name="sites" value="${sites.toString()}" />
			<g:hiddenField name="data" value="${data}" />
			<g:hiddenField name="type" value="${type}" />
			<g:submitButton name="download CSV" value="Download CSV"/>
	</g:form>
	
	<g:form controller="output" action="downloadRepresentView">
			<g:hiddenField name="separator" value="tsv" />
			<g:hiddenField name="sites" value="${sites.toString()}" />
			<g:hiddenField name="data" value="${data}" />
			<g:hiddenField name="type" value="${type}" />
			<g:submitButton name="download TSV" value="Download TSV"/>
	</g:form>
	
	
	
	</body>



</html>