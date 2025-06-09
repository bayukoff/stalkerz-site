package ru.cool.sectorsite.mapper

import org.springframework.stereotype.Component
import ru.cool.sectorsite.dto.NewsDto
import ru.cool.sectorsite.model.News
import ru.cool.sectorsite.util.exception.NewsNotFoundException

@Component
class NewsMapper: Mapper<News, NewsDto> {
    override fun map(model: News): NewsDto = NewsDto(model.newsTitle, model.newsText, model?.imageUrl)

    fun map(dto: NewsDto): News = News(newsTitle = dto.newsTitle, newsText = dto.newsText, imageUrl = dto.imageUrl)

}