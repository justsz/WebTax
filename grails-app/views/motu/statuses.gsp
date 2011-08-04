<html>
	<head>
		<meta name="layout" content="main" />
		<meta http-equiv="refresh" content="10">
		<script type="text/javascript" src="http://www.google.com/jsapi"></script>
	</head>
	
	<body>
	
	<div class="nav">
        	<span class="menuButton"><g:link class="home" action="index" params="[dataset: dataset]"><g:message code="Home"/></g:link></span>
            <span class="menuButton"><g:link class="create" action="create" params="[dataset: dataset]"><g:message code="Add MOTUs" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="search" action="search" params="[dataset: dataset]"><g:message code="Search"/></g:link></span>
            <span class="menuButton"><g:link class="represent" action="repForm" params="[dataset: dataset]"><g:message code="Represent"/></g:link></span>
        </div>
    
    
	
	
	
	
	<div style="float: left" class="list">
		 
		<table>
			
			<thead>
				<tr>
                    <th>Files</th>
                    <th>Progress</th>

				</tr>	
			</thead>
		
		
			<tbody>
			
				<g:each in="${jobIds}" status="i" var="entry">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">                        
								<td>${webtax.Job.get(entry).name}</td>
								<td>
									<g:if test="${ webtax.Job.get(entry).progress == 0 }" >
									Pending
									</g:if>
									<g:elseif test="${ webtax.Job.get(entry).progress == 100 }">
									Complete
									</g:elseif>
									<g:else>
									${webtax.Job.get(entry).progress as Integer} %
									</g:else>
								
								</td>
								
                        </tr>
        		</g:each>
        	
        	</tbody>
        		
        	
        </table>
        
        
	</div>	
	
	<label for="linkToList">Link to dataset</label>
	<input id="linkToList" onclick="this.select(); pageTracker._trackEvent('new-done-click','short-click');" readonly="readonly" class="readonly" 
		value="<g:createLink absolute="true" controller="motu" action="list" params="${[dataset: dataset]}"/>"/>
	
	</body>



</html>