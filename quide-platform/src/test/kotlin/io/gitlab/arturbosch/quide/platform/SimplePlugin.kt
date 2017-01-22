package io.gitlab.arturbosch.quide.platform

import io.gitlab.arturbosch.quide.detection.Detector
import io.gitlab.arturbosch.quide.model.CodeSmell
import io.gitlab.arturbosch.quide.model.SmellContainer

/**
 * @author Artur Bosch
 */
class SimplePlugin : Plugin {

	object Storage : UserData()

	private val storage = Storage

	override fun detector(): Detector<*> {
		return object : Detector<SmellContainer<CodeSmell>> {
			override fun name(): String {
				return "SimpleDetector"
			}

			override fun <U : UserData?> execute(data: U): SmellContainer<CodeSmell>? {
				return DefaultContainer
			}
		}
	}

	override fun processors(): MutableList<Processor> {
		return mutableListOf()
	}

	override fun userData(): UserData {
		return storage
	}
}