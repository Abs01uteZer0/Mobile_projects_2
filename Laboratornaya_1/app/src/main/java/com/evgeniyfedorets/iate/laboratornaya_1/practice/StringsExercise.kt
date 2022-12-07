package com.evgeniyfedorets.iate.laboratornaya_1.practice

//17. Распечатайте текст предложения, который содержит наибольшее количество слов.
class StringsExercise {
    val text = "text text text. txt txt txt txt txt txt txt txt txt txt." +
            " text text text txet txet txet. teext teext teext teext teext teext teext teext teext teext."
    val lines = text.split("\\. ".toRegex())    //Разбиваем текст на предложения и удаляем пробелы после точки
    val mapWithCount = lines.groupBy ({it}, {it.count { it.isWhitespace() }})     //Посчитали сколько в каждом предложении пробелов
    val answer = mapWithCount.keys.max()      //Получаем максимальное значение
    val max_line = mapWithCount.get(answer)     //Получаем строку, где больше всего слов по ключу

    var inputData: String
        get() = text
        set(value) {}

    var result: String
    get() = "The answer is: " + answer + " number of words: " + max_line     //Выводим на экран
    set(value) {}
}