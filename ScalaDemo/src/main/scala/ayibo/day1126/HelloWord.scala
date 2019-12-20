package ayibo.day1126

/**
  * scala APP对象
  * 当类继承APP对象时，就可以直接运行代码，不需要写main方法
  * 而且main方法中的args参数可以直接使用
  */

object HelloWord extends App {

  println("直接打印，不需要main方法  Hello Word")
  if (args.length>0){
    println("有参数")
  }else{
    println("无参数")
  }

}
