package lol.koblizek.jardump

import java.io.File
import java.io.FileInputStream
import java.io.PrintStream
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarInputStream
import java.util.jar.JarOutputStream
import kotlin.time.measureTime

class JarDumper(private val input: File) {
    private var stream: PrintStream = System.out

    fun setStream(stream: PrintStream) {
        this.stream = stream
    }

    fun performInitialChecks(): JarFile? {
        return if (!input.exists() || !input.isFile)
            null else JarFile(input)
    }

    fun dump() {
        stream.println("[JarDumper] Dumping jar...")
        val time = measureTime {
            val jarFile = performInitialChecks()
            if (jarFile == null) {
                stream.println("[JarDumper] Invalid input file")
            } else {
                for (entry in jarFile.entries()) {
                    jarFile.getInputStream(entry).use {
                        
                    }
                }
            }
        }
        stream.println("[JarDumper] Job done in $time")
    }
}