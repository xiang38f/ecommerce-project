<template>
  <div class="container">
    <h1>線上購物</h1>
    <div class="card">
      <h2 class="card-header">商品列表</h2>
      <div class="card-body">
        <table v-if="products.length > 0" class="product-table">
          <thead>
            <tr>
              <th>商品名稱</th>
              <th>售價</th>
              <th>庫存</th>
              <th class="quantity-col">購買數量</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="product in products" :key="product.productId">
              <td data-label="商品名稱">{{ product.productName }}</td>
              <td data-label="售價">${{ product.price.toLocaleString() }}</td>
              <td data-label="庫存">{{ product.quantity }}</td>
              <td data-label="購買數量">
                <input
                  type="number"
                  min="0"
                  :max="product.quantity"
                  v-model.number="cart[product.productId]"
                  placeholder="0"
                />
              </td>
            </tr>
          </tbody>
        </table>
        <p v-else>目前沒有可購買的商品。</p>
      </div>
    </div>

    <div class="card order-summary" v-if="orderTotal > 0">
      <h2 class="card-header">訂單總覽</h2>
      <div class="card-body">
        <ul class="order-items">
          <li v-for="item in cartItems" :key="item.productId">
            <span>{{ item.productName }} x {{ item.purchaseQuantity }}</span>
            <span class="item-subtotal">${{ item.subtotal.toLocaleString() }}</span>
          </li>
        </ul>
        <hr />

        <div class="customer-info">
            <div class="form-group">
                <label for="customerName">顧客姓名</label>
                <input id="customerName" type="text" v-model.trim="customerName" placeholder="請輸入姓名" />
            </div>
            <div class="form-group">
                <label for="memberId">會員ID</label>
                <input id="memberId" type="text" v-model.trim="memberId" placeholder="請輸入會員ID" />
            </div>
        </div>

        <p class="total">
          <span>總金額</span>
          <span>${{ orderTotal.toLocaleString() }}</span>
        </p>

        <button @click="submitOrder" class="submit-btn">確認並建立訂單</button>
        <p v-if="orderMessage"
           :class="['message', orderMessage.includes('成功') ? 'success' : 'error']">
           {{ orderMessage }}
        </p>
      </div>
    </div>
    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import apiService from '@/services/apiService';

// ... (products, loading, error, cart 維持不變)
const products = ref([]);
const loading = ref(true);
const error = ref(null);
const cart = ref({});
const orderMessage = ref('');

// 2. 新增 ref 來綁定輸入框的資料
const customerName = ref('');
const memberId = ref('');

// onMounted 和 cartItems, orderTotal 維持不變
onMounted(async () => {
  try {
    const response = await apiService.getProducts();
    products.value = response.data;
  } catch (err) {
    error.value = '無法載入商品列表，請檢查後端服務或網路連線。';
  } finally {
    loading.value = false;
  }
});

const cartItems = computed(() => {
  return products.value
    .map(product => {
      const purchaseQuantity = cart.value[product.productId] || 0;
      return {
        ...product,
        purchaseQuantity,
        subtotal: product.price * purchaseQuantity
      };
    })
    .filter(item => item.purchaseQuantity > 0);
});

const orderTotal = computed(() => {
  return cartItems.value.reduce((total, item) => total + item.subtotal, 0);
});

// 3. 修改 submitOrder 函式以符合後端 API 格式
const submitOrder = async () => {
  orderMessage.value = ''; // 先清空訊息
  // 簡易驗證
  if (!customerName.value || !memberId.value) {
    orderMessage.value = '請填寫顧客姓名和會員ID。';
    return;
  }

  // 依照後端 API 規格，建立 request body
  const orderData = {
    customerName: customerName.value,
    memberId: memberId.value,
    totalPrice: orderTotal.value, // 後端需要總價
    items: cartItems.value.map(item => ({
      productId: item.productId,
      quantity: item.purchaseQuantity,
      standPrice: item.price, // 後端需要單價
      itemPrice: item.subtotal // 後端需要該品項總價
    }))
  };

  try {
    const response = await apiService.createOrder(orderData);
    orderMessage.value = `訂單建立成功！訂單編號: ${response.data.orderId}`;
    // 成功後清空購物車和輸入欄位
    cart.value = {};
    customerName.value = '';
    memberId.value = '';
  } catch (err) {
    orderMessage.value = '訂單建立失敗：' + (err.response?.data?.message || '庫存不足或系統錯誤');
    console.error(err);
  }
};
</script>

<style scoped>
/* ... (大部分樣式不變) ... */
.card {
  background: var(--surface-color);
  border: 1px solid var(--border-color);
  border-radius: 8px;
  margin-bottom: 2rem;
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

.product-table {
  width: 100%;
  border-collapse: collapse;
}
.product-table th, .product-table td {
  padding: 0.75rem 1rem;
  text-align: left;
  border-bottom: 1px solid var(--border-color);
}
.product-table th {
  font-weight: bold;
}
.product-table tbody tr:last-child td {
  border-bottom: none;
}
.product-table input[type="number"] {
  width: 80px;
  text-align: center;
  padding: 0.5rem;
}
.quantity-col {
  width: 120px;
}

.order-summary hr {
  border: 0;
  border-top: 1px solid var(--border-color);
  margin: 1.5rem 0;
}
.order-items li {
  display: flex;
  justify-content: space-between;
  padding: 0.5rem 0;
  list-style: none;
}
.order-items {
  padding-left: 0;
}
.item-subtotal {
  font-weight: 500;
}
.total {
  display: flex;
  justify-content: space-between;
  font-size: 1.5rem;
  font-weight: bold;
  color: var(--esun-green);
  margin-top: 1.5rem; /* 與上方區塊拉開距離 */
}
.submit-btn {
  width: 100%;
  margin-top: 1rem;
}
/* 新增的樣式 */
.customer-info {
  margin-top: 1.5rem;
}
.form-group {
  margin-bottom: 1rem;
}
.form-group label {
  display: block;
  font-weight: 500;
  margin-bottom: 0.5rem;
}
</style>
