package ayibo.day1210

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
  * 1.创建StreamingContext 核心：DStream 离散流 就是RDD的集合
  *
  * 2.DStream的表现形式：就是RDD
  *
  * 3.使用DStream把连续的数据流变成不连续的RDD
  *
  */


object MyNetworkWordCount {

  def main(args: Array[String]): Unit = {

    //创建运行时环境 local[2] 代表开启两个线程
    val conf = new SparkConf().setAppName("MyNetworkWordCount").setMaster("local[2]")

    //接收两个参数，第一个conf，第二个采样时间间隔
    //spark streaming 是把连续的数据流变成不连续的RDD，即定时采样 每隔3秒
    val sc = new StreamingContext(conf,Seconds(3))

    //创建一个DStream 处理数据
    //第一个参数 地址  第二个参数 端口  第三个参数 缓存地址
    val lines = sc.socketTextStream("10.50.12.55",1234,StorageLevel.MEMORY_ONLY)

    /**
      * 1.正常的map函数实现单词计数
      *
      */
    //单词统计
    val words =lines.flatMap(_.split(" "))
    //val wordCount = words.map((_,1)).reduceByKey(_+_)
    /**
      * 2.使用spark Streaming的transform实现单词计数
      *
      */
    val wordCount = words.transform(x => x.map(x => (x,1)))

    //结果打印
    wordCount.print()

    //启动 StreamingContext 进行计算
    sc.start()

    //等待任务结束
    sc.awaitTermination()


  }
}
