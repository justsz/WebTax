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
		
		out.append "accNum${s}bitscore${s}TaxID${s}species${s}genus${s}family${s}order${s}class${s}phylum\n"
		for (i in 0..<idFilter.size()) {
			def hit = BlastHit.get(idFilter[i][1])
			def row = [hit.accNum, hit.bitScore, hit.taxID, hit.species, hit.genus, hit.family, hit.taxOrder, hit.taxClass, hit.phylum]
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
		
		def header = "seqID${s}cutoff${s}site${s}freq${s}"
		for (i in 1..9) {
			def blastHeader = "accNum$i${s}bitscore$i${s}TaxID$i${s}species$i${s}genus$i${s}family$i${s}order$i${s}class$i${s}phylum$i${s}"
			header += blastHeader
		}
		header += "accNum10${s}bitscore10${s}TaxID10${s}species10${s}genus10${s}family10${s}order10${s}class10${s}phylum10\n"
		out.append header
		
		def motus = Dataset.findByName(dataset).motus
		
		
		
		motus.each {
			def row = [it.seqID, it.cutoff, it.site, it.freq]
			def hits = it.hits.sort {-it.bitScore}
			hits.each {
				row += [it.accNum, it.bitScore, it.taxID, it.species, it.genus, it.family, it.taxOrder, it.taxClass, it.phylum]
			}
			out.append row.join("${s}")
			out.append '\n'
		}
		
		
		return out
	}
	
	def File makeSearchView(String dataset, String site, String cutoff, String separator) {
		def s
		if (separator == 'csv') {
			s = ','
		} else if (separator == 'tsv') {
			s = '\t'
		}
		
		def out = new File('list')
		out.delete()
		
		def header = "seqID${s}cutoff${s}site${s}freq${s}"
		for (i in 1..9) {
			def blastHeader = "accNum$i${s}bitscore$i${s}TaxID$i${s}species$i${s}genus$i${s}family$i${s}order$i${s}class$i${s}phylum$i${s}"
			header += blastHeader
		}
		header += "accNum10${s}bitscore10${s}TaxID10${s}species10${s}genus10${s}family10${s}order10${s}class10${s}phylum10\n"
		out.append header
		
		def query = {
			'in'("id", Dataset.findByName(dataset).motus*.id)
			eq('site', site)
			eq('cutoff', cutoff)
		}

		def motus = Motu.createCriteria().list(query)
		
		motus.each {
			def row = [it.seqID, it.cutoff, it.site, it.freq]
			def hits = it.hits.sort {-it.bitScore}
			hits.each {
				row += [it.accNum, it.bitScore, it.taxID, it.species, it.genus, it.family, it.taxOrder, it.taxClass, it.phylum]
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
		
		out.append "$type"
		sitesList.each { out.append "${s}${it}" }
		out.append "\n"
		
		
		def pattern = /\[(.*?)\]/
		def filter = data =~ pattern
		
		
		for (i in 0..<filter.size()) {
			def row = filter[i][1].split(', ')
			out.append row.join(s)
			out.append "\n"
		}
		
		
		
		return out
	}
}

//represent view input sample
//[[Arthrinium sacchari, 3, 1], [Grammatophora marina, 11, 11], [Herdmania momus, 22, 31], [Moultonianthus leembruggianus, 5, 7]]
//[creer1, creer2]
//species

