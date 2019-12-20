package ayibo.day1209

import java.text.SimpleDateFormat

import org.apache.spark.sql.SparkSession



object SparkSQLExample {

  /**
    * 获取当前时间函数
    */
  def getDate(time: String) = {
    val now: Long = System.currentTimeMillis()
    val df: SimpleDateFormat = new SimpleDateFormat(time)
    df.format(now)
  }


  def main(args: Array[String]): Unit = {

    //使用spark session 创建运行环境
    val spark = SparkSession.builder().master("local")
                            .appName("SparkSQLExample").getOrCreate()
    import spark.sqlContext.implicits._

    //读取数据
    spark.sparkContext.textFile("D:\\Study\\BigData\\input\\Student.csv")
      .map(_.split(","))
      .map(s => Student(s(0),s(1),s(2),s(3),s(4)))
      .toDF
      .createOrReplaceTempView("Student")

    spark.sparkContext.textFile("D:\\Study\\BigData\\input\\Course.csv")
      .map(_.split(","))
      .map(c => Course(c(0),c(1),c(2)))
      .toDF
      .createOrReplaceTempView("Course")

    spark.sparkContext.textFile("D:\\Study\\BigData\\input\\Score.csv")
      .map(_.split(","))
      .map(s => Score(s(0),s(1),s(2)))
      .toDF
      .createOrReplaceTempView("Score")

    spark.sparkContext.textFile("D:\\Study\\BigData\\input\\Teacher.csv")
      .map(_.split(","))
      .map(t => Teacher(t(0),t(1),t(2),t(3),t(4),t(5)))
      .toDF
      .createOrReplaceTempView("Teacher")

    //执行sql

    //1.查询student表sname ssex sclass列
    //spark.sql("select sname,ssex,sclass from student").show()

    //2.查询教师表中不重复的depart列 false 当列很长时不省略 group by 也可以实现去重
    //spark.sql("select distinct tdepart from teacher").show(false)
    //spark.sql("select tdepart from teacher group by tdepart").show(false)

    //3.查询score表中成绩在60-80之间的所有记录
    //spark.sql("select * from score where degree >= 60 and degree <= 80 order by Int(degree) desc ").show()
    //spark.sql("select * from score where degree between 60 and 80 order by Int(degree) desc").show()

    //4.查询score表中成绩为85 86 或 88的记录  单引号不加也可以
    //spark.sql("select * from score where degree ='85' or degree ='86' or degree ='88' order by Int(degree) desc").show()

    //5.查询每门课的平均成绩
    //spark.sql("select cno,avg(degree) avg from score group by cno").show()

    //6. 查询score表中至少有5名学生选修的课，并且名字以3开头的课程的平均分数
    //spark.sql("select cno ,avg(degree) from score where cno like '3%' group by cno  having count(1) >= 5").show()


    //7.查询所有学生中的sname cname degree
          /*spark.sql("select s.sname, t.degree,c.cname from score t " +
              "join student s on t.sno=s.sno " +
              "join course c on c.cno=t.cno").show(false)*/

    //8.查询score中选择多门课程的同学中，分数为非最高分成绩的记录
        /*spark.sql("select * from score where " +
          "sno in (select sno from score t group by t.sno having count(1) > 1) " +
          " and degree != (select max(degree) from score)").show()*/

    //9.查询和学号为108的同学同年出生的所有学生的sno sname sbirthday 列
          /*spark.sql("select sno,sname,sbirthday from student where substring(sbirthday,0,4) = (" +
              " select substring(t.sbirthday,0,4) from student t where sno='108')").show()*/

    //10.查询选修某课程的同学人数多于5人的教师姓名
         /* spark.sql("select tname from teacher e " +
              " join course c on e.tno = c.tno " +
              " join (select cno from score group by cno having count(cno) > 5) t on c.cno = t.cno").show()*/

    //11.查询成绩比该课程平均成绩低的同学的成绩表
       //spark.sql("select s.* from score s where s.degree < (select avg(degree) from score c where s.cno = c.cno)").show()

    //12.查询所有没有讲课的教师的tname 和 depart
       // spark.sql("select tname , tdepart from teacher t where t.tno not in (select tno from course c where c.cno in (select cno from score))").show(false)

    //13.查询至少有2名男生的班号
        spark.sql("select sclass from student t where ssex='male' group by sclass having count(ssex) >= 2").show()

    //14.查询student表中不姓 王 的同学记录
        spark.sql("select * from student t where sname not like('Wang%')").show()

    //15.查询student表中每个学生的姓名和年龄
        spark.sql("select sname, (cast(" + getDate("yyyy") + " as int) - cast(substring(sbirthday,0,4) as int)) as age from student t").show()


    spark.stop()


  }

}

/**
  *
  * @param sno  学生编号
  * @param sname 学生姓名
  * @param ssex 学生性别
  * @param sbirthday 学生出生日期
  * @param sclass 学生所在班级
  */
case class Student(sno:String,sname:String,ssex:String,sbirthday:String,sclass:String)

/**
  *
  * @param cno 课程编号
  * @param cname 课程名称
  * @param tno 教师编号
  */
case class Course(cno:String,cname:String,tno:String)

/**
  *
  * @param sno 学生编号
  * @param cno 课程编号
  * @param degree 成绩
  */
case class Score (sno:String,cno:String,degree:String)

/**
  *
  * @param tno 教师编号
  * @param tname 教师姓名
  * @param tsex 教师性别
  * @param tbirthday 教师出生日期
  * @param tprof  职称
  * @param tdepart 教师所在部门
  */
case class Teacher(tno:String,tname:String,tsex:String,tbirthday:String,tprof:String,tdepart:String)