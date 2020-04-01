package lv.rbs.ds.lab03
import play.api.libs.json.{JsValue, Json}

class KMPmatcher(pattern:String) {

  var algo:String = "KMP"

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



  def findAllIn(text:CharSequence):Iterator[List[Int]] ={
    var offset:Int = 0

    var start1:Int = 0
    var end1:Int = 0
    var end2:Int = 0
    var returnTable:List[List[Int]] = List()

    val lookupTable = getPrefixFun()
    var i= 0 // for text
    var j= 0 // for pattern

//initialize start and offset values for the case if the first elements match
   if(this.pattern(0)==text.charAt(0)){
     offset = 0
     start1 = 0
    }

    //iterate over the string once
    while (i < text.length) {
      if (text.charAt(i) == this.pattern(j)) {


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
        if (i < text.length && text.charAt(i) != this.pattern(j)) {

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

    println(returnTable)
    returnTable.iterator
  }



  def toJson(text: CharSequence): String={
    val jspat:JsValue = Json.toJson(this.pattern)
    val jstext:JsValue = Json.toJson(text.toString)
    val jsalgo:JsValue = Json.toJson(this.algo)

    var jsprefixlist:List[JsValue] = List()
    val prefixlist:List[Int] = getPrefixFun()
    //for each element of prefixFun returned list turn element's index and elemnt to JsValues
    //add the to a list, turn this list into JsValue, add the JsValue-list to list jsprefixlist
    for (i <- prefixlist.indices){
      var smallList:List[JsValue] = List()
      jsprefixlist :+= Json.toJson(List(Json.toJson(i), Json.toJson(prefixlist(i))))
    }

    //final prefixlist jsvalue to be added to jsmap
    val jsonprefixes:JsValue = Json.toJson(jsprefixlist)

    var comparisons:Int = 0
    val steps:Iterator[List[Int]] = findAllIn(text)
    //calculate the comparisons
    for(elem <- steps){
      comparisons += elem(2)-elem(1) + 1
    }
    //jsvalue of comparisons to be added to jsmap
    val jscomp:JsValue = Json.toJson(comparisons)


    var listOfstepMaps:List[JsValue]=List()
    for(elem<- steps){
      var smallMap:Map[String,JsValue] = Map(
        "offset" -> Json.toJson(elem(0)),
        "start" -> Json.toJson(elem(1)),
        "end" -> Json.toJson(elem(2)),
      )
      if(elem(3) == 1){
        smallMap += ("match" -> Json.toJson(true))
      }

      listOfstepMaps :+= Json.toJson(smallMap)
    }
    val jsstepList:JsValue = Json.toJson(listOfstepMaps)

    var jsonMap:Map[String, JsValue] = Map(
      "algorithm" -> jsalgo,
      "pattern" -> jspat,
      "text" -> jstext,
      "prefixFun" -> jsonprefixes,
      "steps" -> jsstepList,
      "comparisons" -> jscomp
    )


    Json.stringify(Json.toJson(jsonMap))
  }
}
