<html>
	<head>
		<meta name="layout" content="main" />
	</head>
	
	<body>        
    <g:navigationBar dataset="${dataset}" />
        
	<g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>

	<div class ="upload">
	
		
		<g:form action="uploadFiles" useToken="true" method="post" enctype="multipart/form-data">
			<label for="dataset">Dataset name</label> 
       		<g:textField name="dataset" value="${dataset}"/> 
        	<br/>
			
			<input type="file" name="myFile" />
			<input type="submit" />
			
		</g:form>
		
	</div>
		

	</body>
		
</html>