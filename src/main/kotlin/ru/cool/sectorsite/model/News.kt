package ru.cool.sectorsite.model

import jakarta.persistence.*
import org.springframework.data.jpa.repository.Temporal
import java.util.*

@Table(name = "news")
@Entity
data class News(
    @Id @Column(name = "news_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    @Column(name = "news_title")
    val newsTitle: String = "",
    @Column(name = "news_text")
    val newsText: String = "",
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    val createdAt: Date = Date(),
    @Column(name = "image_url")
    val imageUrl: String? = "",
)
