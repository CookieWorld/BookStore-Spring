<template>
  <b-navbar toggleable="lg" type="dark" variant="dark">
    <b-navbar-brand href="/">Book shop</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-brand href="/">Книги</b-navbar-brand>
      <b-navbar-brand href="#">Корзина</b-navbar-brand>

      <b-navbar-nav class="ml-auto">
        <b-nav-form>
          <b-form-input size="sm" class="mr-sm-2" placeholder="Search"></b-form-input>
          <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
        </b-nav-form>

        <b-navbar-nav class="ml-2 mr-2">
          <a href="#" v-if="profile">User</a>
          <b-button  href="/login" v-else>Log in</b-button>
          <b-button href="#" v-if="profile">Log out</b-button>
        </b-navbar-nav>

      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</template>

<script>
  export default {
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