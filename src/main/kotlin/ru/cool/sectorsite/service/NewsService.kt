package ru.cool.sectorsite.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.cool.sectorsite.dto.NewsDto
import ru.cool.sectorsite.model.News
import ru.cool.sectorsite.repository.NewsRepository

@Service
class NewsService(
    private val newsRepository: NewsRepository
) {
    fun getNews(): List<News> {
        return newsRepository.findAll(Sort.by(Sort.Order.desc("id")))
    }

    fun getNews(pageNum: Int, pageSize: Int, desc: Boolean): List<News> {
        if (desc) {
            return newsRepository.findAll(PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.desc("id")))).content
        }
        return newsRepository.findAll(PageRequest.of(pageNum, pageSize)).content
    }

    fun getLastAmountNews(amount: Int): List<News> {
        return newsRepository.findLastAmountNews(amount)
    }

    fun getNewsAmount(): Int{
        return newsRepository.getNewsAmount()
    }

    fun convertToDto(news: News): NewsDto {
        return NewsDto(news.newsTitle, news.newsText, news.imageUrl)
    }


}