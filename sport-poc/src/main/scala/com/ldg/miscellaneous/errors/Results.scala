package com.ldg.miscellaneous.errors

/**
  * Error model.
  *
  * Created by ldipotet on 2/11/16.
  */

sealed trait FailureTrait {
  val failuresTags: String
}
 // NonExistentTagFailure: is an exception that rise when the tag that you are looking does not exists.
 case class NonExistentTagFailure(failuresTags: String,message:String) extends FailureTrait

 // AmountCuantityTagFailure: is an exception that rise when the cuantity of tags tha you get in your html
 // page is different that you expect.
 case class AmountCuantityTagFailure(failuresTags: String,numRealTag: Int) extends FailureTrait

 // JsoupFailure: is an exception that rise when you try to get a Josuop object instance an for any reason you
 // get an exception i.e if you can't get an http connection
 case class JsoupFailure(failuresTags: String,message:String) extends FailureTrait
