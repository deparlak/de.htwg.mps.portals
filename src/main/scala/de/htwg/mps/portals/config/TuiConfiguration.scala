package de.htwg.mps.portals.config

import com.escalatesoft.subcut.inject.NewBindingModule
import de.htwg.mps.portals.controller.Controller
import de.htwg.mps.portals.controller.Controller
import de.htwg.mps.portals.view.Gui
import de.htwg.mps.portals.view.UserInterface
import de.htwg.mps.portals.view.Tui
import de.htwg.mps.portals.controller.IController
import de.htwg.mps.portals.actor.AktorSystem

object TuiConfiguration extends NewBindingModule(module => {
  import module._ // can now use bind directly

  bind[AktorSystem] toModuleSingle { implicit module => new AktorSystem() }
  bind[IController] toSingle new Controller()
  bind[UserInterface] toProvider { implicit module => new Tui }

})