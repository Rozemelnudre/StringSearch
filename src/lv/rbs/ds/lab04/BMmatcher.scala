package lv.rbs.ds.lab04

import scala.collection.mutable.ListBuffer

class BMmatcher(pattern:String) {

  def getPrefixFun(pat:String):List[Int] ={
    var len:Int = pat.length
    var returnList:List[Int] = List()
    returnList :+= -1
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
    var returnList:ListBuffer[Int] = ListBuffer()

    val leng:Int = this.pattern.length
    val prefixList:List[Int] = getPrefixFun(this.pattern)
    val prefixReversed:List[Int] = getPrefixFun(this.pattern.reverse)


    for (j <- 0 to leng){
      returnList :+= leng - prefixList(leng)

    }
    //returnList :+= firstlst.head
    for (l <- 1 to leng){

      println(l)
      val j = leng - prefixReversed(l)
      println(j)
      returnList(j) = Math.min(returnList(j), l - prefixReversed(l))
    }


    println(returnList)
    returnList.toList
  }



}
