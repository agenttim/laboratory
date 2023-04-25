<template>
  <div class="card" v-for="person in people" :key="person.id">
    {{ person.id + " " + person.name + " " + person.surname + " " + person.dateOfBirth }}
  </div>
</template>

<script>
export default {
  props: ['people']

}
</script>

<style scoped>

</style>