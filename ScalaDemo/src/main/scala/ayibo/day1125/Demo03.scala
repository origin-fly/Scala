package ayibo.day1125

object Demo03 {

  def main(args: Array[String]): Unit = {

    def sum(args :Int*): Unit ={
      var result=0
      for(i <- args) result +=i
      result
    }

    println(sum(1,2))
    println(sum(1,21,23,23))
    println(sum(1,2,5,6))




  }
}
