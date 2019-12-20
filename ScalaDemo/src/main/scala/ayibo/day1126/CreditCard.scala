package ayibo.day1126

/**
  * 利用 private [this] 不会自动生成set get 方法 实现单例模式
  */

object CreditCard {

  //定义一个变量保存信用卡的卡号 private [this] 代表不会自动生成set get 方法
  private [this] var creditCardNumber : Long = 0

  //定义函数来产生卡号
  def generateCCNumber() : Long = {
    creditCardNumber += 1
    creditCardNumber
  }

  //测试程序
  def main(args: Array[String]): Unit = {
    println(CreditCard.generateCCNumber())
    println(CreditCard.generateCCNumber())
    println(CreditCard.generateCCNumber())
    println(CreditCard.generateCCNumber())
    println(CreditCard.generateCCNumber())
    println(CreditCard.generateCCNumber())

  }

}
