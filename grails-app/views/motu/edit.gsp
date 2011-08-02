

<%@ page import="webtax.Motu" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'motu.label', default: 'Motu')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
        	<span class="menuButton"><g:link class="home" action="index" params="[dataset: dataset]"><g:message code="Home"/></g:link></span>
            <span class="menuButton"><g:link class="create" action="create" params="[dataset: dataset]"><g:message code="Add MOTUs" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="search" action="search" params="[dataset: dataset]"><g:message code="Search"/></g:link></span>
            <span class="menuButton"><g:link class="represent" action="repForm" params="[dataset: dataset]"><g:message code="Represent"/></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${motuInstance}">
            <div class="errors">
                <g:renderErrors bean="${motuInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${motuInstance?.id}" />
                <g:hiddenField name="version" value="${motuInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="seqID"><g:message code="motu.seqID.label" default="Seq ID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: motuInstance, field: 'seqID', 'errors')}">
                                    <g:textField name="seqID" value="${motuInstance?.seqID}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="cutoff"><g:message code="motu.cutoff.label" default="Cutoff" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: motuInstance, field: 'cutoff', 'errors')}">
                                    <g:textField name="cutoff" value="${motuInstance?.cutoff}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hits"><g:message code="motu.hits.label" default="Hits" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: motuInstance, field: 'hits', 'errors')}">
                                    <g:select name="hits" from="${webtax.BlastHit.list()}" multiple="yes" optionKey="id" size="5" value="${motuInstance?.hits*.id}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="sequence"><g:message code="motu.sequence.label" default="Sequence" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: motuInstance, field: 'sequence', 'errors')}">
                                    <g:textField name="sequence" value="${motuInstance?.sequence}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="site"><g:message code="motu.site.label" default="Site" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: motuInstance, field: 'site', 'errors')}">
                                    <g:textField name="site" value="${motuInstance?.site}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
