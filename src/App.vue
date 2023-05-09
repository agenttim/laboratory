<template>
  <div class="container">
    <app-alert :alert="alert" @close="alert = null"></app-alert>

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
import AppAlert from "@/main/resources/vue/component/AppAlert";

export default {
  data() {
    return {
      people: [1, 2, 3],
      name: '',
      surname: '',
      email: '',
      alert: null
    };
  },
  methods: {
    async removePerson(id) {
      await fetch(`http://localhost:8081/people/${id}`, {
        method: 'DELETE'
      })
      this.people = this.people.filter(person => person.id !== id)
      this.alert = {
        type: 'primary',
        title: 'Успешно',
        text: 'Пользователь удален'
      }
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
        if (!data) {
          throw new Error("Список пуст");
        }
        this.people = data;
              } catch (e) {
        this.alert = {
          type: 'danger',
          title: 'Ошибка',
          text: e.message
        }
      }
    }
  },
  mounted() {
    this.loadPeople()
  },
  components: {AppPeopleList, AppAlert}
}
</script>

<style>

</style>