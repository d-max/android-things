import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.tasks.JavaExec
import org.gradle.process.internal.ExecException
import java.io.File

open class KtLintExtension(
    var version: String = "0.34.2",
    var debug: Boolean = false,
    var verbose: Boolean = false,
    var android: Boolean = false,
    var ignore: Boolean = false,
    var group: Boolean = false,
    var report: File? = null
)

open class KtLintTask : JavaExec() {

    init {
        group = "verification"
        description = "Check Kotlin code style."
    }

    fun config(configuration: Configuration, extension: KtLintExtension) {
        main = "com.github.shyiko.ktlint.Main"
        classpath = configuration
        isIgnoreExitValue = extension.ignore
        args(extension.toParams())
    }

    override fun exec() {
        try {
            super.exec()
        } catch (e: ExecException) {
            throw GradleException("Ktlint failed", e)
        }
    }

    private fun KtLintExtension.toParams(): List<String> =
        mutableListOf("src/**/*.kt").apply {
            if (android) add("--android")
            if (debug) add("--debug")
            if (verbose) add("--verbose")
            val reporter = "--reporter=plain".let {
                var value = it
                if (group) value = "$value?group_by_file"
                if (report != null) value = "$value,output=${report!!.asReportFile()}"
                value
            }
            add(reporter)
    }

    private fun File.asReportFile() = "$absoluteFile/${project.name}.txt"
}

class KtLintPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.run {
            val extension = createExtension()
            val configuration = createConfiguration()
            afterEvaluate {
                addKtLintDependency(configuration, extension)
                subprojects {
                    it.tasks.create("ktlint", KtLintTask::class.java) {
                        task -> task.config(configuration, extension)
                    }
                }
            }
        }
    }

    private fun Project.createExtension(): KtLintExtension =
        extensions.create("ktlint", KtLintExtension::class.java)

    private fun Project.createConfiguration(): Configuration =
        configurations.create("ktlint")

    private fun Project.addKtLintDependency(configuration: Configuration, extension: KtLintExtension) =
        dependencies.add(
            configuration.name,
            mapOf(
                "group" to "com.github.shyiko",
                "name" to "ktlint",
                "version" to extension.version
            )
        )
}
