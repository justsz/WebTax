<html> 
<head> 
    <title>Search Motus</title> 
    <meta name="layout" content="main"/> 
</head> 
<body> 
<g:navigationBar dataset="${dataset}" />

    <formset> 
        <legend>Search for Motus</legend> 
    <g:form method="get" action="results"> 
    	<g:hiddenField name="dataset" value="${dataset}" />
        <label for="site">Site</label> 
        <g:textField name="site" /> 
        <br/>
        <label for="cutoff">Cutoff</label> 
        <g:textField name="cutoff" /> 
        
        <g:submitButton name="search" value="Search"/> 
    </g:form> 
    </formset> 
    
</body> 
</html>