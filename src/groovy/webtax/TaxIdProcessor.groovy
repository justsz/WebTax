//Most of this file is a port of Martin Jones' Taxonerator.

package webtax

import TreeNode; //Class can be found at the end of this file.

import java.util.List;
import java.util.regex.Pattern

class TaxIdProcessor {

	def taxid2node = []
	def child2parent = []


	//Constructor parses and builds up the taxonomy tree.
	def TaxIdProcessor(taxdumpPath){
		def nodePattern = ~/^(\d+)\t\|\t(\d+)\t\|\t(.+?)\t\|/

		println "reading ncbi taxonomy from $taxdumpPath"
		// open the NCBI taxonomy for structure
		new File("${taxdumpPath}/nodes.dmp").eachLine{
			line ->
			def matcher = (line =~ nodePattern)
			if (matcher.matches()){
				Integer myId = matcher[0][1] as Integer
				Integer parentId = matcher[0][2] as Integer
				String myRank = matcher[0][3]

				// build new node
				def node = new TreeNode(taxid : myId, rank:myRank)
				// add node to lookup hash
				taxid2node[(myId)] = node
				// add to relationship hash
				// we can't just add to parent directly because the nodes are not
				// in any kind of helpful order

				child2parent[(myId)] = parentId
			}
		}

		// now process names file to add scientific names to nodes
		println taxid2node.size()
		println child2parent.size()
		def counter = 0
		Pattern namePattern = ~/^(\d+)\t\|\t(.+)\t\|\t(.*)\t\|\t(.+)\t\|/
		new File("${taxdumpPath}/names.dmp").eachLine{
			line ->
			def nameMatcher = (line =~ namePattern)
			counter++
			if (nameMatcher.matches() && nameMatcher[0][4].equals("scientific name") ){
				Integer taxid = nameMatcher[0][1] as Integer
				String myName = nameMatcher[0][2]

				if ((counter % 100000) ==0){
					print "processing name $counter\n"
				}

				taxid2node[taxid].name = myName
			}
		}
		println "finished processing relationships"
	}


	List<TreeNode> getAncestorsForNode(TreeNode n){
		List<TreeNode> result = []
		TreeNode current = n
		while (current.taxid != 1){

			result.add(current)
			def currentTaxid = child2parent[current.taxid]
			def parent = taxid2node[currentTaxid]
			current = parent
		}
		return result
	}


	TreeNode getNodeForTaxid(Integer taxid){
		return this.taxid2node[taxid]
	}


}

// start NCBI stuff


class TreeNode implements Serializable{
	String name
	String rank
	Integer taxid
}