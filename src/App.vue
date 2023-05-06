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
        <label for="surname">Введите фамилию</label>
        <input type="text" id="surname" v-model.trim="surname"/>
        <label for="email">Введите электронную почту</label>
        <input type="text" id="email" v-model.trim="email"/>
      </div>

      <button class="btn primary" v-bind:disabled="name.length === 0">Создать человека</button>
    </form>

    <app-people-list
        v-bind:people="people"
        v-on:remove="removePerson"
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
      name: '',
      surname: '',
      email: ''
    };
  },
  methods: {
    removePerson(id) {

    },
    async createPerson() {
      const response = await fetch('http://localhost:8081/people', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          name: this.name,
          surname: this.surname,
          email: this.email
        })
      })

      const DBData = await response.json()

      this.people.push({
        name: this.name,
        surname: this.surname,
        email: this.email,
        id: DBData
      })

      this.name = ''
      this.surname = ''
      this.email = ''
    },
    async loadPeople() {
      try {
        let res = await fetch('http://localhost:8081/people', {mode: "cors"});
        let data = await res.json();
        this.info = data;
        this.people = data;
        let names = data.map(user => user.name); // создаем массив из имен пользователей
        console.log(names); // выводим массив в консоль
      } catch (error) {
        console.log(error);
      }
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