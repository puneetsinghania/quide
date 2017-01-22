package io.gitlab.arturbosch.quide.java.evolution

import io.gitlab.arturbosch.quide.model.BaseCodeSmell
import io.gitlab.arturbosch.quide.model.SmellContainer
import io.gitlab.arturbosch.smartsmells.api.SmellResult
import io.gitlab.arturbosch.smartsmells.common.DetectionResult

/**
 * @author Artur Bosch
 */
class JavaCodeSmell(private val smell: DetectionResult) : BaseCodeSmell() {
	init {
		sourcePath = smell.pathAsString
	}

	override fun toString(): String = smell.toString()
}

class JavaSmellContainer(private val smells: SmellResult) : SmellContainer<JavaCodeSmell> {
	override fun all(): MutableList<JavaCodeSmell> {
		return smells.smellSets.values
				.flatMap { it }
				.filter { it != null }
				.map(::JavaCodeSmell)
				.toMutableList()
	}

	override fun findBySourcePath(path: String): MutableList<JavaCodeSmell> {
		return all().filter { it.isLocatedAt(path) }.toMutableList()
	}
}