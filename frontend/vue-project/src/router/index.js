// src/router/index.js

import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ProductAdminView from '../views/ProductAdminView.vue'
import CreateOrderView from '../views/CreateOrderView.vue'
import OrderListView from '../views/OrderListView.vue'
import OrderDetailView from '../views/OrderDetailView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/products',
      name: 'products-list',
      component: CreateOrderView
    },
    {
      path: '/admin/products',
      name: 'product-admin',
      component: ProductAdminView
    },
    {
      path: '/orders',
      name: 'order-list',
      component: OrderListView
    },
    {
      path: '/orders/details/:orderId',
      name: 'order-detail',
      component: OrderDetailView
    }
  ]
})

export default router
