package main.scala.model

object model {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(82); 
  var items : Map[String, String] = Map();System.out.println("""items  : Map[String,String] = """ + $show(items ));$skip(34); 
  items += ("Key 1" -> "Value 1");$skip(34); 
  items += ("Key 2" -> "Value 2");$skip(21); val res$0 = 
  items.get("Key 1");System.out.println("""res0: Option[String] = """ + $show(res$0));$skip(21); val res$1 = 
  items.get("Key 3");System.out.println("""res1: Option[String] = """ + $show(res$1));$skip(29); val res$2 = 
  items.get("Key 3") == None;System.out.println("""res2: Boolean = """ + $show(res$2));$skip(36); val res$3 = 
  items.getOrElse("Key 3", "Error");System.out.println("""res3: String = """ + $show(res$3));$skip(8); val res$4 = 
  items;System.out.println("""res4: Map[String,String] = """ + $show(res$4));$skip(32); 
  items += ("Key 1" -> "New 1");$skip(8); val res$5 = 
  items;System.out.println("""res5: Map[String,String] = """ + $show(res$5));$skip(41); 

  var other : Map[String, Item] = Map();System.out.println("""other  : Map[String,main.scala.model.Item] = """ + $show(other ));$skip(33); 
  other += ("A" -> new Player());$skip(31); 
  other += ("B" -> new Wall());$skip(8); val res$6 = 
  other;System.out.println("""res6: Map[String,main.scala.model.Item] = """ + $show(res$6));$skip(93); 
  //other.collect { case (key, Player) => (key, value) }.toMap
  if (!true) println("hallo");$skip(27); 
  if (true) println("Noo")}
 
}
