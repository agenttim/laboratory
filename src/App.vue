<template>
  <div class="container">
    <div class="card">
      {{ info }}
    </div>
  </div>
</template>

<script>
//import axios from 'axios'
export default {
  data() {
    return {
      info: null
    };
  },
  methods: {
    loadPeople() {
      fetch('http://localhost:8081/people', {mode: "cors"})
          //.then(res => console.log(res))
          .then(res => res.json())
          .then(res => res.map(user => user.name))
          .then((data) => this.info = data)
          .then(userNames => console.log(userNames))
          .catch(error => console.log(error))

    }
  },
  mounted() {
    this.loadPeople()
  }
}
</script>

<style>

</style>