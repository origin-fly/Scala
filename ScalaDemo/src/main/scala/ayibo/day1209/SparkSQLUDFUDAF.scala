package ayibo.day1209

import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * spark 自定义函数
  *
  * UDF 自定义普通函数
  * UDAF 自定义聚合函数
  *
  */

object SparkSQLUDFUDAF {
  def main(args: Array[String]): Unit = {

      val conf = new SparkConf().setMaster("local").setAppName("SparkSQLUDFUDAF")

      val sc = new SparkContext(conf)
      val sqlContext = new SQLContext(sc)

      val bigData = Array("Spark", "Spark", "Hadoop", "spark", "Hadoop", "spark", "Hadoop", "Hadoop", "spark", "spark")

      //创建DataFrame
      val bigDataRDD = sc.parallelize(bigData)

      val bigDataRow = bigDataRDD.map(item => Row(item))

      val structType = StructType(
        Array(
           new StructField("word",StringType)
        )
      )

      val bigDataDF = sqlContext.createDataFrame(bigDataRow,structType)

      bigDataDF.createOrReplaceTempView("bigdataTable")

      //UDF 最多能传22个参数  匿名函数
      //sqlContext.udf.register("computelength",(word:String)=>word.length)
      //sqlContext.sql("select word,computelength(word) length from bigdataTable").show()

      //UDAF
      sqlContext.udf.register("wordcount",new MyUDAF)
      sqlContext.sql("select word,wordcount(word) as count from bigdataTable group by word").show


      sc.stop()


  }

}

class MyUDAF extends UserDefinedAggregateFunction{

  /**
    * 该方法指定具体输入数据的类型
    * @return
    */
  override def inputSchema: StructType = StructType(Array(StructField("word", StringType, true)))

  /**
    * 在进行聚合操作的时候所要处理的数据的结果的类型  一般与最终的输出类型一致
    * @return
    */
  override def bufferSchema: StructType = StructType(Array(StructField("wordcount", IntegerType, true)))

  /**
    * 指定UDAF函数计算后返回的结果类型
    * @return
    */
  override def dataType: DataType = IntegerType

  /**
    * 确保一致性，一般都用true
    * @return
    */
  override def deterministic: Boolean = true

  /**
    * 在Aggregate之前每组数据的初始化结果
    * @param buffer
    */
  override def initialize(buffer: MutableAggregationBuffer): Unit = { buffer(0) = 0 }

  /**
    * 在进行聚合的时候，每当有新的值进来，对分组后的聚合如何进行计算
    * 本地的聚合操作，相当于Hadoop MapReduce模型中的Combiner
    * @param buffer
    * @param input
    */
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0) = buffer.getAs[Int](0) + 1
  }

  /**
    * 最后在分布式节点进行Local Reduce完成后需要进行全局级别的Merge操作
    * @param buffer1
    * @param buffer2
    */
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getAs[Int](0) + buffer2.getAs[Int](0)
  }

  /**
    * 返回UDAF最后的计算结果
    * @param buffer
    * @return
    */
  override def evaluate(buffer: Row): Any = buffer.getAs[Int](0)
}