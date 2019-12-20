package ayibo.day1128

/**
  * scala 隐世函数
  */


class Fruit(name:String){
  def getFriutNae():String = name
}

class Monkey(f:Fruit){
  def say() =println("Monkey like " + f.getFriutNae())
}

object demo03 {

  def main(args: Array[String]): Unit = {

    //定义一个水果对象
    var f : Fruit = new Fruit("Banana")
    println(f.getFriutNae())

    /**
      * 为了实现 水果对象 调用say函数 需要定义一个转换函数
      * 实现把水果对象转换成猴子对象
      * 这样水果对象就可以调用say函数
      *
      * implicit 隐世转换函数 scala自动调用
      *
      */

    implicit def fruittoMonkeey(f:Fruit):Monkey = {
      new Monkey(f)
    }
    println("---------隐世转换---------")
    f.say()

    def fruittoMonkeeyAgain(f:Fruit):Monkey = {
      new Monkey(f)
    }
    println("---------显示转换---------")
    fruittoMonkeeyAgain(f).say()
  }

}
