// Global API Base URL
let API_BASE_URL = 'http://localhost:8080';

// Update API URL from input
function updateApiUrl() {
    const urlInput = document.getElementById('apiUrl');
    API_BASE_URL = urlInput.value.trim();
    if (!API_BASE_URL.startsWith('http')) {
        API_BASE_URL = 'http://' + API_BASE_URL;
    }
    console.log('API Base URL updated to:', API_BASE_URL);
}

// Test API Connection
async function testConnection() {
    updateApiUrl();
    const statusEl = document.getElementById('connectionStatus');
    statusEl.textContent = 'Testing...';
    statusEl.className = '';

    try {
        const response = await fetch(`${API_BASE_URL}/api/products`, {
            method: 'GET',
            headers: { 'Accept': 'application/json' }
        });
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
        console.error('Connection test failed:', error);
    }
}

function escapeHtml(value) {
    return String(value)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;');
}

async function apiRequest(url, method = 'GET', payload = null) {
    updateApiUrl();

    const options = {
        method,
        headers: {
            'Accept': 'application/json'
        }
    };

    if (payload !== null) {
        options.headers['Content-Type'] = 'application/json';
        options.body = JSON.stringify(payload);
    }

    const response = await fetch(url, options);
    const contentType = response.headers.get('content-type') || '';
    const rawText = await response.text();

    let data = null;
    if (rawText) {
        if (contentType.includes('application/json')) {
            try {
                data = JSON.parse(rawText);
            } catch (jsonError) {
                data = { message: rawText };
            }
        } else {
            data = { message: rawText };
        }
    }

    if (!response.ok) {
        const errorMessage = data?.message || (typeof data === 'string' ? data : JSON.stringify(data)) || response.statusText;
        throw new Error(`HTTP ${response.status}: ${errorMessage}`);
    }

    return data;
}

function displayApiResult(containerId, title, data) {
    const container = document.getElementById(containerId);
    const formatted = data === null || data === undefined
        ? 'Success'
        : escapeHtml(JSON.stringify(data, null, 2));

    container.innerHTML = `<div class="success-message">
        <h3>${title}</h3>
        <pre class="json-response">${formatted}</pre>
    </div>`;
}

function parseOptionalInteger(value) {
    const trimmed = value.trim();
    return trimmed === '' ? null : parseInt(trimmed, 10);
}

function parseOptionalNumber(value) {
    const trimmed = value.trim();
    return trimmed === '' ? null : Number(trimmed);
}

function parseRequiredInteger(value, fieldName) {
    const parsed = parseOptionalInteger(value);
    if (parsed === null || Number.isNaN(parsed)) {
        throw new Error(`${fieldName} is required`);
    }
    return parsed;
}

function parseRequiredNumber(value, fieldName) {
    const parsed = parseOptionalNumber(value);
    if (parsed === null || Number.isNaN(parsed)) {
        throw new Error(`${fieldName} is required`);
    }
    return parsed;
}

// Tab Switching
function openTab(tabName) {
    const tabs = document.querySelectorAll('.tab-content');
    tabs.forEach(tab => tab.classList.remove('active'));

    const buttons = document.querySelectorAll('.tab-btn');
    buttons.forEach(btn => btn.classList.remove('active'));

    document.getElementById(tabName).classList.add('active');
    event.target.classList.add('active');
}

// Generic API Call Function
async function makeApiCall(url, resultContainerId) {
    const resultContainer = document.getElementById(resultContainerId);
    resultContainer.innerHTML = '<div class="loading">Loading</div>';

    try {
        const data = await apiRequest(url, 'GET');
        return data;
    } catch (error) {
        resultContainer.innerHTML = `<div class="error-message">
            <strong>Error:</strong> ${error.message}<br>
            <small>Make sure the API server is running at ${API_BASE_URL}</small>
        </div>`;
        console.error('API call failed:', error);
        return null;
    }
}

// Display functions
function displayTable(data, columns, containerId) {
    const container = document.getElementById(containerId);

    if (!data || data.length === 0) {
        container.innerHTML = '<div class="error-message">No data found</div>';
        return;
    }

    let html = '<table class="data-table"><thead><tr>';
    columns.forEach(col => {
        html += `<th>${col.label}</th>`;
    });
    html += '</tr></thead><tbody>';

    data.forEach(item => {
        html += '<tr>';
        columns.forEach(col => {
            const value = item[col.key] !== null && item[col.key] !== undefined
                ? item[col.key]
                : '-';
            html += `<td>${value}</td>`;
        });
        html += '</tr>';
    });

    html += '</tbody></table>';
    container.innerHTML = html;
}

function displaySingleItem(data, title, containerId) {
    const container = document.getElementById(containerId);

    if (!data) {
        container.innerHTML = '<div class="error-message">Item not found</div>';
        return;
    }

    let html = `<div class="item-card"><h3>${title}</h3>`;

    for (const [key, value] of Object.entries(data)) {
        const displayValue = value !== null && value !== undefined ? value : '-';
        html += `<p><strong>${formatKey(key)}:</strong> <span>${displayValue}</span></p>`;
    }

    html += '</div>';
    container.innerHTML = html;
}

function formatKey(key) {
    return key
        .replace(/([A-Z])/g, ' $1')
        .replace(/^./, str => str.toUpperCase())
        .trim();
}

// ========== PRODUCT FUNCTIONS ==========

async function getAllProducts() {
    const data = await makeApiCall(`${API_BASE_URL}/api/products`, 'productsResult');
    if (data) {
        const columns = [
            { key: 'productId', label: 'ID' },
            { key: 'name', label: 'Name' },
            { key: 'description', label: 'Description' },
            { key: 'price', label: 'Price' },
            { key: 'stock', label: 'Stock' },
            { key: 'categoryId', label: 'Category ID' }
        ];
        displayTable(data, columns, 'productsResult');
    }
}

async function getProductById() {
    const id = document.getElementById('productId').value;
    if (!id) {
        alert('Please enter a Product ID');
        return;
    }

    const data = await makeApiCall(`${API_BASE_URL}/api/products/${id}`, 'productsResult');
    if (data) {
        displaySingleItem(data, `Product #${id}`, 'productsResult');
    }
}

async function getProductsByCategory() {
    const categoryId = document.getElementById('categoryIdForProducts').value;
    if (!categoryId) {
        alert('Please enter a Category ID');
        return;
    }

    const data = await makeApiCall(
        `${API_BASE_URL}/api/products/category/${categoryId}`,
        'productsResult'
    );
    if (data) {
        const columns = [
            { key: 'productId', label: 'ID' },
            { key: 'name', label: 'Name' },
            { key: 'description', label: 'Description' },
            { key: 'price', label: 'Price' },
            { key: 'stock', label: 'Stock' }
        ];
        displayTable(data, columns, 'productsResult');
    }
}

async function checkProductStock() {
    const id = document.getElementById('productIdForStock').value;
    if (!id) {
        alert('Please enter a Product ID');
        return;
    }

    const data = await makeApiCall(`${API_BASE_URL}/api/products/${id}/stock`, 'productsResult');
    if (data) {
        const container = document.getElementById('productsResult');
        const inStock = data.inStock;
        const statusClass = inStock ? 'success-message' : 'error-message';
        const statusText = inStock ? 'IN STOCK' : 'OUT OF STOCK';
        container.innerHTML = `<div class="${statusClass}">
            <h3>Product #${id} Stock Status</h3>
            <p style="font-size: 1.5em; margin-top: 10px;"><strong>${statusText}</strong></p>
        </div>`;
    }
}

async function createProduct() {
    try {
        const payload = {
            categoryId: parseRequiredInteger(document.getElementById('productCreateCategoryId').value, 'Category ID'),
            name: document.getElementById('productCreateName').value.trim(),
            description: document.getElementById('productCreateDescription').value.trim(),
            price: parseRequiredNumber(document.getElementById('productCreatePrice').value, 'Price'),
            stock: parseRequiredInteger(document.getElementById('productCreateStock').value, 'Stock'),
            sku: document.getElementById('productCreateSku').value.trim(),
            isActive: document.getElementById('productCreateIsActive').value === 'true'
        };

        if (!payload.name || !payload.sku) {
            throw new Error('Product name and SKU are required');
        }

        const data = await apiRequest(`${API_BASE_URL}/api/products`, 'POST', payload);
        displayApiResult('productsResult', 'Product Created', data);
    } catch (error) {
        document.getElementById('productsResult').innerHTML = `<div class="error-message"><strong>Error:</strong> ${error.message}</div>`;
    }
}

async function updateProduct() {
    try {
        const id = parseRequiredInteger(document.getElementById('productUpdateId').value, 'Product ID');
        const payload = {
            categoryId: parseRequiredInteger(document.getElementById('productUpdateCategoryId').value, 'Category ID'),
            name: document.getElementById('productUpdateName').value.trim(),
            description: document.getElementById('productUpdateDescription').value.trim(),
            price: parseRequiredNumber(document.getElementById('productUpdatePrice').value, 'Price'),
            stock: parseRequiredInteger(document.getElementById('productUpdateStock').value, 'Stock'),
            sku: document.getElementById('productUpdateSku').value.trim(),
            isActive: document.getElementById('productUpdateIsActive').value === 'true'
        };

        if (!payload.name || !payload.sku) {
            throw new Error('Product name and SKU are required');
        }

        const data = await apiRequest(`${API_BASE_URL}/api/products/${id}`, 'PUT', payload);
        displayApiResult('productsResult', 'Product Updated', data);
    } catch (error) {
        document.getElementById('productsResult').innerHTML = `<div class="error-message"><strong>Error:</strong> ${error.message}</div>`;
    }
}

async function deleteProduct() {
    try {
        const id = parseRequiredInteger(document.getElementById('productDeleteId').value, 'Product ID');
        const data = await apiRequest(`${API_BASE_URL}/api/products/${id}`, 'DELETE');
        displayApiResult('productsResult', 'Product Deleted', data);
    } catch (error) {
        document.getElementById('productsResult').innerHTML = `<div class="error-message"><strong>Error:</strong> ${error.message}</div>`;
    }
}

// ========== ORDER FUNCTIONS ==========

async function getAllOrders() {
    const data = await makeApiCall(`${API_BASE_URL}/api/orders`, 'ordersResult');
    if (data) {
        const columns = [
            { key: 'orderId', label: 'Order ID' },
            { key: 'userId', label: 'User ID' },
            { key: 'orderDate', label: 'Order Date' },
            { key: 'status', label: 'Status' },
            { key: 'totalAmount', label: 'Total Amount' }
        ];
        displayTable(data, columns, 'ordersResult');
    }
}

async function getOrderById() {
    const id = document.getElementById('orderId').value;
    if (!id) {
        alert('Please enter an Order ID');
        return;
    }

    const data = await makeApiCall(`${API_BASE_URL}/api/orders/${id}`, 'ordersResult');
    if (data) {
        displaySingleItem(data, `Order #${id}`, 'ordersResult');
    }
}

async function getOrdersByUserId() {
    const userId = document.getElementById('userIdForOrders').value;
    if (!userId) {
        alert('Please enter a User ID');
        return;
    }

    const data = await makeApiCall(
        `${API_BASE_URL}/api/orders/user/${userId}`,
        'ordersResult'
    );
    if (data) {
        const columns = [
            { key: 'orderId', label: 'Order ID' },
            { key: 'orderDate', label: 'Order Date' },
            { key: 'status', label: 'Status' },
            { key: 'totalAmount', label: 'Total Amount' }
        ];
        displayTable(data, columns, 'ordersResult');
    }
}

async function getUserOrderCount() {
    const userId = document.getElementById('userIdForCount').value;
    if (!userId) {
        alert('Please enter a User ID');
        return;
    }

    const data = await makeApiCall(
        `${API_BASE_URL}/api/orders/user/${userId}/count`,
        'ordersResult'
    );
    if (data) {
        const container = document.getElementById('ordersResult');
        container.innerHTML = `<div class="success-message">
            <h3>User #${userId} Order Count</h3>
            <p style="font-size: 2em; margin-top: 10px;"><strong>${data.count}</strong> orders</p>
        </div>`;
    }
}

async function createOrder() {
    try {
        const payload = {
            userId: parseRequiredInteger(document.getElementById('orderCreateUserId').value, 'User ID'),
            orderNumber: document.getElementById('orderCreateOrderNumber').value.trim(),
            status: document.getElementById('orderCreateStatus').value.trim(),
            totalAmount: parseRequiredNumber(document.getElementById('orderCreateTotalAmount').value, 'Total amount'),
            shippingAddressId: parseOptionalInteger(document.getElementById('orderCreateShippingAddressId').value),
            billingAddressId: parseOptionalInteger(document.getElementById('orderCreateBillingAddressId').value),
            notes: document.getElementById('orderCreateNotes').value.trim()
        };

        if (!payload.orderNumber || !payload.status) {
            throw new Error('Order number and status are required');
        }

        const data = await apiRequest(`${API_BASE_URL}/api/orders`, 'POST', payload);
        displayApiResult('ordersResult', 'Order Created', data);
    } catch (error) {
        document.getElementById('ordersResult').innerHTML = `<div class="error-message"><strong>Error:</strong> ${error.message}</div>`;
    }
}

async function updateOrder() {
    try {
        const id = parseRequiredInteger(document.getElementById('orderUpdateId').value, 'Order ID');
        const payload = {
            userId: parseRequiredInteger(document.getElementById('orderUpdateUserId').value, 'User ID'),
            orderNumber: document.getElementById('orderUpdateOrderNumber').value.trim(),
            status: document.getElementById('orderUpdateStatus').value.trim(),
            totalAmount: parseRequiredNumber(document.getElementById('orderUpdateTotalAmount').value, 'Total amount'),
            shippingAddressId: parseOptionalInteger(document.getElementById('orderUpdateShippingAddressId').value),
            billingAddressId: parseOptionalInteger(document.getElementById('orderUpdateBillingAddressId').value),
            notes: document.getElementById('orderUpdateNotes').value.trim()
        };

        if (!payload.orderNumber || !payload.status) {
            throw new Error('Order number and status are required');
        }

        const data = await apiRequest(`${API_BASE_URL}/api/orders/${id}`, 'PUT', payload);
        displayApiResult('ordersResult', 'Order Updated', data);
    } catch (error) {
        document.getElementById('ordersResult').innerHTML = `<div class="error-message"><strong>Error:</strong> ${error.message}</div>`;
    }
}

async function deleteOrder() {
    try {
        const id = parseRequiredInteger(document.getElementById('orderDeleteId').value, 'Order ID');
        const data = await apiRequest(`${API_BASE_URL}/api/orders/${id}`, 'DELETE');
        displayApiResult('ordersResult', 'Order Deleted', data);
    } catch (error) {
        document.getElementById('ordersResult').innerHTML = `<div class="error-message"><strong>Error:</strong> ${error.message}</div>`;
    }
}

// ========== CATEGORY FUNCTIONS ==========

async function getAllCategories() {
    const data = await makeApiCall(`${API_BASE_URL}/api/categories`, 'categoriesResult');
    if (data) {
        const columns = [
            { key: 'categoryId', label: 'ID' },
            { key: 'name', label: 'Name' },
            { key: 'description', label: 'Description' }
        ];
        displayTable(data, columns, 'categoriesResult');
    }
}

async function getCategoryById() {
    const id = document.getElementById('categoryId').value;
    if (!id) {
        alert('Please enter a Category ID');
        return;
    }

    const data = await makeApiCall(`${API_BASE_URL}/api/categories/${id}`, 'categoriesResult');
    if (data) {
        displaySingleItem(data, `Category #${id}`, 'categoriesResult');
    }
}

async function createCategory() {
    try {
        const payload = {
            name: document.getElementById('categoryCreateName').value.trim(),
            description: document.getElementById('categoryCreateDescription').value.trim()
        };

        if (!payload.name) {
            throw new Error('Category name is required');
        }

        const data = await apiRequest(`${API_BASE_URL}/api/categories`, 'POST', payload);
        displayApiResult('categoriesResult', 'Category Created', data);
    } catch (error) {
        document.getElementById('categoriesResult').innerHTML = `<div class="error-message"><strong>Error:</strong> ${error.message}</div>`;
    }
}

async function updateCategory() {
    try {
        const id = parseRequiredInteger(document.getElementById('categoryUpdateId').value, 'Category ID');
        const payload = {
            name: document.getElementById('categoryUpdateName').value.trim(),
            description: document.getElementById('categoryUpdateDescription').value.trim()
        };

        if (!payload.name) {
            throw new Error('Category name is required');
        }

        const data = await apiRequest(`${API_BASE_URL}/api/categories/${id}`, 'PUT', payload);
        displayApiResult('categoriesResult', 'Category Updated', data);
    } catch (error) {
        document.getElementById('categoriesResult').innerHTML = `<div class="error-message"><strong>Error:</strong> ${error.message}</div>`;
    }
}

async function deleteCategory() {
    try {
        const id = parseRequiredInteger(document.getElementById('categoryDeleteId').value, 'Category ID');
        const data = await apiRequest(`${API_BASE_URL}/api/categories/${id}`, 'DELETE');
        displayApiResult('categoriesResult', 'Category Deleted', data);
    } catch (error) {
        document.getElementById('categoriesResult').innerHTML = `<div class="error-message"><strong>Error:</strong> ${error.message}</div>`;
    }
}

// ========== USER FUNCTIONS ==========

async function getAllUsers() {
    const data = await makeApiCall(`${API_BASE_URL}/api/users`, 'usersResult');
    if (data) {
        const columns = [
            { key: 'userId', label: 'ID' },
            { key: 'username', label: 'Username' },
            { key: 'email', label: 'Email' },
            { key: 'firstName', label: 'First Name' },
            { key: 'lastName', label: 'Last Name' },
            { key: 'createdAt', label: 'Created At' }
        ];
        displayTable(data, columns, 'usersResult');
    }
}

async function getUserById() {
    const id = document.getElementById('userId').value;
    if (!id) {
        alert('Please enter a User ID');
        return;
    }

    const data = await makeApiCall(`${API_BASE_URL}/api/users/${id}`, 'usersResult');
    if (data) {
        displaySingleItem(data, `User #${id}`, 'usersResult');
    }
}

async function getUserByEmail() {
    const email = document.getElementById('userEmail').value;
    if (!email) {
        alert('Please enter an email address');
        return;
    }

    const data = await makeApiCall(
        `${API_BASE_URL}/api/users/email/${encodeURIComponent(email)}`,
        'usersResult'
    );
    if (data) {
        displaySingleItem(data, `User: ${email}`, 'usersResult');
    }
}

async function createUser() {
    try {
        const payload = {
            email: document.getElementById('userCreateEmail').value.trim(),
            passwordHash: document.getElementById('userCreatePasswordHash').value.trim(),
            firstName: document.getElementById('userCreateFirstName').value.trim(),
            lastName: document.getElementById('userCreateLastName').value.trim(),
            phone: document.getElementById('userCreatePhone').value.trim(),
            role: document.getElementById('userCreateRole').value.trim(),
            isActive: document.getElementById('userCreateIsActive').value === 'true'
        };

        if (!payload.email || !payload.passwordHash || !payload.firstName || !payload.lastName || !payload.role) {
            throw new Error('Email, password, first name, last name, and role are required');
        }

        const data = await apiRequest(`${API_BASE_URL}/api/users`, 'POST', payload);
        displayApiResult('usersResult', 'User Created', data);
    } catch (error) {
        document.getElementById('usersResult').innerHTML = `<div class="error-message"><strong>Error:</strong> ${error.message}</div>`;
    }
}

async function updateUser() {
    try {
        const id = parseRequiredInteger(document.getElementById('userUpdateId').value, 'User ID');
        const payload = {
            email: document.getElementById('userUpdateEmail').value.trim(),
            passwordHash: document.getElementById('userUpdatePasswordHash').value.trim(),
            firstName: document.getElementById('userUpdateFirstName').value.trim(),
            lastName: document.getElementById('userUpdateLastName').value.trim(),
            phone: document.getElementById('userUpdatePhone').value.trim(),
            role: document.getElementById('userUpdateRole').value.trim(),
            isActive: document.getElementById('userUpdateIsActive').value === 'true'
        };

        if (!payload.email || !payload.passwordHash || !payload.firstName || !payload.lastName || !payload.role) {
            throw new Error('Email, password, first name, last name, and role are required');
        }

        const data = await apiRequest(`${API_BASE_URL}/api/users/${id}`, 'PUT', payload);
        displayApiResult('usersResult', 'User Updated', data);
    } catch (error) {
        document.getElementById('usersResult').innerHTML = `<div class="error-message"><strong>Error:</strong> ${error.message}</div>`;
    }
}

async function deleteUser() {
    try {
        const id = parseRequiredInteger(document.getElementById('userDeleteId').value, 'User ID');
        const data = await apiRequest(`${API_BASE_URL}/api/users/${id}`, 'DELETE');
        displayApiResult('usersResult', 'User Deleted', data);
    } catch (error) {
        document.getElementById('usersResult').innerHTML = `<div class="error-message"><strong>Error:</strong> ${error.message}</div>`;
    }
}

// Initialize on page load
window.addEventListener('load', () => {
    console.log('E-Commerce Web Client loaded');
    console.log('API Base URL:', API_BASE_URL);

    // Test connection on load
    testConnection();
});
