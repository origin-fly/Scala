package ayibo.day1126

/**
  *
  * 类的主构造器和辅助构造器
  *
  */


class student3(var stuName:String ,var age:Int) {

  //属性
  private  var stuId : Int = 1

  /**
    * 定义辅助构造器
    * 辅助构造器就是一个函数,只不过这个函数的名字叫this
    *
    */

  def this(age : Int){
    this("Mike",age) //相当于new student3("Mike",age)
    println("这是辅助构造器this(age : Int)")
  }

  def this(){
    this(10)
    println("这是辅助构造器this()")
  }

}

object student3{
  def main(args: Array[String]): Unit = {
    println("--------使用主构造器-------------")
    var s3 = new student3("Tom",20)
    println(s3.stuName + "\t" + s3.age)

    println("--------使用辅助构造器-------------")

    var s1 = new student3(30)
    s1.stuId=1
    s1.stuName="Jeffy"

    println(s1.stuName + "\t" + s1.stuId + "\t" + s1.age)

    var s2 = new student3()

    println(s2.stuName + "\t" + s2.age)

  }
}
