package ayibo.day1127


/**
  *
  * scala 匿名函数   去掉函数的名字 返回值 和 定义  将入参用 _ 代替
  * scala 高阶函数   函数的入参也是函数
  *
  */


object demo02 {


  def main(args: Array[String]): Unit = {


    var myarray = Array(1,2,3)

    //匿名函数

    //普通函数
    def fun1(x : Int) : Int = x * 3
    //调用
    println("----------普通函数调用------------")
    println( fun1(5))

    //匿名函数1 即去掉函数的名字 返回值 和定义
    (x : Int) => x*3
    //匿名函数2 在匿名函数1的基础上去掉入参 将入参用 _ 代替 _*3
    println("-----------匿名函数1调用-----------")
    println(myarray.map((x : Int) => x*3) )


    println("-----------匿名函数2调用-----------")
    println(myarray.map(_*3))


    //高阶函数
    def someAction(f :(Double) => Double) = f(10)

      //(Double) => Double








  }

}
