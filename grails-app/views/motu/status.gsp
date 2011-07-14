<html>
	<head>
		<meta http-equiv="refresh" content="10">
		<meta name="layout" content="main" /> 
	</head>

	<body>
		
		
		
		<g:if test="${webtax.Job.get(jobId).progress == 100}">
			${response.sendRedirect("list")}
		</g:if>
		<g:else>
			Job status: ${webtax.Job.get(jobId).progress as Integer} %
		</g:else>
	
	</body>

</html>