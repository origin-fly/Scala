package ayibo.day1210

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object FileStreaming {
  def main(args: Array[String]): Unit = {

    //创建运行时环境 local[2] 代表开启两个线程
    val conf = new SparkConf().setAppName("FileStreaming").setMaster("local[2]")

    //接收两个参数，第一个conf，第二个采样时间间隔
    //spark streaming 是把连续的数据流变成不连续的RDD，即定时采样 每隔3秒
    val sc = new StreamingContext(conf,Seconds(3))

    val lines = sc.textFileStream("D:\\Study\\BigData\\input\\input2")

    lines.print()

    sc.start()

    sc.awaitTermination()

  }

}
