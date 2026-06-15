# Car Dealership

Учебный проект — бэкенд для автосалона. Клиенты могут заказать автомобиль или записаться на тест-драйв, сотрудники склада управляют инвентарём и сборкой.

## Сервисы

- **order-service** — приём заказов от клиентов
- **storage-service** — склад: автомобили, комплектующие, сборка

Сервисы общаются через gRPC и RabbitMQ. Для надёжной доставки сообщений реализован паттерн Transactional Outbox.

## Стек

Java 21, Spring Boot 4, PostgreSQL, RabbitMQ, gRPC, Keycloak (OAuth2), Liquibase, Testcontainers, Docker

## Запуск

```bash
# зависимости
docker compose -f docker/docker-file-dependency.yaml up -d

./gradlew :order-service:bootRun
./gradlew :storage-service:bootRun
```

Swagger UI доступен на `localhost:8080/swagger-ui.html` и `localhost:8081/swagger-ui.html`

## Тесты

```bash
./gradlew test             # юнит
./gradlew integrationTest  # интеграционные
```
