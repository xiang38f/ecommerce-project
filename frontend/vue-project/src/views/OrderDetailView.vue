<template>
  <div class="container">
    <h1>訂單詳情</h1>
    <div v-if="loading">載入中...</div>
    <div v-if="error" class="message error">{{ error }}</div>

    <div v-if="order" class="card">
      <div class="card-header">
        訂單編號: {{ order.orderId }}
      </div>
      <div class="card-body">
        <div class="order-info">
          <p><strong>會員ID:</strong> {{ order.memberId }}</p>
          <p><strong>總金額:</strong> ${{ order.totalPrice.toLocaleString() }}</p>
          <p><strong>付款狀態:</strong> {{ order.payStatus === 1 ? '已付款' : '未付款' }}</p>
        </div>
        <hr>
        <h3>購買品項</h3>
        <table class="product-table">
          <thead>
            <tr>
              <th>商品名稱</th>
              <th>單價</th>
              <th>數量</th>
              <th>小計</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in order.items" :key="item.productId">
              <td data-label="商品名稱">{{ item.productName }}</td>
              <td data-label="單價">${{ item.standPrice.toLocaleString() }}</td>
              <td data-label="數量">{{ item.quantity }}</td>
              <td data-label="小計">${{ item.itemPrice.toLocaleString() }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import apiService from '@/services/apiService';

const route = useRoute();
const order = ref(null); // 初始值為 null
const loading = ref(true);
const error = ref(null);

onMounted(async () => {
  const orderId = route.params.orderId;
  try {
    const response = await apiService.getOrderDetail(orderId);
    order.value = response.data; // API 成功後，order 才會有值
  } catch (err) {
    error.value = '無法載入訂單詳情。';
    console.error(err); // 在 console 印出詳細錯誤，方便除錯
  } finally {
    loading.value = false;
  }
});
</script>

<style scoped>
.order-info {
  line-height: 1.8;
}
hr {
  margin: 1.5rem 0;
  border: 0;
  border-top: 1px solid var(--border-color);
}
.product-table {
  width: 100%;
  border-collapse: collapse;
}
.product-table th, .product-table td {
  padding: 0.75rem 1rem;
  text-align: left;
  border-bottom: 1px solid var(--border-color);
}
</style>
