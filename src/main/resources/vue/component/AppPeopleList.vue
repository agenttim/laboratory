<template>
  <div class="card inline" v-for="person in people" :key="person.id">
    <h3>{{ person.id + " " + person.name + " " + person.surname + " " + person.email }}</h3>
    <button class="btn danger" v-on:click="$emit('remove', person.id)">Удалить</button>
  </div>
</template>

<script>
export default {
  props: ['people'],
  emits: ['remove']
}
</script>

<style scoped>
  .inline {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

</style>