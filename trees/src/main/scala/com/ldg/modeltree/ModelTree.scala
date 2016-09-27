package com.ldg.modeltree

/**
  * Created by ldipotet on 27/9/16.
  */
object ModelTree {

  type tagId = String
  type nodeId = String
  type idLang = String
  type descLang = String
  /**
    * Model Languages: Specific Tags for each language i.e: fr_FR: Chinois
    *
    */
  case class TagLang(language: Map[idLang,descLang])


  /**
    * Model Tag: Every Tag point to several languages i.e:
    * Tag: (chinese,Seq(TagLang(en_GB->Chinese), TagLang(fr_FR->Chinois), TagLang( it_IT->Cinese)))
    *
    */

  case class Tag(tagid:tagId,tagLang: Seq[TagLang])

  //Node of Generic tree instead of Binary tree can have more than two children
  case class Node(nodeId: nodeId,tag:Tag, children: Seq[Node] ,isRootNode:Boolean=false)
}
