# Миграция на PostgreSQL

## Выполненные изменения

### 1. Обновлены зависимости Maven
Во всех сервисах (`orders-service`, `products-service`, `payments-service`) заменена зависимость:
- **Удалено**: `com.h2database:h2`
- **Добавлено**: `org.postgresql:postgresql` (scope: runtime)

### 2. Добавлены PostgreSQL контейнеры в docker-compose.yml
Созданы отдельные базы данных для каждого сервиса:
- **postgres-orders**: порт 5432, база данных `orders_db`
- **postgres-products**: порт 5433, база данных `products_db`  
- **postgres-payments**: порт 5434, база данных `payments_db`

### 3. Обновлены конфигурации подключения к БД
В `application.properties` каждого сервиса добавлены настройки PostgreSQL:

#### Orders Service (порт 5432)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/orders_db
spring.datasource.username=orders_user
spring.datasource.password=orders_password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true
```

#### Products Service (порт 5433)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/products_db
spring.datasource.username=products_user
spring.datasource.password=products_password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true
```

#### Payments Service (порт 5434)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5434/payments_db
spring.datasource.username=payments_user
spring.datasource.password=payments_password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true
```

## Инструкции по запуску

### 1. Запуск инфраструктуры
```bash
# Запуск Kafka и PostgreSQL контейнеров
docker-compose up -d
```

### 2. Проверка состояния контейнеров
```bash
# Проверить, что все контейнеры запущены
docker-compose ps
```

### 3. Запуск микросервисов
```bash
# В отдельных терминалах запустить каждый сервис:

# Orders Service (порт 8080)
cd orders-service
mvn spring-boot:run

# Products Service (порт 8081)
cd products-service
mvn spring-boot:run

# Payments Service (порт 8082)
cd payments-service
mvn spring-boot:run

# Credit Card Processor Service (порт 8084)
cd credit-card-processor-service
mvn spring-boot:run
```

## Важные замечания

1. **UUID поддержка**: Все сущности используют `GenerationType.UUID`, что полностью поддерживается PostgreSQL
2. **Автоматическое создание схемы**: Используется `spring.jpa.hibernate.ddl-auto=update` для автоматического создания/обновления таблиц
3. **Отдельные базы данных**: Каждый сервис имеет свою собственную базу данных для соблюдения принципов микросервисной архитектуры
4. **Логирование SQL**: Включено для отладки (`spring.jpa.show-sql=true`)

## Проверка подключения к БД

После запуска сервисов можно проверить подключение к PostgreSQL:

```bash
# Подключение к базе данных orders
docker exec -it postgres-orders psql -U orders_user -d orders_db

# Подключение к базе данных products  
docker exec -it postgres-products psql -U products_user -d products_db

# Подключение к базе данных payments
docker exec -it postgres-payments psql -U payments_user -d payments_db
```

## Откат изменений

Если потребуется вернуться к H2:

1. Восстановить зависимости H2 в pom.xml файлах
2. Удалить PostgreSQL конфигурации из application.properties
3. Удалить PostgreSQL контейнеры из docker-compose.yml

