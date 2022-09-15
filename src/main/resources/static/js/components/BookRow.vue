<template>
  <div>
    <b-card :key="book.id"
            :title="book.name"
            :img-src="itemImage"
            img-top
            img-height="320px"
            style="max-width: 20rem;"
            class="mb-2 mt-2 mr-2 ml-2"
    >
      <b-card-text>
        {{ book.authorB }}
      </b-card-text>

      <b-button href="#" variant="primary">Добавить в корзину</b-button>
    </b-card>
  </div>
</template>

<script>
export default {
  props: ['book'],
  data(){
    return {
      image: this.book.filename
    }
  },
  computed: {
    itemImage() {
      if (!this.image) {
        return
      }
      const fileName = this.image
      return require("../../../img/" + fileName).default
    }
  }
}
</script>

<style scoped>

</style>