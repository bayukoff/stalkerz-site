# StalkerZ MVP

**Краткое описание:**  
Проект — это веб-сайт с фронтендом на React и бэкендом на Spring Boot.  
Основные функции — аутентификация через JWT с httpOnly рефреш-токеном, работа с базой PostgreSQL, кеширование через Redis, логирование через AOP, и многое другое.  
Проект готов к использованию как MVP и собран в Docker-контейнеры для простого запуска.

---

## Технологии

### Фронтенд
- React
- React Query — для работы с асинхронными запросами
- React Three Fiber — 3D графика на React
- Framer Motion — анимации

### Бэкенд
- Spring Boot
- Spring Security + JWT (access + refresh токены)
- Spring Data JPA (PostgreSQL)
- Валидация с помощью Spring Validator
- DTO для передачи данных
- Логирование через Spring AOP аспекты
- Тесты для контроллеров и сервисов
- Redis для кэширования

### Инфраструктура
- Docker
- Nginx (для фронтенда)
- PostgreSQL
- Redis (планируется в Docker Compose)
- Минимальный базовый образ для Java на AWS  
