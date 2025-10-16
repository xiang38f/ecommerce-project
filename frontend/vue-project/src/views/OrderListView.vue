<template>
  <div class="container">
    <h1>訂單紀錄</h1>
    <div v-if="loading">載入中...</div>
    <div v-if="error" class="message error">{{ error }}</div>
    <div class="card">
      <div class="card-body">
        <table class="order-table" v-if="orders.length > 0">
          <thead>
            <tr>
              <th>訂單編號</th>
              <th>會員ID</th>
              <th>訂單金額</th>
              <th>付款狀態</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="order in orders" :key="order.orderId">
              <td data-label="訂單編號">{{ order.orderId }}</td>
              <td data-label="會員ID">{{ order.memberId }}</td>
              <td data-label="訂單金額">${{ order.totalPrice.toLocaleString() }}</td>
              <td data-label="付款狀態">{{ order.payStatus === 1 ? '已付款' : '未付款' }}</td>
              <td data-label="操作">
                <RouterLink :to="`/orders/details/${order.orderId}`" class="btn-detail">查看詳情</RouterLink>
              </td>
            </tr>
          </tbody>
        </table>
        <p v-else>目前沒有任何訂單紀錄。</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { RouterLink } from 'vue-router';
import apiService from '@/services/apiService';

const orders = ref([]);
const loading = ref(true);
const error = ref(null);

onMounted(async () => {
  try {
    const response = await apiService.getOrders();
    orders.value = response.data;
  } catch (err) {
    error.value = '無法載入訂單列表。';
  } finally {
    loading.value = false;
  }
});
</script>

<style scoped>
.btn-detail {
  display: inline-block;
  padding: 0.4rem 0.8rem;
  background-color: var(--esun-green);
  color: white;
  text-decoration: none;
  border-radius: 4px;
  font-size: 0.9rem;
}
.btn-detail:hover {
  background-color: var(--primary-accent);
}
</style>
