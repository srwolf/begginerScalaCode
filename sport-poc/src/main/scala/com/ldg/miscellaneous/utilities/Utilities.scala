package com.ldg.miscellaneous.utilities

import java.text.SimpleDateFormat
import java.util.Calendar

import com.ldg.miscellaneous.wrapper.results.WrapperResult.Times

/**
  * Utility object
  * Created by ldipotet on 2/11/16.
  */
object Utilities {

  // get a typed None
  def fNone[T]: Option[T] = None

  def rightOrLeft[F, S](op: => Option[S])(fail: => F): Either[F, S] = {

    val o = op
    if (o.isDefined) Right(o.get) else Left(fail)
  }

  //get Option values. This method is for internal use. Call ONLY
  //when be sure that there is a <SOME> value

  def getOpt[T](op: => Option[T]):T = op.get

  /**
    * Get the current time in the following format:
    *
    *
    * currentHour: String = 01
    * currentMinute: String = 14
    * amOrPm: String = AM
    */
  def getToday():Times = {

    val today = Calendar.getInstance().getTime()

    // create the date/time formatters
    val minuteFormat = new SimpleDateFormat("mm")
    val hourFormat = new SimpleDateFormat("hh")
    val amPmFormat = new SimpleDateFormat("a")

    val currentHour = hourFormat.format(today)
    val currentMinute = minuteFormat.format(today)
    val amOrPm = amPmFormat.format(today)

    Times(currentHour,currentMinute,amOrPm)

  }


}
