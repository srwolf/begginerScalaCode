import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
case class ThatsOdd(i: Int) extends RuntimeException(s"odd $i received!")
import scala.util.{Try, Success, Failure}

val doComplete: PartialFunction[Try[String],Unit] = {

  case s @ Success(_) => println(s) //
  case f @ Failure(_) => println(f)

}


val futures = (0 to 9) map { // case i if i % 2 == 0 => Future.successful(i.toString)
  case i => Future.failed(ThatsOdd(i))
}

futures map (_ onComplete doComplete)