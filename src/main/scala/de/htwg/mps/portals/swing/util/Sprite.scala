package de.htwg.mps.portals.swing.util

import java.awt.geom.AffineTransform
import java.awt.Graphics2D
import javax.imageio.ImageIO
import java.awt.Dimension
import scala.swing.Panel
import javax.swing.JPanel

class Sprite(val image : String, val width : Int, val height : Int, val x : Int, val y : Int) extends Panel {
  var bufferedImage = ImageIO.read(getClass.getResource(image))  
  val at = new AffineTransform
  val scaleX = 1.0
  val scaleY = 1.0
  this.visible = true
  //this.peer.setComponentZOrder(this.peer, 1)
  update(x , y)

  // paint the selected element of the sprite
  override def paintComponent(g:Graphics2D) = {
    g.drawImage(bufferedImage, at, null)  
  }

  // override the size of the panel, to be conform with the size of one element of the sprite.
  override lazy val peer: JPanel = new JPanel with SuperMixin {
    override def getMinimumSize = new Dimension(width, height)
  	override def getPreferredSize = new Dimension(width, height)
  	override def getMaximumSize = new Dimension(width, height)
  }
  
  def repaintImage(image : String) {
    bufferedImage = ImageIO.read(getClass.getResource(image))  
    this.repaint
  }

  // switch the sprite
  def update(x : Double, y : Double) {
    at.setToIdentity();
    at.translate(x + imageWidth / 2, y + imageHeight / 2);
    at.translate(-imageWidth / 2, -imageHeight / 2);
    at.scale(scaleX, scaleY);
    this.repaint
  }
  
  // move the complete component to the specified coordinates.
  def move(x : Int, y : Int) {
    this.peer.setLocation(x, y)
    this.repaint
  }
  
  // width and size of the complete sprite
  def imageWidth = (bufferedImage.getWidth() * scaleX).toInt
  def imageHeight = (bufferedImage.getHeight() * scaleY).toInt
}