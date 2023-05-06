<template>
  <div class="card" v-for="person in people" :key="person.id">
    <h3>{{ person.id + " " + person.name + " " + person.surname + " " + person.email }}</h3>
  </div>
</template>

<script>
export default {
  props: ['people']

}
</script>

<style scoped>

</style>