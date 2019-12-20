package ayibo.day1210

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.flume.FlumeUtils


/**
  * custom sink模式 生产环境常用此模式 有更好的健壮性和容错性
  * 与flume push 的区别就是createPollingStream
  *
  *
  */

object MyFlumeStreamingSINK {

  def main(args: Array[String]): Unit = {
    //创建运行时环境 local[2] 代表开启两个线程
    val conf = new SparkConf().setAppName("MyFlumeStreamingPUSH").setMaster("local[2]")

    //接收两个参数，第一个conf，第二个采样时间间隔
    //spark streaming 是把连续的数据流变成不连续的RDD，即定时采样 每隔3秒
    val sc = new StreamingContext(conf,Seconds(5))

    //创建flumeEvent DStream
    val flumeEvent = FlumeUtils.createPollingStream(sc,"10.50.12.55",1234,StorageLevel.MEMORY_ONLY)

    val lines = flumeEvent.map( e => {
      new String(e.event.getBody.array)
    })

    lines.print()

    sc.start()

    sc.awaitTermination()

  }

}
