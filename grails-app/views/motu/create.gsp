<g:form action="upload" method="post" enctype="multipart/form-data">
			<input type="file" name="myFile" />
			<input type="submit" />
		</g:form>

<%--<%@ page import="webtax.Motu" %>--%>
<%--<html>--%>
<%--    <head>--%>
<%--        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />--%>
<%--        <meta name="layout" content="main" />--%>
<%--        <g:set var="entityName" value="${message(code: 'motu.label', default: 'Motu')}" />--%>
<%--        <title><g:message code="default.create.label" args="[entityName]" /></title>--%>
<%--    </head>--%>
<%--    <body>--%>
<%--        <div class="nav">--%>
<%--            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>--%>
<%--            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>--%>
<%--        </div>--%>
<%--        <div class="body">--%>
<%--            <h1><g:message code="default.create.label" args="[entityName]" /></h1>--%>
<%--            <g:if test="${flash.message}">--%>
<%--            <div class="message">${flash.message}</div>--%>
<%--            </g:if>--%>
<%--            <g:hasErrors bean="${motuInstance}">--%>
<%--            <div class="errors">--%>
<%--                <g:renderErrors bean="${motuInstance}" as="list" />--%>
<%--            </div>--%>
<%--            </g:hasErrors>--%>
<%--            <g:form action="save" >--%>
<%--                <div class="dialog">--%>
<%--                    <table>--%>
<%--                        <tbody>--%>
<%--                        --%>
<%--                            <tr class="prop">--%>
<%--                                <td valign="top" class="name">--%>
<%--                                    <label for="seqID"><g:message code="motu.seqID.label" default="Seq ID" /></label>--%>
<%--                                </td>--%>
<%--                                <td valign="top" class="value ${hasErrors(bean: motuInstance, field: 'seqID', 'errors')}">--%>
<%--                                    <g:textField name="seqID" value="${motuInstance?.seqID}" />--%>
<%--                                </td>--%>
<%--                            </tr>--%>
<%--                        --%>
<%--                            <tr class="prop">--%>
<%--                                <td valign="top" class="name">--%>
<%--                                    <label for="sequence"><g:message code="motu.sequence.label" default="Sequence" /></label>--%>
<%--                                </td>--%>
<%--                                <td valign="top" class="value ${hasErrors(bean: motuInstance, field: 'sequence', 'errors')}">--%>
<%--                                    <g:textField name="sequence" value="${motuInstance?.sequence}" />--%>
<%--                                </td>--%>
<%--                            </tr>--%>
<%--                        --%>
<%--                        </tbody>--%>
<%--                    </table>--%>
<%--                </div>--%>
<%--                <div class="buttons">--%>
<%--                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>--%>
<%--                </div>--%>
<%--            </g:form>--%>
<%--        </div>--%>
<%--    </body>--%>
<%--</html>--%>
