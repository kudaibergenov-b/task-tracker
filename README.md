# Task Tracker API – Тестовое задание

## Описание

REST API для управления задачами с использованием **Spring Boot**.  
Реализованы базовые CRUD-операции и ряд дополнительных функций (email, кэширование, авторизация и т.д.).

---

## Запуск проекта

### Требования

- Java 17
- Maven
- Docker (для Redis и/или упаковки)
- (опционально) Redis-сервер локально или в Docker

### Сборка и запуск

```bash
mvn clean package
docker build -t task-manager-app .
docker run -p 8080:8080 task-manager-app
```

### Альтернатива: запустить через IDE (IntelliJ IDEA)

---

## Авторизация

API защищён через Basic Auth.

- **Логин**: `admin`
- **Пароль**: `password`

> Пример запроса:
> ```
> curl -u admin:password http://localhost:8080/api/tasks
> ```

---

## API Эндпоинты

| Метод | URL | Описание |
|-------|-----|----------|
| POST  | `/api/tasks`      | Создание задачи |
| GET   | `/api/tasks`      | Получение всех задач |
| GET   | `/api/tasks/{id}` | Получение по ID |
| PUT   | `/api/tasks/{id}` | Обновление задачи |
| DELETE | `/api/tasks/{id}` | Удаление задачи |
| GET   | `/api/tasks/external` | Вызов внешнего API и лог |

---

## Выполненные пункты задания

### Основные:
- [x] Создание задачи
- [x] Получение всех задач
- [x] Получение по ID
- [x] Обновление задачи
- [x] Удаление задачи
- [x] Подключена H2 база данных
- [x] Логируются все входящие/исходящие запросы

### Дополнительные:
- [x] Вызов внешнего API и логирование ответа
- [x] Unit-тесты для `TaskServiceImpl`
- [x] Отправка email при создании задачи
- [x] Basic Authentication для API
- [x] Кэширование списка задач через Redis
- [x] Dockerfile для контейнеризации

---

## Дополнительно

### H2 Console
- URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC: `jdbc:h2:mem:tasksdb`
- Пользователь: `sa`, без пароля

---
