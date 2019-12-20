package ayibo.day1203

import org.apache.spark.{SparkConf, SparkContext}

/**
  * scala 版本的wordcount
  *
  */

object TestWordCount {

  def main(args: Array[String]): Unit = {

    //1、创建Spark环境  new SparkConf() 创建配置文件 setAppName 设置运行spark任务时的名字 setMaster 设置运行的master

    //如果是集群模式，master不用设置 spark-submit会指定
    //本地测试。master 设置成local
    val conf = new SparkConf().setAppName("TestWordCount").setMaster("local")

    //创建SparkContext对象 上下文
    val sc = new SparkContext(conf)

    //读取本地文件
    /**
      * sc.textFile 读取文件数据
      * flatMap 将读取的数据按照规则切分
      * map 对切分的数据进行计数
      * reduceByKey 对key进行分组 相同的key相加
      */

    var result = sc.textFile("D:\\Study\\BigData\\input\\wordct.txt")
                    .flatMap(_.split(" "))
                    .map((_,1))
                    .reduceByKey(_+_)
    //打印信息
    result.foreach(println)
    //停止sc
    sc.stop()

  }

}
