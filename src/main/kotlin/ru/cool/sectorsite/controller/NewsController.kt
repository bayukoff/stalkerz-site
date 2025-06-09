package ru.cool.sectorsite.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.cool.sectorsite.dto.NewsDto
import ru.cool.sectorsite.service.NewsService
import ru.cool.sectorsite.service.UserService

@RestController
@RequestMapping("/api/news")
class NewsController(
    private val newsService: NewsService
) {
    @GetMapping
    fun getAllNews(): List<NewsDto>{
        return newsService.getNews().map {
            newsService.convertToDto(it)
        }
    }

    @GetMapping("/someNews/{amount}")
    fun getLastNews(@PathVariable amount: Int): List<NewsDto> {
        return newsService.getLastAmountNews(amount).map(newsService::convertToDto)
    }

    @GetMapping("/pages/{num}")
    fun getNewsOnPage(@PathVariable num: Int): List<NewsDto> {
        return newsService.getNews(num, 8, true).map(newsService::convertToDto)
    }

    @GetMapping("/pages")
    fun getAmountPages(): Int{
        return newsService.getNewsAmount()
    }

}