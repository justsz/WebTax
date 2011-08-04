<html>
	<head>
		<meta name="layout" content="main" />
		<script type="text/javascript" src="http://www.google.com/jsapi"></script>
	</head>
	
	<body>
	<g:navigationBar dataset="${dataset}" />
	
	<div style="float: left" class="list">
		 <g:form action='deleteFiles'>
		<table>
			
			<thead>
				<tr>
                    <th>Files to annotate</th>
				</tr>	
			</thead>
		
		
			<tbody>
			
				<g:each in="${files}" status="i" var="entry">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">                        
								<td>
								<g:checkBox name="${entry}" />
								${entry}
								</td>
                        </tr>
        		</g:each>
        	
        	</tbody>
        		
        	
        </table>
       
        
        <g:hiddenField name="destination" value="${destination}" />
        <g:hiddenField name="dataset" value="${dataset}" />
        <g:submitButton name="delete" value="Delete selected"/> 
        </g:form>
        
        <g:form action='add'>
        	<g:hiddenField name="dataset" value="${dataset}" />
        	<g:submitButton name="addMore" value="Add more files"/> 
        </g:form>
        
        <g:form action='blast'>
        	<label for="database">BLAST databse</label>
        	<g:select name="database" from="${dbs}" />
        	<g:hiddenField name="dataset" value="${dataset}" />
        	<g:hiddenField name="destination" value="${destination}" />        	
        	<g:submitButton name="blast" value="Blast files!"/> 
        </g:form>
        
        
	</div>	
	
	</body>



</html>