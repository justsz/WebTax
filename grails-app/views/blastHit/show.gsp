
<%@ page import="webtax.BlastHit" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'blastHit.label', default: 'BlastHit')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
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
                            <td valign="top" class="name"><g:message code="blastHit.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: blastHitInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="blastHit.accNum.label" default="Acc Num" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: blastHitInstance, field: "accNum")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="blastHit.bitScore.label" default="Bit Score" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: blastHitInstance, field: "bitScore")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="blastHit.taxID.label" default="Tax ID" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: blastHitInstance, field: "taxID")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="blastHit.phylum.label" default="Phylum" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: blastHitInstance, field: "phylum")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="blastHit.taxClass.label" default="Tax Class" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: blastHitInstance, field: "taxClass")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="blastHit.taxOrder.label" default="Tax Order" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: blastHitInstance, field: "taxOrder")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="blastHit.family.label" default="Family" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: blastHitInstance, field: "family")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="blastHit.genus.label" default="Genus" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: blastHitInstance, field: "genus")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="blastHit.species.label" default="Species" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: blastHitInstance, field: "species")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${blastHitInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
