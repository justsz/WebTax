<html>
	<head>
		<meta http-equiv="refresh" content="10">
		<meta name="layout" content="main" />
		<title>
			${webtax.Job.get(jobId).progress as Integer} %
		</title> 
	</head>
	
	

	<body>
	
		<div class="nav">
        	<span class="menuButton"><g:link class="home" action="index" params="[dataset: dataset]"><g:message code="Home"/></g:link></span>
            <span class="menuButton"><g:link class="create" action="create" params="[dataset: dataset]"><g:message code="Add MOTUs" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="search" action="search" params="[dataset: dataset]"><g:message code="Search"/></g:link></span>
            <span class="menuButton"><g:link class="represent" action="repForm" params="[dataset: dataset]"><g:message code="Represent"/></g:link></span>
        </div>	
		
		
		<g:if test="${webtax.Job.get(jobId).progress == 100}">
<%--			${response.sendRedirect("list")}--%>
			Upload complete. Link to dataset: 
			<input onclick="this.select(); pageTracker._trackEvent('new-done-click','short-click');" readonly="readonly" class="readonly" 
		value="<g:createLink absolute="true" controller="motu" action="list" params="${[dataset: dataset]}"/>"/>
		</g:if>
		<g:else>
			Job status: ${webtax.Job.get(jobId).progress as Integer} %
		</g:else>
	<div id = 'linkToView'>
	<a href ='<g:createLink absolute="true" controller="motu" action="status" params="${[jobId:jobId]}"/>'>Link to this page</a>
	</div>
	</body>

</html>