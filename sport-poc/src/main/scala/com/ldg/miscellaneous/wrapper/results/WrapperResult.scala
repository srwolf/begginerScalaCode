package com.ldg.miscellaneous.wrapper.results

import com.ldg.miscellaneous.errors.FailureTrait
import com.ldg.sport.stats.Match

/**
  * Created by ldipotet on 5/11/16.
  */
object WrapperResult {

  /**
    * Model Result: a wrapper for match outcome.For every match:
    * if success => Result(None,Option[Match])
    * if fail => Result(Option[FailureTrait],None)
    */

  case class Result(failures:Option[FailureTrait],matches:Option[Match])

  /**
    * Model time
    * @param currentHour
    * @param currentMinute
    * @param amOrPm
    */

  case class Times(currentHour:String,currentMinute:String,amOrPm:String)
}
