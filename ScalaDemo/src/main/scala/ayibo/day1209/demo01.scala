package ayibo.day1209

import org.apache.spark.sql.{Row, SparkSession, types}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
  * 使用spark session 创建表
  * 注册临时视图 查询数据显示
  */

object demo01 {

  def main(args: Array[String]): Unit = {

    //使用spark session 创建运行环境 .master("local") 参数用于本地运行
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
    //显示数据
    df.show()

    //停止 spark session
    spark.stop()


  }

}
