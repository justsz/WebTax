<html>
<head>
<title>Add MOTUs</title>
<meta name="layout" content="main" />
</head>

<body>
	<g:navigationBar dataset="${dataset}" />

	<g:if test="${flash.message}">
		<div class="message">
			${flash.message}
		</div>
	</g:if>


	<table class="inputTable">
		<tbody>

			<g:form action="uploadFiles" useToken="true" method="post"
				enctype="multipart/form-data">
				<tr>
					<td><label for="dataset">Dataset name</label></td>
					<td><g:textField name="dataset" value="${dataset}" /></td>
				</tr>

				<tr>
					<td colspan="2"><input type="file" name="myFile" /></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" /></td>
				</tr>
			</g:form>
		</tbody>
	</table>




</body>

</html>