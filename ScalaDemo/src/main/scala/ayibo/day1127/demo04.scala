package ayibo.day1127

/**
  * 模式匹配
  */

object demo04 {

  def main(args: Array[String]): Unit = {

    //1 相当于switch case
    var chi = '-'
    var sign = 0 //标识符 判断chi 如果- 则赋值为 -1

    chi match {

      case '+' => sign = 1
      case '-' => sign = -1
      case _   => sign =0
    }
    println(sign)

   //2 scala的守卫:匹配某种类型的所有值 case _ if
   // 匹配所有的数字 ch2
    var ch2 ='6'
    ch2 match {
      case '+' => println("这是一个加号")
      case '-' => println("这是一个减号")
      case _ if  Character.isDigit(ch2) => Character.digit(ch2,10) //10 代表10进制
      case _ => println("其他")
    }
    println(ch2)

    //3 在模式匹配中 使用变量
    var mystr = "Hello Word"
    mystr(7) match {
      case '+' => println("这是一个加号")
      case '-' => println("这是一个减号")
      case ch  => println(ch) //相当于将mystr(7) 这个变量赋给ch
    }



    /**
      * scala 中容易混淆的类型
      * Any 表示任何类型 相当于java中的object
      * Unit 表示没有值 相当于void
      * Nothing 是scala类层中最低端的类型 他是任何其他类型的子类
      * Null 是所有应用类型的子类 值 null
      * Option scala中的option表示一个值是可选的
      * Some 如果值存在 Option就是None
      *
      * 四个N总结：None Nothing Null Nil
      * None some对立
      * Nothing 抛出异常
      * Null 引用类型子类 值null
      * Nil 一个空的List
      *
      */
    // 4 instanceOf 匹配类型
    var  v4 : Any = 100
    v4 match {
      case x :Int => println("这是一个整数")
      case s :String => println("这是一个字符")
      case _ => println("其他类型")
    }

    //4 匹配数组和列表
    var myArray = Array(1,2,3)
    myArray match {
      case Array(0) => println("数组中只有一个0")
      case Array(x,y) => println("数组中包含两个元素" + (x+y))
      case Array(x,_*) => println("数组中包含多个个元素" + myArray.sum)
    }
    var myList = List(1,2,3)
    myList match {
      case List(0) => println("列表中只有一个0")
      case List(x,y) => println("列表中包含两个元素" + (x+y))
      case List(x,_*) => println("列表中包含多个个元素" + myList.sum)
    }

  }


}
