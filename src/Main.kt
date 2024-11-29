
class Fizmati {

    fun gcd(a: Int, b: Int): Int {
         if (b == 0)
             return a
         else
            return  gcd(b, a % b)
    }

    fun lcm(a: Int, b: Int): Int {
        return (a * b) / gcd(a, b)
    }

    fun containsDollar(str: String): Boolean {
        return str.contains("$")
    }

    fun sumOfOddNumbers(n: Int = 100, sum: Int = 0): Int {
         if (n == 0)
             return sum
         else if (n % 2 == 0) {
             return sumOfOddNumbers(n - 1, sum + n)
         }
         else
             return sumOfOddNumbers(n - 1, sum)
    }

    fun reverseNumber(num: Int): Int {
        var number = num
        var reversed = 0
        while (number != 0) {
            val digit = number % 10
            reversed = reversed * 10 + digit
            number /= 10
        }
        return reversed
    }

    fun isPalindrome(str: String): Boolean {
        if(str.length == 0){
            return true;
        }
        var index = 0;

        while(index <= str.length/2){
            if(str[index] != str[str.length-1-index]){
                return(false)
                break
            }
            index++;
        }


        return true;
    }
}

fun main() {
    val Fizmati = Fizmati()

    println("36-ის და 60-ის უსგ არის: ${Fizmati.gcd(36, 60)}")

    println("36-ის და 60-ის უსჯ არის: ${Fizmati.lcm(36, 60)}")

    println("შეიცავს 'Hello$' '$'? ${Fizmati.containsDollar("Hello$")}")

    println("100-მდე ყველა ლუწი რიცხვის ჯამი : ${Fizmati.sumOfOddNumbers()}")

    println(" 10220-ს შემოტრიალებული რიცხვი არის: ${Fizmati.reverseNumber(10220)}")

    println("არის 'madam' პალინდრომი? ${Fizmati.isPalindrome("madam")}")

    println("არის 'tuso' პალინდრომი? ${Fizmati.isPalindrome("tuso")}")
}
