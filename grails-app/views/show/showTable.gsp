
<%@ page import="webtax.Motu" %>
<%@ page import="webtax.BlastHit" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'motu.label', default: 'Motu')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
    <g:navigationBar dataset="${dataset}" />
    <g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
    </g:if>
    
        <div class="body">
            <h1>Showing taxonomy for MOTU: ${motuInstance.seqID}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
            <div class="cellHighlight">       
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="accNum" title="Acc Number" />
                            <g:sortableColumn property="bitScore" title="Bitscore" />
                            <g:sortableColumn property="taxID" title="TaxID" />
                            <g:sortableColumn property="species" title="Species" />
                            <g:sortableColumn property="genus" title="Genus" />
                            <g:sortableColumn property="family" title="Family" />
                            <g:sortableColumn property="taxOrder" title="Order" />                            
                            <g:sortableColumn property="taxClass" title="Class" />
                            <g:sortableColumn property="phylum" title="Phylum" />             
                        </tr>
                    </thead>
                    
                    <tbody>
                    <g:each in="${hits}" status="i" var="hit">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">                                                
                            <td>${fieldValue(bean: hit, field: "accNum")}</td>
                            <td>${fieldValue(bean: hit, field: "bitScore")}</td>
                            
                            <td onmouseover="this.style.cursor='pointer'"
        					onclick="location.href='${taxidURL.replaceFirst(/putTaxidHere/, hit.taxID.toString())}'">${hit.taxID}</td>
                            <td onmouseover="this.style.cursor='pointer'"
        					onclick="location.href='${taxonomyURL.replaceFirst(/putTaxonomyHere/, hit.species.toString())}'">${hit.species}</td>
                            <td onmouseover="this.style.cursor='pointer'"
        					onclick="location.href='${taxonomyURL.replaceFirst(/putTaxonomyHere/, hit.genus.toString())}'">${hit.genus}</td>
        					<td onmouseover="this.style.cursor='pointer'"
        					onclick="location.href='${taxonomyURL.replaceFirst(/putTaxonomyHere/, hit.family.toString())}'">${hit.family}</td>
                            <td onmouseover="this.style.cursor='pointer'"
        					onclick="location.href='${taxonomyURL.replaceFirst(/putTaxonomyHere/, hit.taxOrder.toString())}'">${hit.taxOrder}</td>
                            <td onmouseover="this.style.cursor='pointer'"
        					onclick="location.href='${taxonomyURL.replaceFirst(/putTaxonomyHere/, hit.taxClass.toString())}'">${hit.taxClass}</td>
                            <td onmouseover="this.style.cursor='pointer'"
        					onclick="location.href='${taxonomyURL.replaceFirst(/putTaxonomyHere/, hit.phylum.toString())}'">${hit.phylum}</td>                      
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
       		
        
        <viewLink:thisView />


		<g:form controller="output" action="downloadTableView">
			<g:hiddenField name="separator" value="csv" />
			<g:hiddenField name="motuInstance" value="${motuInstance}" />
			<g:hiddenField name="hits" value="${hits}" />
			<g:submitButton name="download CSV" value="Download CSV"/>
		</g:form>
		
		<g:form controller="output" action="downloadTableView">
			<g:hiddenField name="separator" value="tsv" />
			<g:hiddenField name="motuInstance" value="${motuInstance}" />
			<g:hiddenField name="hits" value="${hits}" />
			<g:submitButton name="download TSV" value="Download TSV"/>
		</g:form>
    </body>
    
</html>
