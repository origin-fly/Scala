package ayibo.day1127

/**
  * scala case class 和模式匹配
  *
  */

class Vehicle

case class  Car (name : String) extends  Vehicle

case class  Bike (name : String) extends Vehicle

object demo05 {

  def main(args: Array[String]): Unit = {

    var a : Vehicle = new Car("Car")

    a match {

      case Car(name) => println("汽车 "+name)
      case Bike(name) => println("自行车" +name)
      case _  => println("其他类型")

    }

  }

}
