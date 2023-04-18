<template>
  <div class="container">
    <form class="card" @submit.prevent="createPerson">
      <h2>Работа с базой данных</h2>

      <div class="form-control">
        <label for="name">Введите имя</label>
        <input type="text" id="name" v-model.trim="name">
      </div>

      <button class="btn primary" :disabled="name.length === 0">Создать человека</button>
    </form>

    <app-people-list
        :people="people"
        @load="loadPeople"
    ></app-people-list>
  </div>
</template>

<script>
import AppPeopleList from "@/main/resources/vue/component/AppPeopleList";

export default {
  data() {
    return {
      name: '',
      people: []
    }
  },
  methods: {
/*    async loadPeople() {
      let response = await fetch('http://localhost:8081/people', { mode: 'no-cors'})
      console.log(response)
    }*/
    loadPeople() {
      fetch('http://localhost:8081/people',{ mode: 'no-cors'})
          .then((response) => {
            return response.json();
          })
          .then((data) => {
            console.log(data);
          });
    }

/*    async createPerson() {
      const response = await fetch('http://', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          firstName: this.name
        })
      })

      const firebaseData = await response.json()

      console.log(firebaseData)
    }*/
  },
  components: {AppPeopleList}
}
</script>

<style>

</style>