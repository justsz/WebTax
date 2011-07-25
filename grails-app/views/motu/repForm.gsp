<html> 
<head> 
    <title>Find motu distribution.. something like that</title> 
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
        <legend>Get distributions</legend> 
    <g:form action="represent"> 
        <label for="sites">Sites</label> 
        <g:textField name="sites" /> 
        <br/>
        
        <label for="threshold">Threshold</label> 
        <g:textField name="threshold" /> 
        <br/>
        
        <label for="keyPhrase">Filter by phrase</label> 
        <g:textField name="keyPhrase" /> 
        <br/>
        
        <label for="cutoff">Cutoff</label> 
        <g:textField name="cutoff" /> 
        <br/>
        
        <label for="type">Taxonomic type</label>
        <g:select name="type" " from="${['species', 'genus', 'taxOrder', 'family', 'taxClass', 'phylum']}" />
        
        <label for="chart">Chart type</label>
        <g:select name="chart"  from="${['Pie chart', 'Bar chart', 'Column chart']}" />
        
        <g:submitButton name="search" value="Search"/> 
    </g:form> 
    </formset> 
    
</body> 
</html>