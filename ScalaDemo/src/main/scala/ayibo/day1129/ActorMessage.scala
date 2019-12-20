package ayibo.day1129

/**
  * 样本类 保存所有的消息
  *
  */

//worker ---> master 注册节点
case class RegisterWorker(val id:String,val workerHost:String,val memory:String,val cores:String)
//master ---> worker 注册完成通知消息
case class RegisterWorkerReply(val id:String)


//worker --->  master 发送心跳信号
case class HeartBeat(val workid:String)
//worker ---> worker 备忘录 提醒worker自己发送心跳信号
case class SendHeartBeat()


//master ---> master 维护worker列表 检查超时节点
case class CheckOfTimeOutWorker()

