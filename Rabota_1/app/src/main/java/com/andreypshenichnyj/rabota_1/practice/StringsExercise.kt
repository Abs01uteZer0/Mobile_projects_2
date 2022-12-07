package com.andreypshenichnyj.rabota_1.practice

class StringsExercise {
    val text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris faucibus porttitor tincidunt. Sed orci libero, " +
            "efficitur id eleifend sit amet, hendrerit quis nibh. Cras et commodo lectus. Maecenas sem libero, " +
            "condimentum semper condimentum non, mollis viverra massa. Donec cursus maximus gravida. Pellentesque vitae " +
            "cursus magna, at egestas nulla. Ut eget augue eget magna mattis convallis a sed nisl. Donec ut porta lorem, " +
            "et dapibus dolor. Pellentesque ac erat tortor."

    var map = mutableMapOf("a" to 0)

    val words = text.split(" ").filter{ it.all { it.isLetter() } }
    val firstLetters = words.map { it.codePointAt(0).toChar().lowercase() }.sorted()      //Получаем лист первых букв
    val popularWords = words.filter { threeMostPopularLetters().contains(it.codePointAt(0).toChar().lowercase()) }.sorted()     //Находим популярные слова

    fun threeMostPopularLetters(): List<String> {
        var counter = 1
        for (i in 0..firstLetters.size - 2){
            if (firstLetters.get(i) == firstLetters.get(i+1)){        //Сравниваем текущий элемент и следующий
                counter++
            } else {
                map.put(firstLetters.get(i), counter)                  //Записываем в map ключ - букву и значение - ее количество повторений
                counter = 1
            }
        }
        if (firstLetters.get((firstLetters.size-1)) != firstLetters.get((firstLetters.size-2))){        //Проверка на случай, если в конце буква повторяется 1 раз
            map.put(firstLetters.get((firstLetters.size-1)), 1)
        }

        map = map.toList()          //Сортируем по убыванию (наиболее встречающийся элемент окажется первым)
            .sortedByDescending { (key, value) -> value }
            .toMap() as MutableMap<String, Int>

        var n = 0

        if (map.size > 3){
            n = 3
        } else {
            n = map.size
        }
        val Letters = map.keys.toList().take(n)

        return Letters                  //Возвращаем список букв (3 или меньше) отсортированный по убыванию
    }

    var inputData: String
        get() = text
        set(value) {}

    var result: String
        get() = "Most 3 popular letters: " + threeMostPopularLetters().toString() + "\nWords with popular first letters: " + popularWords
        set(value) {}
}