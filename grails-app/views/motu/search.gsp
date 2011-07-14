<html> 
<head> 
    <title>Search Motus</title> 
    <meta name="layout" content="main"/> 
</head> 
<body> 
    <formset> 
        <legend>Search for Motus</legend> 
    <g:form action="results"> 
        <label for="site">Site</label> 
        <g:textField name="site" /> 
        
        <label for="cutoff">Cutoff</label> 
        <g:textField name="cutoff" /> 
        
        <g:submitButton name="search" value="Search"/> 
    </g:form> 
    </formset> 
</body> 
</html>