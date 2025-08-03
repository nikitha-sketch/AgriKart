# 🌾 AgriKart - Smart Agro E-Commerce Platform

**AgriKart** is a multilingual, farmer-friendly web platform designed to simplify access to seeds, fertilizers, pesticides, and farming tools. It supports offline ordering, role-based login, and even provides a smart crop input advisor powered by AI/ML.

![AgriKart Home](src/main/resources/static/assets/images/seeds/mango.jpeg)

---

## 🛠️ Tech Stack

### Frontend
- HTML, Tailwind CSS, JavaScript

### Backend
- Java, Spring Boot, MySQL, REST API

### Machine Learning (Advisor)
- Python (Flask), scikit-learn

---

## ✨ Features

- 🛒 **E-Commerce Portal** for farming essentials (HTML + Tailwind CSS frontend)
- 🌐 **Multi-language Support** (English, Telugu, Hindi, Tamil, Malayalam)
- 👤 **Role-based Authentication** (Admin & Farmer)
- 🛒 Add to Cart & Checkout System
- 📦 Order History and Order Status Tracking
- 🧠 **Smart Crop Input Advisor** using AI/ML
- ☎️ **Offline Ordering** via Call or SMS
- 📊 Admin Dashboard for viewing users, orders, and adding products

---

## 🖼️ Screenshots

### Homepage
![Homepage](src/main/resources/static/assets/images/seeds/mango.jpeg)

### Help Page with Multilingual Support
![Help Page](src/main/resources/static/assets/images/seeds/papaya.jpeg)

---

## 🚀 How to Run the Project

### 1. Backend (Spring Boot)
```bash
cd agrikartKisan
mvn spring-boot:run
```

Runs on: `http://localhost:8080`

### 2. ML Module (Python Flask)
```bash
cd agrikart_ML
pip install -r requirements.txt
python app.py
```

Runs on: `http://localhost:5000`

---

## 📁 Project Structure

```
agrikartKisan/
├── src/
├── agrikart_ML/
│   ├── app.py
│   └── model.pkl
├── pom.xml
└── README.md
```

---

## 👩‍💻 Author

**Nikitha Reddy**  
🎓 Java Full Stack Developer | Exploring AI/ML with Python  
🔗 GitHub: [nikitha-sketch](https://github.com/nikitha-sketch)

📧 Email: nikithareddy1103@gmail.com

---

## 📜 License

This project is open-source and free to use.
