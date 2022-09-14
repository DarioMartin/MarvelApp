package com.dariomartin.marvelapp.data

fun String.convertToHttps(): String {
    return this.replace("http:", "https:")
}