const API_URL = '/api';

// DOM Elements
const productGrid = document.getElementById('productGrid');
const searchInput = document.getElementById('searchInput');
const searchBtn = document.getElementById('searchBtn');
const noResults = document.getElementById('noResults');

// Event Listeners
document.addEventListener('DOMContentLoaded', () => fetchProducts());

searchBtn.addEventListener('click', () => {
    const keyword = searchInput.value.trim();
    fetchProducts(keyword);
});

searchInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        const keyword = searchInput.value.trim();
        fetchProducts(keyword);
    }
});

// Functions

async function fetchProducts(keyword = '') {
    try {
        let url = `${API_URL}/products`;
        if (keyword) {
            url = `${API_URL}/products/search?keyword=${encodeURIComponent(keyword)}`;
        }

        const response = await fetch(url);
        if (!response.ok) throw new Error('Failed to fetch products');

        const products = await response.json();
        renderProducts(products);
    } catch (error) {
        console.error('Error:', error);
        productGrid.innerHTML = '<p class="error">Error loading products. Please try again.</p>';
    }
}

function renderProducts(products) {
    productGrid.innerHTML = '';

    if (products.length === 0) {
        noResults.classList.remove('hidden');
        return;
    }

    noResults.classList.add('hidden');

    products.forEach(product => {
        const card = document.createElement('div');
        card.className = 'product-card';

        // Image URL
        const imageUrl = product.id ? `${API_URL}/product/${product.id}/image` : 'placeholder.jpg';

        card.innerHTML = `
            <img src="${imageUrl}" alt="${product.name}" class="card-image" onerror="this.src='https://via.placeholder.com/300x200?text=No+Image'">
            <div class="card-content">
                <div class="card-header">
                    <span class="product-brand">${product.brand}</span>
                    <span class="product-price">₹${product.price.toFixed(2)}</span>
                </div>
                <h3 class="product-title">${product.name}</h3>
                <p class="product-description">${product.description}</p>
                <div class="card-footer">
                    <span class="stock-status ${product.productAvailable && product.stockQuantity > 0 ? 'in-stock' : 'out-of-stock'}">
                        ${product.productAvailable && product.stockQuantity > 0 ? 'In Stock' : 'Out of Stock'}
                    </span>
                    ${product.productAvailable && product.stockQuantity > 0 ? `<span class="product-quantity">Quantity: ${product.stockQuantity}</span>` : ''}
                    <!-- No Admin Actions here -->
                </div>
            </div>
        `;
        productGrid.appendChild(card);
    });
}
