package lol.koblizek.jardump

import lol.koblizek.jardump.cli.OptionData
import java.io.File

fun main(args: Array<String>) {
//    val opts = OptionData.of(
//        null,
//        OptionData.INPUT,
//    )
//    opts.parseIn(args)
    JarDumper(File("/home/koblizkac/Coding/Java/jardump/client.jar")).dump()
}