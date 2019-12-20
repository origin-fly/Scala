package ayibo.day1205

import java.sql.DriverManager

import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.{SparkConf, SparkContext}

//使用jdbcRDD操作数据库

object JdbcRDD {


  val connection =() =>{
    Class.forName("com.mysql.jdbc.Driver").newInstance()
    DriverManager.getConnection("jdbc:mysql://10.50.12.55:3306/sqoop?characterEncoding=utf-8&useSSL=false",
    "root",
    "Abcd1234!")
  }


  def main(args: Array[String]): Unit = {

    //创建spark环境
    val conf = new SparkConf().setAppName("JdbcRDD").setMaster("local")
    //创建SparkContext对象 上下文
    val sc = new SparkContext(conf)

    val mysqlRDD = new JdbcRDD(sc,connection,"select * from student where id > ? and id < ?",
      2,6,2,r => {
        val id = r.getInt(1)
        val name = r.getString(2)
        (id ,name)
    })
    val result =mysqlRDD.collect()
    println(result.toBuffer)
    sc.stop()
  }
}
