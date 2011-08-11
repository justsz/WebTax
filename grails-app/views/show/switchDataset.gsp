<html>
	<head>
		<meta name="layout" content="main" />
	</head>
	
	<body>
	<g:navigationBar dataset="${dataset}" />
	<g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
    </g:if>

        
        <g:form action="executeSwitch" controller="show">
        	<label for="newDataset">New dataset:</label> 
       		<g:textField name="newDataset"/> 
       		<g:hiddenField name="prevAction" value="${prevAction}" />
        	<g:hiddenField name="prevController" value="${prevController}" />   
        	<g:submitButton name="switch" value="Switch"/> 
        </g:form>
	
	</body>



</html>