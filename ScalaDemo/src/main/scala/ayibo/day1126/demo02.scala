package ayibo.day1126

/**
  * scala 抽象类
  *
  */

//父类 抽象类 Person

abstract class Persons{
  def  food() : String
}

//子类 学生类 从父类继承
class students extends  Persons{
  override def food(): String = "I am a student"
}

//子类 教师类 从父类继承
class teachers extends Persons{
  override def food(): String = "I am a teacher"
}

object demo02 {

  def main(args: Array[String]): Unit = {
    var s1 : Persons = new students
    println(s1.food())

    var s2 : Persons = new teachers
    println(s2.food())

  }
}
