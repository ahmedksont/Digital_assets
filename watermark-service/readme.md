# 🐍 PDF Watermark Service (Flask)

A lightweight Python microservice responsible for **dynamic PDF watermarking**.  
This service is called by the Spring Boot backend during secure asset delivery.

---

## 🎯 Purpose

- Apply **user-specific watermark** (email / userId) to PDF files
- Prevent piracy and unauthorized redistribution
- Keep watermarking logic isolated from the main backend
- Use **100% open-source** libraries (no paid APIs)

---

## 🧱 Tech Stack

| Component | Technology |
|--------|------------|
| Language | Python 3.10+ |
| Framework | Flask |
| PDF Engine | Apache PDFBox |
| Transport | REST (JSON + Base64) |
| Deployment | Local / Free Tier |

---

---

## 🔧 Requirements

- Python **3.10 or higher**
- pip
- Virtualenv (recommended)

---

## ⚙️ Installation

### 1️⃣ Clone the repository
2️⃣ Create virtual environment

Windows
python -m venv venv
venv\Scripts\activate

3️⃣ Install dependencies
pip install -r requirements.txt

📦 requirements.txt
flask==3.0.0
PyPDF2==3.0.1

▶️ Run the Service
python app.py

The service will start on:

http://localhost:5001


📡 API Reference
➤ POST /watermark

Applies a watermark to a PDF.

Request URL
POST /watermark
Headers
Content-Type: application/json
Body
{
  "pdf": "<BASE64_ENCODED_PDF>",
  "watermark": "user@email.com"
}
Response
200 OK
{
  "pdf": "<BASE64_WATERMARKED_PDF>"
}

Error Responses
Code	Meaning
400	Invalid payload
500	Watermarking failed



