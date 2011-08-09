
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
    
        <div class="body">
            <h1>Showing table for MOTU: ${motuInstance.seqID} from site: ${motuInstance.site}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
            <div class="list">       
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="accNum" title="Acc Number" />
                            <g:sortableColumn property="bitScore" title="Bitscore" />
                            <g:sortableColumn property="species" title="Species" />
                            <g:sortableColumn property="genus" title="Genus" />
                            <g:sortableColumn property="taxOrder" title="Order" />
                            <g:sortableColumn property="family" title="Family" />
                            <g:sortableColumn property="taxClass" title="Class" />
                            <g:sortableColumn property="phylum" title="Phylum" />             
                        </tr>
                    </thead>
                    
                    <tbody>
                    <g:each in="${hits}" status="i" var="hit">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">                                                
                            <td>${fieldValue(bean: hit, field: "accNum")}</td>
                            <td>${fieldValue(bean: hit, field: "bitScore")}</td>
                            <td><a href ="http://www.ncbi.nlm.nih.gov/taxonomy?term=${fieldValue(bean: hit, field: "species")}">${fieldValue(bean: hit, field: "species")}</a></td>
                            <td><a href ="http://www.ncbi.nlm.nih.gov/taxonomy?term=${fieldValue(bean: hit, field: "genus")}">${fieldValue(bean: hit, field: "genus")}</a></td>
                            <td><a href ="http://www.ncbi.nlm.nih.gov/taxonomy?term=${fieldValue(bean: hit, field: "taxOrder")}">${fieldValue(bean: hit, field: "taxOrder")}</a></td>
                            <td><a href ="http://www.ncbi.nlm.nih.gov/taxonomy?term=${fieldValue(bean: hit, field: "family")}">${fieldValue(bean: hit, field: "family")}</a></td>
                            <td><a href ="http://www.ncbi.nlm.nih.gov/taxonomy?term=${fieldValue(bean: hit, field: "taxClass")}">${fieldValue(bean: hit, field: "taxClass")}</a></td>
                            <td><a href ="http://www.ncbi.nlm.nih.gov/taxonomy?term=${fieldValue(bean: hit, field: "phylum")}">${fieldValue(bean: hit, field: "phylum")}</a></td>                      
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            
<%--            <div class="buttons">--%>
<%--                <g:form>--%>
<%--                    <g:hiddenField name="id" value="${motuInstance?.id}" />--%>
<%--                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>--%>
<%--                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>--%>
<%--                </g:form>--%>
<%--            </div>--%>
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
