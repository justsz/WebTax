
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
                        
                        
                        
                           
                            
                           
<%--                            <th>Acc Number</th>--%>
<%--                            <g:sortableColumn property="bitScore" title="Bitscore" defaultOrder="desc" />--%>
<%--                            <th>Species</th>--%>
<%--                            <th>Genus</th>--%>
<%--                            <th>Order</th>--%>
<%--                            <th>Family</th>--%>
<%--                            <th>Class</th>--%>
<%--                            <th>Phylum</th>--%>
                            
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
                            <td>${fieldValue(bean: hit, field: "species")}</td>
                            <td>${fieldValue(bean: hit, field: "genus")}</td>
                            <td>${fieldValue(bean: hit, field: "taxOrder")}</td>
                            <td>${fieldValue(bean: hit, field: "family")}</td>
                            <td>${fieldValue(bean: hit, field: "taxClass")}</td>
                            <td>${fieldValue(bean: hit, field: "phylum")}</td>
                        
                            
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
                
                
                
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${motuInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
       		<g:jasperReport jasper="motus" format="XLS" name="Motus">
				<input type="hidden" name="motu_id" value="${motuInstance.id}" />
			</g:jasperReport>
        
        <viewLink:thisView/>


		<g:form action="downloadTableView">
			<g:hiddenField name="separator" value="csv" />
			<g:hiddenField name="motuInstance" value="${motuInstance}" />
			<g:hiddenField name="hits" value="${hits}" />
			<g:submitButton name="download CSV" value="Download CSV"/>
		</g:form>
		
		<g:form action="downloadTableView">
			<g:hiddenField name="separator" value="tsv" />
			<g:hiddenField name="motuInstance" value="${motuInstance}" />
			<g:hiddenField name="hits" value="${hits}" />
			<g:submitButton name="download TSV" value="Download TSV"/>
		</g:form>
    </body>
    
</html>
