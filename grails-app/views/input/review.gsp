<html>
<head>
<title>Review</title>
<meta name="layout" content="main" />
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
</head>

<body>
	<g:navigationBar dataset="${dataset}" />
	<g:if test="${flash.message}">
		<div class="message">
			${flash.message}
		</div>
	</g:if>

	<div style="float: left" class="list">
		<g:form action='blast'>
			<table>

				<thead>
					<tr>
						<th>Files to annotate</th>
					</tr>
				</thead>


				<tbody>

					<g:each in="${files}" status="i" var="entry">
						<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
							<td><g:checkBox name="${entry}" value="on" /> ${entry}
							</td>
						</tr>
					</g:each>


				</tbody>


			</table>


			<label for="database">BLAST database</label>
			<g:select name="database" keys="${dbs}" from="${dbDescriptions}" />
			<g:hiddenField name="dataset" value="${dataset}" />
			<g:hiddenField name="destination" value="${destination}" />
			<br />
			<g:submitButton name="blast" value="Blast and annotate files!" />
		</g:form>

		<g:form action='add'>
			<g:hiddenField name="dataset" value="${dataset}" />
			<g:submitButton name="addMore" value="Add more files" />
		</g:form>
		
		<pre>
		NCBI TaxDump updated on ${taxdumpUpdateDate}
		</pre>



	</div>

</body>



</html>