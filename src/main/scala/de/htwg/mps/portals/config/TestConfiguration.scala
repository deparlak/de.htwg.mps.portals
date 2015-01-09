package de.htwg.mps.portals.config

import com.escalatesoft.subcut.inject.NewBindingModule
import de.htwg.mps.portals.controller.Controller
import de.htwg.mps.portals.controller.Controller
import de.htwg.mps.portals.view.Gui
import de.htwg.mps.portals.view.UserInterface
import de.htwg.mps.portals.view.Tui
import de.htwg.mps.portals.controller.IController

object TestConfiguration extends NewBindingModule(module => {
      import module._   // can now use bind directly


      bind [IController] toSingle new Controller()
      bind [UserInterface] idBy("gui") toProvider { implicit module => new Gui }
      bind [UserInterface] idBy("tui") toProvider { implicit module => new Tui }
      
})