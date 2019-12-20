package ayibo.day1209

import java.util.Properties

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
  * spark读取本地文件 并将数据写入到mysql表中
  *
  */

object demo03 {
  def main(args: Array[String]): Unit = {
    //使用spark session 创建运行环境
    val spark = SparkSession.builder().master("local")
                            .appName("UnderstandSparkSession").getOrCreate()
    //从指定地址创建RDD
    val studentRDD = spark.sparkContext
                          .textFile("D:\\Study\\BigData\\input\\student.txt")
                          .map(_.split("\t"))

    //通过structType声明scheme
    val schema = StructType(
      List(
        StructField("id",IntegerType),
        StructField("name",StringType)
      )
    )
    //把RDD映射到rowRDD
    val rowRDD = studentRDD.map(p=>Row(p(0).toInt,p(1)))
    val studentDF = spark.createDataFrame(rowRDD,schema)

    //注册临时视图
    studentDF.createOrReplaceTempView("student")

    //执行sql
    val df = spark.sql("select * from student")
    //df.show()

    val props = new Properties()
    props.setProperty("user","root")
    props.setProperty("password","Abcd1234!")
    props.setProperty("driver","com.mysql.jdbc.Driver")

    //append 追加 overwrite 覆盖
    df.write.mode("append")
      .jdbc("jdbc:mysql://10.50.12.55:3306/spark?characterEncoding=utf-8&useSSL=false","student",props)


    spark.stop()
  }
}
