
<%@ page import="webtax.Motu"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'motu.label', default: 'Motu')}" />
<title><g:message code="default.list.label" args="[entityName]" />
</title>
</head>
<body>
	<g:navigationBar dataset="${dataset}" />
	<g:if test="${flash.message}">
		<div class="message">
			${flash.message}
		</div>
	</g:if>



	<div class="body">

		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<div class="list">
			<table>
				<thead>
					<tr>

						<g:sortableColumn property="id" title="Id" />

						<g:sortableColumn property="seqID" title="Seq ID" />

						<g:sortableColumn property="cutoff" title="Cutoff" />

						<g:sortableColumn property="site" title="Site" />

						<g:sortableColumn property="freq" title="Freq" />

					</tr>
				</thead>
				<tbody>
					<g:each in="${motuInstanceList}" status="i" var="motuInstance">
						<tr class="${(i % 2) == 0 ? 'odd' : 'even'}"
							onmouseover="this.style.cursor='pointer'"
							onclick="location.href='${createLink(action:'showTable', id:motuInstance.id, params:[dataset:dataset])}'">

							<td>
								${motuInstance.id}
							</td>

							<td>
								${motuInstance.seqID}
							</td>

							<td>
								${motuInstance.cutoff}
							</td>

							<td>
								${motuInstance.site}
							</td>

							<td>
								${motuInstance.freq}
							</td>

						</tr>
					</g:each>
				</tbody>
			</table>
		</div>
		<div class="paginateButtons">
			<g:paginate total="${motuInstanceTotal}" params="${params}" />
		</div>

		<table class="invisibleTable">
			<tbody>
				<tr>
					<td><g:form controller="output" action="downloadListView">
							<g:hiddenField name="separator" value="csv" />
							<g:hiddenField name="dataset" value="${dataset}" />
							<g:submitButton name="download CSV" value="Download CSV" />
						</g:form></td>

					<td><g:form controller="output" action="downloadListView">
							<g:hiddenField name="separator" value="tsv" />
							<g:hiddenField name="dataset" value="${dataset}" />
							<g:submitButton name="download TSV" value="Download TSV" />
						</g:form></td>
				</tr>
			</tbody>
		</table>
	</div>



	<%--        <div id = 'linkToView'>--%>
	<%--		<a href ='<g:createLink absolute="true" controller="motu" action="results" params="${[motuInstanceList:motuInstanceList, motuInstanceTotal:motuInstanceTotal]}"/>'>Link to this page</a>--%>
	<%--		</div>--%>
</body>
</html>
