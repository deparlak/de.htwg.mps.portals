package main.scala.util

trait Timer {
  def start
  def stop
}

class SwingTimer(interval: Int, repeats: Boolean = true)(op: => Unit) extends Timer {
    val timeOut = new javax.swing.AbstractAction() {
      def actionPerformed(e : java.awt.event.ActionEvent) = op
    }
    private val t = new javax.swing.Timer(interval, timeOut)
    t.setRepeats(repeats)
    t.start()    
    def start = t.start()
    def stop = t.stop()
}

// Timer which calls a anonym method cylic
object Timer {
  def apply(interval: Int, repeats: Boolean = true)(op: => Unit) : Timer = new SwingTimer(interval, repeats)(op)
}