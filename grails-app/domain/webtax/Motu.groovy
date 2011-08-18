package webtax

class Motu {
	
	String motuId
	String seqID
	String sequence 
	String cutoff
	String site
	String freq
	
	static hasMany = [hits: BlastHit]

	
	def String toString() {
		"MOTU$seqID"
	}
	
	static mapping = {
		sequence type: 'text'
		site column:'site', index:'site_idx'
		cutoff column:'cutoff', index:'cutoff_idx'
		seqID column:'seqID', index:'seqID_idx'
	}

    static constraints = {
    }
	

}
