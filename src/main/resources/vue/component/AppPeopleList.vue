<template>
  <div v-if="people.length !== 0">
    <div class="card inline" v-for="person in people" :key="person.id">
      <h3>{{ person }}</h3>
    </div>
  </div>
  <div class="card center" v-else>
    <h4>Людей пока нет.</h4>
    <button class="btn" @click="$emit('load')">Загрузить список</button>
  </div>
</template>

<script>
export default {
  emits: ['load'],
  props: ['people']
}
</script>

<style scoped>

</style>