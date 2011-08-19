<html>
<head>
<title>List</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>Tutorial</title>
</head>
<body>

	<h1><a href="${resource(dir:'images',file:'WebTaxTutorial.pdf')}">Download tutorial PDF</a></h1>

	<table class="tutorialTable">
		<tbody>
			<tr>
				<td>Note: You can share your current view with anyone by
					pasting them the URL in the address bar. All the necessary
					information is contained in the URL for their browser to
					dynamically recreate the same tables and graphs that you see.<br /> This
					tutorial will explain how to add MOTUs and subsequent processing
					and viewing.<br /> Begin by clicking "Create new Dataset and add MOTUs.
					Alternatively, you can follow this tutorial with our test dataset,
					b1. To do so, go to this link: <a
					href='http://webtax.bio.ed.ac.uk/input/add?dataset=b1'>http://webtax.bio.ed.ac.uk/input/add?dataset=b1</a>.
					For upload just create a couple empty text files and compress them
					into a .zip file. <br />You will be taken to this screen:</td>
			</tr>

			<tr>
				<td><img src="${resource(dir:'images/tutorial',file:'1.png')}"
					border="1" />
				<td>
			</tr>
			<tr>
				<td>Here you can enter a dataset name. <br />The dataset is all your
					data that you might have gathered on an expedition. The data can be
					further separated into sites or samples if, for example, you took a
					number of samples of the same type in different locations and want
					to compare the taxonomy between them. Choose an input file from
					your local drive and submit. <br />The input should be a .zip file that
					contains some .fasta files, but any plain text document should work
					too. The files should be formatted in a certain way. There should
					be a header followed by the sequence in a new line. <br />The header
					looks like this: >site=reer1motu=MOTU1254cutoff=0freq=1 <br />It is a >
					followed by four key-value pairs. <br />site – name of your sample <br />motu –
					name of the motu <br />cutoff – the MOTU clustering cutoff value used for
					this particular MOTU assembly <br />freq – the number of sequences that
					this MOTU represents <br />Motu is mainly used for simple naming. Site
					and cutoff is frequently used to filter data in the Analyse view,
					for example, so can all be the same if you don’t have the
					information. Freq is will be used to count representatives of
					different species in the Analyse view so it should be a real value
					to make sure the results are correct.</td>
			</tr>





			<tr>
				<td><img src="${resource(dir:'images/tutorial',file:'3.png')}"
					border="1" />
				<td>
			</tr>
			<tr>
				<td>After upload and unpacking has completed, you will be
					presented with the Review screen. Here you can tick the files you
					want to annotate or add more files, select one of the preformatted
					databases and initiate the job. <br />All preformatted databases offered
					on WebTax are built from the SILVA database archive, its version is
					visible on the bottom of the Review screen. The BAE database is
					created by excluding all sequences where any part of the header
					contains the strings 'unclassified' or 'uncultured'. For more
					information on the construction of the ssu_parc and SSURef
					datasets, see the SILVA documentation
					[<a href='http://www.arb-silva.de/documentation/background/'>http://www.arb-silva.de/documentation/background/</a> and then
					‘Release information & Database history’]. If you are primarily
					interested in only bacteria/archaea/eukaryota then we recommend
					that you use the domain-specific databases (B, A and E) as
					processing time will be reduced.</td>
			</tr>



			<tr>
				<td><img src="${resource(dir:'images/tutorial',file:'5.png')}"
					border="1" />
				<td>
			</tr>
			<tr>
				<td>Next you will see the files queued up for blasting and
					annotating. This will continue if you close the browser. You can
					bookmark the page to check the progress later. There is a link to
					the dataset on the bottom. You should remember the dataset name to
					return to it later or bookmark the link.</td>
			</tr>



			<tr>
				<td><img src="${resource(dir:'images/tutorial',file:'7.png')}"
					border="1" />
				<td>
			</tr>
			<tr>
				<td>After all annotation is complete, you can view your data in
					List. There are Download CSV and Download TSV buttons to get entire
					dataset as either a comma or tab separated values file. <br />The Search
					button at the top can be used to filter out the whole dataset by
					cutoff and sample. <br />If you click on one of the rows in the table,
					you will be taken to a table that shows the MOTU’s ten best
					matching Blast hits.</td>
			</tr>

			<tr>
				<td><img src="${resource(dir:'images/tutorial',file:'8.png')}"
					border="1" />
				<td>
			</tr>
			<tr>
				<td>Once there you can sort the table by any of the columns or
					click on any of the highlighted fields to be redirected to NCBI
					taxonomy page for that cell.</td>
			</tr>

			<tr>
				<td><img src="${resource(dir:'images/tutorial',file:'9.png')}"
					border="1" />
				<td>
			</tr>
			<tr>
				<td>The Analyse screen can be used to explore your dataset and
					compare data of different sites, and also to produce a graphical
					representation of the data. <br />Sites/Samples – select which ones to
					display (CTRL click or Apple key click to select multiple) <br />Clumping
					fraction – input a value in percents (no % sign). This will clump
					together all the MOTUs that make up less than the specified
					fraction of the whole sample into one element on the chart <br />Filter
					taxonomy by phrase – the string here must appear somewhere in the
					MOTU’s taxonomy for it to be displayed <br />MOTU Clustering cutoff – the
					allowed basepair difference specified when the MOTU was clustered
					<br />Minimum bitscore – self explanatory <br />Minimum bitscore difference –
					the minimum difference between two possible matches that you
					consider is enough to say that the MOTU belongs to one or the other
					hit. This works by working down the list of hits in descending
					bitscore until a difference is found. Say you are examining at the
					phylum level. If the phylum of the current hit is the same as the
					one of the next, it moves down to the next hit. This is repeated
					until the end of the list of hits is reached at which point the
					current is selected as the correct classification. On the other
					hand, if the next phylum is different, the bitscores of both blast
					hits are compared. If the difference is equal to or greater than
					the specified minimum difference, the current hit (one with higher
					bitscore) is said to be the correct classification. If the
					difference is smaller than the minimum, then we can’t tell which
					one it really is so the hit is dropped and not displayed. <br />Taxonomic
					type – level which to display <br />Chart type – type of chart to
					construct (these are not ideal due to the nature of the data, but
					you can download the tables and make your own charts)</td>
			</tr>

			<tr>
				<td><img src="${resource(dir:'images/tutorial',file:'10.png')}"
					border="1" />
				<td>
			</tr>
			<tr>
				<td>After clicking Analyse, the server will collect the
					necessary data and present you with a table that compares the
					taxonomy of the sites you picked. This table is downloadable in the
					form it is visible (but without the NCBI links). <br />You can click the
					NCBI button to go to their website for the taxonomy on the right.
					<br />You can also click on the name of a creature. This will take you
					down a taxonomic level and show what is under this name. Thus you
					can explore the tree structure of your samples. This is strictly
					climbing down a single tree branch so side branches are not
					included. If you would like to, for example, view what species are
					present in your samples under the phylum Tardigrada, then you
					should select Species in the Analyse input form and enter the
					filtering phrase “Tardigrada”. <br />Below you can find interactive
					graphs generated by Google’s chart API. These enlarge without loss
					of quality if you zoom in on your browser, so a screenshot can be
					taken to obtain the graph. There is also a button that leads to a
					PNG version of the graph, which can be saved by right clicking it
					and selecting the Save option. It looks different than the
					interactive charts, though.</td>
			</tr>
		</tbody>
	</table>



</body>
</html>
