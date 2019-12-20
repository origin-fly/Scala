package ayibo.day1210

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

object RDDQueueStreaming {

  def main(args: Array[String]): Unit = {

    //创建运行时环境 local[2] 代表开启两个线程
    val conf = new SparkConf().setAppName("RDDQueueStreaming").setMaster("local[2]")

    //接收两个参数，第一个conf，第二个采样时间间隔
    //spark streaming 是把连续的数据流变成不连续的RDD，即定时采样 每隔3秒
    val sc = new StreamingContext(conf,Seconds(3))

    val rddQueue = new mutable.Queue[RDD[Int]]()

    for (i <- 1 to 10 ){
      rddQueue += sc.sparkContext.makeRDD(1 to 10)
      Thread.sleep(3000)
    }

      //从队列中接收数据
    val inputStreaming = sc.queueStream(rddQueue)

    val result = inputStreaming.map(x => (x,x*2))

    result.print()

    sc.start()

    sc.awaitTermination()

  }
}
