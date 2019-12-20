package ayibo.day1126

/**
  *scala 抽象字段 把字段定义在抽象类里面
  * 继承时  要么类也是abstract 要么将字段放在类的主构造器里面 要么在类里面将字段赋初值
  */

class demo03 {

  abstract  class Persons1 {
    //定义抽象字段
    val id :Int
    val name : String
  }
 abstract class Emplyeel extends  Persons1 {

 }

  class Emplyee2() extends Emplyeel {
    val id : Int = 1
    val name :String = "yibo"

  }

 abstract class Emplyee3(val id : Int , val name : String ) extends Emplyeel {

  }



}
