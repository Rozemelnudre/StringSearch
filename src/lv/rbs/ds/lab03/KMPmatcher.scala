package lv.rbs.ds.lab03

class KMPmatcher(pattern:String) {

  def getPrefixFun():List[Int] ={
    var len:Int = this.pattern.length
    var returnList:List[Int] = List()
    returnList :+= -1
    returnList :+= 0
    var k:Int = 0
    for (q <- 2 to len){
      while(k>0 && this.pattern(k)!=this.pattern(q-1)){
        k=returnList(k)
      }
      if(this.pattern(k) == this.pattern(q-1)){
        k+= 1
      }
      returnList:+=k
    }
    returnList
  }

  def findAllIn(text:CharSequence):Iterator[Int]={
    var offset:Int = 0
    //var previousFalse:Boolean = true
    var returnList:List[Int] = List()
    //returnList :+= offset
    var textLen:Int = text.length()
    var patLen:Int = this.pattern.length
    val prefixTable:List[Int] = getPrefixFun()

    var timesIncr:Int = 0
    var k:Int = 0
    for(i <- 0 until textLen){

      while(k > 0 && this.pattern(k)!= text.charAt(i)){
        if(timesIncr < 1){
          offset = i - k
          returnList :+= offset
          println("first")
          println(offset)
          timesIncr += 1
        }
        k=prefixTable(k)
        offset = i - k
        println("second")
        println(offset)
        returnList :+= offset
        timesIncr+= 1


      }
      if(this.pattern(k) == text.charAt(i)){
        k += 1
        timesIncr += 1
      }

      if(k == patLen){
        //do smth

        k = prefixTable(k)
        println("third")


        if(i != textLen - 1){
          offset = i+1
          returnList :+= offset
          timesIncr += 1
        }

      }

      if(timesIncr == 0 && k == 0){
        offset = i
        returnList :+= offset
      }
      timesIncr = 0
    }
    println(returnList)
    returnList.iterator
  }

  def findAllSteps(text:String):List[Int] ={
    var offset:Int = 0
    var returnTable:List[Int] = List()

    val lookupTable = getPrefixFun()
    var i= 0 // for text
    var j= 0 // for p

    if(this.pattern(0)==text(0)){
      returnTable :+= offset
    }

    while (i < text.length) {
      if (text(i) == this.pattern(j)) {
        i += 1
        j += 1
      }
      if (j == this.pattern.length) {
        println(s"pattern found at ${i-j}")
        j = lookupTable(j)

      }
      else {
        if (i < text.length && text(i) != this.pattern(j)) {
          if (j != 0) {

            //offset = i - j
            //returnTable :+= offset

            j = lookupTable(j)

            offset = i - j
            returnTable :+= offset

          }else{
            if(i != text.length - 1){
              i+=1
            }
            offset = i
            returnTable :+= offset
          }
        }
      }
    }
    println(returnTable)
    returnTable
  }

  def toJson(text: CharSequence): String={
    "hello"
  }
}
