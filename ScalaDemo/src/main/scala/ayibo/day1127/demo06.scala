package ayibo.day1127

/**
  * scala 泛型类
  *
  * 常用于 数据类型不一样的操作 即 一个类既可以操作整数 也可以操作字符串
  * 一个类如果需要大面积拷贝 但改动却很少就需要使用泛型
  *
  */


class fanxingClass[T] {
  private  var content:T = _  //泛型初始值用 _ 表示

  def set(value: T)={content = value}
  def get() : T = {content}
}



object demo06 {

  def main(args: Array[String]): Unit = {

    var a = new fanxingClass[Int]
    a.set(10)
    println(a.get())

    var b = new fanxingClass[String]
    b.set("Tom")
    println(b.get())


  }


}
