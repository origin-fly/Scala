package ayibo.day1212


import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.sql.SparkSession


object linearRegression {

  def main(args: Array[String]): Unit = {

    //通过SparkSession 创建spark运行环境
    val spark = SparkSession.builder().appName("linearRegression").master("local").getOrCreate()

    //读取数据
    val trainning = spark.read.format("libsvm").load("D:\\Study\\BigData\\input\\sample_linear_regression_data.txt")

    //声明模型并设置训练的次数
    val lr = new LinearRegression().setMaxIter(10000000)

    //开始训练，传入训练数据
    val lrModel = lr.fit(trainning)

    //查看模型训练结果
    val trainningSummary = lrModel.summary

    //查看预测效果，测试集
    trainningSummary.predictions.show()

    println(s"RMSE : ${trainningSummary.rootMeanSquaredError}")

    spark.stop()


  }

}
