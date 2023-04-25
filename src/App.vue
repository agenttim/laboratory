<template>
  <div class="container">
    <div class="card">
      {{ info }}
    </div>

    <form class="card" v-on:submit.prevent="createPerson">
      <h2>Работа с базой данных</h2>

      <div class="form-control">
        <label for="name">Введите имя</label>
        <input type="text" id="name" v-model.trim="name"/>
      </div>

      <button class="btn primary" v-bind:disabled="name.length === 0">Создать человека</button>
    </form>

    <app-people-list
        v-bind:people="people"
    ></app-people-list>
  </div>

</template>

<script>
import AppPeopleList from "@/main/resources/vue/component/AppPeopleList";

export default {
  data() {
    return {
      info: null,
      people: [1, 2, 3],
      name: ''
    };
  },
  methods: {
    createPerson() {

    },
    loadPeople() {
      fetch('http://localhost:8081/people', {mode: "cors"})
          .then(res => res.json())
          .then((data) => this.info = data)
          .then((data) => this.people = data)
          .then(userNames => console.log(userNames))
          .catch(error => console.log(error))
    }
  },
  mounted() {
    this.loadPeople()
  },
  components: {AppPeopleList}
}
</script>

<style>

</style>