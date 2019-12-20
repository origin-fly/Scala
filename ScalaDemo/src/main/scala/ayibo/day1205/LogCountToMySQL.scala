package ayibo.day1205


import org.apache.spark.{SparkConf, SparkContext}
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.DriverManager

object LogCountToMySQL {

  def main(args: Array[String]): Unit = {

    //创建spark环境
    val conf = new SparkConf().setAppName("LogCountToMySQL").setMaster("local")
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
    /*var conn:Connection = null
    var pst:PreparedStatement = null

    conn = DriverManager.getConnection("jdbc:mysql://10.50.12.55:3306/spark?characterEncoding=utf-8&useSSL=false",
      "root",
      "Abcd1234!")

    pst = conn.prepareStatement("insert into logcount values(?,?)")
    rdd1.foreach(
      t => {
        pst.setString(1,t._1)
        pst.setInt(2,t._2)
        pst.executeUpdate()
      }
    )*/

    rdd1.foreachPartition(saveToMysql)

    sc.stop()

  }

  //定义针对分区的数据库操作
  def saveToMysql(it:Iterator[(String,Int)]): Unit ={
    var conn:Connection = null
    var pst:PreparedStatement = null

    try{
      conn = DriverManager.getConnection("jdbc:mysql://10.50.12.55:3306/spark?characterEncoding=utf-8&useSSL=false",
        "root",
        "Abcd1234!")

      pst = conn.prepareStatement("insert into logcount values(?,?)")
      it.foreach(
        t => {
          pst.setString(1,t._1)
          pst.setInt(2,t._2)
          pst.executeUpdate()
        }
      )

    }catch {

      case t:Throwable => t.printStackTrace()

    }finally {
      if(pst != null) pst.close()
      if(conn != null) conn.close()
    }
  }

}
