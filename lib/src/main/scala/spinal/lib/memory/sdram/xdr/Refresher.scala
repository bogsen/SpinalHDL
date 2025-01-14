package spinal.lib.memory.sdram.xdr

import spinal.core._
import spinal.lib._

case class Refresher(cp : CoreParameter) extends Component{
  val io = new Bundle {
    val config = in(CoreConfig(cp))
    val refresh = master(Event)
  }

  val value = Reg(UInt(cp.refWidth bits)) init(0)
  val hit = value === 0
  value := value - 1
  when(hit) {
    value := io.config.REF
  }

  val pending = RegInit(False) clearWhen(io.refresh.ready) setWhen(hit)
  io.refresh.valid := pending
}
