package de.techdev.example

import spark.Spark.get

fun main(args: Array<String>) {
    get("/",  { _, _ -> "hello" })
}