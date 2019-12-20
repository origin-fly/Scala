package ayibo.day1129

import java.util.UUID

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
  * scala worker节点
  *
  */

class Worker extends Actor {

  //master 引用 给master发消息
  var master : ActorSelection = null

  //worker唯一id
  val  id = UUID.randomUUID().toString


  override def receive: Receive = {

    case RegisterWorkerReply(id) => {
      //启动定时任务，向master发送心跳
      context.system.scheduler.schedule(0 millis,5000 millis,self,SendHeartBeat)
    }
    case SendHeartBeat =>{
      println("worker send heartbeat")
      master ! HeartBeat(id)
    }

  }

  //构造方法执行完执行一次
  override def preStart(): Unit = {

    //worker 向system发送链接请求

    master =context.system.actorSelection("akka.tcp://MasterActorSystem@localhost:8888/user/Master")

    //worker 向master发送注册信息
    master ! RegisterWorker(id,"localhost","10240","8")

  }
}

object Worker extends App{
  val clientPort = 8891

  //创建WorkerActorSystem的必要参数
  val configStr =
    s"""
       |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
       |akka.remote.netty.tcp.port = $clientPort
       """.stripMargin

  val config = ConfigFactory.parseString(configStr)

  val actorSystem = ActorSystem("WorkerActorSystem", config)

  //启动Actor，Master会被实例化，生命周期方法会被调用
  actorSystem.actorOf(Props[Worker], "Worker")
}