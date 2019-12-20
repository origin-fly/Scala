package ayibo.day1128

/**
  * scala 上下界
  *
  */

class Vehicle{
  //函数 驾驶
  def  drive() = println("驾驶")
}

class Car extends  Vehicle{
  override def drive() = println("Car 驾驶")
}

class Bike extends  Vehicle{
  override def drive(): Unit = println("Bike 驾驶")
}

object demo01 {

  //定义泛型函数 规定上界 规定了T的类型必须是Vehicle本身或者Vehicle的子类

  def takeV[T <: Vehicle](v:T) = v.drive()

  def main(args: Array[String]): Unit = {

   /* var v:Vehicle = new Vehicle
    takeV(v)*/

    var v1:Car = new Car
    takeV(v1)

   /* var v2 : Bike = new Bike
    takeV(v2)*/

  }

}
