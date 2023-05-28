<template>
  <div>
    <h2>Point Object</h2>




  </div>

</template>

<script>

export default {
  data() {
    return {
      pointObject: {
        longitude: '',
        latitude: '',
        name: '',
        address: ''
      },
      pointObjects: []
    }
  },
  methods: {
    async createPointObject() {
      const response = await fetch('http://localhost:8081/point-objects', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.pointObject)
      })

      if (response.ok) {
        const data = await response.json()
        this.pointObjects.push(data)
        this.pointObject = {
          longitude: '',
          latitude: '',
          name: '',
          address: ''
        }
      } else {
        console.error('Ошибка создания объекта')
      }
    },

    async loadPointObjects() {
      try {
        let res = await fetch('http://localhost:8081/point-object', {mode: "cors"});
        let data = await res.json();
        if (!data) {
          throw new Error("Список пуст");
        }
        this.pointObjects = data;
      } catch (e) {
        console.error(e.message);
      }
    }
  },
  mounted() {
    this.loadPointObjects()
  }
}

</script>

<style scoped>

</style>

