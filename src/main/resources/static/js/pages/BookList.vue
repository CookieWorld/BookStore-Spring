<template>
  <div>
    <book-row :books="books" v-for="book in books"
              :key="book.id"
              :book="book"/>
  </div>
</template>

<script>
import BookRow from "../components/BookRow.vue";
export default {
  components:{
    BookRow
  },
  props: ['books']
  }
</script>

<style>

</style>