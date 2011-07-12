
<%@ page import="webtax.Motu" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'motu.label', default: 'Motu')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'motu.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="seqID" title="${message(code: 'motu.seqID.label', default: 'Seq ID')}" />
                        
                            <g:sortableColumn property="cutoff" title="${message(code: 'motu.cutoff.label', default: 'Cutoff')}" />
                        
<%--                            <g:sortableColumn property="sequence" title="${message(code: 'motu.sequence.label', default: 'Sequence')}" />--%>
                        
                            <g:sortableColumn property="site" title="${message(code: 'motu.site.label', default: 'Site')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${motuInstanceList}" status="i" var="motuInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${motuInstance.id}">${fieldValue(bean: motuInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: motuInstance, field: "seqID")}</td>
                        
                            <td>${fieldValue(bean: motuInstance, field: "cutoff")}</td>
                        
<%--                            <td>${fieldValue(bean: motuInstance, field: "sequence")}</td>--%>
                        
                            <td>${fieldValue(bean: motuInstance, field: "site")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${motuInstanceTotal}" />
<%--                <g:paginate max =10 action="results" total="${motuInstanceTotal}" />--%>
            </div>
        </div>
    </body>
</html>
