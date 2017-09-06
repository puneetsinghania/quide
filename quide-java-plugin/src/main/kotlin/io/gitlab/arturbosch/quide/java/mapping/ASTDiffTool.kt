package io.gitlab.arturbosch.quide.java.mapping

import com.github.javaparser.JavaParser
import com.github.javaparser.ParseProblemException
import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.Node
import difflib.Chunk
import io.gitlab.arturbosch.quide.vcs.DiffTool
import io.gitlab.arturbosch.quide.vcs.SourceFile

/**
 * @author Artur Bosch
 */
class ASTDiffTool : DiffTool<JavaCodeSmellPatch> {

	override fun createPatchFor(oldFile: SourceFile, newFile: SourceFile) =
			try {
				val oldUnit = JavaParser.parse(oldFile.content())
				val newUnit = JavaParser.parse(newFile.content())
				val diffPatch = textDiff(oldFile.content(), newFile.content())
				ASTDiffer(oldUnit, newUnit, diffPatch).patch()
			} catch (e: ParseProblemException) {
				NOOPPatch
			}

	inner class ASTDiffer(private val oldUnit: CompilationUnit,
						  private val newUnit: CompilationUnit,
						  diffPatch: difflib.Patch<String>) {

		private val chunks = diffPatch.deltas
				.map { ASTChunk(it.type, it.original.astElements(oldUnit), it.revised.astElements(newUnit)) }

		private fun <T> Chunk<T>.astElements(unit: CompilationUnit): List<Node> {
			val filter = ElementsInRangeFilter(this)
			filter.visitBreadthFirst(unit)
			return filter.posToElement
		}

		fun patch() = ASTPatch(chunks, newUnit)

	}

}

