package ayibo.day1128

/**
  * scala actor
  *
  */

import akka.actor.{Actor, ActorSystem, Props}

class HelloActor extends Actor{

   def receive= {
     /**
       * 使用case来处理不同类型的消息
       *
       */
     case "hello" => println("您好")
     case _  => println("您是？")
  }

}

object HelloActor {

  def main(args: Array[String]): Unit = {

    //新建一个ActorSystem

    val system = ActorSystem("HelloSystem")
    //创建Actor
    val helloActor = system.actorOf(Props[HelloActor],name="helloActor")

    helloActor ! "hello"
    //helloActor ! "helsdsdsdalo"
  }

}


