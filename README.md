# 📅 Booking Service

Booking-Service is a microservice for managing bookings and blocks. It provides RESTful endpoints to create, update, cancel, rebook, and delete bookings, as well as to create, update, and delete blocks, ensuring that no overlapping reservations occur.

## Features

- **Booking Management:** Create, update, cancel, rebook, and delete bookings.
- **Block Management:** Define blocks to prevent overlapping reservations.
- **RESTful API:** Follows REST architectural style.
- **In-memory Database:** Uses H2 database for development and testing.

## Architecture

The project follows a typical Spring Boot microservices structure with separation into controllers, services, repositories, and domain models.

### Sequence Diagram

Below is the sequence diagram illustrating the booking process:

![Booking Sequence Diagram](./docs/sequenceDiagram.png "Booking Flow")

## Getting Started

### Prerequisites

- Java 21
- Maven (or use the Maven Wrapper included in the project)
- Docker

### Building the Project

You can build the project using Maven with the Maven Wrapper:

```bash
./mvnw clean package -DskipTests
```

### Running Tests

To run tests locally:

```bash
./mvnw test
```

## Docker

You can build and run the application using Docker.

### Build and Run

1. **Build the Docker Image:**

   ```bash
   docker build -t booking-service:latest .
   ```

2. **Run the Docker Container:**

   ```bash
   docker run -d -p 8080:8080 --name booking-service booking-service:latest
   ```

   The service will be available at [http://localhost:8080](http://localhost:8080).


3. **Stop the Container:**

   ```bash
   docker stop booking-service
   ```

4. **Remove the Container:**

   ```bash
   docker rm booking-service
   ```

## API Usage
### Postman Collection
[`docs/booking-service.postman_collection.json`](/docs/booking-service.postman_collection.json)

---
## Booking Service

| Method     | Endpoint                | Description               | Request Body Example                                                                                                      |
|------------|-------------------------|---------------------------|---------------------------------------------------------------------------------------------------------------------------|
| **GET**    | `v1/bookings`           | List all bookings         | -                                                                                                                         |
| **GET**    | `v1/bookings/{id}`      | Get a booking by ID       | -                                                                                                                         |
| **POST**   | `v1/bookings`             | Create a new booking      | {<br>"propertyId": 1,<br> "guestName": "Luno Souza",<br>  "startDate": "2025-05-10",<br>  "endDate": "2025-05-15"<br>}    |
| **PUT**    | `v1/bookings/{id}`        | Update a booking by ID    | {<br>  "propertyId": 1,<br>  "guestName": "Luno Souza",<br>  "startDate": "2025-05-10",<br>  "endDate": "2025-05-25"<br>} |
| **POST**   | `v1/bookings/{id}/cancel` | Cancel a booking          | -                                                                                                                         |
| **POST**   | `v1/bookings/{id}/rebook` | Rebook a canceled booking | -                                                                                                                         |
| **DELETE** | `v1/bookings/{id}`        | Delete a booking          | -                                                                                                                         |

## Block Service
| Method     | Endpoint                | Description                             | Request Body Example                                                                  |
|------------|-------------------------|-----------------------------------------|---------------------------------------------------------------------------------------|
| **GET**    | `v1/blocks`               | List all blocks                         | -                                                                                     |
| **GET**    | `v1/blocks/{id}`          | Get a block by ID                       | -                                                                                     |
| **POST**   | `v1/blocks`               | Create a new block                      | {<br>"propertyId": 1,<br> "startDate": "2025-05-10",<br> "endDate": "2025-05-15"<br>} |
| **PUT**    | `v1/blocks/{id}`          | Update a block by ID                    | {<br>"propertyId": 1,<br> "startDate": "2025-05-10",<br> "endDate": "2025-05-25"<br>} |
| **DELETE** | `v1/blocks/{id}`          | Delete a block                          | -                                                                                     |
---

### 📝 Notes:
- Replace `{id}` with the actual resource ID (e.g., `/bookings/1`).
- Dates must be in **ISO format** (`YYYY-MM-DD`).


(under construction)
