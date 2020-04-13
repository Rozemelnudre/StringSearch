package lv.rbs.ds.lab04

class BMmatcher(pattern:String) {

  def getPrefixFun(pat:String):List[Int] ={
    var len:Int = pat.length
    var returnList:List[Int] = List()
    //returnList :+= -1
    returnList :+= 0
    var k:Int = 0
    for (q <- 2 to len){
      while(k>0 && pat(k)!=pat(q-1)){
        k=returnList(k)
      }
      if(pat(k) == pat(q-1)){
        k+= 1
      }
      returnList:+=k
    }
    returnList
  }

  def getBadCharFun():List[(Char, Int)] ={
    var toReturn:List[(Char, Int)] = List()
    var checked:List[Char] = List()

    for (elem <- this.pattern){
      if(!checked.contains(elem)){
        toReturn +:= (elem, pattern.lastIndexOf(elem))
        println("here values")
        println(elem, pattern.lastIndexOf(elem))
        checked :+= elem
      }
    }

    toReturn
  }

  def getGoodSuffixFun():List[Int] ={
    var returnList:List[Int] = List()

    val leng:Int = this.pattern.length
    val prefixList:List[Int] = getPrefixFun(this.pattern)
    val prefixReversed:List[Int] = getPrefixFun(this.pattern.reverse)

    var firstlst:List[Int] = List()
    for (j <- 0 to this.pattern.length){
      firstlst :+= leng - prefixList(j)
    }
    for (i <- 1 to leng){

    }


    returnList
  }

}
