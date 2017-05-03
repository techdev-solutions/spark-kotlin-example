package de.techdev.example

import com.google.gson.Gson
import spark.Spark.*
import java.sql.ResultSet

data class Person(val id: Long, val firstName: String, val lastName: String)

fun main(args: Array<String>) {
    val jdbc = Jdbc()
    createTablesAndData(jdbc)

    val personMapper = { rs: ResultSet -> Person(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")) }

    staticFiles.location("/static")
    redirect.get("/", "/index.html")
    val gson = Gson()
    get("/person", { req, _ ->
        if(req.queryParams("last_name") != null) {
            jdbc.queryForList("SELECT * FROM person WHERE last_name = ?", personMapper, req.queryParams("last_name"))
        } else {
            jdbc.queryForList("SELECT * FROM person", personMapper)
        }
    }, gson::toJson)
    post("/person", { req, _ ->
        val person = gson.fromJson(req.body(), Person::class.java)
        jdbc.execute("INSERT INTO person (first_name, last_name) VALUES (?, ?)", person.firstName, person.lastName)
        halt(201)
    })
    get("/person/:id", { req, _ ->
        jdbc.query("SELECT * FROM person WHERE id = ?", personMapper, req.params("id").toLong())
    }, gson::toJson)
}

private fun createTablesAndData(jdbc: Jdbc) {
    val initialSql = jdbc.javaClass.classLoader.getResource("initialData.sql").readText()
    jdbc.execute(initialSql)
}