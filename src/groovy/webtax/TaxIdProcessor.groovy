/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webtax

import java.util.regex.Pattern
/**
 *
 * @author martin
 */
class TaxIdProcessor {

    HashMap<Integer, TreeNode> taxid2node = [:]
    HashMap<Integer, Integer> child2parent = [:]


    def TaxIdProcessor(taxdumpPath){


        def nodePattern = ~/^(\d+)\t\|\t(\d+)\t\|\t(.+?)\t\|/

        println "reading ncbi taxonomy from $taxdumpPath"
        def count=0
        // open the NCBI taxonomy for structure
        new File("${taxdumpPath}/nodes.dmp").eachLine{
            line ->
            count++
            def matcher = (line =~ nodePattern)
            if (matcher.matches()){
                Integer myId = matcher[0][1].toInteger()
                Integer parentId = matcher[0][2].toInteger()
                String myRank = matcher[0][3]

                // println "$myId is child of $parentId and has rank $myRank"
                if ((count % 1000) ==0){
                    //print "processing node $count\n"
                }
                // build new node
                def node = new TreeNode(name : "my id is $myId", taxid : myId, rank:myRank)
                // add node to lookup hash
                taxid2node[(myId)] = node
                // add to relationship hash
                // we can't just add to parent directly because the nodes are not
                // in any kind of helpful order

                child2parent[(myId)] = parentId


            }
        }
        // now process names file to add scientific names to nodes
        Pattern namePattern = ~/^(\d+)\t\|\t(.+)\t\|\t(.*)\t\|\t(.+)\t\|/
        new File("${taxdumpPath}/names.dmp").eachLine{
            line ->
            def nameMatcher = (line =~ namePattern)


            if (nameMatcher.matches() && nameMatcher[0][4].equals("scientific name") ){
                Integer taxid = nameMatcher[0][1].toInteger()
                String myName = nameMatcher[0][2]

                //println "scientific name is $name"
                taxid2node.get(taxid).name = myName
            }
        }

        println "finished processing relationships"

    }

    List<TreeNode> getAncestorsForNode(TreeNode n){
        //println "getting ancestors for " + n.taxid
        List<TreeNode> result = []
        TreeNode current = n
        while (current.taxid != 1){
            //println "adding $current.name"
            result.add(current)
            def currentTaxid = child2parent.get(current.taxid)
            def parent = taxid2node.get(currentTaxid)
            current = parent
         //   println "\tcurrent is $current.taxid"
        }
        return result
    }


    TreeNode getNodeForTaxid(Integer taxid){
        return this.taxid2node.get(taxid)
    }


}

// start NCBI stuff


class TreeNode {
    String name
    String rank
    Integer taxid
}