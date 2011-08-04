
<%@ page import="webtax.Motu" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'motu.label', default: 'Motu')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <g:navigationBar dataset="${dataset}" />
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" params="[dataset:dataset]" title="${message(code: 'motu.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="seqID" params="[dataset:dataset]" title="${message(code: 'motu.seqID.label', default: 'Seq ID')}" />
                        
                            <g:sortableColumn property="cutoff" params="[dataset:dataset]" title="${message(code: 'motu.cutoff.label', default: 'Cutoff')}" />
                                            
                            <g:sortableColumn property="site" params="[dataset:dataset]" title="${message(code: 'motu.site.label', default: 'Site')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${motuInstanceList}" status="i" var="motuInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="showTable" id="${motuInstance.id}">${fieldValue(bean: motuInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: motuInstance, field: "seqID")}</td>
                        
                            <td>${fieldValue(bean: motuInstance, field: "cutoff")}</td>                    
                       
                            <td>${fieldValue(bean: motuInstance, field: "site")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate params="${params}" total="${motuInstanceTotal}" />
            </div>
            
        </div>
        
        <g:form action="downloadListView">
        	<g:hiddenField name="separator" value="csv" />
			<g:hiddenField name="dataset" value="${dataset}" />
			<g:submitButton name="download CSV" value="Download CSV"/>
		</g:form>
		
		<g:form action="downloadListView">
			<g:hiddenField name="separator" value="tsv" />
			<g:hiddenField name="dataset" value="${dataset}" />
			<g:submitButton name="download TSV" value="Download TSV"/>
		</g:form>
        
    </body>
</html>
