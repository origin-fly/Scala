package ayibo.day1203;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName TestWordCountJava
 * @Description  Java版本WordCount
 * @Author nieyafei
 * @Date 2019-12-03 17:58
 * Version 1.0
 **/
public class TestWordCountJava {

    public static void main(String[] args) {

        //创建spark环境
        SparkConf conf = new SparkConf().setAppName("TestWordCountJava").setMaster("local");

        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> lines = sc.textFile("D:\\Study\\BigData\\input\\wordct.txt");

        //进行切分
        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {

            public Iterator<String> call(String input) throws Exception {
                return Arrays.asList(input.split(" ")).iterator();
            }
        });

        JavaPairRDD<String, Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {
            public Tuple2<String, Integer> call(String arg0) throws Exception {
                return new Tuple2<String, Integer>(arg0, 1);
            }
        });

        JavaPairRDD<String, Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer arg0, Integer arg1) throws Exception {
                return arg0 + arg1;
            }
        });

        List<Tuple2<String, Integer>> collect = counts.collect();
        for (Tuple2<String, Integer> tuple2 : collect) {
            System.out.println(tuple2._1()+" : "+tuple2._2());
        }


    }
}
