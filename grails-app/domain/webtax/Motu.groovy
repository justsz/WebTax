package webtax

class Motu {
	
	String seqID
	String sequence //Might be a reserved name
	
	static hasMany = [hits: BlastHit]
	
	def String toString() {
		seqID
	}

    static constraints = {
		seqID(unique: true)
    }
	
//	static mapping = {
//		hits sort: 'bitScore'
//	}
}
