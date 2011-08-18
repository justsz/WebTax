<html>
<head>
<title>Analyse MOTU diversity</title>
<meta name="layout" content="main" />
</head>
<body>
	<g:navigationBarWithChangeDataset dataset="${dataset}" />
	<g:if test="${flash.message}">
		<div class="message">
			${flash.message}
		</div>
	</g:if>


	<table class="inputTable">
		<tbody>
			<g:form method="get" action="analyse">
				
				<tr>
					<td><label for="sites">Sites/Samples</label></td>
					<td><g:select name="sites" multiple="true" from="${sites}" /></td>
				</tr>

				<tr>
					<td><label for="threshold">Clumping fraction</label></td>
					<td><g:textField name="threshold" value="${params.threshold}" />
					</td>
				</tr>

				<tr>
					<td><label for="keyPhrase">Filter taxonomy by phrase</label></td>
					<td><g:textField name="keyPhrase" value="${params.keyPhrase}" />
					</td>
				</tr>

				<tr>
					<td><label for="cutoff">MOTU Clustering cutoff</label></td>
					<td><g:textField name="cutoff" value="${params.cutoff}" /></td>
				</tr>

				<tr>
					<td><label for="minBitScore">Minimum bitscore</label></td>
					<td><g:textField name="minBitScore"
							value="${params.minBitScore}" /></td>
				</tr>

				<tr>
					<td><label for="minBitScoreStep">Minimum bitscore difference</label>
					</td>
					<td><g:textField name="minBitScoreStep"
							value="${params.minBitScoreStep}" /></td>
				</tr>

				<tr>
					<td><label for="type">Taxonomic type</label></td>
					<td><g:select name="type"
							keys="${['species', 'genus', 'family', 'taxOrder', 'taxClass', 'phylum']}"
							from="${['Species', 'Genus', 'Family', 'Order', 'Class', 'Phylum']}" />
					</td>
				</tr>

				<tr>
					<td><label for="chart">Chart type</label></td>
					<td><g:select name="chart"
							from="${['Pie chart', 'Bar chart', 'Column chart']}" /></td>
				</tr>
				<g:hiddenField name="dataset" value="${dataset}" />

				<tr align="center">
					<td></td>
					<td><g:submitButton  name="search" value="Analyse" /></td>
				</tr>
			</g:form>
		</tbody>
	</table>



</body>
</html>