package ayibo.day1126

/**
  *
  * scala 类
  *
  */

class student1 {
    //定义学生属性
  private var stuId :Int = 0
  private var stuName : String = "Tom"
  private var age :Int = 20
  private val gender :String = "male"

  //定义方法
  def getStuName() : String = stuName
  def setStuName(newName : String ): Unit ={
    this.stuName = newName
  }
}

object student1{

  def main(args: Array[String]): Unit = {

    //创建一个学生对象
    var s1 = new student1

    //访问并输出属性
    println(s1.getStuName())

    //访问set方法
    s1.stuName="Mike"
    println(s1.getStuName())

    println("-------访问私有属性-----------")
    println(s1.stuId + "\t" + s1.stuName +"\t" +s1.age +"\t" +s1.gender)

    /**
      *
      * 属性的get set 犯法
      * * 1、当一个属性是private的时候，scala会自动为其生成对应的set get 方法
      * *   方法名与属性名一致
      * *
      * * 2、如果只希望scala生成get方法，不生成set方法，可以将它定义为常量
      * *
      * * 3、如果希望属性不能在外部访问，使用private[this] 关键字
      *
      */

  }


}