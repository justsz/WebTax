package webtax

import grails.test.*

class MotuIntegrationTests extends GroovyTestCase {

	void testSaveAndLoad() {
		def motu = new Motu(seqID: "ABC123", sequence: "cgattgca")
		
		assertNotNull motu.save()
		assertNotNull motu.id
		
		def foundMotu = Motu.get(motu.id)
		assertEquals 'ABC123', foundMotu.seqID
		assertEquals 'cgattgca', foundMotu.sequence
	}
	
	void testAddHitAndLoad() {
		def motu = new Motu(seqID: "ABC123", sequence: "cgattgca").save()
		
		def hit1 = new BlastHit(accNum: 'Delta001', bitscore: 300, taxid: 6226, species: 'cat', genus:'big cat', taxOrder: 'second', family: 'single', taxClass: 'MouseCatcher', phylum: 'Spanish cat')
		motu.addToHits(hit1)
		def hit2 = new BlastHit(accNum: 'Delta002', bitscore: 200, taxid: 6006, species: 'dog', genus:'big dog', taxOrder: 'third', family: 'none', taxClass: 'CatCatcher', phylum: 'Royal Canine')
		motu.addToHits(hit2)
		
		assertEquals 2, Motu.get(motu.id).hits.size()
		
//		def foundMotu = Motu.get(motu.id)
//		
//		assertEquals 'Delta001', foundMotu.hits[0].accNum
		
	}
}
