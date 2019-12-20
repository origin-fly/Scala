package ayibo.day1205

import org.apache.spark.{SparkConf, SparkContext}

/**
  * 根据日志信息求出访问量最高的两个网页
  *
  */

object LogCount {

  def main(args: Array[String]): Unit = {

    //创建spark环境
    val conf = new SparkConf().setAppName("LogCount").setMaster("local")
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

                      (jspName,1)
                    }
                  )

    //聚合
    val rdd2 = rdd1.reduceByKey(_+_)

    //排序
    val rdd3 = rdd2.sortBy(_._2,false)

    //取出访问量最高的两个网页
    rdd3.take(2).foreach(println)

    sc.stop()


  }

}
