package com.example.droidchat.navigation.extemsion

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Dp

fun Modifier.bottomBorder(color: Color, strokeWith: Dp) = this.drawBehind {
    val strokeWidthPx = strokeWith.toPx()
    val width = size.width
    val height = size.height - strokeWidthPx / 2

    drawLine(
        color = color,
        start = Offset(x = 0f, y = height),
        end = Offset(x = width, y = height),
        strokeWidth = strokeWidthPx
    )
}

private class BottomBorderNode(
    var color: Color,
    var strokeWith: Dp,
) : DrawModifierNode, Modifier.Node() {
    override fun ContentDrawScope.draw() {
        val strokeWidthPx = strokeWith.toPx()

        val width = size.width
        val height = size.height

        drawLine(
            color = color,
            start = Offset(x = 0f, y = height),
            end = Offset(x = width, y = height),
            strokeWidth = strokeWidthPx
        )
    }
}

private data class BottomBorderElement(
    var color: Color,
    var strokeWith: Dp,
) : ModifierNodeElement<BottomBorderNode>() {

    override fun create(): BottomBorderNode {
        return BottomBorderNode(color, strokeWith)
    }

    override fun update(node: BottomBorderNode) {
        node.color = color
        node.strokeWith = strokeWith
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "bottomBorder"
        properties["color"] = color
        properties["strokeWith"] = strokeWith
    }
}

fun Modifier.bottomBorder2(color: Color, strokeWith: Dp) =
    this then BottomBorderElement(color, strokeWith)