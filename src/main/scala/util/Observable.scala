package main.scala.util

trait Observer[Event] {
  def update(e : Event)
}

class Observable[Event] {
  var subscribers:Vector[Observer[Event]] = Vector()
  def add(s:Observer[Event]) = subscribers=subscribers:+s
  def remove(s:Observer[Event]) = subscribers=subscribers.filterNot(o=>o==s)
  def notifyObservers(e:Event) = subscribers.foreach(o=>o.update(e))
}