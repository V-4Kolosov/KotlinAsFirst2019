@file:Suppress("UNUSED_PARAMETER")

package lesson2.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import javax.tools.Diagnostic
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти число корней квадратного уравнения ax^2 + bx + c = 0
 */
fun quadraticRootNumber(a: Double, b: Double, c: Double): Int {
    val discriminant = discriminant(a, b, c)
    return when {
        discriminant > 0.0 -> 2
        discriminant == 0.0 -> 1
        else -> 0
    }
}

/**
 * Пример
 *
 * Получить строковую нотацию для оценки по пятибалльной системе
 */
fun gradeNotation(grade: Int): String = when (grade) {
    5 -> "отлично"
    4 -> "хорошо"
    3 -> "удовлетворительно"
    2 -> "неудовлетворительно"
    else -> "несуществующая оценка $grade"
}

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    val y3 = max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -sqrt(y3)           // 7
}

/**
 * Простая
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String {
    var answ: String
    answ = ""
    if (age % 100 in 5..20) {
        answ = "лет"
    } else {
        if (age % 10 == 1) answ = "год"
        else if (age % 10 in 2..4) answ = "года"
        else if (age % 10 in 5..9 || age % 10 == 0) answ = "лет"
    }
    return "$age $answ"
}

/**
 * Простая
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(kingX: Int, kingY: Int, rookX1: Int, rookY1: Int, rookX2: Int, rookY2: Int): Int {
    return when {
        ((kingX - rookX1 == 0 || kingY - rookY1 == 0) && (kingX - rookX2 == 0 || kingY - rookY2 == 0)) -> 3
        ((kingX - rookX1 != 0 || kingY - rookY1 != 0) && (kingX - rookX2 == 0 || kingY - rookY2 == 0)) -> 2
        ((kingX - rookX1 == 0 || kingY - rookY1 == 0) && (kingX - rookX2 != 0 || kingY - rookY2 != 0)) -> 1
        else -> 0
    }
}


/**
 * Простая
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
fun timeForHalfWay(
    t1: Double, v1: Double,
    t2: Double, v2: Double,
    t3: Double, v3: Double
): Double {
    val S1: Double = t1 * v1
    val S2: Double = t2 * v2
    val S3: Double = t3 * v3
    val halfS = (S1 + S2 + S3) / 2
    val answ: Double
    answ = when {
        halfS - S1 <= 0 -> halfS / v1
        halfS - S2 - S1 <= 0 -> t1 + (halfS - S1) / v2
        else -> t1 + t2 + (halfS - S1 - S2) / v3
    }
    return answ
}


fun rookOrBishopThreatens(kingX: Int, kingY: Int, rookX: Int, rookY: Int, bishopX: Int, bishopY: Int): Int = when {
    (kingX == rookX || kingY == rookY) && (abs(kingX - bishopX) == abs(kingY - bishopY)) -> 3
    (kingX == rookX || kingY == rookY) -> 1
    (abs(kingX - bishopX) == abs(kingY - bishopY)) -> 2
    else -> 0
}

/**
 * Простая
 *
 * Треугольник задан длинАми своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
fun triangleKind(a: Double, b: Double, c: Double): Int {
    val a1 = sqr(a)
    val b1 = sqr(b)
    val c1 = sqr(c)
    return when {
        (a1 < b1 + c1) && (b1 < a1 + c1) && (c1 < b1 + a1) -> 0
        (a1 == b1 + c1) || (b1 == a1 + c1) || (c1 == b1 + a1) -> 1
        (a + b > c) && (a + c > b) && (c + b > a) -> 2
        else -> -1
    }
}

/**
 * Средняя
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int {
    val answ = min(b, d) - max(a, c)
    return if (answ > -1) answ
    else -1
}


