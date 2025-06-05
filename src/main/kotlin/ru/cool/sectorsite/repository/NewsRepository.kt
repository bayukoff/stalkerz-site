package ru.cool.sectorsite.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.cool.sectorsite.model.News

@Repository
interface NewsRepository: JpaRepository<News, Int> {

    @Query("select news from News news order by news.id desc limit ?1")
    fun findLastAmountNews(amount: Int): List<News>

    @Query("select COUNT(*) from News")
    fun getNewsAmount(): Int

}