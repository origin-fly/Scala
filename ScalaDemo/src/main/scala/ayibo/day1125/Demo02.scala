package ayibo.day1125

/**
  * scala 实现八大排序算法
  */

object Demo02 {

  def main(args: Array[String]): Unit = {

    var array :Array[Int] = Array(3,5,6,12,23,45,34,21,56,76)

    println("------------排序前-------------")
    array.foreach(println)

/*
    println("-----------1、冒泡排序-------------")

    /**
      * 算法分析：
      * 1、比较相邻的元素。如果第一个比第二个大，就交换。
      * 2、对每一对相邻元素做相同的工作，从左往右，第一轮遍历完以后，最后的元素会是最大的数。
      * 3、针对所有的元素重复以上步骤，除了已经排序好的元素。
      *
      * 程序分析：
      * 1、两层循环。
      * 2、外层循环控制比较的次数。
      * 3、内层循环控制到达的位置，也就是结束比较的位置。
      */

    for( i <- 0 until array.length){
      for (j <- 0 until array.length -1 - i){
        //判断大小
        if (array(j) > array(j+1)){
          var tmp = array(j+1)
          array(j+1) = array(j)
          array(j) = tmp
        }
      }
    }
    println("------------排序后-------------")
    array.foreach(println)



    println("-----------2、直接插入排序-------------")

    for (i <- 0 until array.length){
      for (j <- 0 until i){
        //遇到比i大的就交换
          if(array(i) < array(j)){
            var tmp =array(j)
            array(j)=array(i)
            array(i)=tmp
          }
      }
    }
    println("------------排序后-------------")
    array.foreach(println)
*/

    println("-----------3、希尔排序-------------")

    var step =  math.ceil(array.length/2).toInt

    while (step >0){
      for (i <- 0 until step){
        for (j <- step+i until array.length-1){
          if(array(i) > array(j)){
            var tmp = array(j)
            array(j)=array(i)
            array(i)=tmp
          }
        }
      }
    }
    println("------------排序后-------------")
    array.foreach(println)


  }

  println("-----------4、简单选择排序-------------")

  println("-----------5、堆排序-------------")

  println("-----------6、快速排序-------------")

  println("-----------7、归并排序-------------")

  println("-----------7、基数排序-------------")


}
