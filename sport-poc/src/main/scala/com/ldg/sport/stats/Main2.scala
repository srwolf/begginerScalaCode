package com.ldg.sport.stats

import java.io.{PrintStream, FileOutputStream, File}

import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.util.Try

/**
	*  We launch all operations in a concurrent way and the is blocked the execution
	*  waiting until we have a result. Obviously we are NOT in an asynchronous processing.
	*
	*  It is NOT the right solution, sometimes in test scenarios it is a good choice.
	*
	*  The questions is :
	*  Is our process asynchronous ? If YES the next question is how long can we want wait after we have made
	*  all operations an only are waiting te result.
	*
	*  ANY WAY blocking never is the best choise affer a concurrent soluctions
	*  De cualquier modo no es la mejor opcion bloquear un resultado luego de realizar sus calculos de un
	*  modo concurremnte.
	*
	*  REF:
	*  http://stackoverflow.com/questions/21417317/await-a-future-receive-an-either
	*
	**/

/*
 Table of measures of time for the object duration in away mode

  private[this] val timeUnitLabels = List(
    DAYS         -> "d day",
    HOURS        -> "h hour",
    MINUTES      -> "min minute",
    SECONDS      -> "s sec second",
    MILLISECONDS -> "ms milli millisecond",
    MICROSECONDS -> "µs micro microsecond",
    NANOSECONDS  -> "ns nano nanosecond"
  )

*/

/* UNCOMMENT this code For synchronous calculation, a WRONG but sometimes a necessary practices.
   Make calculations an wait for the calculations ends.

object Main2 extends App {

	val defaultFileName = "csvFile"

	val filename = args.length match {
		case filename if (filename == 1) => args(0)
		case _ => defaultFileName
	}

	val file: File = new File(filename + ".txt")

	val fis: FileOutputStream = new FileOutputStream(file)

	val out: PrintStream = new PrintStream(fis)

	System.setOut(out)
*/

	/**
		*
		* The following example shows the resulting url, consists of all matches of the first day of 1996		*
		* http://www.resultados-futbol.com/premier1999/grupo1/jornada1
		*
		**/
/*
	val resultMatch38Season: List[Future[Set[Match]]] = (1996 until 2016).flatMap(
		year => (1 until 39).map {
			jornada =>
				Future.successful {
					new ParserHTML(
						"http://www.resultados-futbol.com/premier" + Integer.toString(year) + "/grupo1/jornada" + jornada, jornada
					).finalMatch
				}
		}
	).toList

	val resultMatch38SeasonSeq = Future.sequence(resultMatch38Season)

	val awaitable = Try {
		Await.result(resultMatch38SeasonSeq, Duration.Inf)
	}

	awaitable.get.foreach(setmtach=>setmtach.foreach(matches=>
		println(
			"hometeam:" +
				matches.homeTeam.name +
				" ,awayteam:" + matches.awayTeam.name +
				" ,location: " + matches.homeTeam.location +", date:" +matches.date
		))
	)
}

*/
