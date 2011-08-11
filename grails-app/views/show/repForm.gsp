<html> 
<head> 
    <title>Find motu distribution.. something like that</title> 
    <meta name="layout" content="main"/> 
</head> 
<body> 
<g:navigationBarWithChangeDataset dataset="${dataset}" />
	<g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
    </g:if>
	
    <formset> 
        <legend>Get distributions</legend> 
    <g:form method="get" action="represent"> 
        <label for="sites">Sites</label> 
        <g:textField name="sites" value="${params.sites}"/> 
        <br/>
        
        <label for="threshold">Threshold</label> 
        <g:textField name="threshold" value="${params.threshold}" /> 
        <br/>
        
        <label for="keyPhrase">Filter by phrase</label> 
        <g:textField name="keyPhrase" value="${params.keyPhrase}" /> 
        <br/>
        
        <label for="cutoff">Cutoff</label> 
        <g:textField name="cutoff" value="${params.cutoff}" /> 
        <br/>
        
        <label for="minBitScore">Minimum bitscore</label> 
        <g:textField name="minBitScore" value="${params.minBitScore}" /> 
        <br/>
        
        <label for="minBitScoreStep">Minimum bitscore step</label> 
        <g:textField name="minBitScoreStep" value="${params.minBitScoreStep}"/> 
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