


class KMPmatcher(pattern:String) {

  def getPrefixFun():List[List[Int]] ={
    var len:Int = this.pattern.length
    var returnList:List[List[Int]] = List()
    returnList :+= List(0,-1)
    returnList :+= List(1,0)
    var k:Int = 0
    for (q <- 2 to len){
      if(this.pattern(k)==this.pattern(q - 1)){
        k+=1
        returnList :+= List(q,k)
      }else{
        if(k == 0){
          returnList :+= List(q, k)
        }else{
          k = returnList(k)(1)
          returnList :+= List(q,k)
        }
      }
    }




    returnList
  }

  def findAllIn(text:CharSequence):Iterator[Int]

  def toJson(text: CharSequence): String
}
