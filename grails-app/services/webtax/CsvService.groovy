package webtax

class CsvService {

    static transactional = false
	
	
	def File makeTableViewCSV(String hits) {
		
		def idPattern = /webtax.BlastHit : (\d+)/
		def idFilter = hits =~ idPattern
		
		
		def out = new File('table.csv')
		out.delete()
		
		out.append 'Acc Num,bitscore,species,genus,order,family,class,phylum\n'
		for (i in 0..<idFilter.size()) {
			def hit = BlastHit.get(idFilter[i][1])
			def row = [hit.accNum, hit.bitScore, hit.species, hit.genus, hit.taxOrder, hit.family, hit.taxClass, hit.phylum]
			out.append row.join(',')
			out.append '\n'
		}
		
		return out
		
	}
    
	def File makeListViewCSV(String dataset) {
		def out = new File('list.csv')
		out.delete()
		
		def header = 'Seq ID,cutoff,site,'
		for (i in 1..10) {
			def blastHeader = "Acc Num$i,bitscore$i,species$i,genus$i,order$i,family$i,class$i,phylum$i"
			header += blastHeader
		}
		out.append header
		
		def motus = Dataset.findByName(dataset).motus
		println motus.size()
		
		
		motus.each {
			def row = [it.seqID, it.cutoff, it.site]
			it.hits.each {
				row += [it.accNum, it.bitScore, it.species, it.genus, it.taxOrder, it.family, it.taxClass, it.phylum]
			}
			out.append row.join(',')
			out.append '\n'
		}
		
		
		return out
	}
	
	def File makeRepresentViewCSV(String data, String sites, String type) {
		def out = new File('represent.csv')
		out.delete()
		
		data = data[1..-2]
		sites = sites[1..-2]
		def sitesList = sites.split(', ')
		
		
		def firstPattern = /\[(\[.*?\])\]/
		def firstFilter = data =~ firstPattern
		
		def secondPattern = /\[(.*?), (\d+)\]/
		
		for (i in 0..<firstFilter.size()) {
			def secondFilter = firstFilter[i][1] =~ secondPattern
			out.append "$type,${sitesList[i]}\n"
			for (e in 0..<secondFilter.size()) {
				out.append "${secondFilter[e][1]},${secondFilter[e][2]}\n"
			}
		}
		
		
		
		return out
	}
}

//represent view input sample
//[[Tardigrada environmental sample, 190], [Arthrinium sacchari, 1], [others, 0]], [[others, 0]]
//[creer1, creer2]
//species

