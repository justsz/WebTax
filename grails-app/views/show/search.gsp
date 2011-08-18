<html> 
<head> 
    <title>Search</title> 
    <meta name="layout" content="main"/> 
</head> 
<body> 
<g:navigationBarWithChangeDataset dataset="${dataset}" />
	<g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
    </g:if>

    
<table class="inputTable">
<tbody>

    <g:form method="get" action="results">
    
    	<g:hiddenField name="dataset" value="${dataset}" />
    	<tr>
    	<td>
        <label for="site">Site</label>
        </td>
        <td> 
        <g:textField name="site" />
        </td>
        </tr>
         
        <tr>
        <td>
        <label for="cutoff">Cutoff</label>
        </td>
        <td> 
        <g:textField name="cutoff" />
        </td>
        </tr> 
        
        <tr>
        <td></td>
        <td>
        <g:submitButton name="search" value="Search"/>
        </td>
        </tr> 
    </g:form>
    </tbody>
    </table> 
 
    
</body> 
</html>