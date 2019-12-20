package ayibo.day1205

import org.apache.spark.{Partitioner, SparkConf, SparkContext}

import scala.collection.mutable


/**
  * 根据日志文件不同的jsp页面分区
  *
  */

object LogPartition {

  def main(args: Array[String]): Unit = {

    //创建spark环境
    val conf = new SparkConf().setAppName("LogPartition").setMaster("local")
    //创建SparkContext对象 上下文
    val sc = new SparkContext(conf)

    //读取数据
    val rdd1 = sc.textFile("D:\\Study\\BigData\\input\\localhost_access_log.2017-07-30.txt")
                  .map(
                    line => {
                      //根据引号解析字符串
                      // 192.168.88.1 - - [30/Jul/2017:12:53:43 +0800] "GET /MyDemoWeb/ HTTP/1.1" 200 259
                      //1.获取引号之内的东西
                      val index1 = line.indexOf("\"")
                      val index2 = line.lastIndexOf("\"")
                      val line1 = line.substring(index1+1,index2)

                      //2、得到两个空格之间的位置
                      val index3 =line1.indexOf(" ")
                      val index4 =line1.lastIndexOf(" ")
                      val line2 = line1.substring(index3+1,index4)
                      //得到jsp内容
                      val jspName = line2.substring(line2.lastIndexOf("/")+1)
                      (jspName,line)
                    }

                  )
    val jspList=rdd1.map(_._1).distinct().collect()
    //自定义分区规则 新建一个分区类
    var logPartition = new LogPartition(jspList)

    val rdd3 = rdd1.partitionBy(logPartition)

    //输出
    rdd3.saveAsTextFile("D:\\Study\\BigData\\output\\logPartition")
    sc.stop()

  }




}


class LogPartition(jspList:Array[String]) extends Partitioner{

  //定义一个集合来保存分区条件
  val partitionMap = new mutable.HashMap[String,Int]()
  var parID = 0  //分区号

  for (jsp <- jspList){
    partitionMap.put(jsp,parID)
    parID += 1
  }
  //返回多少个分区
  override def numPartitions: Int = partitionMap.size

  //根据名字，返回对应分区
  override def getPartition(key: Any): Int = partitionMap.getOrElse(key.toString(),0)
}
