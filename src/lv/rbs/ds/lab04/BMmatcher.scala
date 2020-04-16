package lv.rbs.ds.lab04

import play.api.libs.json.{JsValue, Json}
import scala.collection.mutable.ListBuffer

class BMmatcher(pattern:String) {
val algorithm = "BM"
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

  def getBadCharFun2():List[(Char, Int)] ={
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

  def getBadCharFun():Map[Char, Int] ={
    var toReturn:Map[Char, Int] = Map()
    var checked:List[Char] = List()

    for (elem <- this.pattern){
      if(!checked.contains(elem)){
        toReturn += (elem -> pattern.lastIndexOf(elem))
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



  def findAllIn(text:CharSequence):Iterator[Int] ={
    var returnList:List[Int] = List()
    val textlen:Int = text.length()
    val patlen:Int = this.pattern.length
    val badCharMap:Map[Char,Int] = this.getBadCharFun()
    val goodSuff:List[Int] = getGoodSuffixFun()

    var s:Int = 0
    var j:Int = 0
    while(s <= textlen - patlen){
      j = patlen
      while(j > 0 && this.pattern(j - 1) == text.charAt(s + j - 1)){
        j -= 1
      }
      if(j == 0){

        returnList :+= s
        println("pattern appears with offset " + s)
        s = s + goodSuff.head


      }else{
        returnList :+= s
        // s before incrementing is the old offset
        s = s + Math.max(goodSuff(j), j - 1 - badCharMap.getOrElse(text.charAt(s + j - 1), -1))

        //println("new s " + s)
      }
    }

    println(returnList)
    returnList.iterator
  }

  def findAllSteps(text:CharSequence):List[List[Int]] ={
    var returnList:List[List[Int]] = List()
    val textlen:Int = text.length()
    val patlen:Int = this.pattern.length
    val badCharMap:Map[Char,Int] = this.getBadCharFun()
    val goodSuff:List[Int] = getGoodSuffixFun()

    var s:Int = 0
    var j:Int = 0
    var offset:Int = 0
    var start:Int = 0
    var end:Int = 0
    while(s <= textlen - patlen){
      j = patlen
      start = j - 1
      while(j > 0 && this.pattern(j - 1) == text.charAt(s + j - 1)){
        j -= 1
      }
      if(j == 0){
        end = j
        println("pattern appears with offset " + s)
        returnList :+= List(s, start, end, 1)
        s = s + goodSuff.head


      }else{
         end = j - 1
        println("start " + start)
        println("end " + end)
        println("offset " + s)
        returnList :+= List(s, start, end)
        // s before incrementing is the old offset
        s = s + Math.max(goodSuff(j), j - 1 - badCharMap.getOrElse(text.charAt(s + j - 1), -1))

        //println("new s " + s)
      }
    }

    println("here returnlist" + returnList)
    returnList
  }

  def toJson(text:CharSequence):String ={
    val jspat:JsValue = Json.toJson(this.pattern)
    val jstext:JsValue = Json.toJson(text.toString)
    val jsalgo:JsValue = Json.toJson(this.algorithm)

    //get the bad char function and create json for it
    val badChar:List[(Char, Int)] = getBadCharFun().toList
    var badCharJson:List[JsValue] = List()
    for (elem <- badChar){
      var smalList:List[JsValue] = List()
      smalList :+= Json.toJson(elem._1.toString)
      smalList :+= Json.toJson(elem._2)
      badCharJson :+= Json.toJson(smalList)
    }

    //now for the goodSuffix
    val goddfix:List[Int] = getGoodSuffixFun()
    var goodJson:List[JsValue] = List()

    for (i <- goddfix.indices){
      goodJson :+= Json.toJson(List(Json.toJson(i), Json.toJson(goddfix(i))))
    }

    //now for the steps
    val steplist:List[List[Int]] = findAllSteps(text)
    var jsonSteps:List[JsValue] = List()
    for (elem <- steplist){
      var smallMap:Map[String, JsValue] = Map(
        "offset" -> Json.toJson(elem.head),
          "start" -> Json.toJson(elem(1)),
          "end" -> Json.toJson(elem(2))
      )

      if(elem.length == 4){
        smallMap += "match" -> Json.toJson("true")
      }
      jsonSteps :+= Json.toJson(smallMap)
    }

    //finally, compute comparisons
    var comparis:Int = 0
    for(step <- steplist){
      comparis += step(1) - step(2) + 1
    }

    //add everything together

    var jsMap:Map[String, JsValue] = Map(
      "algorithm" -> jsalgo,
      "pattern" -> jspat,
      "text" -> jstext,
      "goodSuffixFun" -> Json.toJson(goodJson),
      "badCharFun" -> Json.toJson(badCharJson),
      "steps" -> Json.toJson(jsonSteps),
      "comparisons" -> Json.toJson(comparis)
    )


    Json.stringify(Json.toJson(jsMap))

  }


  def toJsonTest(text:CharSequence):String ={
    val jspat:JsValue = Json.toJson(this.pattern)
    val jstext:JsValue = Json.toJson(text.toString)
    val jsalgo:JsValue = Json.toJson(this.algorithm)

    //get the bad char function and create json for it
    val badChar:List[(Char, Int)] = getBadCharFun().toList
    var badCharJson:List[JsValue] = List()
    for (elem <- badChar){
      var smalList:List[JsValue] = List()
      smalList :+= Json.toJson(elem._1.toString)
      smalList :+= Json.toJson(elem._2)
      badCharJson :+= Json.toJson(smalList)
    }

    //now for the goodSuffix
    val goddfix:List[Int] = getGoodSuffixFun()
    var goodJson:List[JsValue] = List()

    for (i <- goddfix.indices){
      goodJson :+= Json.toJson(List(Json.toJson(i), Json.toJson(goddfix(i))))
    }

    //now for the steps
    val steplist:List[List[Int]] = findAllSteps(text)
    var jsonSteps:List[JsValue] = List()
    for (elem <- steplist){
      var smallMap:Map[String, JsValue] = Map(
        "offset" -> Json.toJson(elem.head),
        "start" -> Json.toJson(elem(1)),
        "end" -> Json.toJson(elem(2))
      )

      if(elem.length == 4){
        smallMap += "match" -> Json.toJson(elem(3))
      }
      jsonSteps :+= Json.toJson(smallMap)
    }

    //finally, compute comparisons
    var comparis:Int = 0
    for(step <- steplist){
      comparis += step(1) - step(2) + 1
    }

    //add everything together

    var jsMap:Map[String, JsValue] = Map(
      "algorithm" -> jsalgo,
      "pattern" -> jspat,
      "text" -> jstext,
      "goodSuffixFun" -> Json.toJson(goodJson),
      "badCharFun" -> Json.toJson(badCharJson),
      "steps" -> Json.toJson(jsonSteps),
      "comparisons" -> Json.toJson(comparis)
    )


    Json.stringify(Json.toJson(jsMap))

  }

}
