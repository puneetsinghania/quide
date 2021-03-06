package io.gitlab.arturbosch.quide.api.core

/**
 * @author Artur Bosch
 */
interface StorageAware {
	fun <T> get(key: String): T?
	fun <T : Any> put(key: String, value: T)
}
