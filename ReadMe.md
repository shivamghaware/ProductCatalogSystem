# 🌿 Organic E-Commerce Product Catalog System

A full-stack e-commerce application built with Spring Boot and vanilla JavaScript for managing organic products. This system provides a complete solution for product catalog management with separate user and admin interfaces, secure authentication, and RESTful API endpoints.

## 📋 Table of Contents

- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Architecture](#-architecture)
- [Prerequisites](#-prerequisites)
- [Installation & Setup](#-installation--setup)
- [Running the Application](#-running-the-application)
- [API Documentation](#-api-documentation)
- [Security Configuration](#-security-configuration)
- [Database Schema](#-database-schema)
- [Project Structure](#-project-structure)
- [Contributing](#-contributing)

## ✨ Features

### User Features
- **Product Catalog**: Browse all available organic products with images
- **Product Search**: Search products by name, brand, or category
- **Product Details**: View detailed information including price, description, and availability
- **Responsive Design**: Mobile-friendly interface for all devices
- **Stock Visibility**: Real-time stock quantity display (hidden when out of stock)

### Admin Features
- **Secure Admin Panel**: Protected admin interface with authentication
- **Product Management**: Full CRUD operations (Create, Read, Update, Delete)
- **Image Upload**: Support for product image uploads with multipart form data
- **Inventory Control**: Manage stock quantities and product availability
- **Product Search**: Admin-specific search functionality
- **Form Validation**: Client and server-side validation

### Technical Features
- **RESTful API**: Clean, well-structured REST endpoints
- **Image Storage**: Binary image storage in PostgreSQL database
- **CORS Support**: Cross-Origin Resource Sharing enabled
- **Security**: Spring Security with form-based authentication
- **Database Integration**: PostgreSQL with JPA/Hibernate
- **Hot Reload**: Spring Boot DevTools for development
- **Lombok Integration**: Reduced boilerplate code

## 🛠 Technology Stack

### Backend
- **Java 21**: Latest LTS version
- **Spring Boot 3.5.3**: Application framework
  - Spring Web: RESTful web services
  - Spring Data JPA: Database persistence
  - Spring Security: Authentication & authorization
  - Spring Boot DevTools: Development utilities
- **PostgreSQL**: Relational database
- **Lombok**: Code generation library
- **Maven**: Build and dependency management

### Frontend
- **HTML5**: Semantic markup
- **CSS3**: Modern styling with custom properties
- **Vanilla JavaScript**: No framework dependencies
- **Fetch API**: Asynchronous HTTP requests

## 🏗 Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                        Frontend Layer                        │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐   │
│  │  Index   │  │  Login   │  │  Admin   │  │ Add/Edit │   │
│  │  Page    │  │  Page    │  │  Panel   │  │ Product  │   │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘   │
└─────────────────────────────────────────────────────────────┘
                            ↕ HTTP/REST
┌─────────────────────────────────────────────────────────────┐
│                     Spring Boot Backend                      │
│  ┌──────────────────────────────────────────────────────┐  │
│  │              Security Layer (Spring Security)         │  │
│  │  - Form Login  - HTTP Basic  - CSRF Protection       │  │
│  └──────────────────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────────────────┐  │
│  │         Controller Layer (REST Controllers)           │  │
│  │              ProductController (@RestController)      │  │
│  └──────────────────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────────────────┐  │
│  │            Service Layer (Business Logic)             │  │
│  │                  ProductService                       │  │
│  └──────────────────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────────────────┐  │
│  │         Repository Layer (Data Access)                │  │
│  │          ProductRepo (JPA Repository)                 │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                            ↕ JDBC
┌─────────────────────────────────────────────────────────────┐
│                    PostgreSQL Database                       │
│                      Product Table                           │
└─────────────────────────────────────────────────────────────┘
```

## 📦 Prerequisites

Before running this application, ensure you have the following installed:

- **Java Development Kit (JDK) 21** or later
  - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
  - Verify installation: `java -version`

- **PostgreSQL 12+**
  - Download from [PostgreSQL Official Site](https://www.postgresql.org/download/)
  - Create a database named `productdb`
  - Default credentials: username `adminuser`, password `mypassword`

- **Maven** (Optional - project includes Maven Wrapper)
  - If not using wrapper, download from [Apache Maven](https://maven.apache.org/download.cgi)

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/shivamghaware/ProductCatalogSystem.git
cd ProductCatalogSystem/organic
```

### 2. Configure Database

Create a PostgreSQL database and update the configuration if needed:

```bash
# Connect to PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE productdb;

# Create user (if not exists)
CREATE USER adminuser WITH PASSWORD 'mypassword';

# Grant privileges
GRANT ALL PRIVILEGES ON DATABASE productdb TO adminuser;
```

### 3. Update Application Properties (Optional)

If your database credentials differ, edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/productdb
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### 4. Build the Project

Using Maven Wrapper (recommended):

```bash
# Windows
.\mvnw.cmd clean package

# Linux/Mac
./mvnw clean package
```

Or using installed Maven:

```bash
mvn clean package
```

This will create two JAR files in the `target` directory:
- `v3.0.jar` - Executable Spring Boot JAR (use this one)
- `v3.0.jar.original` - Original thin JAR (backup)

## 🎯 Running the Application

### Development Mode (with hot reload)

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

### Production Mode (using JAR)

```bash
java -jar target/v3.0.jar
```

The application will start on **http://localhost:8080**

### Accessing the Application

- **User Interface**: http://localhost:8080/index.html
- **Admin Login**: http://localhost:8080/login.html
  - Username: `user`
  - Password: `password`
- **Admin Panel**: http://localhost:8080/admin.html (requires authentication)

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Endpoints

#### 1. Get All Products
```http
GET /api/products
```
**Description**: Retrieves all products in the catalog

**Response**: `200 OK`
```json
[
  {
    "id": 1,
    "name": "Organic Honey",
    "description": "Pure raw organic honey",
    "brand": "NatureSweet",
    "price": 12.99,
    "category": "Organic",
    "releaseDate": "2024-01-15",
    "productAvailable": true,
    "stockQuantity": 50,
    "imageName": "honey.jpg",
    "imageType": "image/jpeg"
  }
]
```

#### 2. Get Product by ID
```http
GET /api/product/{id}
```
**Description**: Fetches a specific product by ID

**Parameters**:
- `id` (path) - Product ID (integer)

**Response**: `200 OK` or `404 Not Found`

#### 3. Search Products
```http
GET /api/products/search?keyword={keyword}
```
**Description**: Search products by name, brand, or category

**Parameters**:
- `keyword` (query) - Search term (string)

**Response**: `200 OK`
```json
[
  {
    "id": 1,
    "name": "Organic Honey",
    ...
  }
]
```

#### 4. Get Product Image
```http
GET /api/product/{prodId}/image
```
**Description**: Retrieves the product image as binary data

**Parameters**:
- `prodId` (path) - Product ID (integer)

**Response**: `200 OK` with image binary data

**Content-Type**: Varies (image/jpeg, image/png, etc.)

#### 5. Add New Product (Protected)
```http
POST /api/product
```
**Description**: Creates a new product with optional image

**Authentication**: Required

**Content-Type**: `multipart/form-data`

**Request Body**:
- `product` (JSON):
  ```json
  {
    "name": "Organic Almonds",
    "description": "Premium quality organic almonds",
    "brand": "NutriNature",
    "price": 15.99,
    "category": "Nuts",
    "releaseDate": "2024-12-09",
    "productAvailable": true,
    "stockQuantity": 100
  }
  ```
- `imageFile` (file, optional): Product image

**Response**: `201 Created` or `500 Internal Server Error`

#### 6. Update Product (Protected)
```http
PUT /api/product/{prodId}
```
**Description**: Updates an existing product

**Authentication**: Required

**Parameters**:
- `prodId` (path) - Product ID (integer)

**Content-Type**: `multipart/form-data`

**Request Body**:
- `product` (JSON): Updated product data
- `imageFile` (file, optional): New product image

**Response**: `200 OK` or `400 Bad Request`

#### 7. Delete Product (Protected)
```http
DELETE /api/product/{id}
```
**Description**: Removes a product from the catalog

**Authentication**: Required

**Parameters**:
- `id` (path) - Product ID (integer)

**Response**: `200 OK` or `404 Not Found`

## 🔐 Security Configuration

### Authentication
The application uses **Spring Security** with the following configuration:

- **Public Access**:
  - All GET requests to `/api/**`
  - Product listing and search endpoints
  - Static resources (HTML, CSS, JS)
  - Product images

- **Protected Resources** (Authentication Required):
  - Admin panel (`/admin.html`)
  - Add/Edit product pages
  - POST, PUT, DELETE operations on `/api/product/**`

### Default Credentials
```
Username: user
Password: password
Role: USER
```

### Login Configuration
- **Login Page**: `/login.html`
- **Login Processing URL**: `/login`
- **Success Redirect**: `/admin.html`
- **Logout URL**: `/logout`
- **Logout Success**: `/index.html`

### Security Features
- CSRF protection disabled for API simplicity
- HTTP Basic authentication enabled
- Form-based login with custom page
- In-memory user details service (for demo purposes)

**⚠️ Production Note**: Replace in-memory authentication with a proper user database and implement password encryption for production use.

## 🗄 Database Schema

### Product Table

| Column           | Type          | Constraints           | Description                      |
|------------------|---------------|-----------------------|----------------------------------|
| id               | INTEGER       | PRIMARY KEY, AUTO_INC | Unique product identifier        |
| name             | VARCHAR(255)  | NOT NULL              | Product name                     |
| description      | TEXT          |                       | Detailed product description     |
| brand            | VARCHAR(255)  |                       | Product brand/manufacturer       |
| price            | DECIMAL(10,2) |                       | Product price                    |
| category         | VARCHAR(100)  |                       | Product category                 |
| release_date     | DATE          |                       | Product release date             |
| product_available| BOOLEAN       |                       | Availability status              |
| stock_quantity   | INTEGER       |                       | Current stock count              |
| image_name       | VARCHAR(255)  |                       | Uploaded image filename          |
| image_type       | VARCHAR(50)   |                       | Image MIME type                  |
| image_data       | BYTEA         |                       | Binary image data (LOB)          |

### JPA Configuration
- **Hibernate DDL**: `update` (auto-creates/updates schema)
- **Show SQL**: Enabled for development
- **Database Platform**: PostgreSQL

## 📁 Project Structure

```
ProductCatalogSystem/
├── organic/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/ecom/organic/
│   │   │   │   ├── config/
│   │   │   │   │   └── SecurityConfig.java          # Spring Security configuration
│   │   │   │   ├── controller/
│   │   │   │   │   └── ProductController.java       # REST API endpoints
│   │   │   │   ├── model/
│   │   │   │   │   └── Product.java                 # Product entity
│   │   │   │   ├── repo/
│   │   │   │   │   └── ProductRepo.java             # JPA repository
│   │   │   │   ├── service/
│   │   │   │   │   └── ProductService.java          # Business logic
│   │   │   │   └── OrganicApplication.java          # Main application class
│   │   │   └── resources/
│   │   │       ├── static/
│   │   │       │   ├── index.html                   # User homepage
│   │   │       │   ├── login.html                   # Login page
│   │   │       │   ├── admin.html                   # Admin dashboard
│   │   │       │   ├── add-product.html             # Add product form
│   │   │       │   ├── edit-product.html            # Edit product form
│   │   │       │   ├── style.css                    # Styles
│   │   │       │   ├── script.js                    # User page scripts
│   │   │       │   ├── admin.js                     # Admin page scripts
│   │   │       │   └── product-form.js              # Product form scripts
│   │   │       ├── application.properties           # Application configuration
│   │   │       └── data.sql                         # Initial data (optional)
│   │   └── test/                                    # Test files
│   ├── target/                                      # Build output
│   ├── pom.xml                                      # Maven configuration
│   ├── mvnw                                         # Maven wrapper (Unix)
│   └── mvnw.cmd                                     # Maven wrapper (Windows)
└── README.md                                        # This file
```

## 🔄 Recent Changes & Updates

### Version 3.0
- ✅ Migrated to Spring Boot 3.5.3
- ✅ Upgraded to Java 21
- ✅ Implemented Spring Security with custom login
- ✅ Added admin panel with authentication
- ✅ Separated user and admin interfaces
- ✅ Implemented multipart file upload for images
- ✅ Added product search functionality
- ✅ PostgreSQL database integration
- ✅ Conditional stock quantity display
- ✅ Responsive UI design
- ✅ CORS configuration for API access
- ✅ Custom build configuration (v3.0.jar)

## 🐛 Known Issues & Limitations

- In-memory authentication (not suitable for production)
- Images stored in database (consider file system or cloud storage for large scale)
- No pagination for product listing
- No user registration functionality
- Single admin role (no role-based access control)

## 🚧 Future Enhancements

- [ ] Implement user registration and management
- [ ] Add role-based access control (ADMIN, USER, GUEST)
- [ ] Implement pagination and sorting
- [ ] Add shopping cart functionality
- [ ] Integrate payment gateway
- [ ] Add product reviews and ratings
- [ ] Implement email notifications
- [ ] Add product categories management
- [ ] Implement cloud storage for images (AWS S3, etc.)
- [ ] Add comprehensive unit and integration tests
- [ ] Implement caching (Redis)
- [ ] Add API rate limiting
- [ ] Create Docker containerization

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is open source and available for educational purposes.

## 👤 Author

**Shivam Ghaware**
- GitHub: [@shivamghaware](https://github.com/shivamghaware)

## 📞 Support

For issues, questions, or suggestions, please open an issue on GitHub.

---

**Built with ❤️ using Spring Boot and Java**
