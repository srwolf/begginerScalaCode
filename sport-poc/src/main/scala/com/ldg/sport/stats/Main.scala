package com.ldg.sport.stats

import java.io.{PrintStream, FileOutputStream, File}

import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.{Await, Future}

import scala.util.{Try, Success, Failure}

object Main extends App {

    val defaultFileName = "csvFile"

    val filename = args.length match {
         case filename if (filename == 1) => args(0)
         case _ => defaultFileName
     }

  val file: File = new File(filename + ".txt")

  val fis: FileOutputStream = new FileOutputStream(file)

  val out: PrintStream = new PrintStream(fis)

  //print to our filename every requested hit between our specific RANGE
  System.setOut(out)

  /**
    *
    * The following example shows the resulting url, consists of all matches of premier-league
    * from 1995 to 2016.
    *
    * For example a requested page in the latter range could be:
    * http://www.resultados-futbol.com/premier1999/grupo1/jornada1
    *
    * We are creating an asynchronous calculations. Every calculation is a Future but a successfull
    * Future (Future.successful) because we are dealing with controlled exceptions and finalMatch
    * have successfull match ONLY!!
    *
    * If ALL calculations FAILS resultMatch38Season will be EMPTY
    *
    * The web site selected http://www.resultados-futbol.com is a real example but some data are not right
    * so it must be used ONLY for test cases due to some date have some mistakes.
    *
    */

  /**
    * Range of calculations
    * 2016>= beginyear > 1995
    * 39 >=beginSeas > 0
    *
    */

  val begYear:Int = 2014 //1996
  val endYear:Int = 2016
  val begSeas:Int = 1
  val endSeas:Int = 4 //39

  val resultMatch38Season: List[Future[Set[Match]]] = (begYear until endYear).flatMap(
    year => (begSeas until endSeas).map {
      jornada =>
        Future.successful {
          new ParserHTML(
            "http://www.resultados-futbol.com/premier" + Integer.toString(year) + "/grupo1/jornada" + jornada, jornada
          ).finalMatch
        }
    }
  ).toList

  def doComplete: PartialFunction[Try[Set[Match]],Unit] = { //

    case matches @ Success(_) =>{
      val tmpMatch:Set[Match] = matches.getOrElse(Set.empty[Match])
      val resultMatch:List[Match] = tmpMatch.toList
      resultMatch.foreach(matches=>
        println(
          "hometeam:" +
            matches.homeTeam.name + " ,awayteam:" + matches.awayTeam.name + " ,location: " + matches.homeTeam.location +", date:" +matches.date  ))}
    case fail @ Failure(_) => println(fail.exception.getMessage)
  }

  /**
    * traversing all futures.For every future
    */

  resultMatch38Season foreach (future=> future onComplete {
    Thread.sleep(1)
    doComplete
  })
}


