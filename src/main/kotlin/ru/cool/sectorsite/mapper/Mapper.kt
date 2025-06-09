package ru.cool.sectorsite.mapper

interface Mapper<S, T> {
    fun map(model: S): T
}