package ayibo.day1126


/**
  * scala 继承
  * 父类 Person 人
  * 子类 Emplyee 员工
  */

//定义父类
class Person (val name :String ,val age:Int){
  //定义函数
  def sayHello() : String = "Hello " + name + " age is "+age
}

//定义子类

/**
  * 使用override关键字 把子类的属性 覆盖父类的属性
  */

class Emplyee(override val name:String,override val age:Int,val salary :Int) extends Person(name,age){
  override def sayHello(): String = "子类中的sayHello方法"
}

object demo01 {
  def main(args: Array[String]): Unit = {
    //创建Person对象
    var p1 = new Person("Tom",20)
    println(p1.name + "\t" + p1.age)
    println(p1.sayHello())

    //创建子类对象
    var p2  = new Emplyee("James",35,4000)
    println(p2.name + "\t" +p2.age + "\t"+p2.salary)
    println(p2.sayHello())

    //使用匿名内部类来实现继承
    var p3:Person = new Person("Kobe",40){
      override def sayHello(): String = "匿名内部子类中的sayHello方法"
    }
    println(p3.sayHello())
  }

}
