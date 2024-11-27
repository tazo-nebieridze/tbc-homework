
fun getNumbers(input:String):String {

    return input.filter { c: Char -> c.isDigit() }

}


fun main() {
    while (true) {
        println("შეიყვანეთ X ცვლადის მნიშვნელობა:")
        val x = readLine() ?: ""

        println("შეიყვანეთ ცვლადის მნიშვნელობა Y:")
        val y = readLine() ?: ""

        val xNumbers = getNumbers(x)
        val yNumbers = getNumbers(y)

        var xValue = 0

        if (!xNumbers.isEmpty()){

            xValue = xNumbers.toInt()

        }

        var yValue = 0

        if (!yNumbers.isEmpty()){

            yValue = yNumbers.toInt()

        }

        var result: String

        if (yValue == 0) {
            result = "ნულზე გაყოფა აკრძალულია"
        }  else {
            val z = xValue / yValue
            result = "X და Y განაყოფი არის: $z"
        }

        println(result)

        var answer: String

        while (true) {
            println("გსურთ პროგრამის ხელახლა დაწყება <Y/N>?")
            answer = readLine()?.trim() ?: ""
            if (answer == "y" || answer == "n" || answer == "Y" || answer == "N") {
                break
            } else {
                println("გთხოვთ შეიყვანოთ მხოლოდ \"Y\" ან \"N\"")
            }
        }


        if (answer == "n" || answer == "N") {
            break
        }
    }
}

