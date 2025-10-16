<template>
  <div class="container">
    <h1>後台管理</h1>
    <div class="card">
      <h2 class="card-header">新增商品</h2>
      <div class="card-body">
        <form @submit.prevent="submitProduct">
          <div class="form-group">
            <label for="name">商品名稱</label>
            <input id="name" v-model="product.productName" type="text" required />
          </div>
          <div class="form-group">
            <label for="price">商品售價</label>
            <input id="price" v-model.number="product.price" type="number" step="0.01" min="0" required />
          </div>
          <div class="form-group">
            <label for="quantity">庫存數量</label>
            <input id="quantity" v-model.number="product.quantity" type="number" min="0" required />
          </div>
          <button type="submit" class="submit-btn">確認新增</button>
        </form>
        <p v-if="message"
          :class="['message', message.includes('成功') ? 'success' : 'error']">
          {{ message }}
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import apiService from '@/services/apiService';

const product = ref({
  productName: '',
  price: null,
  quantity: null,
});

// 用來顯示成功或失敗的訊息
const message = ref('');

// 提交表單時觸發的函式
const submitProduct = async () => {
  try {
    const newProductName = product.value.productName;
    await apiService.createProduct(product.value);
    message.value = `商品 "${newProductName}" 新增成功！`;
    product.value = { productName: '', price: null, quantity: null };

  } catch (error) {
    // 如果 API 呼叫失敗，顯示錯誤訊息
    message.value = '新增失敗：' + (error.response?.data?.message || '資料有誤');
    console.error(error);
  }
};
</script>

<style scoped>
/* 頁面專屬的樣式 (維持不變) */
.card {
  background: var(--surface-color);
  border: 1px solid var(--border-color);
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0,0,0,0.05);
  overflow: hidden;
}
.card-header {
  padding: 1rem 1.5rem;
  background-color: #f8f9fa;
  border-bottom: 1px solid var(--border-color);
  margin: 0;
}
.card-body {
  padding: 1.5rem;
}
.form-group {
  margin-bottom: 1.5rem;
}
.form-group label {
  display: block;
  font-weight: 500;
  margin-bottom: 0.5rem;
}
.submit-btn {
  width: 100%;
}
</style>
