import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

//Taxnonomy Tree
//Generic Tree
class TaxonomyTree {

  type tagId = String
  type nodeId = String
  type idLang = String
  type descLang = String

  private var _rootNode:Node=null

  //Getter
  def rootNode = _rootNode

  //Setter
  def node_= (node:Node):Unit = _rootNode = node

  /**
    * Exception for Root Node null
    * @param message
    */
  case class NullRootException(message: String) extends Exception(message)

  /**
    * Model Languages: Specific Tags for each language i.e: fr_FR: Chinois
    *
    * @param language
    */
  case class TagLang(language: Map[idLang,descLang])

  /**
    * Model Tag: Every Tag point to several languages i.e:
    * Tag: (chinese,Seq(TagLang(en_GB->Chinese), TagLang(fr_FR->Chinois), TagLang( it_IT->Cinese)))
    *
    * @param tagid
    * @param tagLang
    */

  case class Tag(tagid:tagId,tagLang: Seq[TagLang])

  //Node of Generic tree instead of Binary tree can have more than two children
  case class Node(nodeId: nodeId,tag:Tag, children: Seq[Node] ,isRootNode:Boolean=false){
    if (isRootNode){
        node_=(this)
    }
  }

  /**
    * Retrieve all the descendants of a node
    *
    * @param nodeBase node form which extract begin
    * @return List of nodes
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
    * Retrieve a node by id
    * @param id
    * @return Some(Node) if exist Node None if NOT
    */
  def getNode(id:nodeId): Option[Node] ={
    val nodeRott = rootNode
    if (nodeRott==null)
      throw NullRootException("set a Tree'root node")
    find(nodeRott,id)
  }

  /**
    * Retrieve all the nodes with a particular tag
    * @param id
    * @return
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