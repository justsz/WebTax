package webtax

class CsvService {

    static transactional = false
	
	
	def File makeTableViewCSV(String hits) {
		
		def idPattern = /webtax.BlastHit : (\d+)/
		def idFilter = hits =~ idPattern
		
		
		def out = new File('table.csv')
		out.delete()
		
		for (i in 0..<idFilter.size()) {
			def hit = BlastHit.get(idFilter[i][1])
			def row = [hit.accNum, hit.bitScore, hit.species, hit.genus, hit.taxOrder, hit.family, hit.taxClass, hit.phylum]
			out.append row.join(',')
			out.append '\n'
		}
		
		return out
		
	}
    
}
