<html>
<head>
<title>Welcome to WebTax</title>
<meta name="layout" content="indexLayout" />
<style type="text/css" media="screen">
#nav {
	margin-top: 20px;
	margin-left: 20px;
	width: 200px;
	float: left;
}

.homePagePanel * {
	margin: 0px;
}

.homePagePanel .panelBody ul {
	list-style-type: none;
	margin-bottom: 10px;
}

.homePagePanel .panelBody h1 {
	text-align: center;
	text-transform: uppercase;
	font-size: 1.3em;
	margin-bottom: 10px;
}

.homePagePanel .panelBody {
	font-size: 1.1em;
	text-align: justify;
	background: url(images/leftnav_midstretch.png) repeat-y top;
	margin: 4px;
	padding: 15px;
}

.homePagePanel .panelBtm {
	background: url(images/leftnav_btm.png) no-repeat top;
	height: 20px;
	margin: 0px;
}

.homePagePanel .panelTop {
	background: url(images/leftnav_top.png) no-repeat top;
	height: 11px;
	margin: 0px;
}

h2 {
	margin-top: 10px;
	margin-bottom: 10px;
	font-size: 14px;
	text-align: center;
}

#pageBody {
	margin-left: 280px;
	margin-right: 20px;
	margin-top: 75px;
}

<%--
#newww { --%> <%-- --%> <%--
	font-size: 11px; --%> <%--
	background: url(../images/index/plus.png) center left no-repeat;
	--%>
	<%--
}
--%>
</style>
</head>
<body>
	<div id="nav">
		<div class="homePagePanel">
			<div class="panelTop"></div>
			<div class="panelBody">
				<h2>Welcome to WebTax</h2>
				<p>
					<br />This tool is designed to take an input collection of environmental sequences
					that have been clustered into Molecular Operational Taxonomic Units
					(MOTUs) and assign taxonomic annotation to them using a database of
					reference sequences. <br /> Choose an action on the right to get
					started. <br /> 
				</p>
			</div>
			<div class="panelBtm"></div>
		</div>
	</div>
	<div id="pageBody">


		<table class="indexTable">
			<tbody>
				<tr>
					<td onmouseover="this.style.cursor='pointer'"
						onclick="location.href='${createLink(controller:'input', action:'add')}'">
						<span class="new"> Create new Dataset and add MOTUs </span>
					</td>
				</tr>
				<tr>
					<td onmouseover="this.style.cursor='pointer'"
						onclick="location.href='${createLink(controller:'show', action:'switchDataset')}'">
						<span class="continue"> Open a previously created Dataset </span>
					</td>
				</tr>
				<tr>
					<td onmouseover="this.style.cursor='pointer'"
						onclick="location.href='${createLink( absolute:'true', url:'tutorial.gsp')}'">
						<span class="tutorial"> Tutorial </span>
					</td>
				</tr>
			</tbody>
		</table>



	</div>
</html>
