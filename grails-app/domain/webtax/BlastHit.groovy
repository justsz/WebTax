package webtax

class BlastHit {
	
	String accNum 
	Double bitScore
	Integer taxID
	
	String species
	String genus
	String taxOrder //another namespace issue with using the word 'order'
	String family
	String taxClass //namespace issue with using the word 'class'
	String phylum 
	


    static constraints = {
		accNum(unique: true)
		bitScore()
		taxID(nullable: true)
		
		phylum(nullable: true)
		taxClass(nullable: true)
		taxOrder(nullable: true)
		family(nullable: true)
		genus(nullable: true)
		species(nullable: true)		
		
    }
	
	
}
