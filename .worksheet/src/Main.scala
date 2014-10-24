object Main {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(106); 
   val source = scala.io.Source.fromFile("D:/Repository/de.htwg.mps.portals/res/level1.txt");System.out.println("""source  : scala.io.BufferedSource = """ + $show(source ));$skip(80); 
   val byteArray = source.map(_.toChar).toArray.foreach( x => print("("+x+")"));System.out.println("""byteArray  : Unit = """ + $show(byteArray ));$skip(18); 
   source.close()}
}
