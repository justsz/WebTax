package webtax

class PrintableService {

    static transactional = false

	def List reFormat(String datum) {
		def out = []
		datum = datum[1..-2]
		
		
		def entryPattern = /\[(.*?), (\d+)\]/
		def entryFilter = datum =~ entryPattern
		
		for (i in 0..<entryFilter.size()) {
			def entry = [entryFilter[i][1], entryFilter[i][2]]
			out.add(entry)
		}
		
		return out
	}
}

//Exapmple input:
//[[Astomonema sp. NCM-2006, 54], [uncultured nematode, 49],..., [others, 0]]