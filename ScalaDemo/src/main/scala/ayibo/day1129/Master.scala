package ayibo.day1129

/**
  * scala master节点
  *
  */

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._


class Master extends Actor{

  //worker列表
  val idToWorker = new mutable.HashMap[String,WorkerInfo]

  //保存所有Worker信息的Set
  val workers = new mutable.HashSet[WorkerInfo]()

  //worker 超时时间
  val WORKER_TIMEOUT = 10 * 1000


  //构造方法执行完执行一次
  override def preStart(): Unit = {

    //启动定时任务，定时检查超时节点
    context.system.scheduler.schedule(5 millis,WORKER_TIMEOUT millis,self,CheckOfTimeOutWorker)

  }

  override def receive: Receive = {

    case RegisterWorker(id,workerHost,memory,cores) => {
      if (! idToWorker.contains(id)){
        val worker = new WorkerInfo(id,workerHost,memory,cores)
        workers.add(worker)
        idToWorker(id)=worker
        println("new register worker" + worker)
        sender() ! RegisterWorkerReply(worker.id)
      }
    }
    case HeartBeat(id) => {
      val workerInfo = idToWorker(id)
      println("get heartbeat message from : "+workerInfo)
      workerInfo.lastHeatBeat = System.currentTimeMillis()
    }

    case CheckOfTimeOutWorker => {
      val currentTime = System.currentTimeMillis()
      val toRemove = workers.filter(workerInfo => currentTime - workerInfo.lastHeatBeat > WORKER_TIMEOUT )
      for (worker <- toRemove){
        workers -= worker
        idToWorker.remove(worker.id)
      }
      println("worker size + " + workers.size)
    }
  }

}

object Master extends App{
  val host = "localhost"
  val port = 8888

  //创建ActorSystem的必要参数

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
}