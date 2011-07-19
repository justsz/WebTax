<html> 
<head> 
    <title>Search Motus</title> 
    <meta name="layout" content="main"/> 
</head> 
<body> 

	<div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="Add MOTUs" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="search" action="search"><g:message code="Search"/></g:link></span>
            <span class="menuButton"><g:link class="represent" action="repForm"><g:message code="Represent"/></g:link></span>
        </div>

    <formset> 
        <legend>Search for Motus</legend> 
    <g:form action="results"> 
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