const API_URL = '/api';

const productForm = document.getElementById('productForm');
const imageFile = document.getElementById('imageFile');
const imagePreviewContainer = document.getElementById('imagePreviewContainer');
const imagePreview = document.getElementById('imagePreview');

// Check for Product ID in URL (for Edit Mode)
const urlParams = new URLSearchParams(window.location.search);
const productId = urlParams.get('id');
const isEditing = !!productId;

document.addEventListener('DOMContentLoaded', () => {
    if (isEditing) {
        document.getElementById('pageTitle').textContent = 'Edit Product';
        document.getElementById('submitBtn').textContent = 'Update Product';
        fetchProductDetails(productId);
        document.getElementById('imageFile').required = false;
    } else {
        document.getElementById('pageTitle').textContent = 'Add New Product';
        document.getElementById('submitBtn').textContent = 'Add Product';
        document.getElementById('imageFile').required = true;
    }
});

// Image Preview
imageFile.addEventListener('change', function () {
    const file = this.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            imagePreview.src = e.target.result;
            imagePreviewContainer.classList.remove('hidden');
        }
        reader.readAsDataURL(file);
    } else {
        if (!isEditing) {
            imagePreviewContainer.classList.add('hidden');
            imagePreview.src = '';
        }
    }
});

async function fetchProductDetails(id) {
    try {
        const response = await fetch(`${API_URL}/product/${id}`);
        if (!response.ok) throw new Error('Failed to fetch product details');
        const product = await response.json();

        // Populate form
        document.getElementById('name').value = product.name;
        document.getElementById('brand').value = product.brand;
        document.getElementById('category').value = product.category;
        document.getElementById('description').value = product.description;
        document.getElementById('price').value = product.price;
        document.getElementById('stockQuantity').value = product.stockQuantity;
        document.getElementById('productAvailable').checked = product.productAvailable;
        document.getElementById('releaseDate').value = product.releaseDate;

        // Show existing image if available (optional enhancement)
        if (product.id) {
            imagePreview.src = `${API_URL}/product/${product.id}/image`;
            imagePreviewContainer.classList.remove('hidden');
        }

    } catch (error) {
        console.error('Error:', error);
        toast.error('Failed to load product details');
    }
}

productForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = new FormData();

    const productData = {
        name: document.getElementById('name').value,
        brand: document.getElementById('brand').value,
        category: document.getElementById('category').value,
        description: document.getElementById('description').value,
        price: parseFloat(document.getElementById('price').value),
        stockQuantity: parseInt(document.getElementById('stockQuantity').value),
        productAvailable: document.getElementById('productAvailable').checked,
        releaseDate: document.getElementById('releaseDate').value
    };

    const url = isEditing ? `${API_URL}/product/${productId}` : `${API_URL}/product`;
    const method = isEditing ? 'PUT' : 'POST';

    // Handle FormData
    formData.append('product', new Blob([JSON.stringify(productData)], { type: 'application/json' }));

    if (imageFile.files[0]) {
        formData.append('imageFile', imageFile.files[0]);
    } else if (isEditing && !imageFile.files[0]) {
        // Fix for backend requiring part: send empty if not updating
        // Actually, I made it optional in backend, so I don't need to send anything!
        // Leaving it as is.
    }

    try {
        const response = await fetch(url, {
            method: method,
            body: formData
        });

        if (response.ok) {
            toast.success(isEditing ? 'Product updated successfully!' : 'Product added successfully!');
            setTimeout(() => {
                window.location.href = 'admin.html';
            }, 1500);
        } else {
            const errorText = await response.text();
            toast.error(`Failed to save product: ${errorText}`);
        }
    } catch (error) {
        console.error('Error:', error);
        toast.error('Error saving product. Please try again.');
    }
});
