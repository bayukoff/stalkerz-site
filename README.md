# StalkerZ MVP
## Проект для ознакомления. Не для запуска.

**Краткое описание:**  
Проект — это веб-сайт с фронтендом на React и бэкендом на Spring Boot.  
Основные функции — аутентификация через JWT с httpOnly рефреш-токеном, работа с базой PostgreSQL, кеширование через Redis, логирование через AOP, и многое другое.

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
- Redis
- Минимальный базовый образ для Java на AWS  
