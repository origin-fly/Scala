package ayibo.day1129

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

/**
  *
  * scala 实现主从管理系统
  *
  * 代码思考设计
  * 首先需要四个类分别保存消息
  * Worker 保存worker消息
  * WorkerInfo 保存worker扩展消息
  * Master 保存master消息
  * ActorMessage 保存 worker和master之间通信消息
  *
  * RegisterWorker：worker向master注册
  * RegisterWorkerReply：master回复消息
  *
  * HeartBeatInfo：备忘录 定时向自己worker发送心跳信号
  * SendHeartBeat: 向master发送心跳信号
  *
  * CheckOfTimeOutWorker：mater维护worker列表，定时向自己发送超时检查
  *
  */


object MasterSlaveSanagementSystem {

/*  def main(args: Array[String]): Unit = {

    val host = "localhost"
    val port = 8888

    //创建ActorSystem的必要参数 拼配置文件指定host 和 port
    val configStr =

      s"""
         |akka.actor.provider = "akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname = "$host"
         |akka.remote.netty.tcp.port = "$port"
       """.stripMargin

    val config = ConfigFactory.parseString(configStr)

    //ActorSystem是单例的，用来创建Actor
    val actorSystem = ActorSystem.create("MasterActorSystem", config)

    //启动Actor，Master会被实例化，生命周期方法会被调用
    actorSystem.actorOf(Props[Master], "Master")

  }*/

}
