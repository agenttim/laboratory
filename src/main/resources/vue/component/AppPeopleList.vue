<template>
  <div class="card inline" v-for="person in people" :key="person.id">
    <h3>{{ person.id + " " + person.name + " " + person.surname + " " + person.email }}</h3>
    <div>
      <button class="btn primary" v-on:click="$emit('fill', person.id)">Обновить</button>
      <button class="btn danger" v-on:click="$emit('remove', person.id)">Удалить</button>
    </div>
  </div>
</template>

<script>
export default {
  props: ['people'],
  emits: ['remove', 'fill']
}
</script>

<style scoped>
  .inline {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

</style>