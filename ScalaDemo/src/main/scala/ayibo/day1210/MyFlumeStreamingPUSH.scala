package ayibo.day1210

import org.apache.spark.SparkConf
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * flume 直接将数据 push  推送到spark streaming 然后 spark接收 并将其转换为字符串
  *
  */

object MyFlumeStreamingPUSH {

  def main(args: Array[String]): Unit = {


    //创建运行时环境 local[2] 代表开启两个线程
    val conf = new SparkConf().setAppName("MyFlumeStreamingPUSH").setMaster("local[2]")

    //接收两个参数，第一个conf，第二个采样时间间隔
    //spark streaming 是把连续的数据流变成不连续的RDD，即定时采样 每隔3秒
    val sc = new StreamingContext(conf,Seconds(5))

    //创建flumeEvent DStream
    val flumeEvent = FlumeUtils.createStream(sc,"10.50.12.55",1234)

    //将flumeEvent事件转换成字符串
    val lines = flumeEvent.map(e => {
      new String (e.event.getBody.array)
    })

    lines.print()

    sc.start()
    sc.awaitTermination()
  }

}
