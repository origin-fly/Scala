package ayibo.day1122

/**
  * scala 循环
  */

import scala.math.sqrt
import util.control.Breaks._

/**
  * object 相当于Java中的static （静态）
  *
  * java中使用static关键字表示静态，scala中没有static关键字，使用object表示
  * object中所有内容都是静态的
  *
  *
  */
object Demo01 {

  def main(args: Array[String]): Unit = {

    var list = List("Tom","James","Kebo","Wade","Curry")

    println("--------for循环第一种写法------------")
    for (s <- list) println(s)

    println("--------for循环第二种写法------------")
    for{
      s <- list
      if(s.length >3 )
    }println(s)

    println("--------for循环第三种写法------------")
    for(s <- list if s.length >3 ) println(s)


    println("--------for循环第四种写法------------")
    var newList =for {
      s <- list
      s1 = s.toUpperCase()
    }yield (s1)
    for (s <- newList)println(s)

    println("--------while循环------------")
    var i : Int =0
    while(i < list.length){
      println(list(i))
      i +=1
    }

    println("--------do while循环------------")
    var j :Int =0
    do{
      println(list(j))
      j +=1

    }while( j < list.length)

    println("--------foreach循环------------")
    //foreach函数，就是把list中的每一个元素取出来，赋给参数的函数-> println  打印出来list中每个元素
    list.foreach(println)

    println("--------判断101-200之间有多少个素数------------")

    /**
      * 判断101-200之间有多少个素数
      *
      * 程序分析：
      * 判断素数的方法：
      * 用一个数分别去 除2 到 sqrt(这个数)，如果可以被整除，则表明此数不是素数，反之是素数。
      *
      * 程序实现方法：
      * 定义两层循环
      *   第一层：101-200
      *   第二层：2 - sqrt(第一层循环的数)
      *
      * 举例：16
      *   2 循环到 sqrt(16) = 4
      */

    var count=0
    for (i <- 101 until 200){
      //标识能否整除
      var flag = false

      breakable{
        var j=2
        while (j <= sqrt(i)){
          if(i % j == 0){
            flag=true
            break
          }
          j +=1
        }
      }
      if (!flag) count += 1
    }
    println("个数为："+count)

  }

}
