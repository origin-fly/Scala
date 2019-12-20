package ayibo.day1210

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 3.使用Spark Streaming的 updateStateByKey 实现单词累计计数
  *
  * 默认情况下，Spark Streaming不记录之前的状态，每次发一条数据，都是从0开始计数
  * 但通过updateStateByKey此算子可实现累加
  *
  * 用法：设置检查点 把之前的结果都保存在检查点目录下 实现累加计算
  *
  */


object MyTotalNetworkWordCount {

  def main(args: Array[String]): Unit = {

    //创建运行时环境 local[2] 代表开启两个线程
    val conf = new SparkConf().setAppName("MyTotalNetworkWordCount").setMaster("local[2]")

    //接收两个参数，第一个conf，第二个采样时间间隔
    //spark streaming 是把连续的数据流变成不连续的RDD，即定时采样 每隔3秒
    val sc = new StreamingContext(conf,Seconds(3))

    //设置检查点目录

    sc.checkpoint("hdfs://10.50.12.55:9000/spark/checkPoint")

    //创建一个DStream 处理数据
    //第一个参数 地址  第二个参数 端口  第三个参数 缓存地址
    val lines = sc.socketTextStream("10.50.12.55",1234,StorageLevel.MEMORY_ONLY)

    //执行wordcount
    val words =lines.flatMap(_.split(" "))

    val wordPair = words.map(x => (x,1))

    /**
      * 定义一个匿名函数
      * 匿名函数的两个参数是API要求的
      * 第一个参数：当前的值是多少
      * 第二个参数：之前的结果是多少
      *
      */

    val addFunc = (curreValues:Seq[Int],previousValues:Option[Int]) =>{
      //进行累加计算
      //1.把当前的序列进行累加
      val currentTotal = curreValues.sum

      //2.在之前的值再进行累加
      //注意：要加判断 之前没有值 取0
      //some 返回值为some类型 因为要求是option类型
      Some(currentTotal + previousValues.getOrElse(0))

    }

    val total = wordPair.updateStateByKey(addFunc)
    total.print()

    sc.start()

    sc.awaitTermination()






  }

}
