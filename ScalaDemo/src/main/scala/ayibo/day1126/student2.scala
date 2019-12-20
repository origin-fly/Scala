package ayibo.day1126

import scala.collection.mutable.ArrayBuffer

/**
  *
  * scala  内部类
  *
  * 定义一个学生类  同时可以保存学生多门课程的成绩
  *
  */

class student2 {

  private var stuId : Int = 0
  private var stuName : String ="Mike"
  private var stuAge : Int = 20

  //定义一个数组 用于保存学生的课程成绩
  private  var courseList = new ArrayBuffer[course]()

  //定义一个方法 用于添加学生的成绩信息

  def addNewCourseGrade(num : Int,name : String,age : Int ): Unit ={

    //创建课程成绩信息
    var c = new course(name,age)
    //添加到学生对象中
    courseList += c

  }

  //定义课程类 存放课程成绩
  class course(var coursename:String,var coursegrade:Int){

  }

}


object  student2{
  def main(args: Array[String]): Unit = {

    //创建学生对象
    var s1 = new student2
    //添加课程成绩信息
    s1.addNewCourseGrade(1,"Math",99)
    s1.addNewCourseGrade(1,"English",90)
    s1.addNewCourseGrade(2,"Math",99)
    s1.addNewCourseGrade(2,"English",90)
    s1.addNewCourseGrade(3,"Math",99)
    s1.addNewCourseGrade(3,"English",90)

    //打印
    println(s1.stuId + "\t" + s1.stuName +"\t"+s1.stuAge)

    //打印课程信息
    for(c <- s1.courseList) println(c.coursename + "\t" + c.coursegrade)
  }
}