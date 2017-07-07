package io.gitlab.arturbosch.quide.java.research

import io.gitlab.arturbosch.kutils.write
import io.gitlab.arturbosch.quide.java.withOutputPath
import io.gitlab.arturbosch.quide.platform.ControlFlow
import io.gitlab.arturbosch.quide.platform.UserData
import io.gitlab.arturbosch.quide.platform.processors.ConditionalProcessor

/**
 * @author Artur Bosch
 */
class EvaluateContainerProcessor : ConditionalProcessor {

	override fun <U : UserData> isActive(data: U) = data.isEvolutionaryAnalysis

	override fun <U : UserData> doIfActive(data: U) {
		data.withOutputPath { output, _, container ->
			val result = evaluateToCSV(container)
			val project = data.projectPath().fileName.toString()
			val file = output.resolve("$project.evaluation.txt")
			file.write(result)
		}
	}

	override fun injectionPoint(): ControlFlow.InjectionPoint {
		return ControlFlow.InjectionPoint.AfterAnalysis
	}

	override fun priority(): Int {
		return 1000
	}

}
