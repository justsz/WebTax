

<%@ page import="webtax.BlastHit" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'blastHit.label', default: 'BlastHit')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${blastHitInstance}">
            <div class="errors">
                <g:renderErrors bean="${blastHitInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${blastHitInstance?.id}" />
                <g:hiddenField name="version" value="${blastHitInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="accNum"><g:message code="blastHit.accNum.label" default="Acc Num" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: blastHitInstance, field: 'accNum', 'errors')}">
                                    <g:textField name="accNum" value="${blastHitInstance?.accNum}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="bitScore"><g:message code="blastHit.bitScore.label" default="Bit Score" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: blastHitInstance, field: 'bitScore', 'errors')}">
                                    <g:textField name="bitScore" value="${fieldValue(bean: blastHitInstance, field: 'bitScore')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="taxID"><g:message code="blastHit.taxID.label" default="Tax ID" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: blastHitInstance, field: 'taxID', 'errors')}">
                                    <g:textField name="taxID" value="${fieldValue(bean: blastHitInstance, field: 'taxID')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phylum"><g:message code="blastHit.phylum.label" default="Phylum" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: blastHitInstance, field: 'phylum', 'errors')}">
                                    <g:textField name="phylum" value="${blastHitInstance?.phylum}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="taxClass"><g:message code="blastHit.taxClass.label" default="Tax Class" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: blastHitInstance, field: 'taxClass', 'errors')}">
                                    <g:textField name="taxClass" value="${blastHitInstance?.taxClass}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="taxOrder"><g:message code="blastHit.taxOrder.label" default="Tax Order" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: blastHitInstance, field: 'taxOrder', 'errors')}">
                                    <g:textField name="taxOrder" value="${blastHitInstance?.taxOrder}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="family"><g:message code="blastHit.family.label" default="Family" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: blastHitInstance, field: 'family', 'errors')}">
                                    <g:textField name="family" value="${blastHitInstance?.family}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="genus"><g:message code="blastHit.genus.label" default="Genus" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: blastHitInstance, field: 'genus', 'errors')}">
                                    <g:textField name="genus" value="${blastHitInstance?.genus}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="species"><g:message code="blastHit.species.label" default="Species" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: blastHitInstance, field: 'species', 'errors')}">
                                    <g:textField name="species" value="${blastHitInstance?.species}" />
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
