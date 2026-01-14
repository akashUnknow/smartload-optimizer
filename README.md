# SmartLoad Optimization API


---

## ✨ Key Features

- Maximizes total payout (handled strictly in **integer cents**, no floating-point arithmetic)
- Enforces truck **weight and volume** capacity constraints
- Ensures **route compatibility** (same origin → destination)
- Stateless, in-memory computation (❌ no database)
- High performance using **bitmask + recursive backtracking with pruning**
- Clean, testable, production-ready architecture
- Dockerized with multi-stage build

---

## 🛠 Tech Stack

- **Java 17**
- **Spring Boot 3.x**
- Maven
- Docker & Docker Compose

---

## 🚀 How to Run

### Prerequisites

- Docker
- Docker Compose

### Start the Service

```bash
docker compose up --build
```

The service will be available at:

```
http://localhost:8080
```

---

## 📡 API Details

### Optimize Truck Load

**Endpoint**
```
POST /api/v1/load-optimizer/optimize
```

---

### Request Example

```json
{
  "truck": {
    "id": "truck-123",
    "max_weight_lbs": 44000,
    "max_volume_cuft": 3000
  },
  "orders": [
    {
      "id": "ord-001",
      "payout_cents": 250000,
      "weight_lbs": 18000,
      "volume_cuft": 1200,
      "origin": "Los Angeles, CA",
      "destination": "Dallas, TX",
      "pickup_date": "2025-12-05",
      "delivery_date": "2025-12-09",
      "is_hazmat": false
    }
  ]
}
```

---

### Success Response (200 OK)

```json
{
  "truck_id": "truck-123",
  "selected_order_ids": ["ord-001", "ord-002"],
  "total_payout_cents": 430000,
  "total_weight_lbs": 30000,
  "total_volume_cuft": 2100,
  "utilization_weight_percent": 68.18,
  "utilization_volume_percent": 70.0
}
```

---

## 🐳 Docker Notes

- Multi-stage Dockerfile
- Lightweight JRE runtime image
- Service listens on **port 8080**

---

## 📂 Project Structure

```
smartload-optimizer/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── README.md
└── src/main/java/com/example/smartload/
    ├── SmartLoadApplication.java
    ├── controller/
    ├── service/
    └── model/
```
---

