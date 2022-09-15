<template>
  <div class="h-100">
    <b-form class="mx-auto my-auto w-25" @submit="onSubmit">
      <b-form-group id="input-group-1" label="Имя пользователя" label-for="input-1">
        <b-form-input
            id="input-1"
            placeholder="user"
            required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-2" label="Пароль" label-for="input-2">
        <b-form-input
            id="input-2"
            type="password"
            placeholder="******"
            required
        ></b-form-input>
      </b-form-group>

      <b-button type="submit" variant="primary">Войти</b-button>

      <div>
          <b-link href="/forgot_password">Забыли пароль?</b-link>
          <b-link href="/registration">Зарегистрироваться</b-link>
      </div>
    </b-form>
  </div>
</template>

<script>
export default {
  methods: {
    onSubmit(event) {

    },
  }
}
</script>

<style scoped>

</style>