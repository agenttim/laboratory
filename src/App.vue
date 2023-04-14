<template>
  <div class="container">
    <form class="card">
      <h2>Работа с базой данных</h2>

      <div class="form-control">
        <label for="name">Введите имя</label>
        <input type="text" id="name" v-model.trim="name">
      </div>

      <button class="btn primary">Создать человека</button>
    </form>
  </div>
</template>

<script>
export default {

}
</script>

<style>

</style>