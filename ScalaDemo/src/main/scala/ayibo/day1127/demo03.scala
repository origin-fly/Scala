package ayibo.day1127

object demo03 {

  def flatMapDemo()={
    val li = List(1,2,3)
    val res = li.flatMap(
      x =>
        x match {
          case 3 => List('a','b','c')
          case _ => List(x*2)
        }
    )
    println(res)

    /**
      * List(2, 4, a, b, c)
      * 执行分析：
      *
      * 1、List(1*2)  List(2)
      * 2、List(2*2)  List(4)
      * 3、List('a','b')
      *
      * List(List(2),List(4),List('a','b','c'))
      *
      * flatten
      * List(2, 4, a, b)
      */

  }

  def mapDemo(): Unit ={
    val li = List(1,2,3)
    val res =li.map(
      x => x match{
        case 3 => List('a','b','c')
        case _ => List(x*2)
      }

    )
    println(res)

    /**
      * List(2, 4, List(a, b))
      *
      * map执行分析
      * 1   2   --> List(2,2,3)
      * 2   4   --> List(2,4,3)
      * 3   List('a','b')-->List(2, 4, List(a, b))
      */

  }


  def main(args: Array[String]): Unit = {
    flatMapDemo()
    mapDemo()
  }

}
