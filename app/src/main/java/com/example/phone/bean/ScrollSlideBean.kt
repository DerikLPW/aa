package com.example.phone.bean

data class FirstBean(
    val resultD: List<ResultD>
)

data class ResultD(
    val code: String,
    val detail: List<Detail>,
    val status: String,
    val title: String
)

data class Detail(
    val rate: String,
    val sub_title: String,
    val time: String,
    val value: String
)