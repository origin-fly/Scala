package ayibo.day1128

/**
  * scala 协变 裂变
  *
  */




class Animal
class Bird extends Animal
class Cats extends Bird

//协变
//定义第四个类 吃东西的泛型类
class eating[+T](t:T)

//定义第五个类 喝水的泛型类
class drinking[-T](t:T)

object demo02 {

  def main(args: Array[String]): Unit = {
    //协变
    //定义一个鸟吃东西的对象
    var c1:eating[Bird] = new eating[Bird](new Bird)
    var c2:eating[Animal] =c1

    //裂变
    //定义一个鸟喝水
    var c3:drinking[Bird] = new drinking[Bird](new Bird)
    var c4:drinking[Cats] = c3


  }

}
