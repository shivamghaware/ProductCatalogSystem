# Organic E-commerce Project

This is a demo project for a Spring Boot application that provides a RESTful API for managing organic products.

## Technologies Used

* Java
* Spring Boot
* Maven
* SQL

## How to Run the Project

1. **Prerequisites:**
   - Java Development Kit (JDK) 8 or later
   - Apache Maven

2. **Clone the repository:**
   ```bash
   git clone <repository-url>
   ```

3. **Navigate to the project directory:**
   ```bash
   cd organic
   ```

4. **Build the project:**
   ```bash
   ./mvnw clean install
   ```

5. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

The application will start on the default port (usually 8080).

## Data Model

The application centers around the `Product` entity, which represents an item in the organic e-commerce store. Each product has several key attributes:

*   **Core Information:**
    *   `id`: A unique numerical identifier for each product.
    *   `name`: The display name of the product (e.g., "Organic Honey").
    *   `description`: A detailed description of the product, its qualities, and ingredients (e.g., "Pure raw organic honey harvested naturally").
    *   `brand`: The manufacturer or brand of the product (e.g., "NatureSweet").
    *   `price`: The cost of the product.
    *   `category`: The category the product belongs to (e.g., "Organic").

*   **Inventory and Availability:**
    *   `releaseDate`: The date the product was made available, formatted as DD-MM-YY.
    *   `productAvailable`: A boolean value (`true` or `false`) indicating if the product is currently for sale.
    *   `stockQuantity`: The number of units currently in stock.

*   **Product Image:**
    *   `imageName`: The filename of the uploaded product image.
    *   `imageType`: The MIME type of the image (e.g., `image/png`).
    *   `imageData`: The actual image file stored as a binary data.

This model provides a comprehensive representation of a product for the e-commerce platform's catalog and inventory management.

## API Endpoints

### Product Management

*   **`GET /api/products`**:
    *   **Description**: Retrieves a list of all available products.
    *   **Response**: An array of `Product` objects.

*   **`GET /api/product/{id}`**:
    *   **Description**: Fetches a specific product by its unique ID.
    *   **Path Variable**: `{id}` (integer) - The ID of the product to retrieve.
    *   **Response**: A `Product` object or `404 Not Found` if the product does not exist.

*   **`POST /api/product`**:
    *   **Description**: Adds a new product to the inventory, including an image.
    *   **Request Body**: `multipart/form-data` with a `Product` object (JSON) and an `imageFile` (file).
    *   **Response**: The created `Product` object or `500 Internal Server Error` if the creation fails.

*   **`PUT /api/product/{prodId}`**:
    *   **Description**: Updates the details and/or image of an existing product.
    *   **Path Variable**: `{prodId}` (integer) - The ID of the product to update.
    *   **Request Body**: `multipart/form-data` with a `Product` object (JSON) and an `imageFile` (file).
    *   **Response**: A success message or `400 Bad Request` if the update fails.

*   **`DELETE /api/product/{id}`**:
    *   **Description**: Removes a product from the inventory.
    *   **Path Variable**: `{id}` (integer) - The ID of the product to delete.
    *   **Response**: A success message or `404 Not Found` if the product does not exist.

### Image and Search

*   **`GET /api/product/{prodId}/image`**:
    *   **Description**: Retrieves the image associated with a specific product.
    *   **Path Variable**: `{prodId}` (integer) - The ID of the product.
    *   **Response**: The image file as a byte array.

*   **`GET /api/products/search`**:
    *   **Description**: Searches for products based on a keyword.
    *   **Query Parameter**: `keyword` (string) - The search term.
    *   **Response**: An array of `Product` objects that match the search criteria.
