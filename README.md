# Synthetic Human Core Starter ("Project Bishop")

> **Код доступа: OMEGA**  
> Разработка универсального Spring Boot стартера для андроидов компании Weyland-Yutani.

---

## 📌 Описание

`synthetic-human-core-starter` — это **готовый Spring Boot стартер**, который предоставляет базовую функциональность для всех будущих моделей синтетических людей (андроидов).

Он включает:
- Приём и валидацию команд
- Исполнение с приоритетами (`CRITICAL` — мгновенно, `COMMON` — в очереди)
- Аудит действий через `@WeylandWatchingYou`
- Мониторинг занятости и статистики
- Единый формат обработки ошибок

Проект соответствует требованиям, поставленным после инцидента с кораблём «Ностромо».

---

## ✅ Выполненные требования

| № | Требование | Реализация |
|---|------------|-----------|
| 1 | **Модуль приёма и исполнения команд** | Команды принимаются, валидируются, `CRITICAL` — выполняются сразу, `COMMON` — в `ThreadPoolExecutor` |
| 2 | **Мониторинг занятости андроида** | Через Actuator: <br>• `/actuator/metrics/queue.size` — текущая очередь <br>• `/actuator/metrics/commands.by.author` — статистика по авторам |
| 3 | **Аудит действий** | Аннотация `@WeylandWatchingYou` логирует вызовы методов. Режимы: <br>• `audit.mode=console` — вывод в консоль <br>• `audit.mode=kafka` — отправка в Kafka |
| 4 | **Модуль обработки ошибок** | Все ошибки возвращаются в едином формате с корректными HTTP-статусами |

---

## 🧪 Демонстрация: `bishop-prototype`

Для проверки функционала создан сервис-эмулятор `bishop-prototype`, принимающий команды через REST API.

### 🛠 Запуск

1. Убедитесь, что Kafka и Zookeeper запущены (см. ниже)
2. Соберите и установите стартер:
   ```bash
   cd synthetic-human-core-starter
   mvn clean install
3. Запустите bishop-prototype:
   ```bash
   cd bishop-prototype
   mvn spring-boot:run

### Запуск Kafka (с Zookeeper)
1. Запустите Zookeeper:
>bin/zookeeper-server-start.sh config/zookeeper.properties

2. Запустите Kafka:
>bin/kafka-server-start.sh config/server.properties

3. Создайте топик:
>bin/kafka-topics.sh --create --topic audit-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

4. Запустите consumer (в отдельном окне):
>bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic audit-topic --from-beginning

## 📡 REST API
bishop-prototype предоставляет REST-интерфейс для взаимодействия с андроидом.
1. Принять команду
    ```bash
   POST /api/commands
    Content-Type: application/json
    
    {
    "description": "Проверить кислород",
    "priority": "CRITICAL",
    "author": "Рипли",
    "time": "2025-04-05T12:00:00Z"
    }
🛑 Ошибки (примеры)
1. Невалидная команда
    ```bash
   HTTP/1.1 400 Bad Request
   
   {
    "message": "description: Description не может быть пустой",
    "status": 400,
    "timestamp": "2025-04-05T12:00:00",
    "path": "/api/commands"
    }
2. Очередь переполнена
    ```bash
   HTTP/1.1 503 Service Unavailable

    {
    "message": "Очередь переполнена, команда не принята",
    "status": 503,
    "timestamp": "2025-04-05T12:00:00",
    "path": "/api/commands"
    }
3. Внутренняя ошибка
    ```bash
   HTTP/1.1 500 Internal Server Error
   
    {
    "message": "Внутренняя ошибка сервера",
    "status": 500,
    "timestamp": "2025-04-05T12:00:00",
    "path": "/api/commands"
    }

## 📊 Метрики (Actuator)
1. Пример ответа /actuator/metrics/queue.size:
    ```bash
   {
    "name": "queue.size",
    "measurements": [ { "statistic": "VALUE", "value": 3.0 } ],
    "availableTags": []
    }
2. Пример ответа /actuator/metrics/commands.by.author:
    ```bash
   {
    "name": "commands.by.author",
    "measurements": [ { "statistic": "COUNT", "value": 7.0 } ],
    "availableTags": [
    { "tag": "author", "values": ["Рипли", "Бретт"] }
    ]
    }
## 📦 Стек технологий
| Технология | Назначение |
|----------|-----------|
| **Spring Boot 3** | Основа приложения и стартера, управление контекстом и зависимостями |
| **Spring AOP** | Реализация аудита через аннотацию `@WeylandWatchingYou` |
| **Kafka** | Асинхронная отправка аудит-событий в топик `audit-topic` при `audit.mode=kafka` |
| **ThreadPoolExecutor** | Управление очередью задач для команд с приоритетом `COMMON` |
| **Actuator + Micrometer** | Публикация метрик для мониторинга: очередь, статистика по авторам |
| **@ExceptionHandler** | Централизованная обработка ошибок и возврат единого формата ответа |
| **Bean Validation** | Валидация полей команды (`@NotBlank`, `@NotNull` и др.) |
| **Maven** | Управление зависимостями и сборка проекта |