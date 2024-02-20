package lol.koblizek.jardump.cli

abstract class AbstractOption {
    abstract fun onUsed(context: Any?, vararg value: String)
}