<! This file doesn't do anything at the moment. The body block is embedded into create.gsp at the moment(20th June, 2011) >

<%@ page import="webtax.Motu" %>
<html>
    <body>
        
		<g:form action="upload" method="post" enctype="multipart/form-data">
			<input type="file" name="myFile" />
			<input type="submit" />
		</g:form>
		
		<g:redirectMainPage/>
        
    </body>
</html>
