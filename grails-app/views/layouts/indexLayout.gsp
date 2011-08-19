<!DOCTYPE html>
<html>
<head>
<title><g:layoutTitle default="WebTax" />
</title>
<link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
<link rel="shortcut icon"
	href="${resource(dir:'images',file:'favicon.png')}" type="image/x-icon" />
<g:layoutHead />
<g:javascript library="application" />
</head>
<body>
	<div class="wrapper">
		<div id="spinner" class="spinner" style="display: none;">
			<img src="${resource(dir:'images',file:'spinner.gif')}"
				alt="${message(code:'spinner.alt',default:'Loading...')}" />
		</div>
		<div id="grailsLogo">

		</div>

		<p>
			<g:layoutBody />
		</p>
		<div class="push"></div>
	</div>

	<div class="footer">
	WebTax was created by Justs Zarins in 2011 at <a href='http://www.nematodes.org/'>Blaxter Lab</a>, University of Edinburgh, as part of a NERC funded summer research placement. Source available on <a href='https://github.com/justsz/WebTax/tree/production'>GitHub</a>. 
	</div>

</body>
</html>