package ru.cool.sectorsite.service

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.cool.sectorsite.dto.NewsDto
import ru.cool.sectorsite.mapper.NewsMapper
import ru.cool.sectorsite.model.News
import ru.cool.sectorsite.repository.NewsRepository

@Service
class NewsService(
    private val newsMapper: NewsMapper,
    private val newsRepository: NewsRepository,
    private val cacheManager: CacheManager
) {

    @Cacheable("news")
    fun getNews(): List<News> {
        return newsRepository.findAll(Sort.by(Sort.Order.desc("id")))
    }

    @Cacheable(value = ["news_page"], key = "#pageNum")
    fun getNews(pageNum: Int, pageSize: Int, desc: Boolean): List<News> {
        if (desc) {
            return newsRepository.findAll(PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.desc("id")))).content
        }
        return newsRepository.findAll(PageRequest.of(pageNum, pageSize)).content
    }

    @Cacheable(value = ["new"], key = "#amount")
    fun getLastAmountNews(amount: Int): List<News> {
        return newsRepository.findLastAmountNews(amount)
    }

    fun getNewsAmount(): Int = newsRepository.getNewsAmount()

    /**
     * Использует ручное кэширование
     */
    fun add(dto: NewsDto): News{
        val news = News(newsTitle = dto.newsTitle, newsText = dto.newsText, imageUrl = dto.imageUrl)
        val cache = newsRepository.save(news)
        cacheManager.getCache("news")?.put(cache.id, cache)
        return cache
    }

    fun convertToDto(news: News): NewsDto = newsMapper.map(news)

}