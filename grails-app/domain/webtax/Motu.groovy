package webtax

class Motu {
	
	String seqID
	String sequence
	
	static hasMany = [hits: BlastHit]
	
	def String toString() {
		seqID
	}

    static constraints = {
		
    }
}
