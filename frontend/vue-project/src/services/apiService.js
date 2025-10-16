// src/services/apiService.js

import axios from 'axios';

// 設定後端 API 的基礎 URL
// 請確保這個位址和您 Spring Boot 後端的位址與埠號一致
const apiClient = axios.create({
  baseURL: 'http://localhost:8080/api', // 如果您的後端不是 8080 port，請修改這裡
  headers: {
    'Content-Type': 'application/json'
  }
});

// 統一管理所有對後端的 API 請求
export default {
  // 獲取所有庫存 > 0 的商品
  getProducts() {
    return apiClient.get('/product/available');
  },
  // 新增商品 (給後台管理頁面用)
  createProduct(productData) {
    return apiClient.post('/product', productData);
  },
  // 建立訂單
  createOrder(orderData) {
    return apiClient.post('/orders', orderData);
  },
  getOrders() {
        return apiClient.get('/orders');
  },
    // 根據 ID 獲取訂單詳情
  getOrderDetail(orderId) {
        return apiClient.get(`/orders/details/${orderId}`);
    }
};
