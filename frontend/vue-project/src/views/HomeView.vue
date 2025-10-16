<template>
  <div class="container home-container">
    <div class="welcome-card">
      <img src="@/assets/logo.svg" alt="Vue Logo" class="logo">
      <h1>電商購物中心</h1>
      <p>歡迎使用本系統，請選擇您要執行的操作：</p>
      <nav class="navigation">
        <RouterLink to="/products" class="nav-link">
          <div class="link-content">
            <h2>查詢商品與訂購</h2>
            <p>瀏覽所有可購買的商品，並建立您的訂單。</p>
          </div>
        </RouterLink>

        <RouterLink to="/orders" class="nav-link">
          <div class="link-content">
            <h2>查詢訂單紀錄</h2>
            <p>查看您所有的歷史訂單與詳細資訊。</p>
          </div>
        </RouterLink>
        <RouterLink to="/admin/products" class="nav-link">
          <div class="link-content">
            <h2>後台管理</h2>
            <p>管理員專用，新增商品至系統資料庫。</p>
          </div>
        </RouterLink>
      </nav>
    </div>
  </div>
</template>

<script setup>
import { RouterLink } from 'vue-router';
</script>

<style scoped>
.home-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 80vh;
  text-align: center;
}

.welcome-card {
  background: var(--surface-color);
  padding: 3rem 2.5rem;
  border-radius: 12px;
  box-shadow: 0 8px 16px rgba(0,0,0,0.1);
  max-width: 600px;
  width: 100%;
}

.logo {
  height: 80px;
  margin-bottom: 1rem;
}

h1 {
  font-size: 2.5rem;
  margin-bottom: 0.5rem;
}

.welcome-card > p {
  font-size: 1.1rem;
  color: #6c757d;
  margin-bottom: 2.5rem;
}

.navigation {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.nav-link {
  display: block;
  background-color: #f8f9fa;
  border: 1px solid var(--border-color);
  border-radius: 8px;
  padding: 1.5rem;
  text-decoration: none;
  color: var(--text-color);
  transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out;
}

.nav-link:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0,0,0,0.1);
  border-color: var(--esun-green);
}

.link-content h2 {
  margin: 0 0 0.5rem 0;
  color: var(--esun-green);
}

.link-content p {
  margin: 0;
  color: #495057;
}
</style>
