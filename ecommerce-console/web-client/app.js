// =============================
// SAFE API BASE URL HANDLER
// =============================
function getApiBaseUrl() {
    const input = document.getElementById("apiBaseUrl");

    if (!input) {
        console.error("apiBaseUrl input NOT found");
        return "http://localhost:8080"; // fallback
    }

    let url = input.value.trim();

    if (!url.startsWith("http")) {
        url = "http://" + url;
    }

    return url;
}

// =============================
// TEST CONNECTION
// =============================
async function testConnection() {
    const statusEl = document.getElementById('connectionStatus');

    try {
        const baseUrl = getApiBaseUrl();

        const response = await fetch(`${baseUrl}/api/test`);

        if (response.ok) {
            statusEl.textContent = '✓ Connected';
            statusEl.className = 'success';
        } else {
            statusEl.textContent = `✗ Error: ${response.status}`;
            statusEl.className = 'error';
        }
    } catch (error) {
        statusEl.textContent = '✗ Connection Failed';
        statusEl.className = 'error';
        console.error(error);
    }
}

// =============================
// GENERIC API REQUEST
// =============================
async function apiRequest(endpoint, method = 'GET', payload = null) {
    const baseUrl = getApiBaseUrl();

    const options = {
        method,
        headers: {
            'Accept': 'application/json'
        }
    };

    if (payload) {
        options.headers['Content-Type'] = 'application/json';
        options.body = JSON.stringify(payload);
    }

    const response = await fetch(`${baseUrl}${endpoint}`, options);

    if (!response.ok) {
        throw new Error(`HTTP ${response.status}`);
    }

    return await response.json();
}

// =============================
// PRODUCTS
// =============================
async function getAllProducts() {
    try {
        const data = await apiRequest('/api/products');
        console.log(data);
        alert("Products loaded!");
    } catch (err) {
        console.error(err);
        alert("Failed to fetch products");
    }
}

async function getProductById() {
    const id = document.getElementById('productId').value;

    if (!id) {
        alert("Enter Product ID");
        return;
    }

    try {
        const data = await apiRequest(`/api/products/${id}`);
        console.log(data);
        alert("Product fetched!");
    } catch (err) {
        console.error(err);
        alert("Failed to fetch product");
    }
}

// =============================
// CATEGORIES
// =============================
async function getAllCategories() {
    try {
        const data = await apiRequest('/api/categories');
        console.log(data);
        alert("Categories loaded!");
    } catch (err) {
        console.error(err);
        alert("Failed to fetch categories");
    }
}

// =============================
// ORDERS
// =============================
async function getAllOrders() {
    try {
        const data = await apiRequest('/api/orders');
        console.log(data);
        alert("Orders loaded!");
    } catch (err) {
        console.error(err);
        alert("Failed to fetch orders");
    }
}

// =============================
// USERS
// =============================
async function getAllUsers() {
    try {
        const data = await apiRequest('/api/users');
        console.log(data);
        alert("Users loaded!");
    } catch (err) {
        console.error(err);
        alert("Failed to fetch users");
    }
}

// =============================
// TAB SWITCHING (FIXED)
// =============================
function openTab(event, tabName) {
    const tabs = document.querySelectorAll('.tab-content');
    tabs.forEach(tab => tab.classList.remove('active'));

    const buttons = document.querySelectorAll('.tab-btn');
    buttons.forEach(btn => btn.classList.remove('active'));

    document.getElementById(tabName).classList.add('active');
    event.currentTarget.classList.add('active');
}

// =============================
// INIT
// =============================
window.addEventListener('DOMContentLoaded', () => {
    console.log("Frontend loaded");

    // Auto test connection AFTER DOM loads
    testConnection();
});
