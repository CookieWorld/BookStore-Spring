<template>
  <div>
    <navbar/>
    <router-view :books="books"/>
  </div>
</template>

<script>
import Navbar from '../components/Navbar.vue'


export default {
  components: {
    Navbar
  },
  data() {
    return {
      books: frontData.books,
      profile: frontData.profile
    }
  }
}
</script>

<style>

</style>