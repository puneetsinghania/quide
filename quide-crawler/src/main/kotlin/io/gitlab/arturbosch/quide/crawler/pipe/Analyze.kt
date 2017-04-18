package io.gitlab.arturbosch.quide.crawler.pipe

import io.gitlab.arturbosch.quide.crawler.Console
import io.gitlab.arturbosch.smartsmells.api.MetricFacade
import java.nio.file.Path

/**
 * @author Artur Bosch
 */
object Analyze {

	private val facade = MetricFacade(null, listOf(".*/src/test/.*"))

	fun start(path: Path) {
		val result = facade.run(path)
		val averages = MetricFacade.average(result)
		Console.write("Results for ${path.fileName}:\n\t" + averages.joinToString("\n\t"))
	}
}