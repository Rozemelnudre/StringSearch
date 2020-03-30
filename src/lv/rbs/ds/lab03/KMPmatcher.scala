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

  def findAllSteps(text:String):List[List[Int]] ={
    var offset:Int = 0
    var start1:Int = 0
    var end1:Int = 0
    var matchy:Boolean = false
    var start2:Int = 0
    var end2:Int = 0
    var returnTable:List[List[Int]] = List()

    val lookupTable = getPrefixFun()
    var i= 0 // for text
    var j= 0 // for p

   if(this.pattern(0)==text(0)){
     offset = 0
     start1 = 0
    }

    while (i < text.length) {
      if (text(i) == this.pattern(j)) {
        i += 1
        j += 1
        //println(i)
      }
      if (j == this.pattern.length) {

        end1 = j
        println("new end " + end1)

        matchy = true
        println(s"pattern found at ${i-j}")
        j = lookupTable(j)

      }
      else {
        if (i < text.length && text(i) != this.pattern(j)) {
          if (j != 0) {

            //offset = i - j
            //returnTable :+= offset

            println("here i")
            println(i)
            println(j)
            println("start1 " +start1)
            if(!matchy){
              end1 = j
            }

            println(offset,start1,end1)
            returnTable :+= List(offset, start1, end1)
            j = lookupTable(j)
            println("here j " + j)

            start1 = j
            println("here offset")
            offset = i - j
            println(offset)

          }else{

              i+=1
              //println(i)


            println("here if j is 0")
            //start1 = j
            end1 = j
            println(offset,start1, end1)
            returnTable :+= List(offset, start1, end1)
            //i+= 1
            println("start again")
            offset = i
            start1 = j
            println("offset " +offset)
            println("start1 " + start1)

          }
        }
      }
      //println(i)
    }
    println(returnTable)
    returnTable
  }

  def toJson(text: CharSequence): String={
    "hello"
  }
}
