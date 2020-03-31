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
    var comparisons:Int = 0
    var start1:Int = 0
    var end1:Int = 0
    var end2:Int = 0
    var returnTable:List[List[Int]] = List()

    val lookupTable = getPrefixFun()
    var i= 0 // for text
    var j= 0 // for pattern

//initialize start and offset values for the case if the first elements match
   if(this.pattern(0)==text(0)){
     offset = 0
     start1 = 0
    }

    //iterate over the string once
    while (i < text.length) {
      if (text(i) == this.pattern(j)) {


        //in case the text ends with incomplete pattern, it won't be recognized and wrong start/end will be added
        //so it has to be overwritten
        if(i == text.length - 1){


          returnTable = returnTable.init :+ List(offset -1, start1, j,0)

        }
        //otherwise increment and move both pattern and string
        i += 1
        j += 1
        //println(i)


      }
      if (j == this.pattern.length) {


        //if the whole string is matched it has to be added here
        //because other cases are only added when a mismatch occurs
        end2 = j
        returnTable :+= List(i-j,start1,j-1,1)


        //matchy = true
        println(s"pattern found at ${i-j}")
        j = lookupTable(j)

        //move the start to 0 and offset to next i value(which is the current i)
        start1 = 0
        offset = i

      }
      else {
        if (i < text.length && text(i) != this.pattern(j)) {

          if (j != 0) {
            //sort the two cases-either mismatched after some matched characters
            //or just another mismatch

            //after a mismatch next offset will occur so the previous has ended and
            //has to be added
            end1 = j
            returnTable :+= List(offset, start1, end1,0)

            j = lookupTable(j)
            //refresh variables to new values to be able to add next offset
            start1 = j
            offset = i - j

          }else{
            //similarily-add previous offset and update the values to represent new offset
            i+=1
            end1 = j
            returnTable :+= List(offset, start1, end1,0)


            offset = i
            start1 = j
          }
        }
      }

    }

    //calculate all the comparisons
    for(elem <- returnTable){
      comparisons += elem(2)-elem(1) + 1
    }
    println(comparisons)
    println(returnTable)
    returnTable
  }



  def toJson(text: CharSequence): String={
    "hello"
  }
}
