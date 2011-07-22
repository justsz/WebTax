package webtax

class Motu {
	
	String seqID
	String sequence //Might be a reserved name
	String cutoff
	String site
	
	static hasMany = [hits: BlastHit]
	//static belongsTo = [dataset: Dataset]
	
	def String toString() {
		"MOTU$seqID"
	}

    static constraints = {
		seqID(unique: true)
    }
	

}
