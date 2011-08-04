<html> 
<head> 
    <title>Find motu distribution.. something like that</title> 
    <meta name="layout" content="main"/> 
</head> 
<body> 
<g:navigationBar dataset="${dataset}" />
	
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
        
        <label for="minBitScore">Minimum bitscore</label> 
        <g:textField name="minBitScore" /> 
        <br/>
        
        <label for="minBitScoreStep">Minimum bitscore step</label> 
        <g:textField name="minBitScoreStep" /> 
        <br/>
        
        <label for="type">Taxonomic type</label>
        <g:select name="type" " from="${['species', 'genus', 'taxOrder', 'family', 'taxClass', 'phylum']}" />
        
        <label for="chart">Chart type</label>
        <g:select name="chart"  from="${['Pie chart', 'Bar chart', 'Column chart']}" />
        
        <g:hiddenField name="dataset" value="${dataset}" />
        
        <g:submitButton name="search" value="Search"/> 
    </g:form> 
    </formset> 
    
</body> 
</html>