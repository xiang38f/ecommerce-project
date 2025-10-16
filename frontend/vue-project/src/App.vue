<script setup>
import { RouterView } from 'vue-router'
</script>

<template>
  <header>
    <nav>
      </nav>
  </header>

  <main>
    <RouterView />
  </main>
</template>

<style scoped>
main {
  padding: 1rem;
}
nav {
  padding: 1rem;
  border-bottom: 1px solid #ddd;
}
nav a {
  margin-right: 1rem;
}
</style>
