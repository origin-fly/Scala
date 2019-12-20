package ayibo.day1128

/**
  * scala 隐世类  在类前面加implicit 扩展类的功能
  *
  */



object demo04 {

  def main(args: Array[String]): Unit = {
    //执行两个数字求和
    println(1.add(2))

    implicit  class Calc(x:Int){
      def add(y:Int):Int = x+y
    }


  }

}
