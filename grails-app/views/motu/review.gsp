<html>
	<head>
		<meta name="layout" content="main" />
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
		 <g:form action='deleteFiles'>
		<table>
			
			<thead>
				<tr>
                    <th>Files to annotate</th>
                    <th>Delete</th>

				</tr>	
			</thead>
		
		
			<tbody>
			
				<g:each in="${files}" status="i" var="entry">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">                        
								<td>${entry}</td>
								<td><g:checkBox name="${entry}" /></td>
								
                        </tr>
        		</g:each>
        	
        	</tbody>
        		
        	
        </table>
       
        
        <g:hiddenField name="destination" value="${destination}" />
        <g:hiddenField name="datasetName" value="${datasetName}" />
        <g:submitButton name="delete" value="Delete"/> 
        </g:form>
        
        <g:form action='create2'>
        	<g:hiddenField name="datasetName" value="${datasetName}" />
        	<g:submitButton name="addMore" value="Add more files"/> 
        </g:form>
        
        <g:form action='blast'>
        	<label for="database">BLAST databse</label>
        	<g:select name="database" from="${dbs}" />
        	<g:hiddenField name="datasetName" value="${datasetName}" />
        	<g:hiddenField name="destination" value="${destination}" />        	
        	<g:submitButton name="blast" value="Blast files!"/> 
        </g:form>
        
        
	</div>	
	
	</body>



</html>