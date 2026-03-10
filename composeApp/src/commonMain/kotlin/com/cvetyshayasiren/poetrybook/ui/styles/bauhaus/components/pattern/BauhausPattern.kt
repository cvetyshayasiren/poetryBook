package com.cvetyshayasiren.poetrybook.ui.styles.bauhaus.components.pattern

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.invalidateDraw

fun Modifier.bauhausPattern(state: BauhausPatternState) = this then BauhausPatternElement(state)

private data class BauhausPatternElement(val state: BauhausPatternState): ModifierNodeElement<BauhausPatternNode>() {
    override fun create(): BauhausPatternNode = BauhausPatternNode(state)

    override fun update(node: BauhausPatternNode) { node.state = state }
}

private class BauhausPatternNode(var state: BauhausPatternState): DrawModifierNode, Modifier.Node() {

    override fun ContentDrawScope.draw() {
        state.draw(size, this)
        drawContent()
    }
}

