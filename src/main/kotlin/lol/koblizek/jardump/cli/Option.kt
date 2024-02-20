package lol.koblizek.jardump.cli

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Option(val name: String, val short: String, val description: String, val required: Boolean = false, val hasArg: Boolean = false) {
}
