<template>
  <div class="container">
    <app-map-polygon></app-map-polygon>
<!--    <app-map></app-map>-->
    <button id="person-button" class="btn" @click="toggleUsers">Управление пользователями</button>
    <app-users v-if="showUsers"></app-users>
    <app-point-object></app-point-object>
  </div>
</template>

<script>
/*import AppMap from "@/main/resources/vue/component/AppMap";*/
import AppMapPolygon from "@/main/resources/vue/component/AppMapPolygon";
import AppUsers from "@/main/resources/vue/component/AppUsers";
import AppPointObject from "@/main/resources/vue/component/AppPointObject";

export default {
  components: {/*AppMap, */AppUsers, AppPointObject, AppMapPolygon},
  data() {
    return {
      showUsers: false
    }
  },
  methods: {
    toggleUsers() {
      this.showUsers = !this.showUsers;
    }
  }
}
</script>

<style>
#person-button {
  position: absolute;
  top: 50px;
  right: 30px;
}
</style>