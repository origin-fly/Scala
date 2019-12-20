package ayibo.day1210

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
  * Spark Streaming 集成 Spark SQL 通过RDD.toDF 转成DataFrame  创建临时视图
  * 在用spark.sql 写sql语句
  *
  */

object MyNetworkWordCountWithSQL {
  def main(args: Array[String]): Unit = {
    //创建运行时环境 local[2] 代表开启两个线程
    val conf = new SparkConf().setAppName("MyNetworkWordCountWithSQL").setMaster("local[2]")

    //接收两个参数，第一个conf，第二个采样时间间隔
    //spark streaming 是把连续的数据流变成不连续的RDD，即定时采样 每隔1秒
    val sc = new StreamingContext(conf,Seconds(5))

    //创建一个DStream 处理数据
    //第一个参数 地址  第二个参数 端口  第三个参数 缓存地址
    val lines = sc.socketTextStream("10.50.12.55",1234,StorageLevel.MEMORY_ONLY)

    //执行wordcount words 由一堆RDD组成
    val words =lines.flatMap(_.split(" "))
    words.foreachRDD(
      rdd => {
        val spark = SparkSession.builder().config(rdd.sparkContext.getConf).getOrCreate()

        import spark.implicits._
        val df = rdd.toDF("word")
        df.createOrReplaceTempView("words")
        spark.sql("select word , count(1) from words group by word ").show()

      }
    )

    sc.start()
    sc.awaitTermination()
  }
}
