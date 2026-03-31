const express = require('express');
const cors = require('cors');
const path = require('path');

const app = express();

app.use(cors());
app.use(express.json());

// Serve frontend
app.use(express.static(path.join(__dirname, '../public')));

// ROOT ROUTE
app.get('/', (req, res) => {
    res.send("E-Commerce App Running 🚀");
});

// TEST ROUTE
app.get('/api/test', (req, res) => {
    res.json({ message: "Server is working" });
});

// CATEGORIES
app.get('/api/categories', (req, res) => {
    res.json([
        { categoryId: 1, name: "Electronics" },
        { categoryId: 2, name: "Clothing" },
        { categoryId: 3, name: "Books" }
    ]);
});

// PRODUCTS
app.get('/api/products', (req, res) => {
    res.json([
        { productId: 1, name: "Laptop", price: 1200, stock: 5, categoryId: 1 },
        { productId: 2, name: "T-Shirt", price: 25, stock: 20, categoryId: 2 },
        { productId: 3, name: "Book", price: 15, stock: 10, categoryId: 3 }
    ]);
});

const PORT = process.env.PORT || 5000;

app.listen(PORT, () => {
    console.log(`🚀 Server running at: http://localhost:${PORT}`);
});
