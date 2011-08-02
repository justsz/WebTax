<html>
	<head>
		<meta name="layout" content="main" />
	</head>
	
	<body>
	
	<div class="nav">
        	<span class="menuButton"><g:link class="home" action="index" params="[dataset: dataset]"><g:message code="Home"/></g:link></span>
            <span class="menuButton"><g:link class="create" action="create" params="[dataset: dataset]"><g:message code="Add MOTUs" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="search" action="search" params="[dataset: dataset]"><g:message code="Search"/></g:link></span>
            <span class="menuButton"><g:link class="represent" action="repForm" params="[dataset: dataset]"><g:message code="Represent"/></g:link></span>
        </div>

	<div class ="upload">
		
		<g:form action="upload" useToken="true" method="post" enctype="multipart/form-data">
			<label for="datasetName">Dataset name</label> 
       		<g:textField name="datasetName" /> 
        	<br/>
			
			<input type="file" name="myFile" />
			<input type="submit" />
			
		</g:form>
		
	</div>
		

	</body>
		
</html>