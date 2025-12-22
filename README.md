# Digital_assets

# 📚 Digital Assets Library & Licensing Hub
*(Protection & Valeur)*

A secure, API-first backend system to store, license, watermark, and deliver digital assets (PDFs, eBooks, templates, training documents) with zero-cost infrastructure (Free Tier & Open Source).

---

## 🎯 Project Objective

The goal of this project is to provide a **professional and secure digital delivery system** for authors and creators, ensuring:

- Protection of intellectual property
- Controlled access through licenses
- Dynamic watermarking at download time
- Centralized asset management
- Revenue optimization via contextual upsells

This backend is designed to integrate with an external **Digital Modular Hub** via a strict API contract.

---

## 🚀 Key Features

- 📦 Digital Assets CRUD (PDF, eBooks)
- 🔐 License-based access control
- 🧾 Dynamic PDF watermarking (user-specific)
- 📥 Secure delivery endpoint (API contract compliant)
- 📊 Download logging (audit & security)
- 💰 Upsell metadata returned on delivery
- 🔄 Asset versioning & updates
- ☁️ Supabase PostgreSQL & Storage
- 🐍 Flask microservice for watermarking

---

## 🏗️ Architecture Overview

┌────────────────────────┐
│ Digital Hub (Client) │
└──────────┬─────────────┘
│ API Contract
▼
┌────────────────────────┐
│ Spring Boot Backend │
│ (Java 17) │
├────────────────────────┤
│ - Assets Management │
│ - Licenses │
│ - Delivery API │
│ - Upsells │
│ - Security │
└───────┬───────┬────────┘
│ │
▼ ▼
┌────────────┐ ┌─────────────────┐
│ PostgreSQL │ │ Supabase Storage │
│ (Supabase) │ │ (PDF Files) │
└────────────┘ └─────────────────┘
│
▼
┌────────────────────────┐
│ Flask Watermark Service│
│ (PDFBox) │
└────────────────────────┘
---

## 🧱 Tech Stack

| Layer | Technology |
|-----|-----------|
Backend API | Spring Boot 3 |
Language | Java 17 |
Database | PostgreSQL (Supabase) |
Storage | Supabase Storage |
Auth | Supabase JWT |
Watermarking | Flask + Apache PDFBox |
Docs | Swagger / OpenAPI |
Testing | Postman |
Build Tool | Maven |

---

## 🔐 Authentication & Security

All protected endpoints require a **Supabase User JWT**.

Authorization: Bearer <SUPABASE_USER_JWT>
⚠️ **Do not use the Supabase service-role key in clients.**  
Only user-issued JWTs are accepted.

---

## 📦 Main API Endpoints

### Assets
POST /api/assets → Create asset
GET /api/assets → List active assets
GET /api/assets/{id} → Get asset by ID
DELETE /api/assets/{id} → Deactivate asset
---

### Licenses
POST /api/licenses → Grant license to user
License rules supported:
- Download limit
- Expiration date
- Per-user per-asset uniqueness

---

### Upsells
POST /api/upsells
GET /api/upsells/asset/{assetId}
Upsells are returned **automatically** during delivery.

---

### 🚚 Delivery (Core Endpoint)

GET /api/deliver?userId={userId}&assetId={assetId}
✔ Validates license  
✔ Downloads original PDF  
✔ Applies dynamic watermark  
✔ Logs download  
✔ Returns file  

**Response Headers**
X-Upsell-Id
X-Upsell-Url
---

## 🐍 Flask Watermark Service

The Flask service is a lightweight microservice dedicated to **PDF watermarking**.

### Endpoint
POST /watermark
### Payload
```json
{
  "pdf": "<base64-pdf>",
  "watermark": "user@email.com"
}
Response
json
Copier le code
{
  "pdf": "<base64-watermarked-pdf>"
}
🧪 Testing with Postman
Import the provided Postman collection

Configure environment variables:

baseUrl

jwt

assetId

Test flow:

Create Asset

Create License

Create Upsell

Deliver Asset

📄 Swagger Documentation
Swagger UI is available at:

http://localhost:8080/swagger-ui.html
OpenAPI spec:
http://localhost:8080/v3/api-docs
⚙️ Configuration
application.properties (excerpt)
properties
Copier le code
spring.datasource.url=jdbc:postgresql://<supabase-host>:5432/postgres?sslmode=require
spring.datasource.username=postgres.<project-ref>
spring.datasource.password=********
spring.jpa.hibernate.ddl-auto=update
Supabase & Flask clients are configured via Spring WebClient.

📈 Security & Compliance
Strict API contract compliance

License validation before delivery

Download attempts tracked

IP & User-Agent logging

Anti-piracy watermarking

No paid APIs used

📚 Educational Objectives
API-first development

Contract-based integration

Secure digital content delivery

Serverless & free-tier architecture

Microservice communication (Spring ↔ Flask)

🧪 Bonus Features (Optional)
⏱ Download rate limiting

⌛ Time-bound licenses

📧 Webhook notification on updates

🐳 Docker Compose (Spring + Flask)

📜 License
MIT License
Educational & MVP usage only.

👨‍💻 Author
Backend developed with Spring Boot & Flask
for the Digital Assets Library & Licensing Hub Project
