package com.ldg.generictree

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import com.ldg.modeltree.ModelTree._

//Taxnonomy Tree
//Generic Tree
class TaxonomyTree {

  private var _rootNode:Node=null

  //Getter
  def rootNode = _rootNode

  //TODO: Fix when node is a root node
  //Setter
  def node_= (node:Node):Unit = _rootNode = node

  /**
    * Exception for Root Node null
    */
  case class NullRootException(message: String) extends Exception(message)


  /**
    * Retrieve a list of nodes -> all the descendants of a node
    *
    * nodeBase: node from which extract begin
    *
    */

  def getDescendants (nodeBase:Node): List[Node] ={
    val result = mutable.ArrayBuffer[Node]()

    def traverseNodes(node: Node):ArrayBuffer[Node]={
      node.children.map(currentNode => traverseNodes(currentNode))
      result += node
    }
    traverseNodes(nodeBase).toList
  }

  def find[T](node: Node, idNode: T): Option[Node] = {

    if (node.nodeId == idNode)
      return Some(node)

    for (ownNode <- node.children) {
      val found = find(ownNode, idNode)
      if (found.isDefined)
        return found
    }
    None
  }

  /**
    * Retrieve a node with specific id
    * return Some(Node) if exist Any if NOT return None
    */

  def getNode(id:nodeId): Option[Node] ={
    val nodeRott = rootNode
    if (nodeRott==null)
      throw NullRootException("set a Tree'root node")
    find(nodeRott,id)
  }

  /**
    * Retrieve all nodes with a particular tag
    *
    */

  def getTag (id:tagId): List[Node] ={
    val result = mutable.ArrayBuffer[Node]()

    def traverseNodes(node: Node):ArrayBuffer[Node]={
      node.children.map(currentNode => traverseNodes(currentNode))
      if (node.tag.tagid.contentEquals(id))
        result += node
      else
        result
    }
    val nodeRott = rootNode
    if (nodeRott==null)
      throw NullRootException("set a Tree'root node")
    traverseNodes(nodeRott).toList
  }
}