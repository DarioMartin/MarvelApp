package com.dariomartin.talentoapp.data

fun String.convertToHttps(): String {
    return this.replace("http:", "https:")
}