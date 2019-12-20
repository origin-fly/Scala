package ayibo.day1126


import java.io.{File, FileInputStream, FileWriter, PrintWriter}

import scala.io.Source._
object ScalaIO {

  def main(args: Array[String]): Unit = {

    var source = fromFile("D:\\Study\\BigData\\input\\order.txt")
    println("-----mkString拼接字符串读取文件----")
    //println(source.mkString)

    println("-----按行读取文件----")
    //val lines = source.getLines()
    //lines.foreach(println)

    println("-----读取字符----")
    //for (c <- source) print(c)

    //从url或者其他数据源读取  http:/www.baidu.com
   /* println("-------forURL-------")
    var source2 = fromURL("http://www.baidu.com","UTF-8")
    println(source2.mkString)*/

    //读取二进制文件
    println("-------read Bytes-------")

    /*var file = new File("D:\\Study\\BigData\\input\\123.txt")
    var in = new FileInputStream(file)
    var buffer = new Array[Byte](file.length().toInt)
    in.read(buffer)
    println(buffer.length)
    in.close()*/


    //写入文本文件
   /* println("-------Write Files-------")
    var out = new PrintWriter()("D:\\Study\\BigData\\input\\in20191125.txt")
    for (i <- 0 until 10) out.println(i)
    out.close()*/

  }
}
