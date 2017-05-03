package de.techdev.example

import com.google.gson.Gson
import spark.Spark.get

data class Person(val id: Long, val firstName: String, val lastName: String)

fun main(args: Array<String>) {
    val jdbc = Jdbc()
    createTablesAndData(jdbc)

    val gson = Gson()
    get("/", { _, _ -> "hello" })
    get("/person", { _, _ ->
        jdbc.queryForList("SELECT * FROM person", {
            Person(it.getLong("id"), it.getString("first_name"), it.getString("last_name"))
        })
    }, gson::toJson)
}

private fun createTablesAndData(jdbc: Jdbc) {
    val initialSql = jdbc.javaClass.classLoader.getResource("initialData.sql").readText()
    jdbc.execute(initialSql)
}