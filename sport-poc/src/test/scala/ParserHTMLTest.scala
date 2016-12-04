import org.scalatest._

class ParserHTMLTest extends FlatSpec with Matchers  {

  /**
    *
    * Pending the most easy solutions is DEPLOY a mock website under
    * python -m SimpleHTTPServer
    * Start the server under the MOCKED WEBSITE
    *
    */

  /**
    * WHAT must be tested :
    *
    *   ParserHTML.parseHtml
    *   ParserHTML.getDocument
    *   ParserHTML.getHrefElement
    *   ParserHTML.getSpecificElement
    *
    */

  /**
    *
    * For example :
    *
    * Start phyton HTTPServer
    *  - cd web_site_saved i.e : premier_resultado.htm and the saved folders
    *  - python -m SimpleHTTPServer
    *
    * val rightval =   new ParserHTML("http://localhost:8000/premier_resultado.htm", 1)
    *                   .finalMatch
    * And then you can make all calculations
    *
    */


  "Test must be here" should "have tests" in {
    true should === (true)
  }
}

