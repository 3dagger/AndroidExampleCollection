package kr.dagger.stateflowexample.extension

import java.util.ArrayList
import java.util.Date
import java.util.regex.Matcher
import java.util.regex.Pattern

fun String?.default(default: String): String {
	return this ?: default
}

fun String?.defaultEmpty(): String {
	return this.default("")
}

fun String.parse(): List<String> {
	val output: MutableList<String> = ArrayList()
	val match: Matcher = Pattern.compile("[0-9]+|[a-z]+|[A-Z]+").matcher(this)
	while (match.find()) {
		output.add(match.group())
	}
	return output
}

fun String.divide(): List<String> {
	val output: MutableList<String> = ArrayList()
	output.add(this.substring(0, 6))
	output.add(this.substring(6))
	return output
}

fun String.toYNBoolean(): Boolean {
	return this == "Y"
}

fun Int?.default(default: Int): Int {
	return this ?: default
}

fun Int?.defaultZero(): Int {
	return this.default(0)
}

fun Long?.default(default: Long): Long {
	return this ?: default
}

fun Long?.defaultZero(): Long {
	return this.default(0)
}

fun Double?.default(default: Double): Double {
	return this ?: default
}

fun Double?.defaultZero(): Double {
	return this.default(0.0)
}

fun Float?.default(default: Float): Float {
	return this ?: default
}

fun Float?.defaultZero(): Float {
	return this.default(0.0f)
}

fun <T> List<T>?.defaultEmpty(): List<T> {
	return this ?: listOf()
}


fun Date?.default(default: Date): Date {
	return this ?: default
}

fun Date?.defaultToday(): Date {
	return this.default(Date())
}

fun Boolean?.default(default: Boolean): Boolean {
	return this ?: default
}

fun Boolean?.defaultFalse(): Boolean {
	return this.default(false)
}

fun Boolean?.defaultTrue(): Boolean {
	return this.default(true)
}

fun Boolean?.toYNString(): String {
	return if(defaultFalse()) "Y" else "N"
}

fun <T> T?.default(default: T): T {
	return this ?: default
}