package com.ldg.sport.stats

import com.ldg.miscellaneous.errors.{JsoupFailure, FailureTrait, NonExistentTagFailure}
import com.ldg.miscellaneous.wrapper.results.StatsTypes.{urlLink, htmlTag}
import com.ldg.miscellaneous.wrapper.results.WrapperResult.Result
import com.ldg.sport.stats.Teams.{AwayTeam, HomeTeam}
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements
import com.ldg.miscellaneous.utilities.Utilities._

import scala.util.{Failure, Success, Try}

/**
  * ref : http://jsoup.org/cookbook/extracting-data/selector-syntax
  * This parser is relate to http://www.resultados-futbol.com
 */

class ParserHTML(url: String, season:Int) {

	val MATCH_LINK = "/partido/"
	val PREFIX_link = "http://www.resultados-futbol.com"

	/**
		* Parse URL with jsoup
		* Add url + partido + the_specific_game
		**/

	def parseHtml(): Set[String] = {
		getDocument(url) match {
			case Success(document) => getHrefElement(document).toSet.filter(link =>
				link.attr("href").startsWith(MATCH_LINK))
				.map(link => PREFIX_link + link.attr("href"))
			case Failure(e) =>
				println("Info from the exception: " + e.getMessage)
				Set.empty[String]
		}
	}

	/**
		*
		* For get controlled exceptions. If goes wrong the algortithm go to the next link.
		* Program don´t stop if something fail. Exception is stored and goes for the next
		*
    */
	def getDocument: String => Try[Document] = link => {
		Try(
			//can deal an IOException
			org.jsoup.Jsoup.connect(link).
				timeout(100 * 1000).userAgent("Chrome 41.0.2228.0").get()
		)
	}

		/**
			* If NOT exists href tags, Not Match that day and return an empty collections(List)
			*/
		def getHrefElement(doc: Document): List[Element] = {
			val linksElements: Elements = doc.select("a[href]")
			if (linksElements.size() > 0) {
				val elements: List[Element] = (0 until linksElements.size()).map(index => linksElements.get(index)).toList
				elements
			} else {
				Nil
			}
		}

		/**
			* matchSet: a set that contain all links that will be crowled
			* for get info about matches
			*/
		def parseMatches(matchSet: Set[String]): Set[Result] = {

			//An example of matchLink example:http://www.resultados-futbol.com/partido/Southampton-Fc/Tottenham-Hotspur-Fc/1993
			matchSet.map { matchLink =>
				buildMatch(matchLink) match {
					case Right(matches) => Result(None,Some(matches))
					case Left(fail) => Result(Some(fail),None)
				}
			}
		}

	/**
		* All successfull operations are under this Set
		* @see com.ldg.miscellaneous.wrapper.results.Result.
		* In case of unsuccessful operations try with the following FILTER:
		* resultWithMatches=> !resultWithMatches.matches.isDefined
		*
		*
		*/
	val finalMatch: Set[Match] = parseMatches(parseHtml)
		.filter(
			resultWithMatches=>
				resultWithMatches.matches.isDefined
		)
		.map(matches=>getOpt[Match](matches.matches))
	finalMatch::Nil

	def getHomeTeam(homeTeamName:Option[Elements],homeGoal:String,homeLocation:Option[Elements]):HomeTeam= HomeTeam(
		getOpt[Elements](homeTeamName).`val`().replaceAll("'", "''"),
		homeGoal.toInt,
		getOpt[Elements](homeLocation).text().replaceAll("'", "''")
	)

	def getAwayTeam(awayTeamName:Option[Elements],awayGoal:String) = AwayTeam(
		getOpt[Elements](awayTeamName).`val`().replaceAll("'", "''"),awayGoal.toInt , ""
	)

	/**
		* If something goes WRONG we rise an exception BUT just for this hit, the principal threat will go on.
		*
		* @param tag tag content to extract
		* @param tagNum minimal tag cuantity for a valid document
		* @param documents the document requested
    * @return if everything OK return the element else return an error but programs always go on
    */
	def getSpecificElement(tag: htmlTag, tagNum: Int)(documents: Try[Document]): Either[FailureTrait, Option[Elements]] =

			documents match {

				case Success(document) => {
					val tagInClass: Elements = document.select(tag)
					if (tagInClass.size() > tagNum)
						Right(Some(tagInClass))
					else
						Left(NonExistentTagFailure(tag, "nonexistent tag exception"))
				}

				case Failure(e) =>
					Left(JsoupFailure(tag, "Jsoup IOException: " + e.getMessage))
			}

			//topics about fold an Option
			//https://kwangyulseo.com/2014/05/21/scala-option-fold-vs-option-mapgetorelse/
			// Inspecting in page Match info (Teams and result) (".claseR")
			def buildMatch(link: urlLink): Either[FailureTrait, Match] = {

				val matchHtml: Try[Document] = getDocument(link)

				for {
					homeTeamName <- getSpecificElement("input#nameshow1", 0)(matchHtml).right
					awayTeamName <- getSpecificElement("input#nameshow2", 0)(matchHtml).right
					homeLocation <- getSpecificElement(".es", 0)(matchHtml).right
					date <- getSpecificElement(".jor-date", 0)(matchHtml).right
					matchTable <- getSpecificElement(".claseR", 0)(matchHtml).right
					homeGoal <- Right(getOpt[Elements](matchTable).get(1).text()).right
					awayGoal <- Right(getOpt[Elements](matchTable).get(0).text()).right

				} yield {
					Match(getHomeTeam(homeTeamName,homeGoal,homeLocation), getAwayTeam(awayTeamName,awayGoal), getOpt[Elements](date).attr("content"), season)
				}

			}
}