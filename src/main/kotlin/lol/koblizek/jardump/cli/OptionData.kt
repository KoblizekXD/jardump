package lol.koblizek.jardump.cli

class OptionData(val context: Any?) {

    private val optionMap = mutableMapOf<AbstractOption, Option?>()

    fun addOption(option: AbstractOption) {
        optionMap[option] = option::class.java.getDeclaredAnnotation(Option::class.java)
    }

    companion object {
        fun of(context: Any?, vararg option: AbstractOption): OptionData {
            return OptionData(context).apply {
                option.forEach { addOption(it) }
            }
        }


        class QuickOption(val name: String, val short: String, val description: String, val required: Boolean = false, val hasArg: Boolean = false, val onUsed: (context: Any?, value: Array<String>) -> Unit) : AbstractOption() {
            override fun onUsed(context: Any?, vararg value: String) {
                onUsed(context, arrayOf(*value))
            }
        }

        val INPUT: QuickOption = QuickOption("input", "i", "Input file", required = true, hasArg = true) { context, value ->
            println("Input: ${value[0]}")
        }

        val HELP = QuickOption("help", "h", "Print help", required = false, hasArg = false) { _, _ -> }
    }

    fun buildOptions(): org.apache.commons.cli.Options {
        val options = org.apache.commons.cli.Options()
        optionMap.forEach { (type, annotation) ->
            if (type is QuickOption) {
                val opt = org.apache.commons.cli.Option.builder(type.short)
                    .longOpt(type.name)
                    .desc(type.description)
                    .hasArg(type.hasArg)
                    .required(type.required)
                    .build()
                options.addOption(opt)
            } else {
                val opt = org.apache.commons.cli.Option.builder(annotation!!.short)
                    .longOpt(annotation.name)
                    .desc(annotation.description)
                    .hasArg(annotation.hasArg)
                    .required(annotation.required)
                    .build()
                options.addOption(opt)
            }
        }
        return options
    }

    fun parseIn(args: Array<String>) {
        val parser = org.apache.commons.cli.DefaultParser()
        val cmd = parser.parse(buildOptions(), args)
        optionMap.forEach { (option, annotation) ->
            cmd.hasOption(annotation!!.name).let {
                option.onUsed(context, cmd.getOptionValue(annotation.name, null))
            }
        }
    }
}