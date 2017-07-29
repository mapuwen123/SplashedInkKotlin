package com.marvin.splashedinkkotlin.db

import org.jetbrains.anko.db.RowParser
import org.jetbrains.anko.db.rowParser

/**
 * Created by Administrator on 2017/7/29.
 */
class MyRowParser : RowParser<Triple<String, String, String>> {
    override fun parseRow(columns: Array<Any?>): Triple<String, String, String> {
        return Triple(columns[0] as String, columns[1] as String, columns[2] as String)
    }

    val parser = rowParser { photo_id: String, url: String, preview_url: String ->
        Triple(photo_id, url, preview_url)
    }
}