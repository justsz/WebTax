<html>
	<head>
		<meta name="layout" content="main" />
	</head>
	
	<body>
	<div class="list">
		<table>
			<thead>
				<tr>
                    <th>${type}</th>
                    <th>Presence</th>
				</tr>	
			</thead>
		
		
			<tbody>
				<g:each in="${reps.entrySet()}" status="i" var="entry">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">                        
								<td>${entry.key}</td>
								<td>${entry.value}</td>
                        </tr>
        		</g:each>
        	</tbody>
        </table>
	</div>
	</body>



</html>