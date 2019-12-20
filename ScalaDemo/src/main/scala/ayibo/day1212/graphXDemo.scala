package ayibo.day1212

import org.apache.spark.graphx.{Edge, Graph}
import org.apache.spark.{SparkConf, SparkContext}

object graphXDemo {

  def main(args: Array[String]): Unit = {
    //创建运行时环境 local[2] 代表开启两个线程
    val conf = new SparkConf().setAppName("graphXDemo").setMaster("local")

    val sc = new SparkContext(conf)

    val users = sc.parallelize(Array((3L,("rxin","student")),(7L,("jgonzal","postdoc"))
                ,(5L,("franklin","prof")),(2L,("istoica","prof"))))

    val relationships = sc.parallelize(Array(Edge(3L,7L,"collab"),Edge(5L,3L,"advisor")))

    val graph = Graph(users,relationships)

    val postdoc_count = graph.vertices.filter{case (id,(name,pos)) => pos == "prof"}.count

    println("postdoc_count: "+postdoc_count)

    val edges_count = graph.edges.filter(e => e.srcId > e.dstId).count()

    println("edges_count："+edges_count)

  }
}
