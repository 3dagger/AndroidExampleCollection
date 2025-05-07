package com.example.composecustomtooltip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupPositionProvider
import com.example.composecustomtooltip.ui.theme.ComposeCustomTooltipTheme
import kotlinx.coroutines.launch

// 툴팁 방향 정의
enum class TooltipDirection {
	TOP_CENTER, TOP_LEFT, TOP_RIGHT, BOTTOM_CENTER, BOTTOM_LEFT, BOTTOM_RIGHT, LEFT, RIGHT
}

// 툴팁 위치 계산기
class CustomTooltipPositionProvider(
	private val direction: TooltipDirection
) : PopupPositionProvider {
	override fun calculatePosition(
		anchorBounds: IntRect,
		windowSize: IntSize,
		layoutDirection: LayoutDirection,
		popupContentSize: IntSize
	): IntOffset {
		val x = anchorBounds.left + (anchorBounds.width - popupContentSize.width) / 2
		val y = when (direction) {
			TooltipDirection.TOP_CENTER -> anchorBounds.top - popupContentSize.height
			TooltipDirection.TOP_LEFT -> anchorBounds.top - popupContentSize.height
			TooltipDirection.TOP_RIGHT -> anchorBounds.top - popupContentSize.height
			TooltipDirection.BOTTOM_CENTER -> anchorBounds.bottom
			TooltipDirection.BOTTOM_LEFT -> anchorBounds.bottom
			TooltipDirection.BOTTOM_RIGHT -> anchorBounds.bottom
			TooltipDirection.LEFT -> anchorBounds.top + (anchorBounds.height - popupContentSize.height) / 2
			TooltipDirection.RIGHT -> anchorBounds.top + (anchorBounds.height - popupContentSize.height) / 2
		}
		return IntOffset(x, 1500)
	}
}

class MainActivity : ComponentActivity() {
	private lateinit var tooltipDirection: TooltipDirection

	@OptIn(ExperimentalMaterial3Api::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			ComposeCustomTooltipTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					val tooltipState = rememberTooltipState(isPersistent = true)
					val scope = rememberCoroutineScope()

					Box(
						modifier = Modifier
							.fillMaxSize()
							.statusBarsPadding()
					) {
						Column {
							Row {
								Button(
									onClick = {
										tooltipDirection = TooltipDirection.TOP_CENTER
										scope.launch {
											tooltipState.show()
										}
									}
								) {
									Text("Show Tooltip - Top Center")
								}
								Button(
									modifier = Modifier,
									onClick = {
										scope.launch {
											tooltipState.dismiss()
										}
									}
								) {
									Text("Dismiss Tooltip")
								}
							}
							Button(
								onClick = {
									tooltipDirection = TooltipDirection.TOP_LEFT
									scope.launch {
										tooltipState.show()
									}
								}
							) {
								Text("Show Tooltip - Top Left")
							}
							Button(
								onClick = {
									tooltipDirection = TooltipDirection.TOP_RIGHT
									scope.launch {
										tooltipState.show()
									}
								}
							) {
								Text("Show Tooltip - Top Right")
							}
							Button(
								onClick = {
									tooltipDirection = TooltipDirection.BOTTOM_CENTER
									scope.launch {
										tooltipState.show()
									}
								}
							) {
								Text("Show Tooltip - Bottom Center")
							}
							Button(
								onClick = {
									tooltipDirection = TooltipDirection.BOTTOM_LEFT
									scope.launch {
										tooltipState.show()
									}
								}
							) {
								Text("Show Tooltip - Bottom Left")
							}
							Button(
								onClick = {
									tooltipDirection = TooltipDirection.BOTTOM_RIGHT
									scope.launch {
										tooltipState.show()
									}
								}
							) {
								Text("Show Tooltip - Bottom Right")
							}
							Button(
								onClick = {
									tooltipDirection = TooltipDirection.LEFT
									scope.launch {
										tooltipState.show()
									}
								}
							) {
								Text("Show Tooltip - Left")
							}
							Button(
								onClick = {
									tooltipDirection = TooltipDirection.RIGHT
									scope.launch {
										tooltipState.show()
									}
								}
							) {
								Text("Show Tooltip - Right")
							}
						}
					}

					// ② 툴팁 대상 컴포저블
					TooltipBox(
						modifier = Modifier
							.padding(16.dp),
						tooltip = {
							ToolTip(tooltipDirection)
						},
						positionProvider = remember {
							CustomTooltipPositionProvider(TooltipDirection.BOTTOM_CENTER)
						},
						state = tooltipState,
						focusable = true,
						enableUserInput = true
					) {
					}
				}
			}
		}
	}
}

@Composable
fun ToolTip(
	direction: TooltipDirection
) {
	val density = LocalDensity.current
	Column(
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Box(
			modifier = Modifier
				.background(
//					color = Color(0xFF000000).copy(alpha = 0.8F),
					color = Color(0xFFED27CF),
					shape = callOutShape(
						direction = direction,
						density = density,
					)
				)
				.padding(
					top = if (direction in listOf(
							TooltipDirection.TOP_CENTER,
							TooltipDirection.TOP_LEFT,
							TooltipDirection.TOP_RIGHT
						)
					) 14.dp else 8.dp,
					bottom = if (direction in listOf(
							TooltipDirection.BOTTOM_CENTER,
							TooltipDirection.BOTTOM_LEFT,
							TooltipDirection.BOTTOM_RIGHT
						)
					) 14.dp else 8.dp,
					start = if (direction == TooltipDirection.LEFT) 18.dp else 12.dp,
					end = if (direction == TooltipDirection.RIGHT) 18.dp else 12.dp
				)
		) {
			Text(text = "Supporting text Supporting \ntext Supporting", color = Color.White)
		}
	}
}

private fun callOutShape(
	direction: TooltipDirection,
	density: Density,
): Shape = GenericShape { size, _ ->
	val cornerRadius = with(density) { 10.dp.toPx() }
	val arrowCornerRadius = with(density) { 1.dp.toPx() }
	val arrowWidth = with(density) { 12.dp.toPx() }
	val arrowHeight = with(density) { 6.dp.toPx() }

	val bodyTop = if (direction in listOf(
			TooltipDirection.TOP_CENTER,
			TooltipDirection.TOP_LEFT,
			TooltipDirection.TOP_RIGHT
		)
	) arrowHeight else 0F

	val bodyBottom = if (direction in listOf(
			TooltipDirection.BOTTOM_CENTER,
			TooltipDirection.BOTTOM_LEFT,
			TooltipDirection.BOTTOM_RIGHT
		)
	) size.height - arrowHeight else size.height

	val bodyLeft = if (direction == TooltipDirection.LEFT) arrowHeight else 0f
	val bodyRight =
		if (direction == TooltipDirection.RIGHT) size.width - arrowHeight else size.width

	addRoundRect(
		roundRect = RoundRect(
			rect = Rect(bodyLeft, bodyTop, bodyRight, bodyBottom),
			topLeft = CornerRadius(cornerRadius, cornerRadius),
			topRight = CornerRadius(cornerRadius, cornerRadius),
			bottomRight = CornerRadius(cornerRadius, cornerRadius),
			bottomLeft = CornerRadius(cornerRadius, cornerRadius),
		)
	)

	if (direction == TooltipDirection.TOP_CENTER) {
		val centerX = size.width / 2F
		val leftX = centerX - arrowWidth / 2F
		val rightX = centerX + arrowWidth / 2F
		val baseY = arrowHeight
		val tipY = 0F

		moveTo(leftX, baseY)
		lineTo(centerX - arrowCornerRadius, arrowCornerRadius)
		quadraticTo(centerX, tipY, centerX + arrowCornerRadius, arrowCornerRadius)
		lineTo(rightX, baseY)
		close()
	}

	if (direction == TooltipDirection.TOP_LEFT) {
		val centerX = with(density) { 20.dp.toPx() } + arrowWidth / 2F
		val leftX = centerX - arrowWidth / 2F
		val rightX = centerX + arrowWidth / 2F
		val baseY = arrowHeight
		val tipY = 0F

		moveTo(leftX, baseY)
		lineTo(centerX - arrowCornerRadius, arrowCornerRadius)
		quadraticTo(centerX, tipY, centerX + arrowCornerRadius, arrowCornerRadius)
		lineTo(rightX, baseY)
		close()
	}

	if (direction == TooltipDirection.TOP_RIGHT) {
		val centerX = size.width - with(density) { 20.dp.toPx() } - arrowWidth / 2F
		val leftX = centerX - arrowWidth / 2F
		val rightX = centerX + arrowWidth / 2F
		val baseY = arrowHeight
		val tipY = 0F

		moveTo(leftX, baseY)
		lineTo(centerX - arrowCornerRadius, arrowCornerRadius)
		quadraticTo(centerX, tipY, centerX + arrowCornerRadius, arrowCornerRadius)
		lineTo(rightX, baseY)
		close()
	}

	if (direction == TooltipDirection.BOTTOM_CENTER) {
		val centerX = size.width / 2F
		val leftX = centerX - arrowWidth / 2F
		val rightX = centerX + arrowWidth / 2F
		val baseY = size.height - arrowHeight
		val tipY = size.height

		moveTo(leftX, baseY)
		lineTo(centerX - arrowCornerRadius, tipY - arrowCornerRadius)
		quadraticTo(centerX, tipY, centerX + arrowCornerRadius, tipY - arrowCornerRadius)
		lineTo(rightX, baseY)
		close()
	}

	if (direction == TooltipDirection.BOTTOM_LEFT) {
		val centerX = with(density) { 20.dp.toPx() } + arrowWidth / 2F
		val leftX = centerX - arrowWidth / 2F
		val rightX = centerX + arrowWidth / 2F
		val baseY = size.height - arrowHeight
		val tipY = size.height

		moveTo(leftX, baseY)
		lineTo(centerX - arrowCornerRadius, tipY - arrowCornerRadius)
		quadraticTo(centerX, tipY, centerX + arrowCornerRadius, tipY - arrowCornerRadius)
		lineTo(rightX, baseY)
		close()
	}

	if (direction == TooltipDirection.BOTTOM_RIGHT) {
		val centerX = size.width - with(density) { 20.dp.toPx() } - arrowWidth / 2F
		val leftX = centerX - arrowWidth / 2F
		val rightX = centerX + arrowWidth / 2F
		val baseY = size.height - arrowHeight
		val tipY = size.height

		moveTo(leftX, baseY)
		lineTo(centerX - arrowCornerRadius, tipY - arrowCornerRadius)
		quadraticTo(centerX, tipY, centerX + arrowCornerRadius, tipY - arrowCornerRadius)
		lineTo(rightX, baseY)
		close()
	}

	if (direction == TooltipDirection.LEFT) {
		val centerY = size.height / 2F
		val halfH = arrowWidth / 2F
		val topY = centerY - halfH
		val bottomY = centerY + halfH
		val baseX = arrowHeight
		val tipX = 0F

		moveTo(baseX, topY)
		lineTo(tipX + arrowCornerRadius, centerY - arrowCornerRadius)
		quadraticTo(tipX, centerY, tipX + arrowCornerRadius, centerY + arrowCornerRadius)
		lineTo(baseX, bottomY)
		close()
	}

	if (direction == TooltipDirection.RIGHT) {
		val centerY = size.height / 2f
		val halfH = arrowWidth / 2f
		val topY = centerY - halfH
		val bottomY = centerY + halfH
		val baseX = size.width - arrowHeight
		val tipX = size.width

		moveTo(baseX, topY)
		lineTo(tipX - arrowCornerRadius, centerY - arrowCornerRadius)
		quadraticTo(tipX, centerY, tipX - arrowCornerRadius, centerY + arrowCornerRadius)
		lineTo(baseX, bottomY)
		close()
	}
}