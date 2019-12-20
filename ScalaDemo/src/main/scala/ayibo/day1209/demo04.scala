package ayibo.day1209

import java.util.Properties

import org.apache.spark.sql.SparkSession

/**
  * 从hive中读取数据 将统计结果放到mysql中
  *
  * hive - spark -mysql
  *
  *执行命令
  * ./spark-submit --master spark://bigdata111:7077
  * --jars /opt/software/spark/jars/mysql-connector-java-8.0.11.jar
  * --driver-class-path  /opt/software/spark/jars/mysql-connector-java-8.0.11.jar
  * --class ayibo.day1209.demo04 /home/spark/ScalaDemo-1.0-SNAPSHOT.jar

  */

object demo04 {
  def main(args: Array[String]): Unit = {

    //使用spark session 创建运行环境
    val spark = SparkSession.builder().appName("HiveToMysql").enableHiveSupport().getOrCreate()
    val result = spark.sql("select * from hive.student order by id ")

    val props = new Properties()
    props.setProperty("user","root")
    props.setProperty("password","Abcd1234!")
    props.setProperty("driver","com.mysql.jdbc.Driver")

    //append 追加 overwrite 覆盖
    result.write.mode("overwrite")
      .jdbc("jdbc:mysql://10.50.12.55:3306/spark?characterEncoding=utf-8&useSSL=false","student",props)

    spark.stop()
  }

}
