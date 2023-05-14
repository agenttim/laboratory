<template>
  <div>
    <form @submit.prevent="submitForm">
      <label>Введите имя:</label>
      <input type="text" v-model="name" />
      <br />
      <label>Введите адрес:</label>
      <input type="text" v-model="address" />
      <br />
      <button type="submit">Отправить</button>
    </form>
  </div>
</template>

<script>
export default {
  name: 'AppPopupForm',
  data() {
    return {
      name: '',
      address: ''
    }
  },
  methods: {
    submitForm() {
      this.$emit('submit', { name: this.name, address: this.address });
    }
  }
}
</script>