package webtax

class BlastHit {
	
	String accNum 
	Integer bitScore
	Integer taxID
	
	String species
	String genus
	String taxOrder //another namespace issue with using the word 'order'
	String family
	String taxClass //namespace issue with using the word 'class'
	String phylum 
	
	static belongsTo = Motu
	
//	def String toString() {
//		accNum
//	}

    static constraints = {
		accNum()
		bitscore()
		taxID(nullable: true)
		
		species(nullable: true)
		genus(nullable: true)
		taxOrder(nullable: true) 
		family(nullable: true)
		taxClass(nullable: true) 
		phylum(nullable: true)
		
    }
	
	static mapping = {
		sort bitScore: "desc"
	}
}
