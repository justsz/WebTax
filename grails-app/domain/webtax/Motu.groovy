package webtax

class Motu {
	
	String motuId
	String seqID
	String sequence //Might be a reserved name
	String cutoff
	String site
	String freq
	
	static hasMany = [hits: BlastHit]
	//static belongsTo = [dataset: Dataset]
	
	def String toString() {
		"MOTU$seqID"
	}

    static constraints = {
		//seqID(unique: true)
    }
	

}
