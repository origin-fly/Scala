
package ayibo.day1128

/**
  * scala 两个actor通信
  */
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
case object AMessage
case object BMessage
case object startMessage
case object stopMessage

class A extends Actor{

  def receive = {

    case BMessage => sender ! AMessage
    case stopMessage =>
      println("stop count 9")
      context.stop(self)
  }

}

class B(a:ActorRef) extends Actor{

  var count =0
  def increment = {
    count +=1
    println("count + 1")
  }

  def receive = {

    case AMessage =>
      if(count >9){
        sender ! stopMessage
        context.stop(self)
      }else{
        increment
        println("receive A message")
        sender ! BMessage
      }
    case startMessage => a ! BMessage

  }

}

object ActorToActor {

  def main(args: Array[String]): Unit = {

    val system = ActorSystem("ABSystem")
    val a = system.actorOf(Props[A],name="A")
    val b = system.actorOf(Props(new B(a)),name="B")

    b ! startMessage

  }

}

