Redis Cache Demo with Spring Boot & Docker

This project demonstrates how to use Spring Boot Cache Abstraction with Redis (running in Docker) to cache REST API responses.

---

Technologies Used

- Spring Boot
- Spring Cache Abstraction
- Redis (Docker container)
- Java 17+
- Maven

---

Project Structure:

src/
├── controller/
│   ├── ProductController.java
│   └── CacheController.java
├── model/
│   └── Product.java
├── service/
│   └── ProductService.java
├── RedisCacheDemoApplication.java
└── resources/
    └── application.properties

 ---

Docker Setup (Redis)

Run Redis using Docker:

- Start Docker by running the command inside the project directory.

docker-compose up -d

- Start spring boot application inside docker

./mvnw spring-boot:run

1. First call (not cached, 6s delay)
curl http://localhost:8080/api/product/1

2. Second call (cached, instant)
curl http://localhost:8080/api/product/1

3. Clear Redis cache
curl -X DELETE http://localhost:8080/api/cache/clear

4. Call again (cache cleared, 6s delay again)
curl http://localhost:8080/api/product/1


