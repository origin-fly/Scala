package ayibo.day1209

import org.apache.spark.sql.SparkSession

/**
  * 使用case class创建表
  *
  */

object demo02 {
  def main(args: Array[String]): Unit = {

    //使用spark session 创建运行环境
    val spark = SparkSession.builder().master("local")
                            .appName("CaseClassDemo").getOrCreate()
    //从指定地址创建RDD
    val lineRDD = spark.sparkContext
                        .textFile("D:\\Study\\BigData\\input\\student.txt")
                        .map(_.split("\t"))

    val studentRDD = lineRDD.map( s => Student1(s(0).toInt,s(1)))
    //生成DataFrame 通过RDD 生成DF，导入隐式转换
    import spark.sqlContext.implicits._
    val studentDF =studentRDD.toDF

    //创建临时视图
    studentDF.createOrReplaceTempView("student")
    spark.sql("select * from student").show()
    spark.stop()
  }

}

case class Student1(stuId:Int,stuName:String)
