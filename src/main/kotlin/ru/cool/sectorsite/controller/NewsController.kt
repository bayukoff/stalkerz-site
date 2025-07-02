package ru.cool.sectorsite.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import ru.cool.sectorsite.dto.NewsDto
import ru.cool.sectorsite.model.News
import ru.cool.sectorsite.response.MessageResponse
import ru.cool.sectorsite.service.NewsService
import ru.cool.sectorsite.service.UserService

@RestController
@RequestMapping("/api/news")
class NewsController(
    private val newsService: NewsService
) {

    private val logger = LoggerFactory.getLogger(NewsController::class.java)

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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new")
    fun addNews(@RequestBody dto: NewsDto): ResponseEntity<MessageResponse> {
        newsService.add(dto)
        logger.info("Новость добавлена")
        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse("Новость успешно добавлена!"))
    }

}