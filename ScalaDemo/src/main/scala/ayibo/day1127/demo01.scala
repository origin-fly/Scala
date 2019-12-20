package ayibo.day1127

/**
  * scala trait 特质
  *
  * 定义两个父类 即 两个trait
  * 人 Human 动作 Action
  *
  * 子类 学生
  *
  */

trait Human{
  //定义抽象字段
  val id : Int
  val name : String
}

trait Action{
  //定义一个抽象函数
  def getActionName() : String
}

//定义子类
class student(val id : Int,val name : String) extends Human with Action{
  override def getActionName(): String = "需要实现getAciton()方法";
}


object demo01 {

  def main(args: Array[String]): Unit = {

    //创建学生对象
    var s1 = new student(1,"James")
    println(s1.id + "\t" + s1.name)
    println(s1.getActionName())

  }

}
