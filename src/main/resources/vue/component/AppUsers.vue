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
      v-on:fill="fillForm"
  ></app-people-list>

</div>
</template>

<script>
import AppPeopleList from "@/main/resources/vue/component/AppPeopleList";
import AppAlert from "@/main/resources/vue/component/AppAlert";

export default {
  components: {AppAlert, AppPeopleList},
  data() {
    return {
      people: [1, 2, 3],
      name: '',
      surname: '',
      email: '',
      alert: null,
      selectedPersonId: null
    };
  },
  methods: {
    fillForm(id) {
      // найти человека по id
      const person = this.people.find(person => person.id === id);
      // заполнить форму данными человека
      this.name = person.name;
      this.surname = person.surname;
      this.email = person.email;
      // установить выбранный id
      this.selectedPersonId = id;
    },

    async updatePerson(id) {
      // получить данные человека по id
      // const person = this.people.find(person => person.id === id);

      // отправить запрос PUT на адрес /people/{id} с телом, содержащим объект PersonDTO
      const response = await fetch(`http://localhost:8081/people/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          name: this.name,
          surname: this.surname,
          email: this.email
        })
      })

      // обработать ответ от сервера
      if (response.ok) {
        // обновить данные человека в массиве people
        this.people = this.people.map(person => person.id === id ? {
          ...person,
          name: this.name,
          surname: this.surname,
          email: this.email
        } : person);

        // показать сообщение об успехе
        this.alert = {
          type: 'primary',
          title: 'Успешно',
          text: 'Данные человека обновлены'
        }

        // очистить форму
        this.name = '';
        this.surname = '';
        this.email = '';

      } else {
        // показать сообщение об ошибке
        this.alert = {
          type: 'danger',
          title: 'Ошибка',
          text: 'Не удалось обновить данные человека'
        }
      }
    },

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
      if (this.selectedPersonId) {
        // вызвать метод updatePerson с id и данными из формы
        await this.updatePerson(this.selectedPersonId, {
          name: this.name,
          surname: this.surname,
          email: this.email
        });
        // сбросить выбранный id
        this.selectedPersonId = null;
      } else {
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

        // показать сообщение об успехе
        this.alert = {
          type: 'primary',
          title: 'Успешно',
          text: 'Человек успешно создан'
        }

        this.name = ''
        this.surname = ''
        this.email = ''
      }
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
  }
}
</script>

<style scoped>

</style>