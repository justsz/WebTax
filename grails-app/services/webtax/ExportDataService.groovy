package webtax

class ExportDataService {

    static transactional = false
	
	
	def File makeTableView(String hits, String separator) {
		def s
		if (separator == 'csv') {
			s = ','
		} else if (separator == 'tsv') {
			s = '\t'
		}
		
		def idPattern = /webtax.BlastHit : (\d+)/
		def idFilter = hits =~ idPattern
		
		
		def out = new File('table')
		out.delete()
		
		out.append "accNum${s}bitscore${s}species${s}genus${s}order${s}family${s}class${s}phylum\n"
		for (i in 0..<idFilter.size()) {
			def hit = BlastHit.get(idFilter[i][1])
			def row = [hit.accNum, hit.bitScore, hit.species, hit.genus, hit.taxOrder, hit.family, hit.taxClass, hit.phylum]
			out.append row.join(s)
			out.append '\n'
		}
		
		return out
		
	}
    
	def File makeListView(String dataset, String separator) {
		def s
		if (separator == 'csv') {
			s = ','
		} else if (separator == 'tsv') {
			s = '\t'
		}
		
		def out = new File('list')
		out.delete()
		
		def header = "seqID${s}cutoff${s}site${s}"
		for (i in 1..9) {
			def blastHeader = "accNum$i${s}bitscore$i${s}species$i${s}genus$i${s}order$i${s}family$i${s}class$i${s}phylum$i${s}"
			header += blastHeader
		}
		header += "accNum10${s}bitscore10${s}species10${s}genus10${s}order10${s}family10${s}class10${s}phylum10\n"
		out.append header
		
		def motus = Dataset.findByName(dataset).motus
		
		
		
		motus.each {
			def row = [it.seqID, it.cutoff, it.site]
			def hits = it.hits.sort {-it.bitScore}
			hits.each {
				row += [it.accNum, it.bitScore, it.species, it.genus, it.taxOrder, it.family, it.taxClass, it.phylum]
			}
			out.append row.join("${s}")
			out.append '\n'
		}
		
		
		return out
	}
	
	def File makeRepresentView(String data, String sites, String type, String separator) {
		def s
		if (separator == 'csv') {
			s = ','
		} else if (separator == 'tsv') {
			s = '\t'
		}
		
		def out = new File('represent')
		out.delete()
		
		data = data[1..-2]
		sites = sites[1..-2]
		def sitesList = sites.split(', ')
		
		
		def firstPattern = /\[(\[.*?\])\]/
		def firstFilter = data =~ firstPattern
		
		def secondPattern = /\[(.*?), (\d+)\]/
		
		for (i in 0..<firstFilter.size()) {
			def secondFilter = firstFilter[i][1] =~ secondPattern
			out.append "$type${s}${sitesList[i]}\n"
			for (e in 0..<secondFilter.size()) {
				out.append "${secondFilter[e][1]}${s}${secondFilter[e][2]}\n"
			}
		}
		
		
		
		return out
	}
}

//represent view input sample
//[[[Tardigrada environmental sample, 190], [Arthrinium sacchari, 1], [others, 0]], [[others, 0]]]
//[creer1, creer2]
//species

