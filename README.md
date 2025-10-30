# Inventory Management System

A **Spring Boot** based backend application designed to manage and streamline inventory operations.  
This system enables efficient management of **Products**, **Suppliers**, **Warehouses**, and **Purchase Orders**, with seamless database integration and cloud-ready deployment.

---

## 🚀 Overview

The **Inventory Management System** provides a RESTful API that allows businesses to efficiently track inventory items, monitor supplier information, manage warehouses, and automate purchase orders when stock levels fall below the defined threshold.

Built with **Java 21**, the project follows clean architecture principles and leverages modern DevOps tools for continuous integration and deployment.

---

## 🧩 Features

- ✅ Full CRUD operations for Products, Warehouses, Suppliers, and Purchase Orders  
- ⚙️ Auto reorder check logic when stock is below threshold  
- 🧱 Entity-relationship mapping via JPA/Hibernate  
- 🧰 Dockerized for containerized deployments  
- 🌍 Cloud-deployable to **Render** or other platforms  
- 🔒 Secure environment configuration via environment variables  
- 🧾 Detailed logging and error handling

---

## 🧰 Tech Stack

| Category | Technology |
|-----------|-------------|
| **Language** | Java 21 |
| **Framework** | Spring Boot 3.5.6 |
| **Database** | MySQL / Aiven Cloud MySQL |
| **ORM** | Hibernate / JPA |
| **Build Tool** | Maven |
| **Validation** | Jakarta Validation (Spring Boot Starter Validation) |
| **Testing** | JUnit, Spring Boot Starter Test, H2 Database |
| **Dependency Management** | Maven |
| **Containerization** | Docker |
| **Deployment** | Render.com |

---

## 📦 Dependencies

Below are the main dependencies used in the project:

| Dependency | Purpose |
|-------------|----------|
| `spring-boot-starter-web` | Builds RESTful APIs |
| `spring-boot-starter-data-jpa` | ORM support for entity management |
| `spring-boot-starter-validation` | Input validation with Jakarta Validation |
| `spring-boot-starter-test` | Unit testing suite |
| `mysql-connector-j` | JDBC driver for MySQL |
| `h2` | In-memory database for testing |
| `lombok` | Boilerplate code reduction (Getters/Setters/Constructors) |

---

## ⚙️ Configuration

### `application.properties`
```properties
spring.application.name=InventoryManagementSystem
spring.datasource.url=jdbc:mysql://<DB_HOST>:<PORT>/<DB_NAME>?sslMode=REQUIRED&useSSL=true&requireSSL=true&serverTimezone=UTC
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
server.port=${PORT:8080}
```

### Environment Variables

| Variable | Description |
|-----------|--------------|
| `DB_USER` | Database username |
| `DB_PASSWORD` | Database password |
| `DB_HOST` | Database hostname or connection URL |
| `DB_NAME` | Database name |
| `PORT` | Server port (auto-assigned on Render) |

---

## 🐳 Docker Setup

### Build Image
```bash
docker build -t inventorymanagementsystem .
```

### Run Container
```bash
docker run -d -p 8080:8080   -e DB_USER=<your_db_user>   -e DB_PASSWORD=<your_db_password>   -e DB_HOST=<your_db_host>   -e DB_NAME=<your_db_name>   inventorymanagementsystem
```

### Verify
Visit [http://localhost:8080](http://localhost:8080)

---

## ☁️ Deployment on Render

1. Connect the GitHub repository to Render.com  
2. Add Environment Variables under **Render → Environment → Add Variables**  
3. Deploy the service and monitor logs to confirm connection to Aiven MySQL  
4. Application runs on `https://inventory-management-system-mmo5.onrender.com`

---

## 📫 API Endpoints

Base URL: `https://inventory-management-system-mmo5.onrender.com/api`

### **Warehouse APIs**
| Method | Endpoint | Description |
|--------|-----------|-------------|
| POST | `/warehouses` | Create new warehouse |
| GET | `/warehouses` | Fetch all warehouses |
| GET | `/warehouses/{id}` | Fetch warehouse by ID |
| DELETE | `/warehouses/{id}` | Delete warehouse |

### **Supplier APIs**
| Method | Endpoint | Description |
|--------|-----------|-------------|
| POST | `/suppliers` | Create new supplier |
| GET | `/suppliers` | Fetch all suppliers |
| GET | `/suppliers/{id}` | Fetch supplier by ID |
| DELETE | `/suppliers/{id}` | Delete supplier |

### **Product APIs**

| Method | Endpoint | Description |
|--------|-----------|-------------|
| POST | `/products` | Create new product |
| GET | `/products` | Fetch all products |
| GET | `/products/{id}` | Fetch product by ID |
| POST | `/products/check-reorder/{id}` | Check reorder threshold |
| DELETE | `/products/{id}` | Delete product |

### **Purchase Order APIs**

| Method | Endpoint | Description |
|--------|-----------|-------------|
| POST | `/purchase-orders` | Create new purchase order |
| GET | `/purchase-orders` | Fetch all purchase orders |
| DELETE | `/purchase-orders/{id}` | Delete purchase order |

---


## 📜 License

This project is licensed under the [MIT License](./LICENSE).

---

## 🏗️ Future Enhancements

- Add authentication and role-based access control  
- Integrate notification for low-stock alerts  
- Build a React frontend for the API  
- Implement audit logging and analytics dashboards

---
