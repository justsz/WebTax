
<%@ page import="webtax.Motu" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'motu.label', default: 'Motu')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
        	<span class="menuButton"><g:link class="home" action="index" params="[dataset: dataset]"><g:message code="Home"/></g:link></span>
            <span class="menuButton"><g:link class="create" action="create" params="[dataset: dataset]"><g:message code="Add MOTUs" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="search" action="search" params="[dataset: dataset]"><g:message code="Search"/></g:link></span>
            <span class="menuButton"><g:link class="represent" action="repForm" params="[dataset: dataset]"><g:message code="Represent"/></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="motu.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: motuInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="motu.seqID.label" default="Seq ID" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: motuInstance, field: "seqID")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="motu.cutoff.label" default="Cutoff" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: motuInstance, field: "cutoff")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="motu.hits.label" default="Hits" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${motuInstance.hits}" var="h">
                                    <li><g:link controller="blastHit" action="show" id="${h.id}">${h?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="motu.sequence.label" default="Sequence" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: motuInstance, field: "sequence")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="motu.site.label" default="Site" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: motuInstance, field: "site")}</td>
                            
                        </tr>
                    
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
    </body>
</html>
