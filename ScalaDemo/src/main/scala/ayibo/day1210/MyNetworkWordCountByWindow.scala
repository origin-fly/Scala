package ayibo.day1210

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 窗口操作  该模式用于定时统计 比如 统计每天 访问量 或者每小时访问量等
  *
  */

object MyNetworkWordCountByWindow {

  def main(args: Array[String]): Unit = {
    //创建运行时环境 local[2] 代表开启两个线程
    val conf = new SparkConf().setAppName("MyTotalNetworkWordCount").setMaster("local[2]")

    //接收两个参数，第一个conf，第二个采样时间间隔
    //spark streaming 是把连续的数据流变成不连续的RDD，即定时采样 每隔1秒
    val sc = new StreamingContext(conf,Seconds(1))

    //创建一个DStream 处理数据
    //第一个参数 地址  第二个参数 端口  第三个参数 缓存地址
    val lines = sc.socketTextStream("10.50.12.55",1234,StorageLevel.MEMORY_ONLY)

    //执行wordcount
    val words =lines.flatMap(_.split(" ")).map(x => (x,1))

    /**
      * 需求：
      * 每过10秒 把过去30秒的数据读取出来
      * 即：窗口长度30秒，滑动距离10秒
      *
      * 注意：滑动距离必须是定时采样的整数倍 即 10 % 1 ==0
      *
      */

    val result = words.reduceByKeyAndWindow((x:Int,y:Int) => (x+y),Seconds(30),Seconds(10))

    result.print()

    sc.start()

    sc.awaitTermination()





  }

}
