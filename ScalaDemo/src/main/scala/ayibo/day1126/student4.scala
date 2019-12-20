package ayibo.day1126

/**
  * 定义student4的apply方法
  *
  * 使用apply方法时，创建对象时可以省略new关键字
  * 但apply方法使用必须放在伴生对象中
  *
  */

class student4(var stuName:String)

object student4{
  def apply(name:String) = {
    println("调用了apply方法")
    new student4(name)
  }

  def main(args: Array[String]): Unit = {
    //使用主构造器创建学生对象
    var s1 = new student4("James")
    println(s1.stuName)

    //通过apply方法创建学生对象
    var s2 = student4("Marry")
    println(s2.stuName)

  }
}