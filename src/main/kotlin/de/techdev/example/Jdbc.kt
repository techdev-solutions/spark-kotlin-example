package de.techdev.example

import org.h2.jdbcx.JdbcDataSource
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import javax.sql.DataSource

class Jdbc {

    private val dataSource: DataSource

    init {
        dataSource = JdbcDataSource()
        // DB_CLOSE_DELAY needed so the tables don't get removed after a connection is closed
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
        dataSource.user = "sa"
        dataSource.password = "sa"
    }

    /**
     * Executes a query like an INSERT or UPDATE
     */
    fun execute(sql: String, vararg args: Any?) {
        callInternal(sql, {
            it.execute()
        }, *args)
    }

    /**
     * Queries for a single object. Throws an IllegalStateException if no row is found for the query.
     */
    fun <T> query(sql: String, mapper: (rs: ResultSet) -> T, vararg args: Any?): T {
        return callInternal(sql, {
            val rs = it.executeQuery()
            if (rs.next()) {
                mapper.invoke(rs)
            } else {
                throw IllegalStateException("No result found for query.")
            }
        }, *args)
    }

    /**
     * Queries the database for a list of objects.
     */
    fun <T> queryForList(sql: String, rowMapper: (rs: ResultSet) -> T, vararg args: Any?): List<T> {
        return callInternal(sql, {
            val result = ArrayList<T>()
            val rs = it.executeQuery()
            while (rs.next()) {
                result.add(rowMapper.invoke(rs))
            }
            result
        }, *args)
    }

    /**
     * Creates and cleans up a prepared statement with a user provided action on the statement.
     */
    private fun <T> callInternal(sql: String, action: (pstmt: PreparedStatement) -> T, vararg args: Any?): T {
        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        try {
            conn = dataSource.connection
            pstmt = conn.prepareStatement(sql)
            pstmt.setParameters(*args)
            return action.invoke(pstmt)
        } finally {
            conn?.close()
            pstmt?.close()
        }
    }
}

fun PreparedStatement.setParameters(vararg args: Any?) {
    for (i in args.indices) {
        val arg = args[i]
        val statementIndex = i + 1
        when(arg) {
            is String -> this.setString(statementIndex, arg)
            is Long -> this.setLong(statementIndex, arg)
        }
    }
}