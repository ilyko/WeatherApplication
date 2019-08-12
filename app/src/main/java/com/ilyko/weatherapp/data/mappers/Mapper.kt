package com.ilyko.weatherapp.data.mappers

interface Mapper<OUT, IN> {
    fun map(input: IN): OUT
}